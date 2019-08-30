package com.weilai.query;


import com.weilai.model.DataGridFilter;

import org.hibernate.query.criteria.internal.path.PluralAttributePath;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.metamodel.EntityType;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;



public class QueryTypeSpecification<T> implements Specification<T> {

    private final DataGridFilter dataGridReq;

    public QueryTypeSpecification(DataGridFilter dataGridReq) {
        this.dataGridReq = dataGridReq;
    }

    @Override
    public Predicate toPredicate(Root<T> entityRoot, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        if (dataGridReq == null) {
            return null;
        }
        if (StringUtils.isEmpty(dataGridReq.getOrderDirection()) || StringUtils.isEmpty(dataGridReq.getOrderField())) {
            // 没有排序方式 默认按id倒序
            Class<?> idClass = entityRoot.getModel().getIdType().getJavaType();
            Path<?> path = entityRoot.get(entityRoot.getModel().getId(idClass));
            criteriaQuery.orderBy(criteriaBuilder.desc(path));
        }
        try {
            List<Field> fields = new ArrayList<Field>();
            List<Predicate> andList = new ArrayList<Predicate>();
            Map<String, List<Predicate>> orMap = new LinkedHashMap<String, List<Predicate>>();
            fields.addAll(Arrays.asList(dataGridReq.getClass().getDeclaredFields()));
            fields.addAll(Arrays.asList(dataGridReq.getClass().getSuperclass().getDeclaredFields()));
            for (Field field : fields) {
                QueryType queryType = (QueryType) field.getAnnotation(QueryType.class);
                if (queryType == null) {
                    continue;
                }
                field.setAccessible(true);
                Object value = field.get(dataGridReq);
                field.setAccessible(false);
                if (Restrictions.OR.equals(queryType.link())
                        || Restrictions.NOTBETWEEN_NOT_EQ_MIN.equals(queryType.value())
                        || Restrictions.NOTBETWEEN_NOT_EQ_MAX.equals(queryType.value())
                        || Restrictions.NOTBETWEEN_EQ_MIN.equals(queryType.value())
                        || Restrictions.NOTBETWEEN_EQ_MAX.equals(queryType.value())) {// 判断是否为or操作
                    // 或者not
                    // between操作
                    String key = null;
                    if (!StringUtils.isEmpty(queryType.code())) {
                        key = queryType.code();
                    } else {
                        key = queryType.column();
                    }
                    List<Predicate> orList = orMap.get(key);
                    if (orList == null) {
                        orList = new ArrayList<Predicate>();
                        orMap.put(key, orList);
                    }
                    reflectField(criteriaBuilder, entityRoot, orList, queryType, value);
                } else {
                    reflectField(criteriaBuilder, entityRoot, andList, queryType, value);
                }
            }
            if (!orMap.isEmpty()) {
                for (Map.Entry<String, List<Predicate>> entry : orMap.entrySet()) {
                    if (entry.getValue() == null || entry.getValue().isEmpty()) {
                        continue;
                    }
                    if (entry.getValue().size() == 1) {// 只有一个参数合并到当前Searchable中
                        andList.add(entry.getValue().get(0));
                    } else {
                        Predicate[] predicates = entry.getValue().toArray(new Predicate[entry.getValue().size()]);
                        andList.add(criteriaBuilder.or(predicates));
                    }
                }
            }
            Predicate predicate = null;
            if (!andList.isEmpty()) {
                if (andList.size() == 1) {
                    predicate = andList.get(0);
                } else {
                    predicate = criteriaBuilder.and(andList.toArray(new Predicate[andList.size()]));
                }
            }
            return predicate;
        } catch (Exception e) {
            throw new RuntimeException("QueryType解析出错.", e);
        }
    }

    @SuppressWarnings("unchecked")
    private void reflectField(CriteriaBuilder criteriaBuilder, Root<T> entityRoot, List<Predicate> predicates,
                              QueryType queryType, Object value) {
        // Path path =
        // entityRoot.get(entityRoot.getModel().getSingularAttribute(queryType.column()));
        // Path path = getPath(queryType.column(), entityRoot);
        Predicate predicate = null;
        if (DefaultValue.isDefault(value, queryType.defaultValue())) {
            switch (queryType.value()) {
                case ISNULL:
                    predicate = criteriaBuilder.isNull(getPath(queryType.column(), entityRoot));
                    break;
                case ISNOTNULL:
                    predicate = criteriaBuilder.isNotNull(getPath(queryType.column(), entityRoot));
                    break;
                case ISEMPTY:
                    if (PluralAttributePath.class.isInstance(getPath(queryType.column(), entityRoot))) {
                        predicate = criteriaBuilder.isEmpty(getPath(queryType.column(), entityRoot));
                    } else {
                        predicate = criteriaBuilder.or(criteriaBuilder.isNull(getPath(queryType.column(), entityRoot)),
                                criteriaBuilder.equal(getPath(queryType.column(), entityRoot), ""));
                    }
                    break;
                case ISNOTEMPTY:
                    if (PluralAttributePath.class.isInstance(getPath(queryType.column(), entityRoot))) {
                        predicate = criteriaBuilder.isNotEmpty(getPath(queryType.column(), entityRoot));
                    } else {
                        predicate = criteriaBuilder.and(criteriaBuilder.isNotEmpty(getPath(queryType.column(), entityRoot)),
                                criteriaBuilder.notEqual(getPath(queryType.column(), entityRoot), ""));
                    }
                    break;
                default:
                    break;
            }
        } else {
            switch (queryType.value()) {
                case EQ:
                    predicate = criteriaBuilder.equal(getPath(queryType.column(), entityRoot), value);
                    break;
                case NEQ:
                    predicate = criteriaBuilder.notEqual(getPath(queryType.column(), entityRoot), value);
                case GT:
                case BETWEEN_NOT_EQ_MIN:
                case NOTBETWEEN_NOT_EQ_MAX:
                    if (Number.class.isAssignableFrom(value.getClass())) {
                        predicate = criteriaBuilder.gt((Path<Number>) getPath(queryType.column(), entityRoot),
                                Number.class.cast(value));
                    } else if (Date.class.isAssignableFrom(value.getClass())) {
                        predicate = criteriaBuilder.greaterThan(getPath(queryType.column(), entityRoot), (Date) value);
                    } else {
                        predicate = criteriaBuilder.greaterThan(getPath(queryType.column(), entityRoot), value.toString());
                    }
                    break;
                case GE:
                case BETWEEN_EQ_MIN:
                case NOTBETWEEN_EQ_MAX:
                    if (Number.class.isAssignableFrom(value.getClass())) {
                        predicate = criteriaBuilder.ge((Path<Number>) getPath(queryType.column(), entityRoot),
                                Number.class.cast(value));
                    } else if (Date.class.isAssignableFrom(value.getClass())) {
                        predicate = criteriaBuilder.greaterThanOrEqualTo(getPath(queryType.column(), entityRoot),
                                (Date) value);
                    } else {
                        predicate = criteriaBuilder.greaterThanOrEqualTo(getPath(queryType.column(), entityRoot),
                                value.toString());
                    }
                    break;
                case LT:
                case BETWEEN_NOT_EQ_MAX:
                case NOTBETWEEN_NOT_EQ_MIN:
                    if (Number.class.isAssignableFrom(value.getClass())) {
                        predicate = criteriaBuilder.lt((Path<Number>) getPath(queryType.column(), entityRoot),
                                Number.class.cast(value));
                    } else if (Date.class.isAssignableFrom(value.getClass())) {
                        predicate = criteriaBuilder.lessThan(getPath(queryType.column(), entityRoot), (Date) value);
                    } else {
                        predicate = criteriaBuilder.lessThan(getPath(queryType.column(), entityRoot), value.toString());
                    }
                    break;
                case LE:
                case BETWEEN_EQ_MAX:
                case NOTBETWEEN_EQ_MIN:
                    if (Number.class.isAssignableFrom(value.getClass())) {
                        predicate = criteriaBuilder.le((Path<Number>) getPath(queryType.column(), entityRoot),
                                Number.class.cast(value));
                    } else if (Date.class.isAssignableFrom(value.getClass())) {
                        predicate = criteriaBuilder.lessThanOrEqualTo(getPath(queryType.column(), entityRoot),
                                (Date) value);
                    } else {
                        predicate = criteriaBuilder.lessThanOrEqualTo(getPath(queryType.column(), entityRoot),
                                value.toString());
                    }
                    break;
                case IN:
                    if (Collection.class.isInstance(value)) {
                        Collection<?> collection = (Collection<?>) value;
                        In<Object> in = criteriaBuilder.in(getPath(queryType.column(), entityRoot));
                        for (Object object : collection) {
                            in.value(object);
                        }
                        predicate = in;
                    } else if (value.getClass().isArray()) {
                        In<Object> in = criteriaBuilder.in(getPath(queryType.column(), entityRoot));
                        for (int i = 0; i < Array.getLength(value); i++) {
                            in.value(Array.get(value, i));
                        }
                        predicate = in;
                    }
                    break;
                case NOTIN:
                    if (Collection.class.isInstance(value)) {
                        Collection<?> collection = (Collection<?>) value;
                        In<Object> in = criteriaBuilder.in(getPath(queryType.column(), entityRoot));
                        for (Object object : collection) {
                            in.value(object);
                        }
                        predicate = in;
                    } else if (value.getClass().isArray()) {
                        In<Object> in = criteriaBuilder.in(getPath(queryType.column(), entityRoot));
                        for (int i = 0; i < Array.getLength(value); i++) {
                            in.value(Array.get(value, i));
                        }
                        predicate = in;
                    } else {
                        throw new RuntimeException("'in' Behind the query must be an array or a list");
                    }
                    if (predicate != null) {
                        predicate.not();
                    }
                    break;
                case LIKE:
                    if (MatchMode.START.equals(queryType.matchMode())) {
                        predicate = criteriaBuilder.like(getPath(queryType.column(), entityRoot), value.toString() + "%");
                    } else if (MatchMode.ANYWHERE.equals(queryType.matchMode())) {
                        predicate = criteriaBuilder.like(getPath(queryType.column(), entityRoot),
                                "%" + value.toString() + "%");
                    } else if (MatchMode.END.equals(queryType.matchMode())) {
                        predicate = criteriaBuilder.like(getPath(queryType.column(), entityRoot), "%" + value.toString());
                    } else if (MatchMode.EXACT.equals(queryType.matchMode())) {
                        predicate = criteriaBuilder.like(getPath(queryType.column(), entityRoot), value.toString());
                    } else {
                        predicate = criteriaBuilder.equal(getPath(queryType.column(), entityRoot), value);
                    }
                    break;
                default:
                    break;
            }
        }
        if (predicate != null) {
            predicates.add(predicate);
        }
    }

    @SuppressWarnings("rawtypes")
    public Path getPath(String columnName, Root<T> entityRoot) {
        EntityType<T> entityType = entityRoot.getModel();
        if (columnName.indexOf(".") > 0) { // 带Join查询
            String[] columns = columnName.split("\\.");
            From path = entityRoot;
            for (int i = 0; i < columns.length - 1; i++) {// 不包含最后一位
                path = path.join(columns[i]);
            }
            return path.get(columns[columns.length - 1]);
        } else {
            return entityRoot.get(entityType.getSingularAttribute(columnName));
        }
    }
}

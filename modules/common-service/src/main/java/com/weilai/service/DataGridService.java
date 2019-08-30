package com.weilai.service;



import com.weilai.model.DataGridFilter;
import com.weilai.model.DataGridResp;
import com.weilai.query.QueryTypeSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.util.StringUtils;

/**
 *
 */
public abstract class DataGridService<T> {

	public DataGridResp<T> dataGrid(DataGridFilter dataGridFilter) {
		Specification<T> specification = new QueryTypeSpecification<T>(dataGridFilter);
		Pageable pageable = parsePageQuery(dataGridFilter);
		Page<T> currentPage = getCurrentRepository().findAll(specification, pageable);

		DataGridResp<T> dataGridResp = new DataGridResp<T>(currentPage.getNumber() + 1, currentPage.getSize());
		dataGridResp.setTotalCount(currentPage.getTotalElements());
		dataGridResp.setPageNumShown(currentPage.getTotalPages());

		for (T content : currentPage.getContent()) {
			dataGridResp.getRecords().add(content);
		}

		return dataGridResp;
	}

	public PageRequest parsePageQuery(DataGridFilter dataGridFilter) {
		Sort sort = null;
		if (!StringUtils.isEmpty(dataGridFilter.getOrderDirection())
				&& !StringUtils.isEmpty(dataGridFilter.getOrderField())) {
			Sort.Direction direction = Sort.Direction.DESC;
			if ("asc".equalsIgnoreCase(dataGridFilter.getOrderDirection())) {
				direction = Sort.Direction.ASC;
			}
			sort = new Sort(direction, dataGridFilter.getOrderField());
		}
		return new PageRequest(dataGridFilter.getCurrentPage() - 1, dataGridFilter.getNumPerPage(), sort);
	}

	protected abstract JpaSpecificationExecutor<T> getCurrentRepository();

}

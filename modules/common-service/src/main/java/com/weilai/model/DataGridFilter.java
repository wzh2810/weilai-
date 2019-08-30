package com.weilai.model;

/**
 *
 */
public class DataGridFilter {

    protected Integer currentPage = 1;

    /**
     * 每页记录数(适用：datagrid)
     */
    protected Integer numPerPage = 12;

    /**
     * 排序的字段(适用：datagrid)
     */
    protected String orderField = null;

    /**
     * 排序方式(asc，desc 适用：datagrid)
     */
    protected String orderDirection = null;

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getNumPerPage() {
        return numPerPage;
    }

    public void setNumPerPage(Integer numPerPage) {
        this.numPerPage = numPerPage;
    }

    public String getOrderField() {
        return orderField;
    }

    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }

    public String getOrderDirection() {
        return orderDirection;
    }

    public void setOrderDirection(String orderDirection) {
        this.orderDirection = orderDirection;
    }
}

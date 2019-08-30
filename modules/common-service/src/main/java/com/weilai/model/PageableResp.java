package com.weilai.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: wangzhen
 * @Date: 2018/11/17 20:32
 */
public class PageableResp<T>  {

    private int totalPages;

    private long totalElements;

    private long allElements;

    private PageInfo nextPage;

    private List<T> dataList;

    public PageableResp() {
    }

    public PageableResp(int nextPageNumber, int pageSize) {
        this.dataList = new ArrayList<>();
        this.nextPage = new PageInfo(nextPageNumber, pageSize);
    }

    public PageInfo getNextPage() {
        return nextPage;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public static class PageInfo {

        private int pageNumber;

        private int pageSize;

        public PageInfo() {
        }

        public PageInfo(int pageNumber, int pageSize) {
            this.pageNumber = pageNumber;
            this.pageSize = pageSize;
        }

        public int getPageNumber() {
            return pageNumber;
        }

        public int getPageSize() {
            return pageSize;
        }
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }


    public long getAllElements() {
        return allElements;
    }

    public void setAllElements(long allElements) {
        this.allElements = allElements;
    }
}

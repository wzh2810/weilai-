package com.weilai.model;

/**
 * @Author: wangzhen
 * @Date: 2018/11/10 13:25
 */
public class CountPage {

    /**
     * 当前页
     */
    private Integer currentPage = 1;
    /**
     * 总页数
     */
    private long pageNumShown;
    /**
     * 总记录数
     */
    private Long totalCount;
    /**
     * 每页记录数
     */
    private Integer numPerPage = 12;


    /**
     * 要获取记录的开始索引
     **/
    public Integer getFirstResult() {
        return (this.currentPage - 1) * this.numPerPage;
    }

    /**
     * 当前页
     */
    public Integer getCurrentPage() {
        return currentPage;
    }

    /**
     * 当前页
     */
    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    /**
     * 总页数
     */
    public long getPageNumShown() {
        return pageNumShown;
    }

    /**
     * 总页数
     */
    public void setPageNumShown(long pageNumShown) {
        this.pageNumShown = pageNumShown;
    }

    /**
     * 总记录数
     */
    public Long getTotalCount() {
        return totalCount;
    }

    /**
     * 总记录数
     */
    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
        setPageNumShown(this.totalCount % this.numPerPage == 0 ? this.totalCount / this.numPerPage : this.totalCount / this.numPerPage + 1);
    }

    /**
     * 每页记录数
     */
    public Integer getNumPerPage() {
        return numPerPage;
    }

    /**
     * 每页记录数
     */
    public void setNumPerPage(Integer numPerPage) {
        this.numPerPage = numPerPage;
    }
}

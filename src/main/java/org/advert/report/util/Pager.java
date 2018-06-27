package org.advert.report.util;

import java.util.List;

/**
 * Created by shiqm on 2016-03-29.
 */
public class Pager {


    public static final Integer MAX_PAGE_SIZE = 50;// 每页记录数限

    private Integer pageNumber = 1;// 当前页码
    private Integer pageSize = 30;// 每页记录
    private Integer totalCount = 0;// 总记录数
    private Integer pageCount = 0;// 总页
    private List list;// 数据List

    @Override
    public String toString() {
        return "Pager{" +
                "pageNumber=" + pageNumber +
                ", pageSize=" + pageSize +
                ", totalCount=" + totalCount +
                ", pageCount=" + pageCount +
                ", list=" + list +
                '}';
    }

    public static Integer getMaxPageSize() {
        return MAX_PAGE_SIZE;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        if (pageNumber < 1) {
            pageNumber = 1;
        }
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

//    public void setPageSize(Integer pageSize) {
//        if (pageSize < 1) {
//            pageSize = 1;
//        } else if(pageSize > MAX_PAGE_SIZE) {
//            pageSize = MAX_PAGE_SIZE;
//        }
//        this.pageSize = pageSize;
//    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getPageCount() {
        pageCount = totalCount / pageSize;
        if (totalCount % pageSize > 0) {
            pageCount ++;
        }
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

}

package com.shuishu.demo.security.common.config.domain;


import com.querydsl.core.QueryResults;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author ：谁书-ss
 * @date ：2023-01-01 14:28
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @description ：
 */
public class PageVO<T> implements Serializable {
    private List<T> dataList = new ArrayList<>();

    @Min(value = 1, message = "每页数目从1开始")
    private long pageSize = 5;

    @Min(value = 1, message = "页码从1开始")
    private long pageNumber = 1;

    private Long offset;

    private long totalPages;

    private long totalElements;

    private String pageOrder;

    public PageVO() {
        super();
    }

    public PageVO(long pageSize, long pageNumber) {
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
    }


    public PageVO(List<T> data, long pageSize, long pageNumber, long totalElements) {
        this.dataList = data;
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
        this.totalElements = totalElements;
        this.totalPages = (totalElements % pageSize == 0) ? (totalElements / pageSize) : (totalElements / pageSize) + 1;
    }

    public static PageVO of(List data, long pageSize, long pageNumber, long totalElements) {
        return new PageVO(data, pageSize, pageNumber, totalElements);
    }

    public static PageVO of(List data, Pageable pageable, long totalElements) {
        return new PageVO(data, pageable.getPageSize(), pageable.getPageNumber(), totalElements);
    }

    public static PageVO ofEmptyPage(PageVO page) {

        PageVO<Object> emptyPage = new PageVO<>();
        emptyPage.setDataList(Collections.emptyList());
        emptyPage.setPageNumber(page.getPageNumber());
        emptyPage.setPageSize(page.getPageSize());
        return emptyPage;
    }

    public PageVO updatePage(List data) {
        this.dataList = data;
        return this;
    }

    public PageVO updatePage(List data, long count) {
        this.dataList = data;
        setTotalElements(count);
        return this;
    }

    public long getOffset() {
        if (offset != null) {
            return offset.longValue();
        }
        return (pageNumber - 1) * pageSize;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public long getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(long pageNumber) {
        this.pageNumber = pageNumber;
    }

    public long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        if (pageSize > 0) {
            this.totalPages = (totalElements % pageSize == 0) ? (totalElements / pageSize) : (totalElements / pageSize) + 1;
        }
        this.totalElements = totalElements;
    }

    public String getPageOrder() {
        return pageOrder;
    }

    public void setPageOrder(String pageOrder) {
        this.pageOrder = pageOrder;
    }

    //转换dsl的查询结果
    public PageVO<T> tranferDsl(PageVO page, QueryResults<T> results) {
        page.setOffset(results.getOffset());
        page.setDataList(results.getResults());
        page.setTotalElements(results.getTotal());
        //总页数
        long pageSize = page.getPageSize();
        long totalElements = page.getTotalElements();
        long totalPage = (totalElements % pageSize == 0) ? (totalElements / pageSize) : (totalElements / pageSize + 1);
        page.setTotalPages(totalPage);
        return page;
    }

    @Override
    public String toString() {
        return "PageVO{" +
                "dataList=" + dataList +
                ", pageSize=" + pageSize +
                ", pageNumber=" + pageNumber +
                ", offset=" + offset +
                ", totalPages=" + totalPages +
                ", totalElements=" + totalElements +
                ", pageOrder='" + pageOrder + '\'' +
                '}';
    }
}

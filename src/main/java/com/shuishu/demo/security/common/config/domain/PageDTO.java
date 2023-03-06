package com.shuishu.demo.security.common.config.domain;


import jakarta.validation.constraints.Min;

/**
 * @author ：谁书-ss
 * @date ：2023-01-01 14:27
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @description ：分页 DTO对象
 */
public class PageDTO {
    @Min(value = 1, message = "每页数目从1开始")
    private long pageSize = 5;

    @Min(value = 1, message = "页码从1开始")
    private long pageNumber = 1;

    private String pageOrder = "DESC";

    public <T> PageVO<T> toPageVO(Class<T> cl) {
        PageVO<T> pageVo = new PageVO<T>();
        pageVo.setPageNumber(pageNumber);
        pageVo.setPageSize(pageSize);
        return pageVo;
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

    public String getPageOrder() {
        return pageOrder;
    }

    public void setPageOrder(String pageOrder) {
        this.pageOrder = pageOrder;
    }
}

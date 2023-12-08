package com.cn.taskmanagement.dto;

public class SortingAndPaginationParams {
    private String sortBy;
    private String sortOrder;
    private int page;
    private int size;

    // Default constructor (needed for Jackson deserialization)
    public SortingAndPaginationParams() {
    }

    // Custom constructor
    public SortingAndPaginationParams(String sortBy, String sortOrder, int page, int size) {
        this.sortBy = sortBy;
        this.sortOrder = sortOrder;
        this.page = page;
        this.size = size;
    }

    // Getters and setters
    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

}

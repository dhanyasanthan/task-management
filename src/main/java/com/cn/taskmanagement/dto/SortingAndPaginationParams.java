package com.cn.taskmanagement.dto;

/**
 * Data Transfer Object (DTO) representing sorting and
 * pagination parameters for query requests.
 */
public class SortingAndPaginationParams {
    private String sortBy;
    private String sortOrder;
    private int page;
    private int size;

    // Default constructor
    public SortingAndPaginationParams() {
    }

    /**
     * Custom constructor for SortingAndPaginationParams.
     *
     * @param sortBy    The field to sort by.
     * @param sortOrder The sort order (ASC or DESC).
     * @param page      The page number.
     * @param size      The number of items per page.
     */
    public SortingAndPaginationParams(String sortBy, String sortOrder, int page, int size) {
        this.sortBy = sortBy;
        this.sortOrder = sortOrder;
        this.page = page;
        this.size = size;
    }

    // Getters and setters

    /**
     * Gets the field to sort by.
     *
     * @return The field to sort by.
     */
    public String getSortBy() {
        return sortBy;
    }

    /**
     * Sets the field to sort by.
     *
     * @param sortBy The field to set for sorting.
     */
    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    /**
     * Gets the sort order (ASC or DESC).
     *
     * @return The sort order.
     */
    public String getSortOrder() {
        return sortOrder;
    }

    /**
     * Sets the sort order (ASC or DESC).
     *
     * @param sortOrder The sort order to set.
     */
    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * Gets the page number.
     *
     * @return The page number.
     */
    public int getPage() {
        return page;
    }

    /**
     * Sets the page number.
     *
     * @param page The page number to set.
     */
    public void setPage(int page) {
        this.page = page;
    }

    /**
     * Gets the number of items per page.
     *
     * @return The number of items per page.
     */
    public int getSize() {
        return size;
    }

    /**
     * Sets the number of items per page.
     *
     * @param size The number of items per page to set.
     */
    public void setSize(int size) {
        this.size = size;
    }

}

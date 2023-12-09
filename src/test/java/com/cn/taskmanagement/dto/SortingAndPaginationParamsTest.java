package com.cn.taskmanagement.dto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SortingAndPaginationParamsTest {

    @Test
    public void testEqualsAndHashCode() {
        SortingAndPaginationParams params1 = new SortingAndPaginationParams("priority", "ASC", 1, 10);
        SortingAndPaginationParams params2 = new SortingAndPaginationParams("priority", "ASC", 1, 10);

        assertEquals(params1, params2);
        assertEquals(params1.hashCode(), params2.hashCode());
    }

    @Test
    public void testGettersAndSetters() {
        SortingAndPaginationParams params = new SortingAndPaginationParams();
        params.setSortBy("priority");
        params.setSortOrder("ASC");
        params.setPage(1);
        params.setSize(10);

        assertEquals("priority", params.getSortBy());
        assertEquals("ASC", params.getSortOrder());
        assertEquals(1, params.getPage());
        assertEquals(10, params.getSize());
    }

    @Test
    public void testNotEqualsWithDifferentSortBy() {
        SortingAndPaginationParams params1 = new SortingAndPaginationParams("priority", "ASC", 1, 10);
        SortingAndPaginationParams params2 = new SortingAndPaginationParams("deadline", "ASC", 1, 10);

        assertNotEquals(params1, params2);
    }

    @Test
    public void testNotEqualsWithDifferentSortOrder() {
        SortingAndPaginationParams params1 = new SortingAndPaginationParams("priority", "ASC", 1, 10);
        SortingAndPaginationParams params2 = new SortingAndPaginationParams("priority", "DESC", 1, 10);

        assertNotEquals(params1, params2);
    }

    @Test
    public void testNotEqualsWithDifferentPage() {
        SortingAndPaginationParams params1 = new SortingAndPaginationParams("priority", "ASC", 1, 10);
        SortingAndPaginationParams params2 = new SortingAndPaginationParams("priority", "ASC", 2, 10);

        assertNotEquals(params1, params2);
    }

    @Test
    public void testNotEqualsWithDifferentSize() {
        SortingAndPaginationParams params1 = new SortingAndPaginationParams("priority", "ASC", 1, 10);
        SortingAndPaginationParams params2 = new SortingAndPaginationParams("priority", "ASC", 1, 20);

        assertNotEquals(params1, params2);
    }

    @Test
    public void testNotEqualsWithNullSortBy() {
        SortingAndPaginationParams params1 = new SortingAndPaginationParams(null, "ASC", 1, 10);
        SortingAndPaginationParams params2 = new SortingAndPaginationParams("priority", "ASC", 1, 10);

        assertNotEquals(params1, params2);
    }
}

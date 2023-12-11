package com.cn.taskmanagement.dto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link SortingAndPaginationParams} class.
 */
public class SortingAndPaginationParamsTest {

    /**
     * Test for the equals and hashCode methods.
     */
    @Test
    public void testEqualsAndHashCode() {
        // Arrange
        SortingAndPaginationParams params1 = new SortingAndPaginationParams("priority", "ASC", 1, 10);
        SortingAndPaginationParams params2 = new SortingAndPaginationParams("priority", "ASC", 1, 10);

        // Assert
        assertEquals(params1, params2);
        assertEquals(params1.hashCode(), params2.hashCode());
    }

    /**
     * Test for the getters and setters.
     */
    @Test
    public void testGettersAndSetters() {
        // Arrange
        SortingAndPaginationParams params = new SortingAndPaginationParams();
        params.setSortBy("priority");
        params.setSortOrder("ASC");
        params.setPage(1);
        params.setSize(10);

        // Assert
        assertEquals("priority", params.getSortBy());
        assertEquals("ASC", params.getSortOrder());
        assertEquals(1, params.getPage());
        assertEquals(10, params.getSize());
    }

    /**
     * Test for not equals with different sortBy.
     */
    @Test
    public void testNotEqualsWithDifferentSortBy() {
        // Arrange
        SortingAndPaginationParams params1 = new SortingAndPaginationParams("priority", "ASC", 1, 10);
        SortingAndPaginationParams params2 = new SortingAndPaginationParams("deadline", "ASC", 1, 10);

        // Assert
        assertNotEquals(params1, params2);
    }

    /**
     * Test for not equals with different sortOrder.
     */
    @Test
    public void testNotEqualsWithDifferentSortOrder() {
        // Arrange
        SortingAndPaginationParams params1 = new SortingAndPaginationParams("priority", "ASC", 1, 10);
        SortingAndPaginationParams params2 = new SortingAndPaginationParams("priority", "DESC", 1, 10);

        // Assert
        assertNotEquals(params1, params2);
    }

    /**
     * Test for not equals with different page.
     */
    @Test
    public void testNotEqualsWithDifferentPage() {
        // Arrange
        SortingAndPaginationParams params1 = new SortingAndPaginationParams("priority", "ASC", 1, 10);
        SortingAndPaginationParams params2 = new SortingAndPaginationParams("priority", "ASC", 2, 10);

        // Assert
        assertNotEquals(params1, params2);
    }

    /**
     * Test for not equals with different size.
     */
    @Test
    public void testNotEqualsWithDifferentSize() {
        // Arrange
        SortingAndPaginationParams params1 = new SortingAndPaginationParams("priority", "ASC", 1, 10);
        SortingAndPaginationParams params2 = new SortingAndPaginationParams("priority", "ASC", 1, 20);

        // Assert
        assertNotEquals(params1, params2);
    }

    /**
     * Test for not equals with null sortBy.
     */
    @Test
    public void testNotEqualsWithNullSortBy() {
        // Arrange
        SortingAndPaginationParams params1 = new SortingAndPaginationParams(null, "ASC", 1, 10);
        SortingAndPaginationParams params2 = new SortingAndPaginationParams("priority", "ASC", 1, 10);

        // Assert
        assertNotEquals(params1, params2);
    }

}

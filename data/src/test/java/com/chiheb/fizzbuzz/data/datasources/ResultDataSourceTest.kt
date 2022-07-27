package com.chiheb.fizzbuzz.data.datasources

import com.chiheb.fizzbuzz.data.entities.InputData
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ResultDataSourceTest {

    private lateinit var dataSource: ResultDataSource

    @BeforeEach
    fun setup() {
        dataSource = ResultDataSource()
    }


    @Test
    fun `calculateResult returns success`() {
        // Given
        val input = InputData(
            firstDivider = 3,
            secondDivider = 5,
            firstReplacementText = "Fizz",
            secondReplacementText = "Buzz",
            calculationLimit = 1000,
            pageNumber = 1
        )
        // When
        val result = dataSource.calculateResult(input)
        // Then
        assertNotNull(result)
        assertTrue(result.data.isNotEmpty())
        assertTrue(result.hasMoreItems)
        assertEquals(result.nextPage, 2)
        assertEquals(result.data[1], "2")
        assertEquals(result.data[2], "Fizz")
        assertEquals(result.data[4], "Buzz")
    }

    @Test
    fun `calculateResult returns empty list`() {
        // Given
        val input = InputData(
            firstDivider = 3,
            secondDivider = 5,
            firstReplacementText = "Fizz",
            secondReplacementText = "Buzz",
            calculationLimit = 1000,
            pageNumber = 11
        )
        // When
        val result = dataSource.calculateResult(input)
        // Then
        assertNotNull(result)
        assertTrue(result.data.isEmpty())
        assertFalse(result.hasMoreItems)
    }

    @Test
    fun `calculateResult returns success for second page`() {
        // Given
        val input = InputData(
            firstDivider = 3,
            secondDivider = 5,
            firstReplacementText = "Fizz",
            secondReplacementText = "Buzz",
            calculationLimit = 155,
            pageNumber = 2
        )
        // When
        val result = dataSource.calculateResult(input)
        // Then
        assertNotNull(result)
        assertTrue(result.data.isNotEmpty())
        assertTrue(result.hasMoreItems)
        assertEquals(result.data.size, 55)
        assertEquals(result.nextPage, 3)
        assertEquals(result.data[0], "101")
        assertEquals(result.data[1], "Fizz")
        assertEquals(result.data[4], "FizzBuzz")
        assertEquals(result.data[9], "Buzz")
    }
}
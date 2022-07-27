package com.chiheb.fizzbuzz.data.repositories

import com.chiheb.fizzbuzz.data.datasources.ResultDataSource
import com.chiheb.fizzbuzz.data.entities.ResultEntity
import com.chiheb.fizzbuzz.domain.models.InputDomain
import com.chiheb.fizzbuzz.domain.repositories.IResultRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class ResultRepositoryTest {

    private val dataSource: ResultDataSource = mockk()

    private lateinit var repository: IResultRepository

    private val list = listOf(
        "1",
        "2",
        "Fizz",
        "4",
        "Buzz",
        "Fizz",
        "7",
        "8",
        "Fizz",
        "Buzz",
        "11",
        "Fizz",
        "13",
        "14",
        "FizzBuzz"
    )

    @BeforeEach
    fun setup() {
        repository = ResultRepositoryImpl(dataSource)
    }

    @Test
    fun `getResult returns success`() = runTest {
        // Given
        coEvery { dataSource.calculateResult(any()) } returns ResultEntity(
            data = list,
            nextPage = 2,
            hasMoreItems = true
        )
        // When
        val result = repository.getResult(
            InputDomain(
                firstDivider = 3,
                secondDivider = 5,
                firstReplacementText = "Fizz",
                secondReplacementText = "Buzz",
                calculationLimit = 15,
                pageNumber = 1
            )
        )
        // Then
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()?.data?.isNotEmpty() == true)
    }

    @Test
    fun `getResult returns empty list`() = runTest {
        // Given
        coEvery { dataSource.calculateResult(any()) } returns ResultEntity(
            data = emptyList(),
            nextPage = 2,
            hasMoreItems = false
        )
        // When
        val result = repository.getResult(
            InputDomain(
                firstDivider = 3,
                secondDivider = 5,
                firstReplacementText = "Fizz",
                secondReplacementText = "Buzz",
                calculationLimit = 15,
                pageNumber = 2
            )
        )
        // Then
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()?.data?.isEmpty() == true)
    }
}
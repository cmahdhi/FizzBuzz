package com.chiheb.fizzbuzz.domain.interactors

import com.chiheb.fizzbuzz.domain.models.InputDomain
import com.chiheb.fizzbuzz.domain.models.ResultDomain
import com.chiheb.fizzbuzz.domain.repositories.IResultRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class GetResultUseCaseTest {

    private val repository: IResultRepository = mockk()

    private lateinit var useCase: GetResultUseCase

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
        useCase = GetResultUseCase(repository)
    }

    @Test
    fun `invoke returns success`() = runTest {
        // Given
        coEvery { repository.getResult(any()) } returns Result.success(
            ResultDomain(
                data = list,
                nextPage = 2,
                hasMoreItems = true
            )
        )
        // When
        val result = useCase(
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
        Assertions.assertTrue(result.isSuccess)
        Assertions.assertTrue(result.getOrNull()?.data?.isNotEmpty() == true)
    }

    @Test
    fun `invoke returns empty list`() = runTest {
        // Given
        coEvery { repository.getResult(any()) } returns Result.success(
            ResultDomain(
                data = emptyList(),
                nextPage = 2,
                hasMoreItems = false
            )
        )
        // When
        val result = useCase(
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
        Assertions.assertTrue(result.isSuccess)
        Assertions.assertTrue(result.getOrNull()?.data?.isEmpty() == true)
    }
}
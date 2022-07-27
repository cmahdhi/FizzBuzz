package com.chiheb.fizzbuzz.presentation.viewmodels

import com.chiheb.fizzbuzz.domain.interactors.GetResultUseCase
import com.chiheb.fizzbuzz.domain.models.ResultDomain
import com.chiheb.fizzbuzz.presentation.models.CalculationInput
import com.chiheb.fizzbuzz.presentation.observeUiState
import com.chiheb.fizzbuzz.presentation.test
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class ResultListViewModelTest {

    private val useCase: GetResultUseCase = mockk()

    private lateinit var viewModel: ResultListViewModel

    private val domainData = ResultDomain(
        data = listOf("1", "2", "Fizz", "4", "Buzz"),
        nextPage = 1,
        hasMoreItems = true
    )

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        viewModel = ResultListViewModel(useCase)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state`() = runTest {
        viewModel.observeUiState().test {
            Assertions.assertEquals(ResultListViewModel.UIState.EmptyState, expectMostRecentItem())
            expectNoEvents()
        }
    }

    @Test
    fun `fetch first page`() = runTest {
        // Given
        val stateWatcher = viewModel.observeUiState()
        coEvery { useCase(any()) } returns Result.success(domainData)
        // When
        viewModel.sendIntent(
            ResultListViewModel.UserAction.CalculateResult(
                CalculationInput(
                    firstDivider = 3,
                    secondDivider = 5,
                    firstReplacementText = "Fizz",
                    secondReplacementText = "Buzz",
                    calculationLimit = 5
                )
            )
        )
        // Then
        stateWatcher.test {
            val state = expectMostRecentItem()
            Assertions.assertTrue(state is ResultListViewModel.UIState.PageResult)
            expectNoEvents()
        }
    }
}
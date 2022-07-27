package com.chiheb.fizzbuzz.presentation.viewmodels

import com.chiheb.fizzbuzz.presentation.models.CalculationInput
import com.chiheb.fizzbuzz.presentation.observeUiEvent
import com.chiheb.fizzbuzz.presentation.observeUiState
import com.chiheb.fizzbuzz.presentation.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class FormViewModelTest {

    private lateinit var viewModel: FormViewModel

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        viewModel = FormViewModel()
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state`() = runTest {
        viewModel.observeUiState().test {
            assertEquals(FormViewModel.UIState.EmptyState, expectMostRecentItem())
            expectNoEvents()
        }
    }

    @Test
    fun `validate form returns error when divider is not valid`() = runTest {
        // Given
        val eventWatcher = viewModel.observeUiEvent<FormViewModel.UiEvent>()
        // When
        viewModel.sendIntent(
            FormViewModel.UserAction.ValidateInput(
                CalculationInput(
                    firstDivider = 0,
                    secondDivider = 0,
                    firstReplacementText = "Fizz",
                    secondReplacementText = "Fizz",
                    calculationLimit = 100
                )
            )
        )
        // Then
        eventWatcher.test {
            val event = expectMostRecentItem()
            assertTrue(event is FormViewModel.UiEvent.InputError)
            expectNoEvents()
        }
    }

    @Test
    fun `validate form returns error when text is not valid`() = runTest {
        // Given
        val eventWatcher = viewModel.observeUiEvent<FormViewModel.UiEvent>()
        // When
        viewModel.sendIntent(
            FormViewModel.UserAction.ValidateInput(
                CalculationInput(
                    firstDivider = 3,
                    secondDivider = 5,
                    firstReplacementText = "",
                    secondReplacementText = "",
                    calculationLimit = 100
                )
            )
        )
        // Then
        eventWatcher.test {
            val event = expectMostRecentItem()
            assertTrue(event is FormViewModel.UiEvent.InputError)
            expectNoEvents()
        }
    }

    @Test
    fun `validate form returns error when limit is not valid`() = runTest {
        // Given
        val eventWatcher = viewModel.observeUiEvent<FormViewModel.UiEvent>()
        // When
        viewModel.sendIntent(
            FormViewModel.UserAction.ValidateInput(
                CalculationInput(
                    firstDivider = 3,
                    secondDivider = 5,
                    firstReplacementText = "Fizz",
                    secondReplacementText = "Buzz",
                    calculationLimit = 0
                )
            )
        )
        // Then
        eventWatcher.test {
            val event = expectMostRecentItem()
            assertTrue(event is FormViewModel.UiEvent.InputError)
            expectNoEvents()
        }
    }

    @Test
    fun `validate form returns success`() = runTest {
        // Given
        val eventWatcher = viewModel.observeUiEvent<FormViewModel.UiEvent>()
        // When
        viewModel.sendIntent(
            FormViewModel.UserAction.ValidateInput(
                CalculationInput(
                    firstDivider = 3,
                    secondDivider = 5,
                    firstReplacementText = "Fizz",
                    secondReplacementText = "Buzz",
                    calculationLimit = 100
                )
            )
        )
        // Then
        eventWatcher.test {
            val event = expectMostRecentItem()
            assertTrue(event is FormViewModel.UiEvent.NavigateToResult)
            expectNoEvents()
        }
    }
}
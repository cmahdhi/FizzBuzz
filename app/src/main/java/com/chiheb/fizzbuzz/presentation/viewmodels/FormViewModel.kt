package com.chiheb.fizzbuzz.presentation.viewmodels

import com.chiheb.fizzbuzz.core.bases.BaseViewModel
import com.chiheb.fizzbuzz.core.bases.MVI
import com.chiheb.fizzbuzz.presentation.models.CalculationInput
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FormViewModel @Inject constructor() :
    BaseViewModel<FormViewModel.UIState, FormViewModel.UserAction>(UIState.EmptyState) {

    sealed interface UIState : MVI.State {
        object EmptyState : UIState
    }

    sealed interface UiEvent : MVI.Event {
        object InputError : UiEvent
        data class NavigateToResult(val input: CalculationInput) : UiEvent
    }

    sealed interface UserAction : MVI.Intent {
        data class ValidateInput(val input: CalculationInput) : UserAction
    }

    override suspend fun processIntent(intent: UserAction) {
        when (intent) {
            is UserAction.ValidateInput -> validateInput(intent.input)
        }
    }

    private fun validateInput(input: CalculationInput) {
        emitEvent(
            if (isValidInput(input)) {
                UiEvent.NavigateToResult(input)
            } else {
                UiEvent.InputError
            }
        )
    }

    private fun isValidInput(input: CalculationInput): Boolean {
        with(input) {
            return firstDivider > 0 &&
                    secondDivider > 0 &&
                    firstDivider != secondDivider &&
                    firstDivider < calculationLimit &&
                    secondDivider < calculationLimit &&
                    firstReplacementText.isNotBlank() &&
                    secondReplacementText.isNotBlank() &&
                    firstReplacementText != secondReplacementText &&
                    calculationLimit > 0
        }
    }
}
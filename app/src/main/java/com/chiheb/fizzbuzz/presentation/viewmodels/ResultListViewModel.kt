package com.chiheb.fizzbuzz.presentation.viewmodels

import com.chiheb.fizzbuzz.core.bases.BaseViewModel
import com.chiheb.fizzbuzz.core.bases.MVI
import com.chiheb.fizzbuzz.domain.interactors.GetResultUseCase
import com.chiheb.fizzbuzz.presentation.models.CalculationInput
import com.chiheb.fizzbuzz.presentation.models.Result
import com.chiheb.fizzbuzz.presentation.models.extensions.toDomain
import com.chiheb.fizzbuzz.presentation.models.extensions.toModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class ResultListViewModel @Inject constructor(
    val useCase: GetResultUseCase
) : BaseViewModel<ResultListViewModel.UIState, ResultListViewModel.UserAction>(UIState.EmptyState) {

    sealed interface UIState : MVI.State {
        object EmptyState : UIState
        data class PageResult(val result: Result) : UIState
    }

    sealed interface UserAction : MVI.Intent {
        data class CalculateResult(val input: CalculationInput) : UserAction
    }

    private val result = MutableStateFlow<Result?>(null)

    override suspend fun processIntent(intent: UserAction) {
        when (intent) {
            is UserAction.CalculateResult -> calculateResult(intent.input)
        }
    }

    private suspend fun calculateResult(input: CalculationInput) {
        useCase(input.toDomain().copy(pageNumber = result.value?.nextPage ?: 1)).onSuccess {
            val data = it.toModel()
            result.value = data
            publishState(UIState.PageResult(data))
        }
    }
}
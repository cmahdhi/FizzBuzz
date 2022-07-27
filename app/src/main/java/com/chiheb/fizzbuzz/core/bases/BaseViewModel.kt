package com.chiheb.fizzbuzz.core.bases

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*

abstract class BaseViewModel<S : MVI.State, I : MVI.Intent>(
    private val initialState: S,
    dispatcher: CoroutineDispatcher = Dispatchers.Main.immediate
) : ViewModel() {

    val mviScope: CoroutineScope = viewModelScope + dispatcher

    private var _currentState: S = initialState
    protected val currentState: S get() = _currentState

    private val _uiState = MutableSharedFlow<S>(
        replay = 1,
        extraBufferCapacity = 64,
        onBufferOverflow = BufferOverflow.SUSPEND
    )

    private val _uiEvent: MutableSharedFlow<MVI.Event> = MutableSharedFlow(
        extraBufferCapacity = 1,
    )

    val uiState: SharedFlow<S> =
        _uiState.onStart {
            emit(initialState)
        }.shareIn(
            scope = mviScope,
            started = SharingStarted.Eagerly,
            replay = 1
        )

    val uiEvent: SharedFlow<MVI.Event> = _uiEvent.asSharedFlow()


    protected fun publishState(state: S) {
        val published = _uiState.tryEmit(state)
        if (published) {
            _currentState = state
        }
    }

    protected fun emitEvent(event: MVI.Event) {
        mviScope.launch {
            _uiEvent.emit(event)
        }
    }

    fun sendIntent(intent: I) {
        mviScope.launch {
            processIntent(intent)
        }
    }

    protected abstract suspend fun processIntent(intent: I)
}

object MVI {
    interface State
    interface Intent
    interface Event
}
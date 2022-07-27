package com.chiheb.fizzbuzz.presentation

import app.cash.turbine.FlowTurbine
import app.cash.turbine.test
import com.chiheb.fizzbuzz.core.bases.BaseViewModel
import com.chiheb.fizzbuzz.core.bases.MVI
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

fun <S : MVI.State> BaseViewModel<S, *>.observeUiState(): Pair<Channel<S>, Job> {
    val stateChannel = Channel<S>(Channel.BUFFERED)
    val subscriber = mviScope.launch {
        uiState.collect { stateChannel.send(it) }
    }

    return stateChannel to subscriber
}

fun <E : MVI.Event> BaseViewModel<*, *>.observeUiEvent(): Pair<Channel<E>, Job> {
    val eventChannel = Channel<E>(Channel.BUFFERED)
    val subscriber = mviScope.launch {
        uiEvent.collect { eventChannel.send(it as E) }
    }

    return eventChannel to subscriber
}

suspend fun <S> Pair<Channel<S>, Job>.test(
    validate: suspend FlowTurbine<S>.() -> Unit
) {
    first.consumeAsFlow().test(validate)
    second.cancel()
}

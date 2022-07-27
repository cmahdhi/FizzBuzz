package com.chiheb.fizzbuzz.core.utils

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.chiheb.fizzbuzz.core.bases.BaseViewModel
import com.chiheb.fizzbuzz.core.bases.MVI
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

fun <E : MVI.Event> Fragment.processEvent(
    viewModel: BaseViewModel<*, *>,
    action: E.() -> Unit
) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            viewModel.uiEvent.map { action(it as E) }.launchIn(this)
        }
    }
}

fun <S : MVI.State, I : MVI.Intent> Fragment.processState(
    viewModel: BaseViewModel<S, I>,
    action: S.() -> Unit
) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            viewModel.uiState.map { action(it) }
                .launchIn(this)
        }
    }
}

fun EditText.getStringText(): String = this.text.toString()

fun EditText.getNumber(): Int = this.text.toString().toIntOrNull() ?: 0

fun Fragment.hideKeyboard() {
    view?.apply {
        clearFocus()
        (context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.hideSoftInputFromWindow(
            windowToken,
            0
        )
    }
}
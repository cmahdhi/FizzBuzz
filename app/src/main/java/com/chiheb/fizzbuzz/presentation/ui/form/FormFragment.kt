package com.chiheb.fizzbuzz.presentation.ui.form

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.chiheb.fizzbuzz.R
import com.chiheb.fizzbuzz.core.utils.getNumber
import com.chiheb.fizzbuzz.core.utils.getStringText
import com.chiheb.fizzbuzz.core.utils.hideKeyboard
import com.chiheb.fizzbuzz.core.utils.processEvent
import com.chiheb.fizzbuzz.databinding.FragmentFormBinding
import com.chiheb.fizzbuzz.presentation.models.CalculationInput
import com.chiheb.fizzbuzz.presentation.viewmodels.FormViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FormFragment : Fragment() {

    private val binding by lazy { FragmentFormBinding.inflate(layoutInflater) }
    private val viewModel: FormViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        processEvent(viewModel, ::renderEvent)
    }

    private fun initListeners() {
        binding.validationButton.setOnClickListener {
            viewModel.sendIntent(FormViewModel.UserAction.ValidateInput(getInput()))
        }
    }

    private fun renderEvent(event: FormViewModel.UiEvent) {
        when (event) {
            is FormViewModel.UiEvent.NavigateToResult -> {
                findNavController().navigate(FormFragmentDirections.toResultListFragment(event.input))
            }
            is FormViewModel.UiEvent.InputError -> handleError()
        }
    }

    private fun getInput(): CalculationInput {
        with(binding) {
            return CalculationInput(
                firstDivider = firstDividerInput.getNumber(),
                secondDivider = secondDividerInput.getNumber(),
                firstReplacementText = firstMultipleTextInput.getStringText(),
                secondReplacementText = secondMultipleTextInput.getStringText(),
                calculationLimit = limitInput.getNumber()
            )
        }
    }

    private fun handleError() {
        hideKeyboard()
        Snackbar.make(binding.root, R.string.form_error_message, Snackbar.LENGTH_SHORT).show()
    }
}
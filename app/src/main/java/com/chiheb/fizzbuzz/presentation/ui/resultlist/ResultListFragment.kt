package com.chiheb.fizzbuzz.presentation.ui.resultlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.chiheb.fizzbuzz.R
import com.chiheb.fizzbuzz.core.components.MarginItemDecoration
import com.chiheb.fizzbuzz.core.utils.processState
import com.chiheb.fizzbuzz.databinding.FragmentResultListBinding
import com.chiheb.fizzbuzz.presentation.models.Result
import com.chiheb.fizzbuzz.presentation.viewmodels.ResultListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultListFragment : Fragment() {

    private val binding by lazy { FragmentResultListBinding.inflate(layoutInflater) }
    private val viewModel: ResultListViewModel by viewModels()
    private val args: ResultListFragmentArgs by navArgs()
    private val resultAdapter by lazy { ResultAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initToolbar()
        initListeners()
        processState(viewModel, ::renderState)
        viewModel.sendIntent(ResultListViewModel.UserAction.CalculateResult(args.calculationInput))
    }

    private fun renderState(uiState: ResultListViewModel.UIState) {
        when (uiState) {
            is ResultListViewModel.UIState.PageResult -> handleResult(uiState.result)
        }
    }

    private fun handleResult(result: Result) {
        resultAdapter.updateResult(result.data)
        binding.resultList.hasMoreItems = result.hasMoreItems
    }

    private fun initListeners() {
        binding.resultList.loadMoreCallback = {
            viewModel.sendIntent(ResultListViewModel.UserAction.CalculateResult(args.calculationInput))
        }
    }

    private fun initView() {
        binding.resultList.apply {
            adapter = resultAdapter
            addItemDecoration(MarginItemDecoration(resources.getDimensionPixelSize(R.dimen.spacing_s)))
        }
    }

    private fun initToolbar() {
        binding.toolbar.setNavigationOnClickListener { activity?.onBackPressed() }
    }
}
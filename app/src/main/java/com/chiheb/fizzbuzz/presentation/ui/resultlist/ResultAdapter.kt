package com.chiheb.fizzbuzz.presentation.ui.resultlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chiheb.fizzbuzz.databinding.HolderResultItemBinding

class ResultAdapter(
    private val list: MutableList<String> = mutableListOf()
) : RecyclerView.Adapter<ResultAdapter.ResultViewHolder>() {

    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        return ResultViewHolder(
            HolderResultItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        holder.bindView(list[position])
    }

    fun updateResult(result: List<String>) {
        list.addAll(result)
        notifyDataSetChanged()
    }

    inner class ResultViewHolder(private val binding: HolderResultItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(text: String) {
            binding.root.text = text
        }
    }
}
package com.chiheb.fizzbuzz.core.components.infinitelist

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val INFINITE_SCROLL_THRESHOLD = 5

abstract class InfiniteScrollListener : RecyclerView.OnScrollListener() {

    private var loading = true
    private var previousTotalItemCount = 0

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (!hasMoreItems() || dy < 0) {
            // No action if scrolling to top or no next page available
            return
        }

        (recyclerView.layoutManager as LinearLayoutManager).apply {
            val totalItemCount = recyclerView.adapter?.itemCount ?: return
            val lastVisibleItemPosition = findLastVisibleItemPosition()

            if (totalItemCount < previousTotalItemCount) {
                loading = true
                previousTotalItemCount = 0
            }

            if (loading && (totalItemCount > previousTotalItemCount)) {
                loading = false
                previousTotalItemCount = totalItemCount
            }

            if (!loading && totalItemCount - lastVisibleItemPosition <= INFINITE_SCROLL_THRESHOLD) {
                onLoadMore()
                loading = true
            }
        }
    }

    abstract fun hasMoreItems(): Boolean

    abstract fun onLoadMore()
}
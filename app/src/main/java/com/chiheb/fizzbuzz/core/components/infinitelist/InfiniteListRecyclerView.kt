package com.chiheb.fizzbuzz.core.components.infinitelist

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView

class InfiniteListRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    var hasMoreItems: Boolean = false

    var loadMoreCallback: (() -> Unit)? = null
        set(value) {
            field = value
            addOnScrollListener(object : InfiniteScrollListener() {

                override fun hasMoreItems(): Boolean = hasMoreItems

                override fun onLoadMore() {
                    loadMoreCallback?.invoke()
                }
            })
        }
}

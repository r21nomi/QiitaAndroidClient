package com.r21nomi.qiitaclientandroid.ui.adapter

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

/**
 * Created by Ryota Niinomi on 2016/11/24.
 */
abstract class InfiniteScrollRecyclerListener : RecyclerView.OnScrollListener {

    companion object {
        private var THRESHOLD_BUFFER_VIEW_TYPE_LINE = 10
    }

    abstract fun onLoadMore(page: Int, totalItemCount: Int)

    private var loading: Boolean = false
    private var currentPage: Int = 0
    private var previousTotal: Int = 0
    private var visibleThreshold: Int
    private var layoutManager: LinearLayoutManager

    constructor(layoutManager: LinearLayoutManager) : this(layoutManager, THRESHOLD_BUFFER_VIEW_TYPE_LINE)

    constructor(layoutManager: LinearLayoutManager, visibleThreshold: Int) : super() {
        this.layoutManager = layoutManager
        this.visibleThreshold = visibleThreshold
        init()
    }

    private fun init() {
        loading = true
        currentPage = 0
        previousTotal = 0
    }

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        val visibleItemCount = recyclerView!!.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

        if (dy < 0) {
            // Do nothing if up direction.
            return
        }
        if (totalItemCount < previousTotal) {
            init()
        }

        if (loading && totalItemCount > previousTotal) {
            loading = false
            previousTotal = totalItemCount
            currentPage++
        }

        // Not loading.
        // The item being Displayed is within 'visibleThreshold'.
        if (!loading && totalItemCount - visibleItemCount <= firstVisibleItem + visibleThreshold) {
            // End has been reached

            onLoadMore(currentPage + 1, totalItemCount)

            loading = true
        }
    }
}
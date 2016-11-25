package com.r21nomi.qiitaclientandroid.ui.controller

import android.databinding.DataBindingUtil
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SimpleItemAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.r21nomi.qiitaclientandroid.R
import com.r21nomi.qiitaclientandroid.databinding.ControllerMainBinding
import com.r21nomi.qiitaclientandroid.di.component.ControllerComponent
import com.r21nomi.qiitaclientandroid.model.ItemModel
import com.r21nomi.qiitaclientandroid.model.entity.Item
import com.r21nomi.qiitaclientandroid.ui.activity.DetailActivity
import com.r21nomi.qiitaclientandroid.ui.adapter.InfiniteScrollRecyclerListener
import com.r21nomi.qiitaclientandroid.ui.adapter.ItemBinder
import com.r21nomi.qiitaclientandroid.util.ViewUtil
import com.yqritc.recyclerviewmultipleviewtypesadapter.ListBindAdapter
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Ryota Niinomi on 2016/11/24.
 */
class MainController : BaseController() {

    companion object {
        val LIMIT = 20
    }

    @Inject
    lateinit var itemModel: ItemModel

    private var currentPage: Int = 1
    private var subscription: Subscription? = null
    private var recyclerView: RecyclerView? = null
    private var progressBar: ProgressBar? = null
    private var itemBinder: ItemBinder? = null
    private val adapter: ListBindAdapter = ListBindAdapter()
    private lateinit var binding: ControllerMainBinding

    override fun getLayout(): Int {
        return R.layout.controller_main
    }

    override fun injectDependency(component: ControllerComponent) {
        component.inject(this)
    }

    private val itemOnClick: (View, Item) -> Unit = { view, item ->
        startActivity(DetailActivity.createIntent(activity!!, item.url))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = super.onCreateView(inflater, container)

        binding = DataBindingUtil.bind(view)

        recyclerView = binding.recyclerView
        progressBar = binding.progressBar
        currentPage = 1
        itemBinder = ItemBinder(adapter, itemOnClick)

        adapter.addBinder(itemBinder)

        val layoutManager = LinearLayoutManager(activity)

        if (recyclerView?.itemAnimator is SimpleItemAnimator) {
            (recyclerView?.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        }
        recyclerView?.setHasFixedSize(false)
        recyclerView?.layoutManager = layoutManager
        recyclerView?.adapter = adapter
        recyclerView?.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        recyclerView?.addOnScrollListener(object : InfiniteScrollRecyclerListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemCount: Int) {
                fetchItems()
            }
        })

        fetchItems()

        return view
    }

    private fun fetchItems() {
        if (subscription?.run { !isUnsubscribed } ?: false) {
            Timber.d("Do nothing since now fetching...")
            return
        }
        progressBar?.visibility = View.VISIBLE
        subscription = itemModel
                .fetchItems(currentPage, LIMIT, "android")
                .observeOn(AndroidSchedulers.mainThread())
                .doOnTerminate { progressBar?.visibility = View.GONE }
                .subscribe({ items ->
                    Timber.d(items[0].rendered_body)
                    itemBinder?.addDataSet(items)
                    itemBinder?.notifyBinderDataSetChanged()

                    currentPage++
                }, { throwable ->
                    ViewUtil.showSnackBar(
                            activity ?: return@subscribe,
                            throwable.message ?: return@subscribe
                    )
                })

        subscriptionsOnDestroy?.add(subscription)
    }
}
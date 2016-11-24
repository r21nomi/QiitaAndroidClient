package com.r21nomi.qiitaclientandroid.ui.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.r21nomi.qiitaclientandroid.R
import com.r21nomi.qiitaclientandroid.databinding.ActivityMainBinding
import com.r21nomi.qiitaclientandroid.di.component.ActivityComponent
import com.r21nomi.qiitaclientandroid.model.ItemModel
import com.r21nomi.qiitaclientandroid.model.TagModel
import com.r21nomi.qiitaclientandroid.model.entity.Item
import com.r21nomi.qiitaclientandroid.ui.adapter.InfiniteScrollRecyclerListener
import com.r21nomi.qiitaclientandroid.ui.adapter.ItemBinder
import com.r21nomi.qiitaclientandroid.ui.adapter.decoration.DividerItemDecoration
import com.yqritc.recyclerviewmultipleviewtypesadapter.ListBindAdapter
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import timber.log.Timber
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var tagModel: TagModel
    @Inject
    lateinit var itemModel: ItemModel

    private var currentPage: Int = 1
    private var subscription: Subscription? = null
    private var binding: ActivityMainBinding? = null
    private var recyclerView: RecyclerView? = null
    private var itemBinder: ItemBinder? = null
    private val adapter: ListBindAdapter = ListBindAdapter()

    private val itemOnClick: (View, Item) -> Unit = { view, item ->
        startActivity(DetailActivity.createIntent(this, item.url))
    }

    override fun injectDependency(component: ActivityComponent) {
        component.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        recyclerView = binding?.recyclerView
        currentPage = 1

        itemBinder = ItemBinder(adapter, itemOnClick)

        adapter.addBinder(itemBinder)

        val layoutManager = LinearLayoutManager(this)

        recyclerView?.setHasFixedSize(false)
        recyclerView?.layoutManager = layoutManager
        recyclerView?.adapter = adapter
        recyclerView?.addItemDecoration(DividerItemDecoration(this))
        recyclerView?.addOnScrollListener(object : InfiniteScrollRecyclerListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemCount: Int) {
                fetchItems()
            }
        })

        fetchItems()
    }

    private fun fetchItems() {
        if (subscription != null && !subscription!!.isUnsubscribed) {
            Timber.d("Do nothing since now fetching...")
            return
        }
        subscription = itemModel
                .getItems(currentPage, 20, "android")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ items ->
                    Timber.d(items[0].rendered_body)
                    itemBinder?.addDataSet(items)
                    itemBinder?.notifyBinderDataSetChanged()

                    currentPage++
                })

        subscriptionsOnDestroy?.add(subscription)
    }
}

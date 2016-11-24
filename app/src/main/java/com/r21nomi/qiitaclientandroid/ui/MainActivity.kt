package com.r21nomi.qiitaclientandroid.ui

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.r21nomi.qiitaclientandroid.R
import com.r21nomi.qiitaclientandroid.databinding.ActivityMainBinding
import com.r21nomi.qiitaclientandroid.di.component.ActivityComponent
import com.r21nomi.qiitaclientandroid.model.ItemModel
import com.r21nomi.qiitaclientandroid.model.TagModel
import com.r21nomi.qiitaclientandroid.ui.adapter.ItemBinder
import com.r21nomi.qiitaclientandroid.ui.adapter.decoration.DividerItemDecoration
import com.yqritc.recyclerviewmultipleviewtypesadapter.ListBindAdapter
import rx.android.schedulers.AndroidSchedulers
import timber.log.Timber
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var tagModel: TagModel
    @Inject
    lateinit var itemModel: ItemModel

    var binding: ActivityMainBinding? = null
    var recyclerView: RecyclerView? = null
    var itemBinder: ItemBinder? = null
    val adapter: ListBindAdapter = ListBindAdapter()

    override fun injectDependency(component: ActivityComponent) {
        component.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        recyclerView = binding?.recyclerView

        itemBinder = ItemBinder(adapter)
        adapter.addBinder(itemBinder)

        recyclerView?.setHasFixedSize(false)
        recyclerView?.layoutManager = LinearLayoutManager(this)
        recyclerView?.adapter = adapter
        recyclerView?.addItemDecoration(DividerItemDecoration(this))

        itemModel
                .getItems(1, 20, "openframeworks")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ items ->
                    Timber.d(items[0].rendered_body)
                    itemBinder?.setDataSet(items)
                    itemBinder?.notifyBinderDataSetChanged()
                })
    }

    override fun onResume() {
        super.onResume()
    }
}

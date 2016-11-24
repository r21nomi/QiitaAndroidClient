package com.r21nomi.qiitaclientandroid.ui.adapter

import android.databinding.DataBindingUtil
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.r21nomi.qiitaclientandroid.R
import com.r21nomi.qiitaclientandroid.databinding.ItemViewholderBinding
import com.r21nomi.qiitaclientandroid.model.entity.Item
import com.yqritc.recyclerviewmultipleviewtypesadapter.DataBindAdapter
import com.yqritc.recyclerviewmultipleviewtypesadapter.DataBinder
import java.util.*

/**
 * Created by Ryota Niinomi on 2016/11/24.
 */
class ItemBinder(dataBindAdapter: DataBindAdapter,
                 private val itemOnClick: ((View, Item) -> Unit)) : DataBinder<ItemBinder.ViewHolder>(dataBindAdapter) {

    private var dataSet: MutableList<Item> = ArrayList()

    override fun bindViewHolder(holder: ViewHolder, position: Int) {
        val item: Item = dataSet[position]
        holder.binding.item = item

        val uri = Uri.parse(item.user.profile_image_url)
        holder.binding.thumb.setImageURI(uri, null)

        holder.binding.root.setOnClickListener { view ->
            itemOnClick.invoke(view, item)
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun newViewHolder(parent: ViewGroup): ViewHolder {
        val binding = DataBindingUtil.inflate<ItemViewholderBinding>(
                LayoutInflater.from(parent.context), R.layout.item_viewholder, parent, false
        )
        return ViewHolder(binding)
    }

    fun addDataSet(dataSet: List<Item>) {
        this.dataSet.addAll(dataSet)
    }

    inner class ViewHolder(binding: ItemViewholderBinding) : RecyclerView.ViewHolder(binding.root) {
        val binding: ItemViewholderBinding

        init {
            this.binding = binding
        }
    }
}
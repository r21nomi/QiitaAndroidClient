package com.r21nomi.qiitaclientandroid.ui.controller

import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.r21nomi.qiitaclientandroid.R
import com.r21nomi.qiitaclientandroid.databinding.ControllerTagsBinding
import com.r21nomi.qiitaclientandroid.di.component.ControllerComponent
import com.r21nomi.qiitaclientandroid.model.TagModel
import com.r21nomi.qiitaclientandroid.util.ViewUtil
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import javax.inject.Inject

/**
 * Created by Ryota Niinomi on 2016/11/25.
 */
class TagsController : BaseController() {

    companion object {
        val LIMIT = 100
    }

    @Inject
    lateinit var tagModel: TagModel

    private lateinit var binding: ControllerTagsBinding
    private var currentPage: Int = 1

    override fun getLayout(): Int {
        return R.layout.controller_tags
    }

    override fun injectDependency(component: ControllerComponent) {
        component.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = super.onCreateView(inflater, container)

        binding = DataBindingUtil.bind(view)

        tagModel
                .fetchTags(currentPage, LIMIT)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ tags ->
                    val sum: String = Observable
                            .from(tags)
                            .map { tag -> tag.id }
                            .reduce { s1: String?, s2: String? -> s1 + ", " + s2 }
                            .toBlocking()
                            .first()

                    binding.text.text = sum
                }, { throwable ->
                    ViewUtil.showSnackBar(
                            activity ?: return@subscribe,
                            throwable.message ?: return@subscribe
                    )
                })

        return view
    }
}
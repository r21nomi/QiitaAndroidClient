package com.r21nomi.qiitaclientandroid.util

import android.app.Activity
import android.support.design.widget.Snackbar

/**
 * Created by Ryota Niinomi on 2016/11/25.
 */
class ViewUtil {
    companion object {
        fun showSnackBar(activity: Activity, message: String) {
            Snackbar.make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show()
        }
    }
}
package com.moji.menulog.extentions

import android.content.Context
import android.net.ConnectivityManager
import android.support.v4.app.Fragment
import android.view.inputmethod.InputMethodManager

fun Fragment.hideKeyboard() {
    activity?.currentFocus?.let {
        val imm = this.activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(it.windowToken, 0)
    }
}

fun Context.isNetworkAvailable() : Boolean {
    val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetworkInfo = connectivityManager.activeNetworkInfo
    return activeNetworkInfo != null
}
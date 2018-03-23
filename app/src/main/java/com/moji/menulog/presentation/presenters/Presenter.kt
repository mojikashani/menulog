package com.moji.menulog.presentation.presenters

import android.content.Context
import android.net.ConnectivityManager

import com.moji.menulog.presentation.listeners.RestListener
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.Serializable

abstract class Presenter<T : Serializable>(private val context : Context) {

    var runASynchronous = true

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null
    }

    protected fun callApi(baseObservable : Observable<T>, observer: Observer<T>, listener: RestListener){
        if (isNetworkAvailable()) {
            // run synchronised if flag is false
            var observable  = baseObservable
            if (runASynchronous) {
                observable = baseObservable
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
            }
            observable.subscribe(observer)
        } else {
            listener.onNoNetworkError()
        }
    }
}

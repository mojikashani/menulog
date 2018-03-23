package com.moji.menulog.presentation.presenters

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import com.moji.menulog.presentation.listeners.RestListener
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.io.Serializable


abstract class BaseViewSubscriber<T : Serializable, V : RestListener>(private val listener: V) : Observer<T> {

    protected var showProgressView: Boolean = true

    protected val progressMessage: String = "Loading..."

    override fun onSubscribe(d: Disposable) {
        if (showProgressView) {
            listener.showProgress(progressMessage)
        }
    }

    override fun onNext(response: T) {
        onSucceed(response)
        if (showProgressView) {
            listener.hideProgress()
        }
    }

    override fun onError(e: Throwable) {
        if (e is HttpException && e.code() == 401) {
            listener.onAuthorizationError(e)
        }else {// otherwise onError will be called
            listener.onError(e.message)
        }

        // Notify that the progress view should be hidden now
        if (showProgressView) {
            listener.hideProgress()
        }
        e.printStackTrace()
    }

    override fun onComplete() {
    }

    protected abstract fun onSucceed(response: T)

}
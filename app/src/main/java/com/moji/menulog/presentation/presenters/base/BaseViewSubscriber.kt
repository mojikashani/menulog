package com.moji.menulog.presentation.presenters.base

import com.moji.menulog.presentation.views.base.RequestView
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import retrofit2.HttpException


abstract class BaseViewSubscriber<T : Any, V : RequestView>(private val view: V) : Observer<T> {

    open var showProgressView: Boolean = true

    open val progressMessage: String = "Loading..."

    override fun onSubscribe(d: Disposable) {
        if (showProgressView) {
            view.showLoading(progressMessage)
        }
    }

    override fun onNext(response: T) {
        onSucceed(response)
        if (showProgressView) {
            view.hideLoading()
        }
    }

    override fun onError(e: Throwable) {
        if (e is HttpException && e.code() == 401) {
            view.onAuthorizationError(e)
        }else {// otherwise onError will be called
            view.onError(e.message)
        }

        // Notify that the progress view should be hidden now
        if (showProgressView) {
            view.hideLoading()
        }
        e.printStackTrace()
    }

    override fun onComplete() {
    }

    protected abstract fun onSucceed(response: T)

}
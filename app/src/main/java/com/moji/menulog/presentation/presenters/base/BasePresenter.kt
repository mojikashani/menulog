package com.moji.menulog.presentation.presenters.base

import android.content.Context
import android.net.ConnectivityManager
import com.moji.menulog.injection.component.DaggerPresenterInjector
import com.moji.menulog.injection.component.PresenterInjector
import com.moji.menulog.injection.module.ContextModule
import com.moji.menulog.injection.module.NetworkModule
import com.moji.menulog.presentation.presenters.PostcodePresenter
import com.moji.menulog.presentation.presenters.RestaurantPresenter
import com.moji.menulog.presentation.views.base.RequestView
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Base presenter any presenter of the application must extend. It provides initial injections and
 * required methods.
 * @param V the type of the View the presenter is based on
 * @property view the view the presenter is based on
 * @constructor Injects the required dependencies
 */
abstract class BasePresenter<out V : RequestView>(protected val view: V) {

    init {
        val injector: PresenterInjector = DaggerPresenterInjector
                .builder()
                .baseView(view)
                .contextModule(ContextModule)
                .networkModule(NetworkModule)
                .build()
        inject(injector)
    }

    // Set this flag to false for synchronous execution, when in testing
    var runASynchronous = true

    open fun onViewCreated(){}

    open fun onViewDestroyed(){}

    protected fun <T> callApi(baseObservable : Observable<T>, observer: Observer<T>){
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
            view.onNoNetworkError()
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = view.getContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null
    }

    /**
     * Injects the required dependencies
     */
    private fun inject(injector: PresenterInjector) {
        when (this) {
            is RestaurantPresenter -> injector.inject(this)
            is PostcodePresenter -> injector.inject(this)
        }
    }
}
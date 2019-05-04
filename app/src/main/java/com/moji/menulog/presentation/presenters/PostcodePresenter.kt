package com.moji.menulog.presentation.presenters

import com.moji.menulog.model.GetPostcodeResponse
import com.moji.menulog.network.GoogleApi
import com.moji.menulog.presentation.presenters.base.BasePresenter
import com.moji.menulog.presentation.presenters.base.BaseViewSubscriber
import com.moji.menulog.presentation.views.GetPostcodeView
import com.moji.menulog.utils.API_KEY
import javax.inject.Inject

/**
 * Created by moji on 20/3/18.
 */
open class PostcodePresenter(view: GetPostcodeView)
    : BasePresenter<GetPostcodeView>(view) {
    constructor(view: GetPostcodeView, googleApiTest: GoogleApi):this(view){
        googleApi = googleApiTest
    }

    @Inject
    lateinit var googleApi: GoogleApi

    // this method calls all api request and handle all possible scenarios
    fun getPostcode(lan: Double, lng: Double) {
        callApi(googleApi.getPostcode(lan.toString()+","+lng.toString(), API_KEY), PostcodeObserver(view))
    }

    private class PostcodeObserver(val _view: GetPostcodeView) : BaseViewSubscriber<GetPostcodeResponse, GetPostcodeView>(_view) {
        override fun onSucceed(response: GetPostcodeResponse) {
            var postcode = ""
            response.results?.get(0)?.address_components?.let {
                mainLoop@for(component in it)
                {
                    val types: List<String> = component.types ?: continue
                    for (type in types){
                        if(type == "postal_code"){
                            postcode = component.short_name ?: ""
                            break@mainLoop
                        }
                    }
                }

            }

            _view.onPostcodeFetched(postcode)
        }
    }

}
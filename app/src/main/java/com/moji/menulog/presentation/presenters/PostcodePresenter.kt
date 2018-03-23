package com.moji.menulog.presentation.presenters

import android.content.Context
import com.moji.menulog.data.rest.GoogleApi
import com.moji.menulog.data.rest.GoogleEndpoints
import com.moji.menulog.domain.entities.GetPostcodeResponseView
import com.moji.menulog.presentation.listeners.GetPostcodeListener

/**
 * Created by moji on 20/3/18.
 */
class PostcodePresenter(private val context: Context, private val endpoints: GoogleEndpoints, private val listener: GetPostcodeListener)
    : Presenter<GetPostcodeResponseView>(context) {
    constructor(context: Context, listener: GetPostcodeListener): this(context, GoogleApi.getEndpoints(), listener)

    // this method calls all api request and handle all possible scenarios
    fun getPostcode(lan: Double, lng: Double) {
        callApi(endpoints.getPostcode(lan.toString()+","+lng.toString(), GoogleEndpoints.API_KEY), PostcodeObserver(listener), listener)
    }

    private class PostcodeObserver(private var listener: GetPostcodeListener) : BaseViewSubscriber<GetPostcodeResponseView, GetPostcodeListener>(listener) {
        override fun onSucceed(response: GetPostcodeResponseView) {
            var postcode = ""
            response.results?.get(0)?.addressComponents?.let {
                mainLoop@for(component in it)
                {
                    if(component.types != null) {
                        for (type in component.types){
                            if(type == "postal_code"){
                                postcode = component.shortName ?: ""
                                break@mainLoop
                            }
                        }

                    }
                }

            }

            listener.onPostcodeFetched(postcode)
        }
    }

}
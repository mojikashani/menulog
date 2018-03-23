package com.moji.menulog.presentation.presenters

import android.content.Context
import com.moji.menulog.data.rest.RestApi
import com.moji.menulog.domain.entities.GetRestaurantListResponseView
import com.moji.menulog.presentation.listeners.GetRestaurantListListener
import com.moji.menulog.data.rest.Endpoints


class RestaurantPresenter(private val context: Context, private val endpoints: Endpoints, private val listener: GetRestaurantListListener)
    : Presenter<GetRestaurantListResponseView>(context) {
    constructor(context: Context,  listener: GetRestaurantListListener): this(context, RestApi.getEndpoints(), listener)

    // this method calls all api request and handle all possible scenarios
    fun getRestaurantList(postcode : String) {
        callApi(endpoints.getRestaurantList(postcode), RestaurantObserver(listener), listener)
    }

    private class RestaurantObserver(private var listener: GetRestaurantListListener) : BaseViewSubscriber<GetRestaurantListResponseView, GetRestaurantListListener>(listener) {
        override fun onSucceed(response: GetRestaurantListResponseView) {
            listener.onRestaurantListFetched(response.restaurantList)
        }
    }

}


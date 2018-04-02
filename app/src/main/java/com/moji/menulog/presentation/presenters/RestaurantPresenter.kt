package com.moji.menulog.presentation.presenters

import com.moji.menulog.model.GetRestaurantListResponse
import com.moji.menulog.network.GoogleApi
import com.moji.menulog.network.RestaurantApi
import com.moji.menulog.presentation.views.GetRestaurantListView
import com.moji.menulog.presentation.presenters.base.BasePresenter
import com.moji.menulog.presentation.presenters.base.BaseViewSubscriber
import com.moji.menulog.presentation.views.GetPostcodeView
import javax.inject.Inject


class RestaurantPresenter(view: GetRestaurantListView)
    : BasePresenter<GetRestaurantListView>(view) {
    constructor(view: GetRestaurantListView, restaurantApiTest: RestaurantApi):this(view){
        restaurantApi = restaurantApiTest
    }

    @Inject
    lateinit var restaurantApi: RestaurantApi

    // this method calls all api request and handle all possible scenarios
    fun getRestaurantList(postcode : String) {
        callApi(restaurantApi.getRestaurantList(postcode), RestaurantObserver(view))
    }

    private class RestaurantObserver(val _view: GetRestaurantListView) : BaseViewSubscriber<GetRestaurantListResponse, GetRestaurantListView>(_view) {
        override fun onSucceed(response: GetRestaurantListResponse) {
            _view.onRestaurantListFetched(response.Restaurants ?: listOf())
        }
    }

}


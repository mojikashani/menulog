package com.moji.menulog.presentation.views

import com.moji.menulog.model.Restaurant
import com.moji.menulog.presentation.views.base.RequestView

interface GetRestaurantListView : RequestView {
    fun onRestaurantListFetched(restaurantList : List<Restaurant>)
}

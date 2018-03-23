package com.moji.menulog.presentation.listeners

import com.moji.menulog.domain.entities.RestaurantView

interface GetRestaurantListListener : RestListener {
    fun onRestaurantListFetched(restaurantList : List<RestaurantView>?)
}

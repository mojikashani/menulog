package com.moji.menulog.domain.entities

import com.google.gson.annotations.SerializedName

import java.io.Serializable

class GetRestaurantListResponseView : Serializable {

    @SerializedName("Restaurants")
    val restaurantList: List<RestaurantView>? = null
}

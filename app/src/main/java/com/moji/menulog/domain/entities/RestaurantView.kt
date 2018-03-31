package com.moji.menulog.domain.entities

import com.google.gson.annotations.SerializedName

import java.io.Serializable

class RestaurantView : Serializable {

    @SerializedName("Name")
    val name: String? = null

    @SerializedName("RatingStars")
    val ratingStars: Float? = null

    @SerializedName("CuisineTypes")
    val cuisineTypes: List<CuisineTypeView>? = null

    @SerializedName("Logo")
    val logo: List<LogoView>? = null

    val cuisineTypesInString : String
    get() {
        cuisineTypes?.let {
            var cuisineTypesString = ""
            for (cuisine in cuisineTypes) {
                cuisineTypesString += cuisine.name + ", "
            }
            if(cuisineTypesString.length >=2){
                cuisineTypesString= cuisineTypesString.substring(0, cuisineTypesString.length - 2)
            }
            return cuisineTypesString
        } ?: run {
            return ""
        }
    }

}

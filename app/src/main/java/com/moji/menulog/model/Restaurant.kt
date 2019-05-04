package com.moji.menulog.model

data class Restaurant(val Name: String?, val RatingStars: Float?
                      , val CuisineTypes: List<CuisineType>?
                      , val Logo: List<Logo>?){

    val cuisineTypesInString : String
    get() {
        CuisineTypes?.let {
            var cuisineTypesString = ""
            for (cuisine in it) {
                cuisineTypesString += cuisine.Name + ", "
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

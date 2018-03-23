package com.moji.menulog.domain.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class CuisineTypeView : Serializable {

    @SerializedName("Name")
    var name: String? = null
}
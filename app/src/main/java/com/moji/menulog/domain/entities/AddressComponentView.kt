package com.moji.menulog.domain.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class AddressComponentView : Serializable {

    @SerializedName("long_name")
    val longName: String? = null

    @SerializedName("short_name")
    val shortName: String? = null

    @SerializedName("types")
    val types: List<String>? = null
}
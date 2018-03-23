package com.moji.menulog.domain.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class PostCodeResultView : Serializable {

    @SerializedName("address_components")
    val addressComponents: List<AddressComponentView>? = null
}
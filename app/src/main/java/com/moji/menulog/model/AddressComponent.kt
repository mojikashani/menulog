package com.moji.menulog.model

import com.google.gson.annotations.SerializedName

data class AddressComponent(
        @SerializedName("long_name") val long_name: String?
        ,@SerializedName("short_name") val short_name: String?
        ,@SerializedName("types") val types: List<String>?)
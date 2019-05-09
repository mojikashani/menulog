package com.moji.menulog.model

import com.google.gson.annotations.SerializedName

data class PostCodeResult(@SerializedName("address_components") val address_components: List<AddressComponent>?)
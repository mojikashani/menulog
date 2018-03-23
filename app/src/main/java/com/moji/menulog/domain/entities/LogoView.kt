package com.moji.menulog.domain.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class LogoView : Serializable {

    @SerializedName("StandardResolutionURL")
    var standardResolutionURL: String? = null
}
package com.moji.menulog.domain.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class GetPostcodeResponseView : Serializable {

    @SerializedName("results")
    val results: List<PostCodeResultView>? = null
}

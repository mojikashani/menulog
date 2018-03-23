package com.moji.menulog.presentation.listeners

import com.moji.menulog.domain.entities.RestaurantView

/**
 * Created by moji on 20/3/18.
 */
interface GetPostcodeListener : RestListener {
    fun onPostcodeFetched(postcode : String?)
}

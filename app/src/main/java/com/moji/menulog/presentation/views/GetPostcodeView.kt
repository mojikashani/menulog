package com.moji.menulog.presentation.views

import com.moji.menulog.presentation.views.base.RequestView

/**
 * Created by moji on 20/3/18.
 */
interface GetPostcodeView : RequestView {
    fun onPostcodeFetched(postcode : String?)
}

package com.moji.menulog.presentation.activities

import android.os.Bundle
import android.support.v4.app.Fragment
import com.moji.menulog.R
import com.moji.menulog.presentation.activities.base.BaseActivity
import com.moji.menulog.presentation.fragments.PostCodeFragment


class MainActivity : BaseActivity() {
    override val initiallyDisplayedFragment: Fragment by lazy{ PostCodeFragment()}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
    }

}

package com.moji.menulog.presentation.activities

import android.os.Bundle
import android.app.Fragment
import com.moji.menulog.R
import com.moji.menulog.presentation.activities.base.BaseActivity
import com.moji.menulog.presentation.fragments.PostCodeFragment


class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
    }

    override fun getInitiallyDisplayedFragment(): Fragment {
        return PostCodeFragment()
    }

}

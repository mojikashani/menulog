package com.moji.menulog.presentation.activities

import android.os.Bundle
import com.moji.menulog.R
import com.moji.menulog.presentation.fragments.PostCodeFragment
import com.moji.menulog.presentation.fragments.ToolbarFragment


class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
    }

    override fun getInitiallyDisplayedFragment(): ToolbarFragment {
        return PostCodeFragment()
    }

}

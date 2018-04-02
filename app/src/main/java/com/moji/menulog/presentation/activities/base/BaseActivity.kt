package com.moji.menulog.presentation.activities.base

import android.app.Fragment
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.moji.menulog.R
import com.moji.menulog.presentation.presenters.base.BasePresenter
import com.moji.menulog.presentation.fragments.base.BaseFragment
import com.moji.menulog.presentation.views.base.BaseView

/** Base Activity for the app
 * We use fragment for transition between contents to ensure a smoother user experience **/
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        // set initial fragment
        if (savedInstanceState == null) {
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_place_holder, getInitiallyDisplayedFragment())
            fragmentTransaction.commit()
        }
    }

    // activities that extends BaseActivity should determine their initially displayed fragment
    abstract fun getInitiallyDisplayedFragment() : Fragment

}

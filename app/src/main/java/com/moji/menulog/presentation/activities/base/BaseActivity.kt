package com.moji.menulog.presentation.activities.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.moji.menulog.R

/** Base Activity for the app
 * We use fragment for transition between contents to ensure a smoother user experience **/
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        // set initial fragment
        if (savedInstanceState == null) {
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_place_holder, initiallyDisplayedFragment)
            fragmentTransaction.commit()
        }
    }

    // activities that extends BaseActivity should determine their initially displayed fragment
    abstract val initiallyDisplayedFragment : Fragment

}

package com.moji.menulog.presentation.fragments

import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.moji.menulog.R
import kotlinx.android.synthetic.main.view_toolbar.*

abstract class ToolbarFragment : Fragment() {

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (fragmentManager.backStackEntryCount > 0) {
            toolbar_btn_back.visibility = View.VISIBLE
        }
        toolbar_btn_back.setOnClickListener({ activity.onBackPressed() })
    }

    protected fun setToolbarTitle(title: String)
    {
        toolbar_tv_title.text = title
    }

    protected fun hideKeyboard(){
        val view = activity.currentFocus
        if (view != null) {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    protected fun navigateTo(fragment: ToolbarFragment){
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(R.animator.slide_in_right, R.animator.slide_out_left
                , R.animator.slide_in_left, R.animator.slide_out_right)
        fragmentTransaction.replace(R.id.fragment_place_holder, fragment)
        fragmentTransaction.addToBackStack(fragment.javaClass.simpleName)
        fragmentTransaction.commit()
    }

}
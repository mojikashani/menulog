package com.moji.menulog.presentation.fragments.base

import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.moji.menulog.R
import com.moji.menulog.presentation.presenters.base.BasePresenter
import com.moji.menulog.presentation.views.base.RequestView
import kotlinx.android.synthetic.main.view_toolbar.*

abstract class BaseFragment<P : BasePresenter<RequestView>> : RequestView, Fragment() {
    protected lateinit var presenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = instantiatePresenter()
    }

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

    protected fun navigateTo(fragment: Fragment){
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(R.animator.slide_in_right, R.animator.slide_out_left
                , R.animator.slide_in_left, R.animator.slide_out_right)
        fragmentTransaction.replace(R.id.fragment_place_holder, fragment)
        fragmentTransaction.addToBackStack(fragment.javaClass.simpleName)
        fragmentTransaction.commit()
    }

    /**
     * Instantiates the presenter the Activity is based on.
     */
    protected abstract fun instantiatePresenter(): P

    override fun getContext(): Context {
        return activity
    }

}
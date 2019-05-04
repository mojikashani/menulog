package com.moji.menulog.presentation.fragments.base

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentManager?.backStackEntryCount?.let {
            val visibility = if (it > 0) View.VISIBLE else View.GONE
            toolbar_btn_back.visibility = visibility
        }
        toolbar_btn_back.setOnClickListener { activity?.onBackPressed() }
    }

    protected fun setToolbarTitle(title: String) {
        toolbarTitleTextView.text = title
    }

    protected fun navigateTo(fragment: Fragment) {
        fragmentManager?.beginTransaction()?.run {
            setCustomAnimations(R.animator.slide_in_right, R.animator.slide_out_left
                    , R.animator.slide_in_left, R.animator.slide_out_right)
            replace(R.id.fragment_place_holder, fragment)
            addToBackStack(fragment.javaClass.simpleName)
            commit()
        }
    }

    override fun getContext(): Context? {
        return activity?.applicationContext
    }

    /**
     * Instantiates the presenter the Activity is based on.
     */
    protected abstract fun instantiatePresenter(): P

}
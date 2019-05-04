package com.moji.menulog.presentation.fragments

import android.os.Bundle
import android.os.Parcelable
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.moji.menulog.R
import com.moji.menulog.model.Restaurant
import com.moji.menulog.presentation.fragments.base.BaseFragment
import com.moji.menulog.presentation.views.GetRestaurantListView
import com.moji.menulog.presentation.presenters.RestaurantPresenter
import com.moji.menulog.presentation.adapters.RestaurantAdapter
import com.moji.menulog.utils.EXTRA_POSTCODE
import com.moji.menulog.utils.LIST_STATE_KEY
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseFragment<RestaurantPresenter>(), GetRestaurantListView {

    private val newsAdapter = RestaurantAdapter(emptyList())
    private val postcode : String by lazy{ arguments?.getString(EXTRA_POSTCODE) ?: ""}
    private var listState: Parcelable? = null

    override fun instantiatePresenter(): RestaurantPresenter {
        return RestaurantPresenter(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
        presenter.getRestaurantList(postcode)
    }

    override fun onSaveInstanceState(state: Bundle) {
        super.onSaveInstanceState(state)
        // Save list state
        listState = recyclerRestaurants.layoutManager?.onSaveInstanceState()
        state.putParcelable(LIST_STATE_KEY, listState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        savedInstanceState?.let {
            listState = it.getParcelable(LIST_STATE_KEY)
        }
    }

    private fun setViews(){
        swipeRefreshNews.setOnRefreshListener{
            presenter.getRestaurantList(postcode)
        }

        setToolbarTitle(getString(R.string.restaurants_in, postcode))

        with(recyclerRestaurants) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = newsAdapter
        }
    }

    // this is called when requesting for news list is successful
    override fun onRestaurantListFetched(restaurantList: List<Restaurant>) {
        newsAdapter.updateRestaurants(restaurantList)
        if(listState != null){
            recyclerRestaurants.layoutManager?.onRestoreInstanceState(listState)
            listState = null
        }
    }

    // this is called when presenter is done with api calling
    override fun hideLoading() {
        swipeRefreshNews?.let {
            it.isRefreshing = false
        }
    }

    // this is called when 401 error is sent from server
    // in certain circumstances this should navigate user to login page
    override fun onAuthorizationError(e: Throwable) {
        Toast.makeText(activity,getString(R.string.error_authentication_failed), Toast.LENGTH_LONG).show()
    }

    // this is called when any error except for 401 and no network error happens
    // during the api call
    override fun onError(message: String?) {
        Toast.makeText(activity,message?:getString(R.string.error_unknown_error), Toast.LENGTH_LONG).show()
    }

    // this is called when there is no network during the api call
    override fun onNoNetworkError() {
        Toast.makeText(activity,getString(R.string.error_no_internet_connection), Toast.LENGTH_LONG).show()
    }

    // this is called when the presenter starts sending request for data
    override fun showLoading(message: String) {
        swipeRefreshNews?.let {
            it.isRefreshing = true
        }
    }
}

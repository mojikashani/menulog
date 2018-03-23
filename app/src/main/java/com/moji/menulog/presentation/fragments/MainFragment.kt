package com.moji.menulog.presentation.fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.moji.menulog.R
import com.moji.menulog.domain.entities.RestaurantView
import com.moji.menulog.presentation.listeners.GetRestaurantListListener
import com.moji.menulog.presentation.presenters.RestaurantPresenter
import com.moji.menulog.presentation.recyclerviews.RestaurantAdapter
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : ToolbarFragment(), GetRestaurantListListener {

    private lateinit var presenter : RestaurantPresenter
    private val newsAdapter = RestaurantAdapter(emptyList())
    private lateinit var postcode : String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
        presenter.getRestaurantList(postcode)
    }

    private fun setViews(){
        postcode = arguments.getString(PostCodeFragment.EXTRA_POSTCODE)
        presenter = RestaurantPresenter(activity, this)
        swipeRefreshNews.setOnRefreshListener{
            presenter.getRestaurantList(postcode)
        }

        setToolbarTitle(getString(R.string.restaurants_in, postcode))

        recyclerRestaurants.setHasFixedSize(true)
        recyclerRestaurants.layoutManager = LinearLayoutManager(activity)
        recyclerRestaurants.adapter = newsAdapter
    }

    // this is called when requesting for news list is successful
    override fun onRestaurantListFetched(restaurantList: List<RestaurantView>?) {
        newsAdapter.data = restaurantList
    }

    // this is called when presenter is done with api calling
    override fun hideProgress() {
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
    override fun showProgress(message: String) {
        swipeRefreshNews?.let {
            it.isRefreshing = true
        }
    }
}

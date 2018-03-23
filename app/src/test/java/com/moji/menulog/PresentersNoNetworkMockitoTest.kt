package com.moji.menulog

import android.content.Context
import android.net.ConnectivityManager
import com.moji.menulog.data.rest.RestApi
import com.moji.menulog.presentation.listeners.GetRestaurantListListener
import com.moji.menulog.presentation.presenters.RestaurantPresenter
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PresentersNoNetworkMockitoTest {

    @Mock
    private val mMockContext: Context = Mockito.mock(Context::class.java)

    @Mock
    private val mMockRestaurantListener: GetRestaurantListListener = Mockito.mock(GetRestaurantListListener::class.java)

    private lateinit var presenter : RestaurantPresenter

    @Before
    fun setUp() {
        /** mocking Context, ConnectivityManager and NetworkInfo so it assume there is
        no network connection **/
        val connectivityManager: ConnectivityManager = Mockito.mock(ConnectivityManager::class.java)
        Mockito.`when`( mMockContext.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn( connectivityManager )
        Mockito.`when`( connectivityManager.activeNetworkInfo).thenReturn( null )
        presenter = RestaurantPresenter(mMockContext, RestApi.getEndpoints(), mMockRestaurantListener)
        presenter.runASynchronous = false
    }

    /** test NewsPresenter behaviour on a unsuccessful scenario due to no active network
     *  presenter  invoke onNoNetworkError method **/
    @Test
    fun presenter_show_progress_invoke_onNoNetworkError_then_hide_progress() {
        presenter.getRestaurantList("se19")
        Mockito.verify(mMockRestaurantListener).onNoNetworkError()
    }
}


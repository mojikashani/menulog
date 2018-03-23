package com.moji.menulog

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.moji.menulog.data.rest.RestApi
import com.moji.menulog.domain.entities.RestaurantView
import com.moji.menulog.presentation.listeners.GetRestaurantListListener
import com.moji.menulog.presentation.presenters.RestaurantPresenter
import com.nhaarman.mockito_kotlin.capture
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PresentersSucceedMockitoTest {

    @Mock
    private val mMockContext: Context = Mockito.mock(Context::class.java)

    @Mock
    private val mMockRestaurantListener: GetRestaurantListListener = Mockito.mock(GetRestaurantListListener::class.java)

    private lateinit var presenter : RestaurantPresenter

    @Captor
    private lateinit var captor: ArgumentCaptor<List<RestaurantView>>

    @Before
    fun setUp() {
        /** mocking Context, ConnectivityManager and NetworkInfo so it assume there is
        a network connection **/
        val connectivityManager: ConnectivityManager = Mockito.mock(ConnectivityManager::class.java)
        val networkInfo: NetworkInfo = Mockito.mock(NetworkInfo::class.java)
        Mockito.`when`( mMockContext.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn( connectivityManager )
        Mockito.`when`( connectivityManager.activeNetworkInfo).thenReturn( networkInfo )
        presenter = RestaurantPresenter(mMockContext, RestApi.getEndpoints(), mMockRestaurantListener)
        presenter.runASynchronous = false
    }

    /** test NewsPresenter behaviour on a successful scenario
     *  presenter show progress view then return at least an asset then
     *  hide progress view**/
    @Test
    fun presenter_show_progress_returns_a_new_asset_hide_progress() {
        presenter.getRestaurantList("se19")
        Mockito.verify(mMockRestaurantListener).showProgress(Mockito.anyString())
        Mockito.verify(mMockRestaurantListener).onRestaurantListFetched(capture(captor))
        Mockito.verify(mMockRestaurantListener).hideProgress()
        val capturedArgument = captor.value
        Assert.assertTrue(capturedArgument.isNotEmpty())
    }
}


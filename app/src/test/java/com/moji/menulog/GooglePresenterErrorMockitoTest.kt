package com.moji.menulog

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.moji.menulog.data.rest.GoogleEndpoints
import com.moji.menulog.presentation.listeners.GetPostcodeListener
import com.moji.menulog.presentation.listeners.GetRestaurantListListener
import com.moji.menulog.presentation.presenters.PostcodePresenter
import com.moji.menulog.presentation.presenters.RestaurantPresenter
import com.nhaarman.mockito_kotlin.any
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GooglePresenterErrorsMockitoTest {

    @Mock
    private val mMockContext: Context = Mockito.mock(Context::class.java)

    @Mock
    private val mMockEndpoints: GoogleEndpoints = Mockito.mock(GoogleEndpoints::class.java)

    @Mock
    private val mMockPostcodeListener: GetPostcodeListener = Mockito.mock(GetPostcodeListener::class.java)

    @InjectMocks
    lateinit var presenter : PostcodePresenter

    @Before
    fun setUp() {
        /** mocking Context, ConnectivityManager and NetworkInfo so it assume there is
        a network connection **/
        val connectivityManager: ConnectivityManager = Mockito.mock(ConnectivityManager::class.java)
        val networkInfo: NetworkInfo = Mockito.mock(NetworkInfo::class.java)
        Mockito.`when`( mMockContext.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn( connectivityManager )
        Mockito.`when`( connectivityManager.activeNetworkInfo).thenReturn( networkInfo )
        // mocking endpoint, so whenever it gets called it throws an exception
        Mockito.`when`( mMockEndpoints.getPostcode(any(), any())).thenReturn( Observable.error(Exception("Error")))

        presenter.runASynchronous = false
    }

    /** test NewsPresenter behaviour on a unsuccessful scenario due to api call exception
     *  presenter show progress view then invoke onError method then
     *  hide progress view**/
    @Test
    fun presenter_show_progress_invoke_onError_then_hide_progress() {
        presenter.getPostcode(51.417521 , - 0.094144)
        Mockito.verify(mMockPostcodeListener).showProgress(Mockito.anyString())
        Mockito.verify(mMockPostcodeListener).onError("Error")
        Mockito.verify(mMockPostcodeListener).hideProgress()
    }
}

package com.moji.menulog

import android.content.Context
import android.net.ConnectivityManager
import com.moji.menulog.presentation.views.GetPostcodeView
import com.moji.menulog.presentation.presenters.PostcodePresenter
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GooglePresenterNoNetworkMockitoTest {

    @Mock
    private val mMockContext: Context = Mockito.mock(Context::class.java)

    @Mock
    private val mMockPostcodeView: GetPostcodeView = Mockito.mock(GetPostcodeView::class.java)

    private lateinit var presenter : PostcodePresenter

    @Before
    fun setUp() {
        /** mocking Context, ConnectivityManager and NetworkInfo so it assume there is
        no network connection **/
        val connectivityManager: ConnectivityManager = Mockito.mock(ConnectivityManager::class.java)
        Mockito.`when`( mMockContext.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn( connectivityManager )
        Mockito.`when`( connectivityManager.activeNetworkInfo).thenReturn( null )

        Mockito.`when`( mMockPostcodeView.getContext()).thenReturn(mMockContext)
        presenter = PostcodePresenter(mMockPostcodeView)
        presenter.runASynchronous = false
    }

    /** test NewsPresenter behaviour on a unsuccessful scenario due to no active network
     *  presenter  invoke onNoNetworkError method **/
    @Test
    fun presenter_show_progress_invoke_onNoNetworkError_then_hide_progress() {
        presenter.getPostcode(51.417521 , - 0.094144)
        Mockito.verify(mMockPostcodeView).onNoNetworkError()
    }
}

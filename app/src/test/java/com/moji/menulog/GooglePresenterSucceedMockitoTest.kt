package com.moji.menulog

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.moji.menulog.presentation.views.GetPostcodeView
import com.moji.menulog.presentation.presenters.PostcodePresenter
import com.nhaarman.mockito_kotlin.capture
import org.hamcrest.CoreMatchers
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
class GooglePresenterSucceedMockitoTest {

    @Mock
    private val mMockContext: Context = Mockito.mock(Context::class.java)

    @Mock
    private val mMockPostcodeView: GetPostcodeView = Mockito.mock(GetPostcodeView::class.java)

    private lateinit var presenter : PostcodePresenter

    @Captor
    private lateinit var captor: ArgumentCaptor<String>

    @Before
    fun setUp() {
        /** mocking Context, ConnectivityManager and NetworkInfo so it assume there is
        a network connection **/
        val connectivityManager: ConnectivityManager = Mockito.mock(ConnectivityManager::class.java)
        val networkInfo: NetworkInfo = Mockito.mock(NetworkInfo::class.java)
        Mockito.`when`( mMockContext.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn( connectivityManager )
        Mockito.`when`( connectivityManager.activeNetworkInfo).thenReturn( networkInfo )

        Mockito.`when`( mMockPostcodeView.getContext()).thenReturn(mMockContext)
        presenter = PostcodePresenter(mMockPostcodeView)
        presenter.runASynchronous = false
    }

    /** test NewsPresenter behaviour on a successful scenario
     *  presenter show progress view then return at least an asset then
     *  hide progress view**/
    @Test
    fun presenter_show_progress_returns_a_new_asset_hide_progress() {
        presenter.getPostcode(51.417521 , - 0.094144)
        Mockito.verify(mMockPostcodeView).showLoading(Mockito.anyString())
        Mockito.verify(mMockPostcodeView).onPostcodeFetched(capture(captor))
        Mockito.verify(mMockPostcodeView).hideLoading()
        val capturedArgument = captor.value
        Assert.assertThat(capturedArgument, CoreMatchers.containsString("SE19"))
    }
}


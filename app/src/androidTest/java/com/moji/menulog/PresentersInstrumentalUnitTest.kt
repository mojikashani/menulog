package com.moji.menulog

import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.moji.menulog.data.rest.RestApi
import com.moji.menulog.domain.entities.RestaurantView
import com.moji.menulog.presentation.listeners.GetRestaurantListListener
import com.moji.menulog.presentation.presenters.RestaurantPresenter
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.*

@RunWith(AndroidJUnit4::class)
internal class PresentersInstrumentalUnitTest {
    @Mock
    private val mMockRestaurantListener: GetRestaurantListListener = Mockito.mock(GetRestaurantListListener::class.java)
    @Captor
    private lateinit var captor: ArgumentCaptor<List<RestaurantView>>
    private lateinit var appContext : Context
    private lateinit var  presenter : RestaurantPresenter

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
        appContext = InstrumentationRegistry.getTargetContext()
        presenter = RestaurantPresenter(appContext, RestApi.getEndpoints(), mMockRestaurantListener)
        presenter.runASynchronous = false
    }

    @Test
    fun presenter_returns_at_least_one_restaurant() {
        presenter.getRestaurantList("SE19")
        Mockito.verify(mMockRestaurantListener).showProgress(Mockito.anyString())
        Mockito.verify(mMockRestaurantListener).onRestaurantListFetched(captor.capture())
        Mockito.verify(mMockRestaurantListener).hideProgress()
        val capturedArgument = captor.value
        Assert.assertThat(capturedArgument, CoreMatchers.not(emptyList()))
    }
}
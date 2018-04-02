package com.moji.menulog

import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.moji.menulog.model.Restaurant
import com.moji.menulog.presentation.views.GetRestaurantListView
import com.moji.menulog.presentation.presenters.RestaurantPresenter
import com.nhaarman.mockito_kotlin.capture
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.*

@RunWith(AndroidJUnit4::class)
internal class PresentersInstrumentalUnitTest {
    @Mock
    private val mMockRestaurantView: GetRestaurantListView = Mockito.mock(GetRestaurantListView::class.java)
    @Captor
    private lateinit var captor: ArgumentCaptor<List<Restaurant>>
    private lateinit var appContext : Context

    private lateinit var presenter : RestaurantPresenter

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
        appContext = InstrumentationRegistry.getTargetContext()
        Mockito.`when`( mMockRestaurantView.getContext()).thenReturn( appContext )
        presenter = RestaurantPresenter(mMockRestaurantView)
        presenter.runASynchronous = false
    }

    @Test
    fun presenter_returns_at_least_one_restaurant() {
        presenter.getRestaurantList("SE19")
        Mockito.verify(mMockRestaurantView).showLoading(Mockito.anyString())
        Mockito.verify(mMockRestaurantView).onRestaurantListFetched(capture(captor))
        Mockito.verify(mMockRestaurantView).hideLoading()
        val capturedArgument = captor.value
        Assert.assertThat(capturedArgument, CoreMatchers.not(emptyList()))
    }
}
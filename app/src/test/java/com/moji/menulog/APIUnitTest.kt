package com.moji.menulog

import com.moji.menulog.injection.module.NetworkModule.provideRestaurantApi
import com.moji.menulog.injection.module.NetworkModule.provideRetrofitInterface
import com.moji.menulog.network.RestaurantApi
import com.moji.menulog.model.GetRestaurantListResponse
import io.reactivex.observers.TestObserver
import junit.framework.Assert.assertTrue
import org.junit.Test

import org.junit.Before
import javax.inject.Inject

class APIUnitTest {

    @Inject
    private lateinit var endpoints: RestaurantApi

    @Before
    fun setUp(){
        var retrofit = provideRetrofitInterface()
        endpoints = provideRestaurantApi(retrofit)
    }

    @Test
    fun restaurant_api_should_retrieve_result() {
        var testObserver : TestObserver<GetRestaurantListResponse?> =TestObserver.create()
        endpoints.getRestaurantList("SE19").subscribe(testObserver)
        testObserver.assertNoErrors()
        testObserver.assertComplete()
        var results = testObserver.values()
        assertTrue(results[0]?.Restaurants?.isNotEmpty() ?: false)
    }
}

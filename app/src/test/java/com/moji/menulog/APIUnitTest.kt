package com.moji.menulog

import com.moji.menulog.data.rest.Endpoints
import com.moji.menulog.data.rest.RestApi
import com.moji.menulog.domain.entities.GetRestaurantListResponseView
import io.reactivex.observers.TestObserver
import junit.framework.Assert.assertTrue
import org.junit.Test

import org.junit.Before

class APIUnitTest {

    private lateinit var endpoints:Endpoints

    @Before
    fun setUp(){
        endpoints = RestApi.getEndpoints()
    }

    @Test
    fun restaurant_api_should_retrieve_result() {
        var testObserver : TestObserver<GetRestaurantListResponseView?> =TestObserver.create()
        endpoints.getRestaurantList("SE19").subscribe(testObserver)
        testObserver.assertNoErrors()
        testObserver.assertComplete()
        var results = testObserver.values()
        assertTrue(results[0]?.restaurantList?.isNotEmpty() ?: false)
    }
}

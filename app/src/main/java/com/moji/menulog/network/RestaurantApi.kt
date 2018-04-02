package com.moji.menulog.network

import com.moji.menulog.model.GetRestaurantListResponse
import com.moji.menulog.utils.ACCEPT_LANGUAGE
import com.moji.menulog.utils.ACCEPT_TENANT
import com.moji.menulog.utils.AUTHORIZATION

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

/**
 * The interface which provides methods to get result of webservices
 */
interface RestaurantApi {

    @Headers(ACCEPT_TENANT, ACCEPT_LANGUAGE, AUTHORIZATION)
    @GET("restaurants")
    fun getRestaurantList(@Query("q") postCode : String): Observable<GetRestaurantListResponse>
}

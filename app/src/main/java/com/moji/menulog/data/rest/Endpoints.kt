package com.moji.menulog.data.rest

import com.moji.menulog.domain.entities.GetRestaurantListResponseView

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface Endpoints {
    companion object {
        const val ACCEPT_TENANT = "Accept-Tenant: uk"
        const val ACCEPT_LANGUAGE = "Accept-Language: en-GB"
        const val AUTHORIZATION = "Authorization: Basic VGVjaFRlc3Q6bkQ2NGxXVnZreDVw"
    }
    @Headers(ACCEPT_TENANT, ACCEPT_LANGUAGE, AUTHORIZATION)
    @GET("restaurants")
    fun getRestaurantList(@Query("q") postCode : String): Observable<GetRestaurantListResponseView>
}

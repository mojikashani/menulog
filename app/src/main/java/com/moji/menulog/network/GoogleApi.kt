package com.moji.menulog.network

import com.moji.menulog.model.GetPostcodeResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * The interface which provides methods to get result of webservices
 */
interface GoogleApi {
    @GET("maps/api/geocode/json")
    fun getPostcode(@Query("latlng") location : String, @Query("key") key : String): Observable<GetPostcodeResponse>
}

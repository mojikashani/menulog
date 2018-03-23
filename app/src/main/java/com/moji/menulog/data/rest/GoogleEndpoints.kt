package com.moji.menulog.data.rest

import com.moji.menulog.domain.entities.GetPostcodeResponseView
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleEndpoints {
    companion object {
        const val API_KEY = "AIzaSyAjva_k_mDjx6OWAhNzaAoOjLgDy26WoDM"}
    @GET("maps/api/geocode/json")
    fun getPostcode(@Query("latlng") location : String, @Query("key") key : String): Observable<GetPostcodeResponseView>
}

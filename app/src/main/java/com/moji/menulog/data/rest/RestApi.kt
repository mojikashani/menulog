package com.moji.menulog.data.rest

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

import java.util.concurrent.TimeUnit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RestApi {
    private val endpoints = provideApiEndpoints()

    private fun provideApiEndpoints(): Endpoints {
        val httpClient = OkHttpClient.Builder()
        httpClient.readTimeout(30, TimeUnit.SECONDS)
        httpClient.connectTimeout(30, TimeUnit.SECONDS)

        val retrofit = Retrofit.Builder()
                .baseUrl(" https://public.je-apis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient.build())
                .build()

        return retrofit.create(Endpoints::class.java)
    }

    fun getEndpoints(): Endpoints {
        return endpoints
    }
}

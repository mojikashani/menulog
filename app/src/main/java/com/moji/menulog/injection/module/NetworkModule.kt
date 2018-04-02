package com.moji.menulog.injection.module

import com.moji.menulog.network.GoogleApi
import com.moji.menulog.network.RestaurantApi
import com.moji.menulog.utils.BASE_URL
import com.moji.menulog.utils.GOOGLE_BASE_URL
import dagger.Module
import dagger.Provides
import dagger.Reusable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named

/**
 * Module which provides all required dependencies about network
 */
@Module
@Suppress("unused")
object NetworkModule {
    /**
     * Provides the Post service implementation.
     * @param retrofit the Retrofit object used to instantiate the service
     * @return the Restaurant service implementation.
     */
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideRestaurantApi(@Named("retrofit") retrofit: Retrofit): RestaurantApi {
        return retrofit.create(RestaurantApi::class.java)
    }

    /**
     * Provides the Post service implementation.
     * @param retrofit the Retrofit object used to instantiate the service
     * @return the Restaurant service implementation.
     */
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideGoogleApi(@Named("googleRetrofit")googleRetrofit: Retrofit): GoogleApi {
        return googleRetrofit.create(GoogleApi::class.java)
    }

    /**
     * Provides the Retrofit object.
     * @return the Retrofit object
     */
    @Provides
    @Reusable
    @JvmStatic
    @Named("retrofit")
    internal fun provideRetrofitInterface(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    /**
     * Provides the Retrofit object.
     * @return the Retrofit object
     */
    @Provides
    @Reusable
    @JvmStatic
    @Named("googleRetrofit")
    internal fun provideGoogleRetrofitInterface(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(GOOGLE_BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }
}
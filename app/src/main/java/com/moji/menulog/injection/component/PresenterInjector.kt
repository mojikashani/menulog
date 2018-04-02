package com.moji.menulog.injection.component

import com.moji.menulog.injection.module.ContextModule
import com.moji.menulog.injection.module.NetworkModule
import com.moji.menulog.presentation.presenters.PostcodePresenter
import com.moji.menulog.presentation.presenters.RestaurantPresenter
import com.moji.menulog.presentation.views.base.BaseView
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * Component providing inject() methods for presenters.
 */
@Singleton
@Component(modules = [(ContextModule::class), (NetworkModule::class)])
interface PresenterInjector {
    /**
     * Injects required dependencies into the specified PostPresenter.
     * @param postPresenter PostPresenter in which to inject the dependencies
     */
    fun inject(restaurantPresenter: RestaurantPresenter)
    fun inject(postcodePresenter: PostcodePresenter)

    @Component.Builder
    interface Builder {
        fun build(): PresenterInjector

        fun networkModule(networkModule: NetworkModule): Builder
        fun contextModule(contextModule: ContextModule): Builder

        @BindsInstance
        fun baseView(baseView: BaseView): Builder
    }
}
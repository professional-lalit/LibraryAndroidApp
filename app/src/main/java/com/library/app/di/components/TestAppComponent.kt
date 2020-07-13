package com.library.app.di.components

import com.library.app.common.UITestApplication
import com.library.app.di.modules.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class, TestNetworkModule::class, RepositoryModule::class
        , ViewModelFactoryModule::class, UIInteractorModule::class, AuthViewModelsModule::class,
        ActivityBuildersModule::class, TestAppModule::class]
)
interface TestAppComponent : AndroidInjector<UITestApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: UITestApplication): Builder

        fun build(): TestAppComponent
    }

}
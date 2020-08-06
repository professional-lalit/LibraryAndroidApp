package com.library.app.di.components

import com.library.app.common.CustomApplication
import com.library.app.common.Prefs
import com.library.app.di.modules.*
import com.library.app.screens.common.UIInteractor
import com.library.app.screens.main.fragments.books.book_details.BookDetailsUIInteractor
import com.library.app.screens.main.fragments.books.book_list.BookListUIInteractor
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class, NetworkModule::class, RepositoryModule::class
        , ViewModelFactoryModule::class, UIInteractorModule::class, AuthViewModelsModule::class,
        ActivityBuildersModule::class, AppModule::class]
)
interface AppComponent : AndroidInjector<CustomApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: CustomApplication): Builder

        fun build(): AppComponent
    }

    fun getPrefs(): Prefs

    fun getBookListUIInteractor(): BookListUIInteractor

    fun getBookDetailsUIInteractor(): BookDetailsUIInteractor
}
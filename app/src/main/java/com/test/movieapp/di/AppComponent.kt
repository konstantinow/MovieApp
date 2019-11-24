package com.test.movieapp.di

import android.content.Context
import com.test.movieapp.main.MainActivity
import com.test.movieapp.main.MainPresenter
import com.test.movieapp.main.MoviesAdapter
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [NetworkModule::class, AppModule::class])
@Singleton
interface AppComponent {
    fun inject(moviesAdapter: MoviesAdapter)
    fun inject(mainView: MainActivity)
    fun inject(mainPresenter: MainPresenter)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }
}

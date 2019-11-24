package com.test.movieapp.di

import com.test.movieapp.main.IMainModel
import com.test.movieapp.main.MainModel
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface AppModule {
    @Binds
    @Singleton
    fun provideMainModel(mainModel: MainModel): IMainModel
}
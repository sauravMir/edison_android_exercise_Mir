package jp.speakbuddy.edisonandroidexercise.infrastructure.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jp.speakbuddy.edisonandroidexercise.infrastructure.common.retrofitprovider.RetroFitProvider
import retrofit2.Retrofit

@InstallIn(SingletonComponent::class)
@Module

object NetworkModule {
    @Provides
    fun provideRetrofit(): Retrofit {
        return RetroFitProvider.getRetrofit()
    }

}
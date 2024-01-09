package jp.speakbuddy.edisonandroidexercise.infrastructure.network.fact.networkservice

import retrofit2.Retrofit

object FactServiceProvider {

    fun provide(retrofit: Retrofit): FactService = retrofit.create(FactService::class.java)
}
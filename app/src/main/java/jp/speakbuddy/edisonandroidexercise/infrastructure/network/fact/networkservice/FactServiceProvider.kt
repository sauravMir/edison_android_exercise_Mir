package jp.speakbuddy.edisonandroidexercise.infrastructure.network.fact.networkservice

import retrofit2.Retrofit
import javax.inject.Inject

class FactServiceProvider @Inject constructor(val retrofit: Retrofit) {

    fun provide(): FactService = retrofit.create(FactService::class.java)
}
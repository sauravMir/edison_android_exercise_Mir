package jp.speakbuddy.edisonandroidexercise.infrastructure.network.fact.networkservice

import jp.speakbuddy.edisonandroidexercise.model.entities.fact.Fact
import retrofit2.http.GET

interface FactService {
    @GET("fact")
    suspend fun getFact(): Fact
}
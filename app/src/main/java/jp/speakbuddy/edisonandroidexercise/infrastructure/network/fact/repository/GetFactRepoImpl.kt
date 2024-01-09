package jp.speakbuddy.edisonandroidexercise.infrastructure.network.fact.repository

import jp.speakbuddy.edisonandroidexercise.infrastructure.common.retrofitprovider.RetroFitProvider
import jp.speakbuddy.edisonandroidexercise.infrastructure.network.fact.networkservice.FactServiceProvider
import jp.speakbuddy.edisonandroidexercise.model.entities.fact.Fact
import jp.speakbuddy.edisonandroidexercise.model.repositories.fact.GetFactRepo

class GetFactRepoImpl: GetFactRepo {
    override suspend fun getFact(): Fact {
        return FactServiceProvider.provide(RetroFitProvider.getRetrofit()).getFact()
    }
}
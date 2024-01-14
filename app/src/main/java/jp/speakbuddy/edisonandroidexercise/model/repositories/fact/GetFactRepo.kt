package jp.speakbuddy.edisonandroidexercise.model.repositories.fact

import jp.speakbuddy.edisonandroidexercise.model.entities.fact.Fact

interface GetFactRepo {
    suspend fun getFact(): Fact
}
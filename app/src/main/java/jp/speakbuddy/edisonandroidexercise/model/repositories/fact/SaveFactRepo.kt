package jp.speakbuddy.edisonandroidexercise.model.repositories.fact

import jp.speakbuddy.edisonandroidexercise.model.entities.fact.Fact

interface SaveFactRepo {
    suspend fun saveFact(fact: Fact): Boolean
}
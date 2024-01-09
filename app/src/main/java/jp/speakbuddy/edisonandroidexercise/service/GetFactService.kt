package jp.speakbuddy.edisonandroidexercise.service

import jp.speakbuddy.edisonandroidexercise.model.entities.fact.Fact
import jp.speakbuddy.edisonandroidexercise.model.repositories.fact.GetFactRepo

class GetFactService {
    suspend fun getFact(repo: GetFactRepo): Fact {
        return repo.getFact()
    }
}
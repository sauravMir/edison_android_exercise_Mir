package jp.speakbuddy.edisonandroidexercise.service.fact

import jp.speakbuddy.edisonandroidexercise.model.entities.fact.Fact
import jp.speakbuddy.edisonandroidexercise.model.repositories.fact.GetFactRepo
import javax.inject.Inject

class GetFactService @Inject constructor() {
    suspend fun getFact(repo: GetFactRepo): Fact {
        return repo.getFact()
    }
}
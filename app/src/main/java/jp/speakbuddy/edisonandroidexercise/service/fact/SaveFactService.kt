package jp.speakbuddy.edisonandroidexercise.service.fact

import jp.speakbuddy.edisonandroidexercise.model.entities.fact.Fact
import jp.speakbuddy.edisonandroidexercise.model.repositories.fact.SaveFactRepo

class SaveFactService {
    suspend fun saveFact(saveFactRepo: SaveFactRepo, fact: Fact): Boolean {
        return saveFactRepo.saveFact(fact)
    }
}
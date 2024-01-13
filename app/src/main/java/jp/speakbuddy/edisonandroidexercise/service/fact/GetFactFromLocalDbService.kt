package jp.speakbuddy.edisonandroidexercise.service.fact

import jp.speakbuddy.edisonandroidexercise.model.repositories.fact.GetFactFromLocalDbRepo

class GetFactFromLocalDbService {
    suspend fun getFactStringFromLocalDb(key: String, getFactFromLocalDbRepo: GetFactFromLocalDbRepo): String? {
        return getFactFromLocalDbRepo.getFactStringFromLocalDb(key)
    }
    suspend fun getFactLengthFromLocalDb(key: String, getFactFromLocalDbRepo: GetFactFromLocalDbRepo): Int? {
        return getFactFromLocalDbRepo.getFactLengthFromLocalDb(key)
    }
}
package jp.speakbuddy.edisonandroidexercise.model.repositories.fact

interface GetFactFromLocalDbRepo {
    suspend fun getFactStringFromLocalDb(key: String): String?

    suspend fun getFactLengthFromLocalDb(key: String): Int?
}
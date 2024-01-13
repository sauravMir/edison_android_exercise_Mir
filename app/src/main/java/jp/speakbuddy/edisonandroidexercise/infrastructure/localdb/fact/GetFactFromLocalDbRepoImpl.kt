package jp.speakbuddy.edisonandroidexercise.infrastructure.localdb.fact

import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import jp.speakbuddy.edisonandroidexercise.infrastructure.localdb.db.dto.DataStoreDto
import jp.speakbuddy.edisonandroidexercise.infrastructure.localdb.db.keys.LocalDBKeys
import jp.speakbuddy.edisonandroidexercise.model.repositories.fact.GetFactFromLocalDbRepo
import kotlinx.coroutines.flow.first
import java.io.IOException

class GetFactFromLocalDbRepoImpl constructor(
    private val dataStoreDto: DataStoreDto,
): GetFactFromLocalDbRepo {
    override suspend fun getFactStringFromLocalDb(key: String): String? {
        val stringKey = stringPreferencesKey(LocalDBKeys.FACT_STRING)
        return try {
            dataStoreDto.dataStore.data.first()[stringKey]
        } catch (exc: Exception) {
            when (exc) {
                is NoSuchElementException, is IOException -> {
                    null
                }

                else -> null
            }
        }
    }

    override suspend fun getFactLengthFromLocalDb(key: String): Int? {
        val lengthKey = intPreferencesKey(LocalDBKeys.FACT_LENGTH_INT)
        return try {
            dataStoreDto.dataStore.data.first()[lengthKey]
        } catch (exc: Exception) {
            when (exc) {
                is NoSuchElementException, is IOException -> {
                    null
                }

                else -> null
            }
        }
    }

}
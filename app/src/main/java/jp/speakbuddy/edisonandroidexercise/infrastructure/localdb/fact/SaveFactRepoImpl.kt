package jp.speakbuddy.edisonandroidexercise.infrastructure.localdb.fact

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import jp.speakbuddy.edisonandroidexercise.infrastructure.localdb.db.dto.DataStoreDto
import jp.speakbuddy.edisonandroidexercise.infrastructure.localdb.db.keys.LocalDBKeys
import jp.speakbuddy.edisonandroidexercise.model.entities.fact.Fact
import jp.speakbuddy.edisonandroidexercise.model.repositories.fact.SaveFactRepo
import java.io.IOException
import javax.inject.Inject

class SaveFactRepoImpl @Inject constructor(
    private val dataStoreDto: DataStoreDto,
) : SaveFactRepo {
    override suspend fun saveFact(fact: Fact): Boolean {
        val lengthKey = intPreferencesKey(LocalDBKeys.FACT_LENGTH_INT)
        val stringKey = stringPreferencesKey(LocalDBKeys.FACT_STRING)
        return try {
            dataStoreDto.dataStore.edit {
                it[lengthKey] = fact.length
                it[stringKey] = fact.fact
            }
            true
        } catch (exc: IOException) {
            false
        }

    }
}
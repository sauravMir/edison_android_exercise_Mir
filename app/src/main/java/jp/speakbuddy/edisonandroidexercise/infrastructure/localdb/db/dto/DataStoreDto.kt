package jp.speakbuddy.edisonandroidexercise.infrastructure.localdb.db.dto

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

data class DataStoreDto(
    val dataStore: DataStore<Preferences>
) {
}
package jp.speakbuddy.edisonandroidexercise.infrastructure.localdb.db.dto

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import javax.inject.Inject

data class DataStoreDto @Inject constructor(
    val dataStore: DataStore<Preferences>
)
package jp.speakbuddy.edisonandroidexercise.infrastructure.di.fact

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jp.speakbuddy.edisonandroidexercise.infrastructure.localdb.db.dto.DataStoreDto
import jp.speakbuddy.edisonandroidexercise.infrastructure.presenter.main.dataStore
import jp.speakbuddy.edisonandroidexercise.infrastructure.presenter.viewmodels.fact.FactViewModel

@InstallIn(SingletonComponent::class)
@Module
object LocalDbProvider {
    @Provides
    fun provideDatastore(@ApplicationContext appContext: Context): DataStoreDto {
        return DataStoreDto(appContext.dataStore)
    }
}
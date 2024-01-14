package jp.speakbuddy.edisonandroidexercise.infrastructure.di.fact

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jp.speakbuddy.edisonandroidexercise.infrastructure.localdb.db.dto.DataStoreDto
import jp.speakbuddy.edisonandroidexercise.infrastructure.localdb.fact.GetFactFromLocalDbRepoImpl
import jp.speakbuddy.edisonandroidexercise.infrastructure.localdb.fact.SaveFactRepoImpl
import jp.speakbuddy.edisonandroidexercise.infrastructure.network.fact.networkservice.FactServiceProvider
import jp.speakbuddy.edisonandroidexercise.infrastructure.network.fact.repository.GetFactRepoImpl
import jp.speakbuddy.edisonandroidexercise.model.repositories.fact.GetFactFromLocalDbRepo
import jp.speakbuddy.edisonandroidexercise.model.repositories.fact.GetFactRepo
import jp.speakbuddy.edisonandroidexercise.model.repositories.fact.SaveFactRepo

@InstallIn(SingletonComponent::class)
@Module
object FactRepoProvider {
    @Provides
    fun provideSaveFactRepo(dataStoreDto: DataStoreDto): SaveFactRepo {
        return SaveFactRepoImpl(dataStoreDto)
    }

    @Provides
    fun provideGetFactFromLocalDbRepo(dataStoreDto: DataStoreDto): GetFactFromLocalDbRepo {
        return GetFactFromLocalDbRepoImpl(dataStoreDto)
    }

    @Provides
    fun provideGetFactRepo(factServiceProvider: FactServiceProvider): GetFactRepo {
        return GetFactRepoImpl(factServiceProvider)
    }
}
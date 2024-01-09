package jp.speakbuddy.edisonandroidexercise.infrastructure.presenter.viewmodels.fact

import androidx.lifecycle.ViewModel
import jp.speakbuddy.edisonandroidexercise.infrastructure.network.fact.networkservice.FactServiceProvider
import jp.speakbuddy.edisonandroidexercise.infrastructure.network.fact.repository.GetFactRepoImpl
import jp.speakbuddy.edisonandroidexercise.service.GetFactService
import kotlinx.coroutines.runBlocking

class FactViewModel : ViewModel() {
    fun updateFact(completion: () -> Unit): String =
        runBlocking {
            try {
                val factRepo = GetFactRepoImpl()
                val factService = GetFactService()
                factService.getFact(factRepo).fact
            } catch (e: Throwable) {
                "something went wrong. error = ${e.message}"
            }.also { completion() }
        }
}

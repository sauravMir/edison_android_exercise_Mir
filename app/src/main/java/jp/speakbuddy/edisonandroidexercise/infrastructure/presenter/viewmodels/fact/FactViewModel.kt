package jp.speakbuddy.edisonandroidexercise.infrastructure.presenter.viewmodels.fact

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jp.speakbuddy.edisonandroidexercise.infrastructure.network.fact.repository.GetFactRepoImpl
import jp.speakbuddy.edisonandroidexercise.infrastructure.utils.constant.Constants
import jp.speakbuddy.edisonandroidexercise.model.entities.fact.Fact
import jp.speakbuddy.edisonandroidexercise.model.repositories.fact.GetFactFromLocalDbRepo
import jp.speakbuddy.edisonandroidexercise.model.repositories.fact.SaveFactRepo
import jp.speakbuddy.edisonandroidexercise.service.fact.GetFactService
import jp.speakbuddy.edisonandroidexercise.service.fact.SaveFactService
import kotlinx.coroutines.launch

class FactViewModel(
    private val factRepo: GetFactRepoImpl,
    private val factService: GetFactService
) : ViewModel() {
    private var _facts = MutableLiveData<Fact>()
    val facts = _facts

    fun updateFact(onErrorFact: Fact) {
        viewModelScope.launch {
            try {
                _facts.value = factService.getFact(factRepo)
            } catch (e: Throwable) {
                "something went wrong. error = ${e.message}"
                _facts.value = onErrorFact
            }
        }
    }

    suspend fun saveFact(
        saveFactService: SaveFactService,
        saveFactRepo: SaveFactRepo,
        fact: Fact
    ): Boolean {
        return saveFactService.saveFact(saveFactRepo, fact)
    }

    fun updateFactFromLocalDb(fact: Fact) {
        _facts.value = fact
    }

    fun isLengthGreaterThan100(fact: String): Boolean{
        return fact.length > 100
    }

    // when you write "Cats" it will return false (as mentioned in the requirement)
    fun isMultipleCats(fact: String): Boolean{
        return fact.contains(Constants.CATS_TAG)
    }

    suspend fun readFactString(key: String, getFactFromLocalDbRepo: GetFactFromLocalDbRepo): String? {
        return getFactFromLocalDbRepo.getFactStringFromLocalDb(key)
    }

    suspend fun readFactLength(key: String, getFactFromLocalDbRepo: GetFactFromLocalDbRepo): Int? {
        return getFactFromLocalDbRepo.getFactLengthFromLocalDb(key)
    }
}

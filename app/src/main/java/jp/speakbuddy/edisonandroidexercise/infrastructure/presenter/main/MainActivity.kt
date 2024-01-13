package jp.speakbuddy.edisonandroidexercise.infrastructure.presenter.main

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import jp.speakbuddy.edisonandroidexercise.infrastructure.localdb.db.dto.DataStoreDto
import jp.speakbuddy.edisonandroidexercise.infrastructure.localdb.db.keys.LocalDBKeys
import jp.speakbuddy.edisonandroidexercise.infrastructure.localdb.fact.GetFactFromLocalDbRepoImpl
import jp.speakbuddy.edisonandroidexercise.infrastructure.localdb.fact.SaveFactRepoImpl
import jp.speakbuddy.edisonandroidexercise.infrastructure.network.fact.repository.GetFactRepoImpl
import jp.speakbuddy.edisonandroidexercise.infrastructure.presenter.composable.fact.FactScreen
import jp.speakbuddy.edisonandroidexercise.infrastructure.presenter.viewmodels.fact.FactViewModel
import jp.speakbuddy.edisonandroidexercise.infrastructure.utils.theme.EdisonAndroidExerciseTheme
import jp.speakbuddy.edisonandroidexercise.model.entities.fact.Fact
import jp.speakbuddy.edisonandroidexercise.model.repositories.fact.GetFactFromLocalDbRepo
import jp.speakbuddy.edisonandroidexercise.model.repositories.fact.SaveFactRepo
import jp.speakbuddy.edisonandroidexercise.service.fact.GetFactService
import jp.speakbuddy.edisonandroidexercise.service.fact.SaveFactService
import kotlinx.coroutines.launch


private const val FACT_NAME = "Fact"
val Context.dataStore : DataStore<Preferences> by preferencesDataStore(
name = FACT_NAME
)
class MainActivity : ComponentActivity() {
    private val getFactRepo: GetFactRepoImpl = GetFactRepoImpl()
    private val getFactService: GetFactService = GetFactService()
    private val saveFactService: SaveFactService = SaveFactService()
    private var dataStoreDto:DataStoreDto? = null
    private var saveFactRepo: SaveFactRepo? = null
    private var getFactFromLocalDbRepo: GetFactFromLocalDbRepo? = null


    private val onErrorFact: Fact = Fact("", 0)
    var viewModel: FactViewModel? = null
    private var viewModelFactory: FactViewModelFactory? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModelFactory = FactViewModelFactory(getFactRepo, getFactService)
        viewModelFactory?.let {
            viewModel = ViewModelProvider(this, it)[FactViewModel::class.java]
        }
        dataStoreDto = DataStoreDto(dataStore)
        saveFactRepo = SaveFactRepoImpl(dataStoreDto!!)
        getFactFromLocalDbRepo = GetFactFromLocalDbRepoImpl(dataStoreDto!!)
        setContent {
            EdisonAndroidExerciseTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FactScreen(viewModel!!) {
                        onClickUpdate()
                    }
                }
            }
        }
        observeFactsData()
    }

    private fun onClickUpdate() {
        viewModel?.updateFact(onErrorFact)
    }
    fun observeFactsData() {
        viewModel?.facts?.observe(this) {
            viewModel?.viewModelScope?.launch {
                viewModel?.saveFact(saveFactService, saveFactRepo!!, it)
            }
        }
    }

    fun readDataStore() {
        viewModel?.viewModelScope?.launch {
            val factString = viewModel?.readFactString(LocalDBKeys.FACT_STRING, getFactFromLocalDbRepo!!) ?: ""
            val length = viewModel?.readFactLength(LocalDBKeys.FACT_LENGTH_INT, getFactFromLocalDbRepo!!) ?: 0
            viewModel?.updateFactFromLocalDb(Fact.createFact(factString, length))
        }
    }

    override fun onStart() {
        super.onStart()
        readDataStore()
    }
}

class FactViewModelFactory(
    private val factRepo: GetFactRepoImpl,
    private val factService: GetFactService
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FactViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FactViewModel(factRepo, factService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
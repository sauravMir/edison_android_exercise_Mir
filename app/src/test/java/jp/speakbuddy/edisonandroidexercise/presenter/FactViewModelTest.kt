package jp.speakbuddy.edisonandroidexercise.presenter

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import jp.speakbuddy.edisonandroidexercise.infrastructure.presenter.viewmodels.fact.FactViewModel
import jp.speakbuddy.edisonandroidexercise.model.entities.fact.Fact
import jp.speakbuddy.edisonandroidexercise.model.repositories.fact.GetFactFromLocalDbRepo
import jp.speakbuddy.edisonandroidexercise.model.repositories.fact.GetFactRepo
import jp.speakbuddy.edisonandroidexercise.model.repositories.fact.SaveFactRepo
import jp.speakbuddy.edisonandroidexercise.service.fact.GetFactService
import jp.speakbuddy.edisonandroidexercise.service.fact.SaveFactService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException

@ExperimentalCoroutinesApi
class FactViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()

    lateinit var repository: GetFactRepo
    lateinit var service: GetFactService
    lateinit var viewModel: FactViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk<GetFactRepo>()
        service = GetFactService()
        viewModel = FactViewModel(repository, service)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cancel()
    }

    @Test
    fun updateFact_Success() {
        val factStr = "teststr"
        val length = 7
        val factStrError = "error"
        val lengthError = 5

        val fact: Fact = Fact.createFact(factStr, length)
        val factError: Fact = Fact.createFact(factStrError, lengthError)

        coEvery { repository.getFact() } returns fact
        val viewModel = FactViewModel(repository, service)

        viewModel.updateFact(factError)

        coVerify(exactly = 1) {
            repository.getFact()
        }

        Assert.assertEquals(factStr, viewModel.facts.value?.fact)
    }

    @Test
    fun updateFact_Exception() {
        val factStrError = "error"
        val lengthError = 5

        val factError: Fact = Fact.createFact(factStrError, lengthError)

        coEvery { repository.getFact() }  throws IOException("exc")
        val viewModel = FactViewModel(repository, service)

        viewModel.updateFact(factError)

        coVerify(exactly = 1) {
            repository.getFact()
        }

        Assert.assertEquals(factStrError, viewModel.facts.value?.fact)
    }

    @Test
    fun saveFact_success(): Unit = runBlocking{
        val factStr = "teststr"
        val length = 7
        val saveFactService = SaveFactService()
        val saveFactRepo: SaveFactRepo = mockk<SaveFactRepo>()

        val fact: Fact = Fact.createFact(factStr, length)

        coEvery { saveFactRepo.saveFact(fact) }  returns true

        val result = viewModel.saveFact(saveFactService,saveFactRepo, fact)

        coVerify(exactly = 1) {
            saveFactRepo.saveFact(fact)
        }

        Assert.assertEquals(result, true)
    }

    @Test
    fun updateFactFromLocalDb() {
        val factStr = "teststr"
        val length = 7

        val fact: Fact = Fact.createFact(factStr, length)

        viewModel.updateFactFromLocalDb(fact)

        Assert.assertEquals(viewModel.facts.value?.fact, factStr)
        Assert.assertEquals(viewModel.facts.value?.length, length)
    }

    @Test
    fun isLengthGreaterThan100_length100() {
        //length 100
        val factStr = "teststr123teststr123teststr123teststr123teststr123teststr123teststr123teststr123teststr123teststr123"
        val result = viewModel.isLengthGreaterThan100(factStr)

        Assert.assertEquals(result, false)
    }
    @Test
    fun isLengthGreaterThan100_lengthLessThan100() {
        //length 100
        val factStr = "teststr123"
        val result = viewModel.isLengthGreaterThan100(factStr)

        Assert.assertEquals(result, false)
    }

    @Test
    fun isLengthGreaterThan100_lengthGreater100() {
        //length 100
        val factStr = "teststr123teststr123teststr123teststr123teststr123teststr123teststr123teststr123teststr123teststr123123"
        val result = viewModel.isLengthGreaterThan100(factStr)

        Assert.assertEquals(result, true)
    }

    @Test
    fun isMultipleCats_fail1() {
        //length 100
        val factStr = "I dont like Cats"
        val result = viewModel.isMultipleCats(factStr)

        Assert.assertEquals(result, false)
    }

    @Test
    fun isMultipleCats_fail2() {
        //length 100
        val factStr = "I dont like Cat"
        val result = viewModel.isMultipleCats(factStr)

        Assert.assertEquals(result, false)
    }

    @Test
    fun isMultipleCats_pass() {
        //length 100
        val factStr = "I dont like cats"
        val result = viewModel.isMultipleCats(factStr)

        Assert.assertEquals(result, true)
    }

    @Test
    fun readFactString_pass() = runBlocking{
        val readString = "factStr"
        val getFactFromLocalDbRepo: GetFactFromLocalDbRepo = mockk<GetFactFromLocalDbRepo>()
        coEvery { getFactFromLocalDbRepo.getFactStringFromLocalDb(any<String>()) } coAnswers {readString}
        val result = viewModel.readFactString("key", getFactFromLocalDbRepo)

        Assert.assertEquals(result, readString)
    }

    @Test
    fun readFactString_fail() = runBlocking{
        val readString: String? = null
        val getFactFromLocalDbRepo: GetFactFromLocalDbRepo = mockk<GetFactFromLocalDbRepo>()
        coEvery { getFactFromLocalDbRepo.getFactStringFromLocalDb(any<String>()) } coAnswers {readString}
        val result = viewModel.readFactString("key", getFactFromLocalDbRepo)

        Assert.assertEquals(result, readString)
    }

    @Test
    fun readFactLength_pass() = runBlocking{
        val readLength = 10
        val getFactFromLocalDbRepo: GetFactFromLocalDbRepo = mockk<GetFactFromLocalDbRepo>()
        coEvery { getFactFromLocalDbRepo.getFactLengthFromLocalDb(any<String>()) } coAnswers {readLength}
        val result = viewModel.readFactLength("key", getFactFromLocalDbRepo)

        Assert.assertEquals(result, readLength)
    }

    @Test
    fun readFactLength_fail() = runBlocking{
        val readLength: Int? = null
        val getFactFromLocalDbRepo: GetFactFromLocalDbRepo = mockk<GetFactFromLocalDbRepo>()
        coEvery { getFactFromLocalDbRepo.getFactLengthFromLocalDb(any<String>()) } coAnswers {readLength}
        val result = viewModel.readFactLength("key", getFactFromLocalDbRepo)

        Assert.assertEquals(result, readLength)
    }
}

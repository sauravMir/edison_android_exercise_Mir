package jp.speakbuddy.edisonandroidexercise.infrastructure.presenter.composable.fact

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jp.speakbuddy.edisonandroidexercise.infrastructure.network.fact.repository.GetFactRepoImpl
import jp.speakbuddy.edisonandroidexercise.infrastructure.presenter.viewmodels.fact.FactViewModel
import jp.speakbuddy.edisonandroidexercise.infrastructure.utils.constant.Constants
import jp.speakbuddy.edisonandroidexercise.infrastructure.utils.theme.EdisonAndroidExerciseTheme
import jp.speakbuddy.edisonandroidexercise.model.entities.fact.Fact
import jp.speakbuddy.edisonandroidexercise.service.fact.GetFactService

@Composable
fun FactScreen(
    factViewModel: FactViewModel,
    onClickUpdate: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = 16.dp,
            alignment = Alignment.CenterVertically
        )
    ) {
        var factStr by rememberSaveable { mutableStateOf("") }
        var factLength by rememberSaveable { mutableStateOf(0) }
        val fact by factViewModel.facts.observeAsState(Fact(factStr, factLength))

        factStr = fact.fact
        factLength = fact.length
        Text(
            text = "Fact",
            style = MaterialTheme.typography.titleLarge
        )

        if(factViewModel.isMultipleCats(factStr))
        Text(
            text = Constants.MULTIPLE_CATS,
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            text = factStr,
            style = MaterialTheme.typography.bodyLarge
        )

        if(factViewModel.isLengthGreaterThan100(factStr))
        Text(
            text = "Length: $factLength",
            style = MaterialTheme.typography.bodyLarge
        )

        val onClick = {
            onClickUpdate()
        }

        Button(onClick = onClick) {
            Text(text = "Update fact")
        }
    }
}

@Preview
@Composable
private fun FactScreenPreview() {
    EdisonAndroidExerciseTheme {
        FactScreen(FactViewModel(GetFactRepoImpl(), GetFactService()), {})
    }
}

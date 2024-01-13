package jp.speakbuddy.edisonandroidexercise.model.entities.fact

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Serializable
@Keep
data class Fact(
    val fact: String,
    val length: Int
) {
    companion object {
        fun createFact(
            factStr: String,
            length: Int
        ): Fact {
            return Fact(factStr, length)
        }
    }
}
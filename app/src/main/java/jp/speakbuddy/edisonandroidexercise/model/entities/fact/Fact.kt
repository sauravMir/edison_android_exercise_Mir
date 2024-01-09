package jp.speakbuddy.edisonandroidexercise.model.entities.fact

import kotlinx.serialization.Serializable

@Serializable
data class Fact(
    val fact: String,
    val length: Int
)
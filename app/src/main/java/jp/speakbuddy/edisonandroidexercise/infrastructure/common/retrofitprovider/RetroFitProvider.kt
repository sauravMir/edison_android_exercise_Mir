package jp.speakbuddy.edisonandroidexercise.infrastructure.common.retrofitprovider

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import jp.speakbuddy.edisonandroidexercise.infrastructure.common.retrofitprovider.url.URLs
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

object RetroFitProvider {
    @OptIn(ExperimentalSerializationApi::class)
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(URLs.BASE_URL)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }
}
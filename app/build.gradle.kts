import com.google.protobuf.gradle.builtins
import com.google.protobuf.gradle.generateProtoTasks
import com.google.protobuf.gradle.protobuf
import com.google.protobuf.gradle.protoc

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.serialization")

    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    id("com.google.protobuf")
}

android {
    namespace = "jp.speakbuddy.edisonandroidexercise"
    compileSdk = 33

    defaultConfig {
        applicationId = "jp.speakbuddy.edisonandroidexercise"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "0.0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.2"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

// Setup protobuf configuration, generating lite Java and Kotlin classes
protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:22.0"
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                val java by registering {
                    option("lite")
                }
                val kotlin by registering {
                    option("lite")
                }
            }
        }
    }
}

dependencies {
    implementation(libs.androidx.ktx)
    implementation(libs.androidx.lifecycle)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.compose)
    implementation(libs.androidx.composeuitooling)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.datastore.prefs)
    implementation(libs.androidx.datastore)
    implementation(libs.hilt)

    kapt(libs.hilt.compiler)

    implementation(libs.protobuf)
    implementation(libs.kotlin.serialization)
    implementation(libs.retrofit.converter)
    implementation(libs.okhttp3)
    implementation(libs.retrofit2)
    implementation(libs.runtime.live.data.compose)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.espresso)
    androidTestImplementation(libs.compose.uitest)
    debugImplementation(libs.compose.uitooling)
    debugImplementation(libs.compose.uitestmanifest)
}

kapt {
    correctErrorTypes = true
}
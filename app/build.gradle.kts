plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt)
    kotlin("kapt")
}

android {
    namespace = "com.moshi.vocalink"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.moshi.vocalink"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
}

dependencies {

    implementation(platform(libs.androidx.compose.bom))

// --- Compose UI ---
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.paging.runtime.ktx)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

// --- Hilt (DI) ---
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

// --- Lifecycle & Navigation ---
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.common.android)
    implementation(libs.androidx.navigation.runtime.android)

// --- Paging ---
    implementation(libs.androidx.paging.compose)
    implementation(libs.androidx.paging.common.android)

// --- Kotlin, Coroutines, Serialization ---
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.coroutines.core)
    implementation(libs.serialization.json)

// --- Network (Retrofit, Logging, Result) ---
    implementation(libs.network.retrofit)
    implementation(libs.logging.interceptor)
    implementation(libs.retrofit2.kotlinx.serialization.converter)
    implementation(libs.retrofit.adapters.result)

// --- Testing ---
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.turbine)
    testImplementation(kotlin("test"))

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

// --- Optional Annotation Support ---
    implementation(libs.androidx.annotation)

    //coil
    implementation("io.coil-kt:coil:2.7.0") // Core Coil
    implementation("io.coil-kt:coil-compose:2.5.0")
    implementation(platform("androidx.compose:compose-bom:2024.05.00"))
    implementation(project(":features:voiceToText"))
    implementation(project(":features:voiceHistory"))
    implementation(project(":data"))
    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation("androidx.compose.material3:material3-window-size-class:1.2.1")

    // Accompanist Navigation Material for BottomSheetNavigator
    implementation("com.google.accompanist:accompanist-navigation-material:0.34.0")

}
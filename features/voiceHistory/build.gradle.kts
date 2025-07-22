plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt)
    kotlin("kapt")
}

android {
    namespace = "com.example.voicehistory"
    compileSdk = 36

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {

    implementation(libs.androidx.compose.bom)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

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
    implementation(project(":core:voice"))
    implementation(project(":core:ui"))
    implementation(project(":domain:timer"))
    implementation(project(":domain:voice"))
    implementation(project(":core:utils"))
    implementation(project(":features:voiceToText"))
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

// For coroutine testing
    testImplementation(libs.kotlinx.coroutines.test)

// For flow testing
    testImplementation(libs.turbine)

// Mocking
    testImplementation(libs.mockk)

// JUnit
    testImplementation(libs.junit)
    testImplementation(kotlin("test"))

}
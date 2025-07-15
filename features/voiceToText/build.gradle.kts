plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt)
    kotlin("kapt")
}

android {
    namespace = "com.vocalInk.feature.voicetotext"
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
    implementation(platform("androidx.compose:compose-bom:2024.05.00"))
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
    implementation(project(":voice"))
    implementation(project(":core_ui"))
    implementation(project(":domain"))

    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

// For flow testing
    testImplementation("app.cash.turbine:turbine:1.0.0")

// Mocking
    testImplementation("io.mockk:mockk:1.13.5")

// JUnit
    testImplementation("junit:junit:4.13.2")
    testImplementation(kotlin("test"))

}
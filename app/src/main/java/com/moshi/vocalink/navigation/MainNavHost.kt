package com.moshi.vocalink.navigation

// OLD (Deprecated)
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.navigation.bottomSheet

// NEW (AndroidX)
import androidx.compose.material3.ModalBottomSheet
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.navigation.ModalBottomSheetLayout
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.navigation.rememberBottomSheetNavigator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.voicehistory.screen.VoiceHistoryScreen
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.vocalInk.feature.voicetotext.screen.VoiceToTextScreen

@OptIn(ExperimentalMaterialNavigationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MainNavHost() {
    val bottomSheetNavigator = rememberBottomSheetNavigator()
    val navController = rememberNavController(bottomSheetNavigator)

    ModalBottomSheetLayout(bottomSheetNavigator = bottomSheetNavigator) {
        NavHost(navController = navController, startDestination = "history") {
            composable("history") {
                VoiceHistoryScreen(
                    onAddVoice = { navController.navigate("voice") },
                )
            }
            bottomSheet("voice") {
                VoiceToTextScreen(
                    onFinish = { navController.popBackStack() }
                )
            }
        }
    }
}

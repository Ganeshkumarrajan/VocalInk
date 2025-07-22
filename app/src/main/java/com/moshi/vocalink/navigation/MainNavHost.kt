package com.moshi.vocalink.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.voicehistory.screen.VoiceHistoryScreen
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.bottomSheet
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.vocalInk.feature.voicetotext.screen.VoiceToTextScreen

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialNavigationApi::class)
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

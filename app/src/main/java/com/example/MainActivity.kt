package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.ui.screens.squad.SquadScreen
import com.example.ui.screens.village.VillageScreen
import com.example.ui.theme.NarutoIdleTheme

enum class MainDestination {
    HOME,
    SQUAD
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NarutoIdleTheme(darkTheme = true) {
                var currentScreen by remember { mutableStateOf(MainDestination.SQUAD) }

                BackHandler(enabled = currentScreen != MainDestination.HOME) {
                    currentScreen = MainDestination.HOME
                }

                when (currentScreen) {
                    MainDestination.HOME -> {
                        VillageScreen(
                            modifier = Modifier.fillMaxSize(),
                            onNavigateToSquad = { currentScreen = MainDestination.SQUAD }
                        )
                    }
                    MainDestination.SQUAD -> {
                        SquadScreen(
                            modifier = Modifier.fillMaxSize(),
                            onNavigateToHome = { currentScreen = MainDestination.HOME }
                        )
                    }
                }
            }
        }
    }
}

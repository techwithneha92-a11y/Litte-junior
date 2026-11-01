package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.audio.KidsAudioPlayer
import com.example.data.ProgressRepository
import com.example.ui.screens.AchievementsScreen
import com.example.ui.screens.AlphabetScreen
import com.example.ui.screens.GkScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.MathScreen
import com.example.ui.screens.NumbersScreen
import com.example.ui.screens.ParentDashboardScreen
import com.example.ui.theme.LittleGeniusTheme

sealed class AppScreen {
    data object Home : AppScreen()
    data object Alphabet : AppScreen()
    data object Numbers : AppScreen()
    data object Math : AppScreen()
    data object GeneralKnowledge : AppScreen()
    data object Achievements : AppScreen()
    data object ParentDashboard : AppScreen()
}

class MainActivity : ComponentActivity() {

    private lateinit var audioPlayer: KidsAudioPlayer
    private lateinit var progressRepo: ProgressRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        audioPlayer = KidsAudioPlayer(this)
        progressRepo = ProgressRepository(this)

        setContent {
            LittleGeniusTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .statusBarsPadding()
                        .navigationBarsPadding(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LittleGeniusApp(
                        audioPlayer = audioPlayer,
                        progressRepo = progressRepo
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        audioPlayer.shutdown()
    }
}

@Composable
fun LittleGeniusApp(
    audioPlayer: KidsAudioPlayer,
    progressRepo: ProgressRepository
) {
    var currentScreen by remember { mutableStateOf<AppScreen>(AppScreen.Home) }
    val progress by progressRepo.progressState.collectAsState()

    // Handle system back navigation gracefully
    if (currentScreen !is AppScreen.Home) {
        BackHandler {
            audioPlayer.stop()
            currentScreen = AppScreen.Home
        }
    }

    Crossfade(targetState = currentScreen, label = "screenTransition") { screen ->
        when (screen) {
            is AppScreen.Home -> {
                HomeScreen(
                    audioPlayer = audioPlayer,
                    progressRepo = progressRepo,
                    onNavigateToAlphabet = { currentScreen = AppScreen.Alphabet },
                    onNavigateToNumbers = { currentScreen = AppScreen.Numbers },
                    onNavigateToMath = { currentScreen = AppScreen.Math },
                    onNavigateToGk = { currentScreen = AppScreen.GeneralKnowledge },
                    onNavigateToAchievements = { currentScreen = AppScreen.Achievements },
                    onNavigateToParent = { currentScreen = AppScreen.ParentDashboard }
                )
            }
            is AppScreen.Alphabet -> {
                AlphabetScreen(
                    audioPlayer = audioPlayer,
                    progressRepo = progressRepo,
                    totalStars = progress.totalStars,
                    completedLetters = progress.completedLetters,
                    onBackToHome = {
                        audioPlayer.stop()
                        currentScreen = AppScreen.Home
                    }
                )
            }
            is AppScreen.Numbers -> {
                NumbersScreen(
                    audioPlayer = audioPlayer,
                    progressRepo = progressRepo,
                    totalStars = progress.totalStars,
                    completedNumbers = progress.completedNumbers,
                    onBackToHome = {
                        audioPlayer.stop()
                        currentScreen = AppScreen.Home
                    }
                )
            }
            is AppScreen.Math -> {
                MathScreen(
                    audioPlayer = audioPlayer,
                    progressRepo = progressRepo,
                    totalStars = progress.totalStars,
                    onBackToHome = {
                        audioPlayer.stop()
                        currentScreen = AppScreen.Home
                    }
                )
            }
            is AppScreen.GeneralKnowledge -> {
                GkScreen(
                    audioPlayer = audioPlayer,
                    progressRepo = progressRepo,
                    totalStars = progress.totalStars,
                    completedGkCategories = progress.completedGkCategories,
                    onBackToHome = {
                        audioPlayer.stop()
                        currentScreen = AppScreen.Home
                    }
                )
            }
            is AppScreen.Achievements -> {
                AchievementsScreen(
                    badges = progressRepo.getBadges(progress),
                    totalStars = progress.totalStars,
                    onBackToHome = {
                        audioPlayer.stop()
                        currentScreen = AppScreen.Home
                    }
                )
            }
            is AppScreen.ParentDashboard -> {
                ParentDashboardScreen(
                    audioPlayer = audioPlayer,
                    progressRepo = progressRepo,
                    onBackToHome = {
                        audioPlayer.stop()
                        currentScreen = AppScreen.Home
                    }
                )
            }
        }
    }
}

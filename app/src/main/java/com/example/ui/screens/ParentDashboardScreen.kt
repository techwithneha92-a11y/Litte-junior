package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.KidsAudioPlayer
import com.example.data.ProgressRepository
import com.example.data.UserProgress
import com.example.ui.components.KidPrimaryButton
import com.example.ui.theme.KidBubblePink
import com.example.ui.theme.KidMintGreen
import com.example.ui.theme.KidSkyBlue
import com.example.ui.theme.KidSunnyYellow

@Composable
fun ParentDashboardScreen(
    audioPlayer: KidsAudioPlayer,
    progressRepo: ProgressRepository,
    onBackToHome: () -> Unit
) {
    val progress by progressRepo.progressState.collectAsState()
    val isSoundOn by audioPlayer.isSoundEnabled.collectAsState()
    val isSlowMode by audioPlayer.isSlowMode.collectAsState()

    var isUnlocked by remember { mutableStateOf(false) }
    var mathChallengeAnswer by remember { mutableStateOf("") }
    var showResetDialog by remember { mutableStateOf(false) }

    // Simple Parental Gate: Math challenge "What is 3 x 4?"
    val numA = 3
    val numB = 4
    val correctAnswer = (numA * numB).toString()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
            .testTag("parent_dashboard_screen")
    ) {
        // Header
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color.White,
            shadowElevation = 4.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = onBackToHome,
                    modifier = Modifier.testTag("parent_back_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back to Home",
                        tint = KidSkyBlue,
                        modifier = Modifier.size(28.dp)
                    )
                }

                Text(
                    text = "Parent Dashboard",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp,
                    color = Color(0xFF1E293B)
                )

                Spacer(modifier = Modifier.size(48.dp))
            }
        }

        if (!isUnlocked) {
            // Parental Gate Screen
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(28.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFFEDE9FE),
                            modifier = Modifier.size(68.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Lock,
                                    contentDescription = "Parents Only",
                                    tint = Color(0xFF7C3AED),
                                    modifier = Modifier.size(36.dp)
                                )
                            }
                        }

                        Text(
                            text = "Parents Only Gate",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1E293B)
                        )

                        Text(
                            text = "To verify you are a parent, please solve this simple question:",
                            fontSize = 15.sp,
                            color = Color(0xFF64748B),
                            textAlign = TextAlign.Center
                        )

                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = Color(0xFFF1F5F9),
                            modifier = Modifier.padding(vertical = 4.dp)
                        ) {
                            Text(
                                text = "What is $numA × $numB = ?",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF0F172A),
                                modifier = Modifier.padding(horizontal = 24.dp, vertical = 10.dp)
                            )
                        }

                        // Easy answer buttons for parent
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            listOf("7", "12", "14").forEach { opt ->
                                KidPrimaryButton(
                                    text = opt,
                                    onClick = {
                                        if (opt == correctAnswer) {
                                            isUnlocked = true
                                        }
                                    },
                                    modifier = Modifier.weight(1f),
                                    containerColor = if (opt == correctAnswer) KidSkyBlue else Color(0xFFE2E8F0),
                                    contentColor = if (opt == correctAnswer) Color.White else Color(0xFF334155),
                                    testTag = "parent_gate_opt_$opt"
                                )
                            }
                        }
                    }
                }
            }
        } else {
            // Dashboard Content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Learning Overview Summary
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Text(
                            text = "📊 Learning Performance & Analytics",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF0F172A)
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            StatBox(
                                label = "Total Stars",
                                value = "${progress.totalStars} ⭐",
                                color = KidSunnyYellow,
                                modifier = Modifier.weight(1f)
                            )
                            StatBox(
                                label = "Quizzes Passed",
                                value = "${progress.totalQuizzesPassed} 🏆",
                                color = KidMintGreen,
                                modifier = Modifier.weight(1f)
                            )
                            StatBox(
                                label = "Time Spent",
                                value = "~${progress.estimatedMinutesSpent}m ⏱️",
                                color = KidSkyBlue,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }

                // Subject Progress Bars
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "📚 Subject Completion",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF0F172A)
                        )

                        ProgressRow(
                            title = "Alphabet (A to Z)",
                            current = progress.completedLetters.size,
                            total = 26,
                            color = KidSkyBlue
                        )
                        ProgressRow(
                            title = "Numbers (1 to 20)",
                            current = progress.completedNumbers.size,
                            total = 20,
                            color = KidSunnyYellow
                        )
                        ProgressRow(
                            title = "Fun Maths Solved",
                            current = progress.completedMathsCount.coerceAtMost(10),
                            total = 10,
                            color = KidMintGreen
                        )
                        ProgressRow(
                            title = "General Knowledge Categories",
                            current = progress.completedGkCategories.size,
                            total = 10,
                            color = KidBubblePink
                        )
                    }
                }

                // Parental Controls & Audio Settings
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Text(
                            text = "⚙️ Audio & Voice Controls",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF0F172A)
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Sound & Pronunciation",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xFF1E293B)
                                )
                                Text(
                                    text = "Enable text-to-speech voice narration",
                                    fontSize = 13.sp,
                                    color = Color(0xFF64748B)
                                )
                            }
                            Switch(
                                checked = isSoundOn,
                                onCheckedChange = { audioPlayer.toggleSound(it) },
                                colors = SwitchDefaults.colors(checkedThumbColor = KidSkyBlue),
                                modifier = Modifier.testTag("parent_sound_switch")
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Slow Speech Mode",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xFF1E293B)
                                )
                                Text(
                                    text = "Slower narration for preschool children",
                                    fontSize = 13.sp,
                                    color = Color(0xFF64748B)
                                )
                            }
                            Switch(
                                checked = isSlowMode,
                                onCheckedChange = { audioPlayer.toggleSlowMode(it) },
                                colors = SwitchDefaults.colors(checkedThumbColor = KidSkyBlue),
                                modifier = Modifier.testTag("parent_slow_mode_switch")
                            )
                        }
                    }
                }

                // Privacy Notice
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0xFFECFDF5),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFA7F3D0)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "🛡️", fontSize = 24.sp)
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Kid-Safe & Private: All learning progress is stored locally on device. No ads, no external tracking, and no child accounts required.",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF065F46)
                        )
                    }
                }

                // Reset Progress Action
                KidPrimaryButton(
                    text = "Reset Child Progress",
                    onClick = { showResetDialog = true },
                    modifier = Modifier.fillMaxWidth(),
                    containerColor = Color(0xFFEF4444),
                    testTag = "reset_progress_button"
                )
            }
        }

        if (showResetDialog) {
            AlertDialog(
                onDismissRequest = { showResetDialog = false },
                title = {
                    Text("Reset All Learning Progress?", fontWeight = FontWeight.Bold)
                },
                text = {
                    Text("This will reset all stars, badges, and completed lessons. Are you sure you want to start fresh?")
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            progressRepo.resetAllProgress()
                            showResetDialog = false
                        }
                    ) {
                        Text("Reset", color = Color(0xFFEF4444), fontWeight = FontWeight.Bold)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showResetDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}

@Composable
fun StatBox(label: String, value: String, color: Color, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        color = color.copy(alpha = 0.12f),
        border = androidx.compose.foundation.BorderStroke(1.5.dp, color.copy(alpha = 0.4f))
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = value, fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF0F172A))
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = label, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF475569))
        }
    }
}

@Composable
fun ProgressRow(title: String, current: Int, total: Int, color: Color) {
    val fraction = (current.toFloat() / total).coerceIn(0f, 1f)
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = title, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF1E293B))
            Text(text = "$current / $total (${(fraction * 100).toInt()}%)", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = color)
        }
        Spacer(modifier = Modifier.height(6.dp))
        LinearProgressIndicator(
            progress = { fraction },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = color,
            trackColor = Color(0xFFE2E8F0)
        )
    }
}

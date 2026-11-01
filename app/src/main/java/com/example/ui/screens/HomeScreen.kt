package com.example.ui.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.VolumeOff
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
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
import com.example.ui.components.StarBadge
import com.example.ui.theme.KidBubblePink
import com.example.ui.theme.KidBubblePinkLight
import com.example.ui.theme.KidMintGreen
import com.example.ui.theme.KidMintGreenLight
import com.example.ui.theme.KidSkyBlue
import com.example.ui.theme.KidSkyBlueLight
import com.example.ui.theme.KidStarGold
import com.example.ui.theme.KidSunnyYellow
import com.example.ui.theme.KidSunnyYellowLight
import com.example.ui.theme.KidVibrantPurple
import com.example.ui.theme.KidVibrantPurpleLight
import com.example.ui.theme.KidWarmOrange
import com.example.ui.theme.KidWarmOrangeLight

@Composable
fun HomeScreen(
    audioPlayer: KidsAudioPlayer,
    progressRepo: ProgressRepository,
    onNavigateToAlphabet: () -> Unit,
    onNavigateToNumbers: () -> Unit,
    onNavigateToMath: () -> Unit,
    onNavigateToGk: () -> Unit,
    onNavigateToAchievements: () -> Unit,
    onNavigateToParent: () -> Unit
) {
    val progress by progressRepo.progressState.collectAsState()
    val isSoundOn by audioPlayer.isSoundEnabled.collectAsState()

    val infiniteTransition = rememberInfiniteTransition(label = "mascotBounce")
    val mascotBounce by infiniteTransition.animateFloat(
        initialValue = 0.96f,
        targetValue = 1.04f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "mascotScale"
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F7FE))
            .testTag("home_screen"),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        // App Top Bar
        item {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color.White,
                shadowElevation = 4.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = KidSkyBlue,
                            modifier = Modifier.size(44.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(text = "⭐", fontSize = 22.sp)
                            }
                        }
                        Column {
                            Text(
                                text = "LITTLE GENIUS",
                                fontSize = 19.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = KidSkyBlue
                            )
                            Text(
                                text = "Smart Kids Learning",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF64748B)
                            )
                        }
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        // Sound Toggle
                        IconButton(
                            onClick = {
                                audioPlayer.toggleSound()
                                if (!isSoundOn) {
                                    audioPlayer.speak("Sound is on!")
                                }
                            },
                            modifier = Modifier.testTag("home_sound_toggle")
                        ) {
                            Icon(
                                imageVector = if (isSoundOn) Icons.Default.VolumeUp else Icons.Default.VolumeOff,
                                contentDescription = "Toggle Sound",
                                tint = if (isSoundOn) KidSkyBlue else Color(0xFF94A3B8),
                                modifier = Modifier.size(28.dp)
                            )
                        }

                        // Achievements
                        IconButton(
                            onClick = onNavigateToAchievements,
                            modifier = Modifier.testTag("home_achievements_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.EmojiEvents,
                                contentDescription = "Achievements",
                                tint = KidSunnyYellow,
                                modifier = Modifier.size(28.dp)
                            )
                        }

                        // Parent Dashboard
                        IconButton(
                            onClick = onNavigateToParent,
                            modifier = Modifier.testTag("home_parent_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Security,
                                contentDescription = "Parent Dashboard",
                                tint = KidVibrantPurple,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }
                }
            }
        }

        // Hero Welcome Mascot Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .testTag("hero_banner"),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(Color(0xFF4FACFE), Color(0xFF00F2FE))
                            )
                        )
                        .padding(18.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = "Hello, Little Genius! 🌟",
                                fontSize = 21.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.White
                            )
                            Text(
                                text = "Learn Something New Every Day!",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFE0F7FA)
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            StarBadge(
                                stars = progress.totalStars,
                                onClick = {
                                    audioPlayer.speak("You have earned ${progress.totalStars} shining stars! You are amazing!")
                                }
                            )
                        }

                        // Animated Mascot
                        Box(
                            modifier = Modifier
                                .size(90.dp)
                                .scale(mascotBounce)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.25f))
                                .border(3.dp, Color.White, CircleShape)
                                .clickable {
                                    audioPlayer.playCheer("Hello little friend! Tap any subject to start learning!")
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "🦉", fontSize = 52.sp)
                        }
                    }
                }
            }
        }

        // Daily Learning Challenge Banner
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp)
                    .testTag("daily_challenge_card"),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF9C4)),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, KidStarGold)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(text = "🎯", fontSize = 34.sp)
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Daily Learning Challenge",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF78350F)
                        )
                        Text(
                            text = if (progress.dailyChallengeCompleted) "Completed today! +5 Bonus stars added!" else "Explore 3 lessons today! (${progress.dailyProgressCount}/3)",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF92400E)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        LinearProgressIndicator(
                            progress = { (progress.dailyProgressCount.toFloat() / 3f).coerceIn(0f, 1f) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp)),
                            color = KidMintGreen,
                            trackColor = Color(0xFFFEF3C7)
                        )
                    }
                    if (progress.dailyChallengeCompleted) {
                        Text(text = "✅ Done", fontWeight = FontWeight.Bold, color = KidMintGreen, fontSize = 13.sp)
                    }
                }
            }
        }

        // Section Title: 4 Main Subjects
        item {
            PaddingValues(horizontal = 16.dp)
            Text(
                text = "Choose Your Learning Adventure:",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E293B),
                modifier = Modifier.padding(start = 18.dp, top = 16.dp, bottom = 6.dp)
            )
        }

        // 1. ABC Alphabet Learning Card
        item {
            SubjectCard(
                title = "ABC Alphabet Learning",
                tagline = "A to Z Letters & Interactive Flashcards",
                description = "Learn 26 letters with 3D flip cards, clear child pronunciation, fun facts, and quizzes!",
                iconsEmoji = "🔤 🍎 🐱 🦁",
                progressText = "${progress.completedLetters.size} / 26 Letters",
                progressFraction = progress.completedLetters.size / 26f,
                accentColor = KidSkyBlue,
                bgColor = KidSkyBlueLight,
                onStartLearning = {
                    audioPlayer.playTapSound()
                    onNavigateToAlphabet()
                },
                testTag = "subject_card_alphabet"
            )
        }

        // 2. Numbers Learning Card
        item {
            SubjectCard(
                title = "Numbers Learning (1 to 20)",
                tagline = "Counting, Spellings & Object Games",
                description = "Count 1 to 20 with interactive tap counting, spelling audio, more or less, and quizzes!",
                iconsEmoji = "🔢 🎈 ⭐ 🧸",
                progressText = "${progress.completedNumbers.size} / 20 Numbers",
                progressFraction = progress.completedNumbers.size / 20f,
                accentColor = KidSunnyYellow,
                bgColor = KidSunnyYellowLight,
                onStartLearning = {
                    audioPlayer.playTapSound()
                    onNavigateToNumbers()
                },
                testTag = "subject_card_numbers"
            )
        }

        // 3. Fun Maths Card
        item {
            SubjectCard(
                title = "Fun Maths",
                tagline = "Visual Addition & Subtraction",
                description = "Master addition and subtraction with visual object animations, takeaway practice, and challenges!",
                iconsEmoji = "➕ ➖ 🧮 🍓",
                progressText = "${progress.completedMathsCount.coerceAtMost(10)} / 10 Mastered",
                progressFraction = (progress.completedMathsCount / 10f).coerceIn(0f, 1f),
                accentColor = KidMintGreen,
                bgColor = KidMintGreenLight,
                onStartLearning = {
                    audioPlayer.playTapSound()
                    onNavigateToMath()
                },
                testTag = "subject_card_math"
            )
        }

        // 4. General Knowledge Card
        item {
            SubjectCard(
                title = "General Knowledge",
                tagline = "10 Fun Categories to Explore",
                description = "Discover animals, sweet fruits, colors, body parts, days, months, shapes, vehicles, nature, and science!",
                iconsEmoji = "🌍 🦁 🎨 🚗",
                progressText = "${progress.completedGkCategories.size} / 10 Categories",
                progressFraction = progress.completedGkCategories.size / 10f,
                accentColor = KidVibrantPurple,
                bgColor = KidVibrantPurpleLight,
                onStartLearning = {
                    audioPlayer.playTapSound()
                    onNavigateToGk()
                },
                testTag = "subject_card_gk"
            )
        }
    }
}

@Composable
fun SubjectCard(
    title: String,
    tagline: String,
    description: String,
    iconsEmoji: String,
    progressText: String,
    progressFraction: Float,
    accentColor: Color,
    bgColor: Color,
    onStartLearning: () -> Unit,
    testTag: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onStartLearning() }
            .testTag(testTag),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        border = androidx.compose.foundation.BorderStroke(2.dp, accentColor.copy(alpha = 0.35f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(18.dp),
                    color = bgColor,
                    modifier = Modifier.padding(vertical = 2.dp)
                ) {
                    Text(
                        text = iconsEmoji,
                        fontSize = 24.sp,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = accentColor.copy(alpha = 0.12f)
                ) {
                    Text(
                        text = progressText,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = accentColor,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }

            Column {
                Text(
                    text = title,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF1E293B)
                )
                Text(
                    text = tagline,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = accentColor
                )
            }

            Text(
                text = description,
                fontSize = 14.sp,
                color = Color(0xFF64748B),
                lineHeight = 20.sp
            )

            LinearProgressIndicator(
                progress = { progressFraction.coerceIn(0f, 1f) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = accentColor,
                trackColor = Color(0xFFE2E8F0)
            )

            KidPrimaryButton(
                text = "Start Learning! 🚀",
                onClick = onStartLearning,
                modifier = Modifier.fillMaxWidth(),
                containerColor = accentColor,
                testTag = "${testTag}_button"
            )
        }
    }
}

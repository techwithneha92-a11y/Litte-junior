package com.example.ui.screens

import androidx.compose.animation.animateColorAsState
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.KidsAudioPlayer
import com.example.data.GeneralKnowledgeRepository
import com.example.data.GkCategory
import com.example.data.GkItem
import com.example.data.ProgressRepository
import com.example.ui.components.AudioSpeakerButton
import com.example.ui.components.ConfettiOverlay
import com.example.ui.components.KidPrimaryButton
import com.example.ui.components.QuizSuccessCelebrationDialog
import com.example.ui.components.StarBadge
import com.example.ui.theme.KidBubblePink
import com.example.ui.theme.KidMintGreen
import com.example.ui.theme.KidSkyBlue
import com.example.ui.theme.KidSunnyYellow

@Composable
fun GkScreen(
    audioPlayer: KidsAudioPlayer,
    progressRepo: ProgressRepository,
    totalStars: Int,
    completedGkCategories: Set<String>,
    onBackToHome: () -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) } // 0 = Explore Categories, 1 = GK Quiz
    val categories = GeneralKnowledgeRepository.categories
    var selectedCategoryIndex by remember { mutableIntStateOf(0) }
    val currentCategory = categories[selectedCategoryIndex]
    var selectedItemIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(selectedCategoryIndex, selectedTab) {
        if (selectedTab == 0) {
            selectedItemIndex = 0
            progressRepo.markGkCategoryCompleted(currentCategory.id)
            audioPlayer.speak("${currentCategory.title}. ${currentCategory.description}")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F7FE))
            .testTag("gk_screen")
    ) {
        // Top Header
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
                    modifier = Modifier.testTag("gk_back_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back to Home",
                        tint = KidSkyBlue,
                        modifier = Modifier.size(28.dp)
                    )
                }

                Text(
                    text = "General Knowledge",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp,
                    color = Color(0xFF1E293B)
                )

                StarBadge(stars = totalStars)
            }
        }

        // Sub Tabs
        ScrollableTabRow(
            selectedTabIndex = selectedTab,
            edgePadding = 16.dp,
            containerColor = Color.White,
            contentColor = KidSkyBlue
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = { Text("🌍 10 Categories", fontWeight = FontWeight.Bold, fontSize = 16.sp) },
                modifier = Modifier.testTag("tab_gk_categories")
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = { Text("🧠 Science & GK Quiz", fontWeight = FontWeight.Bold, fontSize = 16.sp) },
                modifier = Modifier.testTag("tab_gk_quiz")
            )
        }

        if (selectedTab == 0) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Category Pills Ribbon
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    itemsIndexed(categories) { idx, cat ->
                        val isSelected = idx == selectedCategoryIndex
                        val isDone = completedGkCategories.contains(cat.id)
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = if (isSelected) KidSkyBlue else if (isDone) Color(0xFFE8F5E9) else Color.White,
                            border = androidx.compose.foundation.BorderStroke(
                                1.5.dp,
                                if (isSelected) KidSkyBlue else if (isDone) KidMintGreen else Color(0xFFE2E8F0)
                            ),
                            modifier = Modifier
                                .clickable {
                                    selectedCategoryIndex = idx
                                    audioPlayer.playTapSound()
                                }
                                .testTag("cat_pill_${cat.id}")
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(text = cat.emoji, fontSize = 18.sp)
                                Text(
                                    text = cat.title,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = if (isSelected) Color.White else Color(0xFF334155)
                                )
                            }
                        }
                    }
                }

                // Current Item Flashcard
                val currentItem = currentCategory.items[selectedItemIndex.coerceIn(0, currentCategory.items.size - 1)]

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("gk_item_card"),
                    shape = RoundedCornerShape(32.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        // Card Header
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = currentItem.name,
                                    fontSize = 28.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = KidSkyBlue
                                )
                                Text(
                                    text = currentItem.subtitle,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xFF64748B)
                                )
                            }

                            AudioSpeakerButton(
                                onClick = {
                                    val text = "${currentItem.name}. ${currentItem.subtitle}. " +
                                            "${currentItem.detail1}. ${currentItem.detail2}. Fun fact: ${currentItem.funFact}"
                                    audioPlayer.speak(text)
                                },
                                sizeDp = 48,
                                containerColor = KidSunnyYellow,
                                testTag = "gk_speaker_button"
                            )
                        }

                        // Big Emoji Artwork
                        Box(
                            modifier = Modifier
                                .size(130.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFF8FAFC))
                                .border(3.dp, Color(0xFFE2E8F0), CircleShape)
                                .clickable {
                                    audioPlayer.speak(currentItem.pronunciation)
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = currentItem.emoji, fontSize = 72.sp)
                        }

                        // Detail 1 and 2
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFFF1F5F9), RoundedCornerShape(18.dp))
                                .padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = "📌 ${currentItem.detail1}",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF1E293B)
                            )
                            Text(
                                text = "✨ ${currentItem.detail2}",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF1E293B)
                            )
                        }

                        // Fun Fact
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = Color(0xFFFFFDE7),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = "💡", fontSize = 24.sp)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = currentItem.funFact,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xFF374151)
                                )
                            }
                        }
                    }
                }

                // Previous / Next item in category
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    KidPrimaryButton(
                        text = "Prev Item",
                        onClick = {
                            if (selectedItemIndex > 0) {
                                selectedItemIndex--
                                audioPlayer.playTapSound()
                            }
                        },
                        enabled = selectedItemIndex > 0,
                        modifier = Modifier.weight(1f),
                        containerColor = Color(0xFF94A3B8),
                        testTag = "prev_gk_button"
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    KidPrimaryButton(
                        text = "Next Item",
                        onClick = {
                            if (selectedItemIndex < currentCategory.items.size - 1) {
                                selectedItemIndex++
                                audioPlayer.playTapSound()
                            }
                        },
                        enabled = selectedItemIndex < currentCategory.items.size - 1,
                        modifier = Modifier.weight(1f),
                        containerColor = KidMintGreen,
                        testTag = "next_gk_button"
                    )
                }

                // Item Grid Selector in current category
                Text(
                    text = "Items in ${currentCategory.title} (${currentCategory.items.size}):",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color(0xFF475569),
                    modifier = Modifier.fillMaxWidth()
                )

                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    itemsIndexed(currentCategory.items) { idx, item ->
                        val isItemSel = idx == selectedItemIndex
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = if (isItemSel) KidSkyBlue else Color.White,
                            border = androidx.compose.foundation.BorderStroke(
                                1.5.dp,
                                if (isItemSel) KidSkyBlue else Color(0xFFE2E8F0)
                            ),
                            modifier = Modifier
                                .clickable {
                                    selectedItemIndex = idx
                                    audioPlayer.playTapSound()
                                }
                                .testTag("gk_sub_item_${item.name}")
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(text = item.emoji, fontSize = 20.sp)
                                Text(
                                    text = item.name,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isItemSel) Color.White else Color(0xFF1E293B)
                                )
                            }
                        }
                    }
                }
            }
        } else {
            // General Knowledge Quiz Section
            GkQuizView(
                audioPlayer = audioPlayer,
                onQuizComplete = { score, total ->
                    progressRepo.recordQuizPassed(score * 2)
                }
            )
        }
    }
}

@Composable
fun GkQuizView(
    audioPlayer: KidsAudioPlayer,
    onQuizComplete: (score: Int, total: Int) -> Unit
) {
    var quizStep by remember { mutableIntStateOf(0) }
    var score by remember { mutableIntStateOf(0) }
    var showDialog by remember { mutableStateOf(false) }
    var selectedAnswer by remember { mutableStateOf<String?>(null) }
    var isAnswerCorrect by remember { mutableStateOf<Boolean?>(null) }

    val scienceQuestions = GeneralKnowledgeRepository.scienceQuestions
    val currentQuestion = scienceQuestions[quizStep.coerceIn(0, scienceQuestions.size - 1)]

    LaunchedEffect(quizStep) {
        selectedAnswer = null
        isAnswerCorrect = null
        audioPlayer.speak(currentQuestion.question)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("gk_quiz_view")
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Quiz Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Question ${quizStep + 1} of ${scienceQuestions.size}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = KidSkyBlue
                )
                Text(
                    text = "Score: $score",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 18.sp,
                    color = KidMintGreen
                )
            }

            // Question Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(text = currentQuestion.emoji, fontSize = 48.sp)

                    Text(
                        text = currentQuestion.question,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E293B),
                        textAlign = TextAlign.Center
                    )

                    AudioSpeakerButton(
                        onClick = { audioPlayer.speak(currentQuestion.question) },
                        sizeDp = 44,
                        containerColor = KidSunnyYellow
                    )
                }
            }

            // Options list
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                currentQuestion.options.forEach { option ->
                    val isSelected = selectedAnswer == option
                    val isCorrect = if (isSelected) isAnswerCorrect else null

                    val bgColor by animateColorAsState(
                        targetValue = when {
                            isCorrect == true -> Color(0xFFDCFCE7)
                            isCorrect == false -> Color(0xFFFFE4E6)
                            isSelected -> Color(0xFFE0E7FF)
                            else -> Color.White
                        },
                        label = "gkOptColor"
                    )

                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .clickable {
                                if (selectedAnswer == null) {
                                    selectedAnswer = option
                                    val correct = option == currentQuestion.answer
                                    isAnswerCorrect = correct
                                    if (correct) {
                                        score++
                                        audioPlayer.playCheer("Correct! ${currentQuestion.explanation}")
                                    } else {
                                        audioPlayer.speak("Try again, little genius! The answer is ${currentQuestion.answer}.")
                                    }
                                }
                            }
                            .testTag("gk_option_$option"),
                        shape = RoundedCornerShape(18.dp),
                        color = bgColor,
                        shadowElevation = 2.dp,
                        border = androidx.compose.foundation.BorderStroke(
                            2.dp,
                            when {
                                isCorrect == true -> KidMintGreen
                                isCorrect == false -> Color(0xFFEF4444)
                                else -> Color(0xFFE2E8F0)
                            }
                        )
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = option,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1E293B)
                            )
                        }
                    }
                }
            }

            // Next button
            KidPrimaryButton(
                text = if (quizStep < scienceQuestions.size - 1) "Next Question ➡️" else "Finish GK Quiz 🏆",
                onClick = {
                    if (quizStep < scienceQuestions.size - 1) {
                        quizStep++
                    } else {
                        showDialog = true
                        onQuizComplete(score, scienceQuestions.size)
                    }
                },
                enabled = selectedAnswer != null,
                modifier = Modifier.fillMaxWidth(),
                containerColor = KidMintGreen,
                testTag = "gk_quiz_next_button"
            )
        }

        if (showDialog) {
            ConfettiOverlay()
            QuizSuccessCelebrationDialog(
                score = score,
                total = scienceQuestions.size,
                starsEarned = score * 2,
                onPlayAgain = {
                    showDialog = false
                    quizStep = 0
                    score = 0
                },
                onNext = {
                    showDialog = false
                    quizStep = 0
                    score = 0
                }
            )
        }
    }
}

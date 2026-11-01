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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.data.NumberItem
import com.example.data.NumbersRepository
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
import kotlinx.coroutines.launch

@Composable
fun NumbersScreen(
    audioPlayer: KidsAudioPlayer,
    progressRepo: ProgressRepository,
    totalStars: Int,
    completedNumbers: Set<Int>,
    onBackToHome: () -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) } // 0 = Learn 1-20, 1 = Counting Games
    var currentIndex by remember { mutableIntStateOf(0) }
    val numbers = NumbersRepository.numberList
    val currentNumber = numbers[currentIndex]

    // Track tapped objects for interactive counting
    var countedIndices by remember { mutableStateOf(setOf<Int>()) }

    val lazyListState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(currentIndex, selectedTab) {
        if (selectedTab == 0) {
            countedIndices = emptySet()
            progressRepo.markNumberCompleted(currentNumber.number)
            audioPlayer.speak("${currentNumber.number}. ${currentNumber.spelling}. ${currentNumber.sentence}")
            scope.launch {
                lazyListState.animateScrollToItem((currentIndex - 2).coerceAtLeast(0))
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F7FE))
            .testTag("numbers_screen")
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
                    modifier = Modifier.testTag("numbers_back_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back to Home",
                        tint = KidSkyBlue,
                        modifier = Modifier.size(28.dp)
                    )
                }

                Text(
                    text = "Numbers Learning (1 to 20)",
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
                text = {
                    Text(
                        "🔢 Learn 1 to 20",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                },
                modifier = Modifier.testTag("tab_learn_numbers")
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = {
                    Text(
                        "🎯 Counting Quizzes",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                },
                modifier = Modifier.testTag("tab_numbers_quiz")
            )
        }

        if (selectedTab == 0) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // 1-20 Number Selector Ribbon
                LazyRow(
                    state = lazyListState,
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    itemsIndexed(numbers) { idx, item ->
                        val isSelected = idx == currentIndex
                        val isLearned = completedNumbers.contains(item.number)
                        Surface(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .clickable {
                                    currentIndex = idx
                                    audioPlayer.playTapSound()
                                }
                                .testTag("number_tab_${item.number}"),
                            shape = CircleShape,
                            color = if (isSelected) KidSkyBlue else if (isLearned) Color(0xFFE8F5E9) else Color.White,
                            shadowElevation = if (isSelected) 6.dp else 2.dp,
                            border = androidx.compose.foundation.BorderStroke(
                                2.dp,
                                if (isSelected) KidSkyBlue else if (isLearned) KidMintGreen else Color(0xFFE2E8F0)
                            )
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = "${item.number}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp,
                                    color = if (isSelected) Color.White else if (isLearned) Color(0xFF2E7D32) else Color(0xFF475569)
                                )
                            }
                        }
                    }
                }

                // Main Interactive Number Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("number_learning_card"),
                    shape = RoundedCornerShape(32.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Header with number and speaker
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = RoundedCornerShape(20.dp),
                                color = Color(currentNumber.cardColorHex),
                                modifier = Modifier
                                    .clickable {
                                        audioPlayer.speak("${currentNumber.number}")
                                    }
                                    .testTag("number_badge")
                            ) {
                                Text(
                                    text = "${currentNumber.number}",
                                    fontSize = 44.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color.White,
                                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 6.dp)
                                )
                            }

                            Column(horizontalAlignment = Alignment.End) {
                                Text(
                                    text = currentNumber.spelling,
                                    fontSize = 26.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color(currentNumber.cardColorHex)
                                )
                                Text(
                                    text = "${currentNumber.number} ${currentNumber.objectName}",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xFF64748B)
                                )
                            }

                            AudioSpeakerButton(
                                onClick = {
                                    audioPlayer.speak(
                                        "${currentNumber.number}. ${currentNumber.spelling}. ${currentNumber.sentence}. ${currentNumber.countingFact}"
                                    )
                                },
                                sizeDp = 48,
                                containerColor = KidSunnyYellow,
                                testTag = "number_speaker_button"
                            )
                        }

                        // Interactive Counting Area
                        Surface(
                            shape = RoundedCornerShape(24.dp),
                            color = Color(0xFFF8FAFC),
                            border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFFE2E8F0)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(14.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "👆 Tap each ${currentNumber.objectName.lowercase()} to count!",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = KidSkyBlue
                                )
                                Spacer(modifier = Modifier.height(10.dp))

                                // Render exact count of items
                                val columns = when {
                                    currentNumber.number <= 4 -> 4
                                    currentNumber.number <= 10 -> 5
                                    else -> 5
                                }
                                val rows = (currentNumber.number + columns - 1) / columns

                                Column(
                                    verticalArrangement = Arrangement.spacedBy(8.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    for (r in 0 until rows) {
                                        Row(
                                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            for (c in 0 until columns) {
                                                val itemIdx = r * columns + c
                                                if (itemIdx < currentNumber.number) {
                                                    val isCounted = countedIndices.contains(itemIdx)
                                                    Box(
                                                        modifier = Modifier
                                                            .size(46.dp)
                                                            .clip(RoundedCornerShape(12.dp))
                                                            .background(
                                                                if (isCounted) Color(0xFFDCFCE7) else Color.White
                                                            )
                                                            .border(
                                                                2.dp,
                                                                if (isCounted) KidMintGreen else Color(0xFFCBD5E1),
                                                                RoundedCornerShape(12.dp)
                                                            )
                                                            .clickable {
                                                                val updated = countedIndices + itemIdx
                                                                countedIndices = updated
                                                                audioPlayer.playTapSound()
                                                                audioPlayer.speak("${updated.size}")
                                                                if (updated.size == currentNumber.number) {
                                                                    audioPlayer.playCheer("All ${currentNumber.number} counted! Great job!")
                                                                }
                                                            }
                                                            .testTag("count_item_$itemIdx"),
                                                        contentAlignment = Alignment.Center
                                                    ) {
                                                        Text(
                                                            text = currentNumber.objectEmoji,
                                                            fontSize = 26.sp
                                                        )
                                                        if (isCounted) {
                                                            Box(
                                                                modifier = Modifier
                                                                    .size(18.dp)
                                                                    .align(Alignment.TopEnd)
                                                                    .background(KidMintGreen, CircleShape),
                                                                contentAlignment = Alignment.Center
                                                            ) {
                                                                Text(
                                                                    text = "✓",
                                                                    color = Color.White,
                                                                    fontSize = 11.sp,
                                                                    fontWeight = FontWeight.Bold
                                                                )
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Counted: ${countedIndices.size} / ${currentNumber.number}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = if (countedIndices.size == currentNumber.number) KidMintGreen else Color(0xFF64748B)
                                )
                            }
                        }

                        // Educational Fact
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
                                    text = currentNumber.countingFact,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xFF424242)
                                )
                            }
                        }
                    }
                }

                // Prev / Next Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    KidPrimaryButton(
                        text = "Prev",
                        onClick = {
                            if (currentIndex > 0) {
                                currentIndex--
                                audioPlayer.playTapSound()
                            }
                        },
                        enabled = currentIndex > 0,
                        modifier = Modifier.weight(1f),
                        containerColor = Color(0xFF94A3B8),
                        testTag = "prev_number_button"
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    KidPrimaryButton(
                        text = "Next",
                        onClick = {
                            if (currentIndex < numbers.size - 1) {
                                currentIndex++
                                audioPlayer.playTapSound()
                            }
                        },
                        enabled = currentIndex < numbers.size - 1,
                        modifier = Modifier.weight(1f),
                        containerColor = KidMintGreen,
                        testTag = "next_number_button"
                    )
                }
            }
        } else {
            // Counting Quizzes and Games
            NumbersQuizView(
                audioPlayer = audioPlayer,
                onQuizComplete = { score, total ->
                    progressRepo.recordQuizPassed(score * 2)
                }
            )
        }
    }
}

@Composable
fun NumbersQuizView(
    audioPlayer: KidsAudioPlayer,
    onQuizComplete: (score: Int, total: Int) -> Unit
) {
    var quizStep by remember { mutableIntStateOf(0) }
    var score by remember { mutableIntStateOf(0) }
    var showDialog by remember { mutableStateOf(false) }
    var selectedAnswer by remember { mutableStateOf<Int?>(null) }
    var isAnswerCorrect by remember { mutableStateOf<Boolean?>(null) }

    val numbers = NumbersRepository.numberList
    // 5 progressive questions
    val targetNumber = numbers[(quizStep * 3 + 2) % 15] // 1..15 for clear kid count
    val options = remember(quizStep) {
        val wrong = listOf(
            (targetNumber.number - 2).coerceAtLeast(1),
            (targetNumber.number - 1).coerceAtLeast(1),
            (targetNumber.number + 1).coerceAtMost(20),
            (targetNumber.number + 2).coerceAtMost(20)
        ).distinct().filter { it != targetNumber.number }.take(3)
        (wrong + targetNumber.number).shuffled()
    }

    LaunchedEffect(quizStep) {
        selectedAnswer = null
        isAnswerCorrect = null
        audioPlayer.speak("How many ${targetNumber.objectName.lowercase()} are there?")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("numbers_quiz_view")
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Count Question ${quizStep + 1} of 5",
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

            // Question Card with exact objects
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "How many ${targetNumber.objectName} are there?",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E293B)
                    )

                    // Render objects
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = Color(0xFFF1F5F9),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = (1..targetNumber.number).joinToString(" ") { targetNumber.objectEmoji },
                                fontSize = 32.sp,
                                textAlign = TextAlign.Center,
                                lineHeight = 38.sp
                            )
                        }
                    }

                    AudioSpeakerButton(
                        onClick = {
                            audioPlayer.speak("Count the ${targetNumber.objectName.lowercase()} and tap the correct number!")
                        },
                        sizeDp = 42,
                        containerColor = KidSunnyYellow
                    )
                }
            }

            // 4 Number Options
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    NumberOptionCard(
                        num = options[0],
                        isSelected = selectedAnswer == options[0],
                        isCorrect = if (selectedAnswer == options[0]) isAnswerCorrect else null,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            if (selectedAnswer == null) {
                                selectedAnswer = options[0]
                                val correct = options[0] == targetNumber.number
                                isAnswerCorrect = correct
                                if (correct) {
                                    score++
                                    audioPlayer.playCheer("Correct! There are ${targetNumber.number} ${targetNumber.objectName}!")
                                } else {
                                    audioPlayer.speak("Try again, little genius! It's ${targetNumber.number}.")
                                }
                            }
                        }
                    )
                    NumberOptionCard(
                        num = options[1],
                        isSelected = selectedAnswer == options[1],
                        isCorrect = if (selectedAnswer == options[1]) isAnswerCorrect else null,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            if (selectedAnswer == null) {
                                selectedAnswer = options[1]
                                val correct = options[1] == targetNumber.number
                                isAnswerCorrect = correct
                                if (correct) {
                                    score++
                                    audioPlayer.playCheer("Correct! There are ${targetNumber.number} ${targetNumber.objectName}!")
                                } else {
                                    audioPlayer.speak("Try again, little genius! It's ${targetNumber.number}.")
                                }
                            }
                        }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    NumberOptionCard(
                        num = options[2],
                        isSelected = selectedAnswer == options[2],
                        isCorrect = if (selectedAnswer == options[2]) isAnswerCorrect else null,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            if (selectedAnswer == null) {
                                selectedAnswer = options[2]
                                val correct = options[2] == targetNumber.number
                                isAnswerCorrect = correct
                                if (correct) {
                                    score++
                                    audioPlayer.playCheer("Correct! There are ${targetNumber.number} ${targetNumber.objectName}!")
                                } else {
                                    audioPlayer.speak("Try again, little genius! It's ${targetNumber.number}.")
                                }
                            }
                        }
                    )
                    NumberOptionCard(
                        num = options[3],
                        isSelected = selectedAnswer == options[3],
                        isCorrect = if (selectedAnswer == options[3]) isAnswerCorrect else null,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            if (selectedAnswer == null) {
                                selectedAnswer = options[3]
                                val correct = options[3] == targetNumber.number
                                isAnswerCorrect = correct
                                if (correct) {
                                    score++
                                    audioPlayer.playCheer("Correct! There are ${targetNumber.number} ${targetNumber.objectName}!")
                                } else {
                                    audioPlayer.speak("Try again, little genius! It's ${targetNumber.number}.")
                                }
                            }
                        }
                    )
                }
            }

            // Next Button
            KidPrimaryButton(
                text = if (quizStep < 4) "Next Count ➡️" else "Finish Counting 🏆",
                onClick = {
                    if (quizStep < 4) {
                        quizStep++
                    } else {
                        showDialog = true
                        onQuizComplete(score, 5)
                    }
                },
                enabled = selectedAnswer != null,
                modifier = Modifier.fillMaxWidth(),
                containerColor = KidMintGreen,
                testTag = "number_quiz_next"
            )
        }

        if (showDialog) {
            ConfettiOverlay()
            QuizSuccessCelebrationDialog(
                score = score,
                total = 5,
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

@Composable
fun NumberOptionCard(
    num: Int,
    isSelected: Boolean,
    isCorrect: Boolean?,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val bgColor by animateColorAsState(
        targetValue = when {
            isCorrect == true -> Color(0xFFDCFCE7)
            isCorrect == false -> Color(0xFFFFE4E6)
            isSelected -> Color(0xFFE0E7FF)
            else -> Color.White
        },
        label = "numColor"
    )

    Card(
        modifier = modifier
            .height(90.dp)
            .clickable { onClick() }
            .testTag("number_option_$num"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
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
                text = "$num",
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF1E293B)
            )
        }
    }
}

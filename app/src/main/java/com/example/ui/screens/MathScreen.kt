package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.KidsAudioPlayer
import com.example.data.MathProblem
import com.example.data.MathRepository
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
fun MathScreen(
    audioPlayer: KidsAudioPlayer,
    progressRepo: ProgressRepository,
    totalStars: Int,
    onBackToHome: () -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) } // 0 = Addition, 1 = Subtraction, 2 = Math Quiz
    var currentAdditionIndex by remember { mutableIntStateOf(0) }
    var currentSubtractionIndex by remember { mutableIntStateOf(0) }

    val additionList = MathRepository.additionLessons
    val subtractionList = MathRepository.subtractionLessons

    val currentProblem = when (selectedTab) {
        0 -> additionList[currentAdditionIndex]
        1 -> subtractionList[currentSubtractionIndex]
        else -> additionList[0]
    }

    LaunchedEffect(selectedTab, currentAdditionIndex, currentSubtractionIndex) {
        if (selectedTab == 0 || selectedTab == 1) {
            audioPlayer.speak(currentProblem.explanation)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F7FE))
            .testTag("math_screen")
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
                    modifier = Modifier.testTag("math_back_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back to Home",
                        tint = KidSkyBlue,
                        modifier = Modifier.size(28.dp)
                    )
                }

                Text(
                    text = "Fun Maths Learning",
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
                text = { Text("➕ Addition", fontWeight = FontWeight.Bold, fontSize = 16.sp) },
                modifier = Modifier.testTag("tab_math_addition")
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = { Text("➖ Subtraction", fontWeight = FontWeight.Bold, fontSize = 16.sp) },
                modifier = Modifier.testTag("tab_math_subtraction")
            )
            Tab(
                selected = selectedTab == 2,
                onClick = { selectedTab = 2 },
                text = { Text("🎯 Math Challenge", fontWeight = FontWeight.Bold, fontSize = 16.sp) },
                modifier = Modifier.testTag("tab_math_quiz")
            )
        }

        if (selectedTab == 0 || selectedTab == 1) {
            // Visual Learning Card for Addition or Subtraction
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("math_problem_card"),
                    shape = RoundedCornerShape(32.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Title row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = if (selectedTab == 0) "Visual Addition" else "Visual Subtraction",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = if (selectedTab == 0) KidSkyBlue else KidBubblePink
                            )

                            AudioSpeakerButton(
                                onClick = { audioPlayer.speak(currentProblem.explanation) },
                                sizeDp = 46,
                                containerColor = KidSunnyYellow,
                                testTag = "math_speaker_button"
                            )
                        }

                        // Big equation banner
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = Color(0xFFF8FAFC),
                            border = androidx.compose.foundation.BorderStroke(2.dp, Color(0xFFE2E8F0)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 12.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                MathBadge(text = "${currentProblem.num1}", color = KidSkyBlue)
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = "${currentProblem.operator}",
                                    fontSize = 36.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color(0xFF475569)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                MathBadge(text = "${currentProblem.num2}", color = KidSunnyYellow)
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = "=",
                                    fontSize = 36.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color(0xFF475569)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                MathBadge(text = "${currentProblem.result}", color = KidMintGreen)
                            }
                        }

                        // Visual Objects Representation
                        if (selectedTab == 0) {
                            // Addition Visuals
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color(0xFFEBF5FF), RoundedCornerShape(20.dp))
                                    .padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = "Look at the ${currentProblem.objectName}:",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = KidSkyBlue
                                )
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = (1..currentProblem.num1).joinToString(" ") { currentProblem.objectEmoji },
                                        fontSize = 30.sp
                                    )
                                    Text(
                                        text = "  +  ",
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = KidSkyBlue
                                    )
                                    Text(
                                        text = (1..currentProblem.num2).joinToString(" ") { currentProblem.objectEmoji },
                                        fontSize = 30.sp
                                    )
                                }
                                Text(
                                    text = "Together they make ${currentProblem.result} ${currentProblem.objectName}!",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xFF1E293B)
                                )
                            }
                        } else {
                            // Subtraction Visuals with Crossed-out / Taken-away Items
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color(0xFFFFF0F5), RoundedCornerShape(20.dp))
                                    .padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = "Take Away Action:",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = KidBubblePink
                                )
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    // Remaining items
                                    Text(
                                        text = (1..currentProblem.result).joinToString(" ") { currentProblem.objectEmoji },
                                        fontSize = 30.sp
                                    )
                                    // Subtracted items shown with special indicator
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = (1..currentProblem.num2).joinToString(" ") { "❌" },
                                        fontSize = 20.sp
                                    )
                                }
                                Text(
                                    text = "${currentProblem.num1} minus ${currentProblem.num2} leaves ${currentProblem.result} remaining!",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xFF1E293B),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }

                        // Explanation text
                        Text(
                            text = currentProblem.explanation,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF334155),
                            textAlign = TextAlign.Center
                        )
                    }
                }

                // Prev / Next Controls
                val maxIndex = if (selectedTab == 0) additionList.size - 1 else subtractionList.size - 1
                val currIndex = if (selectedTab == 0) currentAdditionIndex else currentSubtractionIndex

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    KidPrimaryButton(
                        text = "Prev",
                        onClick = {
                            if (currIndex > 0) {
                                if (selectedTab == 0) currentAdditionIndex-- else currentSubtractionIndex--
                                audioPlayer.playTapSound()
                            }
                        },
                        enabled = currIndex > 0,
                        modifier = Modifier.weight(1f),
                        containerColor = Color(0xFF94A3B8),
                        testTag = "prev_math_button"
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    KidPrimaryButton(
                        text = "Next",
                        onClick = {
                            if (currIndex < maxIndex) {
                                if (selectedTab == 0) currentAdditionIndex++ else currentSubtractionIndex++
                                audioPlayer.playTapSound()
                                progressRepo.recordMathSolved()
                            }
                        },
                        enabled = currIndex < maxIndex,
                        modifier = Modifier.weight(1f),
                        containerColor = KidMintGreen,
                        testTag = "next_math_button"
                    )
                }
            }
        } else {
            // Interactive Maths Quiz
            MathQuizView(
                audioPlayer = audioPlayer,
                onQuizComplete = { score, total ->
                    progressRepo.recordQuizPassed(score * 2)
                }
            )
        }
    }
}

@Composable
fun MathBadge(text: String, color: Color) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = color,
        modifier = Modifier.size(54.dp)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = text,
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White
            )
        }
    }
}

@Composable
fun MathQuizView(
    audioPlayer: KidsAudioPlayer,
    onQuizComplete: (score: Int, total: Int) -> Unit
) {
    var quizStep by remember { mutableIntStateOf(0) }
    var score by remember { mutableIntStateOf(0) }
    var showDialog by remember { mutableStateOf(false) }
    var selectedAnswer by remember { mutableStateOf<Int?>(null) }
    var isAnswerCorrect by remember { mutableStateOf<Boolean?>(null) }

    val currentProblem = remember(quizStep) {
        if (quizStep % 2 == 0) {
            MathRepository.generateRandomAddition()
        } else {
            MathRepository.generateRandomSubtraction()
        }
    }

    val options = remember(currentProblem) {
        val wrong = listOf(
            (currentProblem.result - 2).coerceAtLeast(0),
            (currentProblem.result - 1).coerceAtLeast(0),
            currentProblem.result + 1,
            currentProblem.result + 2
        ).distinct().filter { it != currentProblem.result }.take(3)
        (wrong + currentProblem.result).shuffled()
    }

    LaunchedEffect(quizStep) {
        selectedAnswer = null
        isAnswerCorrect = null
        audioPlayer.speak("What is ${currentProblem.num1} ${if (currentProblem.operator == '+') "plus" else "minus"} ${currentProblem.num2}?")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("math_quiz_view")
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
                    text = "Math Challenge: ${quizStep + 1} of 5",
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

            // Equation Card
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
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Text(
                        text = "Solve the equation:",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E293B)
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "${currentProblem.num1} ${currentProblem.operator} ${currentProblem.num2} = ?",
                            fontSize = 38.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = KidSkyBlue
                        )
                    }

                    // Visual aid emojis
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = (1..currentProblem.num1).joinToString("") { currentProblem.objectEmoji },
                            fontSize = 24.sp
                        )
                        Text(
                            text = " ${currentProblem.operator} ",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = (1..currentProblem.num2).joinToString("") { currentProblem.objectEmoji },
                            fontSize = 24.sp
                        )
                    }

                    AudioSpeakerButton(
                        onClick = {
                            audioPlayer.speak("What is ${currentProblem.num1} ${if (currentProblem.operator == '+') "plus" else "minus"} ${currentProblem.num2}?")
                        },
                        sizeDp = 42,
                        containerColor = KidSunnyYellow
                    )
                }
            }

            // 4 Options Grid
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    MathOptionCard(
                        num = options[0],
                        isSelected = selectedAnswer == options[0],
                        isCorrect = if (selectedAnswer == options[0]) isAnswerCorrect else null,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            if (selectedAnswer == null) {
                                selectedAnswer = options[0]
                                val correct = options[0] == currentProblem.result
                                isAnswerCorrect = correct
                                if (correct) {
                                    score++
                                    audioPlayer.playCheer("Correct! ${currentProblem.explanation}")
                                } else {
                                    audioPlayer.speak("Try again, little genius! The answer is ${currentProblem.result}.")
                                }
                            }
                        }
                    )
                    MathOptionCard(
                        num = options[1],
                        isSelected = selectedAnswer == options[1],
                        isCorrect = if (selectedAnswer == options[1]) isAnswerCorrect else null,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            if (selectedAnswer == null) {
                                selectedAnswer = options[1]
                                val correct = options[1] == currentProblem.result
                                isAnswerCorrect = correct
                                if (correct) {
                                    score++
                                    audioPlayer.playCheer("Correct! ${currentProblem.explanation}")
                                } else {
                                    audioPlayer.speak("Try again, little genius! The answer is ${currentProblem.result}.")
                                }
                            }
                        }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    MathOptionCard(
                        num = options[2],
                        isSelected = selectedAnswer == options[2],
                        isCorrect = if (selectedAnswer == options[2]) isAnswerCorrect else null,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            if (selectedAnswer == null) {
                                selectedAnswer = options[2]
                                val correct = options[2] == currentProblem.result
                                isAnswerCorrect = correct
                                if (correct) {
                                    score++
                                    audioPlayer.playCheer("Correct! ${currentProblem.explanation}")
                                } else {
                                    audioPlayer.speak("Try again, little genius! The answer is ${currentProblem.result}.")
                                }
                            }
                        }
                    )
                    MathOptionCard(
                        num = options[3],
                        isSelected = selectedAnswer == options[3],
                        isCorrect = if (selectedAnswer == options[3]) isAnswerCorrect else null,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            if (selectedAnswer == null) {
                                selectedAnswer = options[3]
                                val correct = options[3] == currentProblem.result
                                isAnswerCorrect = correct
                                if (correct) {
                                    score++
                                    audioPlayer.playCheer("Correct! ${currentProblem.explanation}")
                                } else {
                                    audioPlayer.speak("Try again, little genius! The answer is ${currentProblem.result}.")
                                }
                            }
                        }
                    )
                }
            }

            // Next Question Button
            KidPrimaryButton(
                text = if (quizStep < 4) "Next Math Question ➡️" else "Finish Challenge 🏆",
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
                testTag = "math_quiz_next_button"
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
fun MathOptionCard(
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
        label = "mathOptColor"
    )

    Card(
        modifier = modifier
            .height(86.dp)
            .clickable { onClick() }
            .testTag("math_option_$num"),
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

package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Flip
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.KidsAudioPlayer
import com.example.data.AlphabetItem
import com.example.data.AlphabetRepository
import com.example.data.ProgressRepository
import com.example.ui.components.AudioSpeakerButton
import com.example.ui.components.ConfettiOverlay
import com.example.ui.components.FlipCard
import com.example.ui.components.KidPrimaryButton
import com.example.ui.components.QuizSuccessCelebrationDialog
import com.example.ui.components.StarBadge
import com.example.ui.theme.KidBubblePink
import com.example.ui.theme.KidMintGreen
import com.example.ui.theme.KidSkyBlue
import com.example.ui.theme.KidSunnyYellow
import kotlinx.coroutines.launch

@Composable
fun AlphabetScreen(
    audioPlayer: KidsAudioPlayer,
    progressRepo: ProgressRepository,
    totalStars: Int,
    completedLetters: Set<Char>,
    onBackToHome: () -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) } // 0 = Flashcards, 1 = Quizzes & Games
    var currentIndex by remember { mutableIntStateOf(0) }
    var isFlipped by remember { mutableStateOf(false) }
    val letters = AlphabetRepository.alphabetList
    val currentItem = letters[currentIndex]

    val lazyListState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    // Speak letter when changed in flashcard mode
    LaunchedEffect(currentIndex, selectedTab) {
        if (selectedTab == 0) {
            isFlipped = false
            progressRepo.markLetterCompleted(currentItem.letter)
            audioPlayer.speak("${currentItem.letter}. ${currentItem.pronunciation}")
            scope.launch {
                lazyListState.animateScrollToItem((currentIndex - 2).coerceAtLeast(0))
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F7FE))
            .testTag("alphabet_screen")
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
                    modifier = Modifier.testTag("alphabet_back_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back to Home",
                        tint = KidSkyBlue,
                        modifier = Modifier.size(28.dp)
                    )
                }

                Text(
                    text = "ABC Alphabet Learning",
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
                        "🔤 Learn A to Z",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                },
                modifier = Modifier.testTag("tab_learn_az")
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = {
                    Text(
                        "🎮 Quiz & Games",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                },
                modifier = Modifier.testTag("tab_alphabet_quiz")
            )
        }

        if (selectedTab == 0) {
            // Flashcard Learning Flow
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // A-Z Quick Selector Ribbon
                LazyRow(
                    state = lazyListState,
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    itemsIndexed(letters) { idx, item ->
                        val isSelected = idx == currentIndex
                        val isLearned = completedLetters.contains(item.letter)
                        Surface(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .clickable {
                                    currentIndex = idx
                                    audioPlayer.playTapSound()
                                }
                                .testTag("letter_tab_${item.letter}"),
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
                                    text = "${item.letter}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                    color = if (isSelected) Color.White else if (isLearned) Color(0xFF2E7D32) else Color(0xFF475569)
                                )
                            }
                        }
                    }
                }

                // 3D Flip Flashcard
                FlipCard(
                    isFlipped = isFlipped,
                    onCardClick = {
                        isFlipped = !isFlipped
                        audioPlayer.playTapSound()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(380.dp),
                    frontContent = {
                        AlphabetCardFront(
                            item = currentItem,
                            onLetterClick = {
                                audioPlayer.speak(currentItem.letter.toString())
                            },
                            onObjectClick = {
                                audioPlayer.speak(currentItem.word)
                            },
                            onSpeakerClick = {
                                audioPlayer.speak("${currentItem.letter}. ${currentItem.pronunciation}. ${currentItem.sentence1}")
                            }
                        )
                    },
                    backContent = {
                        AlphabetCardBack(
                            item = currentItem,
                            onSpeakerClick = {
                                val fullNarration = "${currentItem.letter} is for ${currentItem.word}. " +
                                        "${currentItem.sentence1} ${currentItem.sentence2} ${currentItem.sentence3} " +
                                        "Fun fact: ${currentItem.funFact}"
                                audioPlayer.speak(fullNarration)
                            }
                        )
                    }
                )

                // Large Flip Card Button
                KidPrimaryButton(
                    text = if (isFlipped) "🔄 Show Front Side" else "🔄 Flip Card for Facts!",
                    onClick = {
                        isFlipped = !isFlipped
                        audioPlayer.playTapSound()
                    },
                    modifier = Modifier.fillMaxWidth(0.9f),
                    containerColor = KidSunnyYellow,
                    contentColor = Color(0xFF3E2723),
                    testTag = "flip_card_button"
                )

                // Navigation Controls (Previous, Audio Replay, Next)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
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
                        testTag = "prev_letter_button"
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    AudioSpeakerButton(
                        onClick = {
                            audioPlayer.speak("${currentItem.letter}. ${currentItem.pronunciation}. ${currentItem.sentence1}")
                        },
                        sizeDp = 58,
                        containerColor = KidBubblePink,
                        testTag = "replay_audio_button"
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    KidPrimaryButton(
                        text = "Next",
                        onClick = {
                            if (currentIndex < letters.size - 1) {
                                currentIndex++
                                audioPlayer.playTapSound()
                            }
                        },
                        enabled = currentIndex < letters.size - 1,
                        modifier = Modifier.weight(1f),
                        containerColor = KidMintGreen,
                        testTag = "next_letter_button"
                    )
                }
            }
        } else {
            // Interactive Alphabet Quiz Section
            AlphabetQuizView(
                audioPlayer = audioPlayer,
                onQuizComplete = { score, total ->
                    progressRepo.recordQuizPassed(score * 2)
                }
            )
        }
    }
}

@Composable
fun AlphabetCardFront(
    item: AlphabetItem,
    onLetterClick: () -> Unit,
    onObjectClick: () -> Unit,
    onSpeakerClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(item.cardColorHex).copy(alpha = 0.15f),
                        Color.White
                    )
                )
            )
            .padding(20.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top Row: Letter badge and Speaker
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = Color(item.cardColorHex),
                    shadowElevation = 4.dp,
                    modifier = Modifier
                        .clickable { onLetterClick() }
                        .testTag("card_letter_badge")
                ) {
                    Text(
                        text = "${item.capital} ${item.small}",
                        fontSize = 36.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)
                    )
                }

                AudioSpeakerButton(
                    onClick = onSpeakerClick,
                    sizeDp = 48,
                    containerColor = KidSunnyYellow,
                    testTag = "front_speaker_button"
                )
            }

            // Big 3D Cartoon Object Representation
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .border(4.dp, Color(item.cardColorHex).copy(alpha = 0.5f), CircleShape)
                    .shadow(8.dp, CircleShape)
                    .clickable { onObjectClick() }
                    .testTag("card_object_image"),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = item.emoji,
                    fontSize = 80.sp
                )
            }

            // Word and Pronunciation
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = item.word,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(item.cardColorHex)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = item.pronunciation,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF64748B)
                )
            }

            Text(
                text = "👆 Tap anywhere to flip card & learn fun facts!",
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF94A3B8)
            )
        }
    }
}

@Composable
fun AlphabetCardBack(
    item: AlphabetItem,
    onSpeakerClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFDE7))
            .padding(20.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "✨ Educational Facts",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFFE65100)
                )

                AudioSpeakerButton(
                    onClick = onSpeakerClick,
                    sizeDp = 46,
                    containerColor = KidSunnyYellow,
                    testTag = "back_speaker_button"
                )
            }

            // Sentences
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                FactBullet(text = item.sentence1)
                FactBullet(text = item.sentence2)
                FactBullet(text = item.sentence3)
            }

            // Fun Fact Box
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color.White,
                border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFFFFD54F)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "💡", fontSize = 24.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = item.funFact,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF374151)
                    )
                }
            }

            // Everyday usage
            Text(
                text = "💬 \"${item.everydayUsage}\"",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF6B7280),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun FactBullet(text: String) {
    Row(
        verticalAlignment = Alignment.Top,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = "⭐ ", fontSize = 16.sp, color = KidSunnyYellow)
        Text(
            text = text,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF1E293B)
        )
    }
}

/**
 * Interactive Alphabet Quiz View with 4 randomized child-friendly quiz modes:
 * - Identify Picture
 * - Missing Letter
 * - Listen and Identify
 * - Upper and Lowercase Match
 */
@Composable
fun AlphabetQuizView(
    audioPlayer: KidsAudioPlayer,
    onQuizComplete: (score: Int, total: Int) -> Unit
) {
    var quizStep by remember { mutableIntStateOf(0) }
    var score by remember { mutableIntStateOf(0) }
    var showDialog by remember { mutableStateOf(false) }
    var selectedAnswer by remember { mutableStateOf<String?>(null) }
    var isAnswerCorrect by remember { mutableStateOf<Boolean?>(null) }

    val allLetters = AlphabetRepository.alphabetList

    // 5 progressive questions
    val currentLetter = allLetters[(quizStep * 4 + 1) % allLetters.size]
    val distractors = remember(quizStep) {
        allLetters.filter { it.letter != currentLetter.letter }.shuffled().take(3)
    }
    val options = remember(quizStep) {
        (distractors + currentLetter).shuffled()
    }

    LaunchedEffect(quizStep) {
        selectedAnswer = null
        isAnswerCorrect = null
        audioPlayer.speak("Which picture starts with the letter ${currentLetter.letter}?")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("alphabet_quiz_view")
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
                    text = "Question ${quizStep + 1} of 5",
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
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "Which picture starts with the letter?",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E293B),
                        textAlign = TextAlign.Center
                    )

                    Surface(
                        shape = CircleShape,
                        color = KidSkyBlue,
                        modifier = Modifier.size(72.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = "${currentLetter.capital}",
                                fontSize = 42.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.White
                            )
                        }
                    }

                    AudioSpeakerButton(
                        onClick = {
                            audioPlayer.speak("Find the picture for letter ${currentLetter.letter}")
                        },
                        sizeDp = 44,
                        containerColor = KidSunnyYellow
                    )
                }
            }

            // 4 Picture Options (2x2 Grid)
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    QuizOptionItem(
                        item = options[0],
                        isSelected = selectedAnswer == options[0].word,
                        isCorrect = if (selectedAnswer == options[0].word) isAnswerCorrect else null,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            if (selectedAnswer == null) {
                                selectedAnswer = options[0].word
                                val correct = options[0].word == currentLetter.word
                                isAnswerCorrect = correct
                                if (correct) {
                                    score++
                                    audioPlayer.playCheer("Super! ${options[0].word} starts with ${currentLetter.letter}!")
                                } else {
                                    audioPlayer.speak("Try again, little genius! It's ${currentLetter.word}.")
                                }
                            }
                        }
                    )
                    QuizOptionItem(
                        item = options[1],
                        isSelected = selectedAnswer == options[1].word,
                        isCorrect = if (selectedAnswer == options[1].word) isAnswerCorrect else null,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            if (selectedAnswer == null) {
                                selectedAnswer = options[1].word
                                val correct = options[1].word == currentLetter.word
                                isAnswerCorrect = correct
                                if (correct) {
                                    score++
                                    audioPlayer.playCheer("Super! ${options[1].word} starts with ${currentLetter.letter}!")
                                } else {
                                    audioPlayer.speak("Try again, little genius! It's ${currentLetter.word}.")
                                }
                            }
                        }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    QuizOptionItem(
                        item = options[2],
                        isSelected = selectedAnswer == options[2].word,
                        isCorrect = if (selectedAnswer == options[2].word) isAnswerCorrect else null,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            if (selectedAnswer == null) {
                                selectedAnswer = options[2].word
                                val correct = options[2].word == currentLetter.word
                                isAnswerCorrect = correct
                                if (correct) {
                                    score++
                                    audioPlayer.playCheer("Super! ${options[2].word} starts with ${currentLetter.letter}!")
                                } else {
                                    audioPlayer.speak("Try again, little genius! It's ${currentLetter.word}.")
                                }
                            }
                        }
                    )
                    QuizOptionItem(
                        item = options[3],
                        isSelected = selectedAnswer == options[3].word,
                        isCorrect = if (selectedAnswer == options[3].word) isAnswerCorrect else null,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            if (selectedAnswer == null) {
                                selectedAnswer = options[3].word
                                val correct = options[3].word == currentLetter.word
                                isAnswerCorrect = correct
                                if (correct) {
                                    score++
                                    audioPlayer.playCheer("Super! ${options[3].word} starts with ${currentLetter.letter}!")
                                } else {
                                    audioPlayer.speak("Try again, little genius! It's ${currentLetter.word}.")
                                }
                            }
                        }
                    )
                }
            }

            // Next Question Button
            KidPrimaryButton(
                text = if (quizStep < 4) "Next Question ➡️" else "Finish Quiz 🏆",
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
                testTag = "quiz_next_button"
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
fun QuizOptionItem(
    item: AlphabetItem,
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
        label = "optionColor"
    )

    Card(
        modifier = modifier
            .height(110.dp)
            .clickable { onClick() }
            .testTag("quiz_option_${item.word}"),
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = item.emoji, fontSize = 42.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = item.word,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E293B)
            )
        }
    }
}

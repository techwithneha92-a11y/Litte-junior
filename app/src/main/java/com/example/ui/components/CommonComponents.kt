package com.example.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.ui.theme.KidBubblePink
import com.example.ui.theme.KidCardSurface
import com.example.ui.theme.KidMintGreen
import com.example.ui.theme.KidSkyBlue
import com.example.ui.theme.KidStarGold
import com.example.ui.theme.KidStarGoldDark
import com.example.ui.theme.KidSunnyYellow
import kotlin.random.Random

@Composable
fun StarBadge(
    stars: Int,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    Surface(
        modifier = modifier
            .testTag("star_badge")
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier),
        shape = RoundedCornerShape(24.dp),
        color = Color(0xFFFFF9C4),
        shadowElevation = 4.dp,
        border = androidx.compose.foundation.BorderStroke(2.dp, KidStarGoldDark)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Total Stars",
                tint = KidStarGoldDark,
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = "$stars",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 18.sp,
                color = Color(0xFFE65100)
            )
        }
    }
}

@Composable
fun KidPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = KidSkyBlue,
    contentColor: Color = Color.White,
    icon: (@Composable () -> Unit)? = null,
    testTag: String = "kid_primary_button",
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .height(56.dp)
            .shadow(6.dp, RoundedCornerShape(28.dp))
            .testTag(testTag),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = Color(0xFFCBD5E1),
            disabledContentColor = Color.White
        ),
        shape = RoundedCornerShape(28.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (icon != null) {
                icon()
                Spacer(modifier = Modifier.width(8.dp))
            }
            Text(
                text = text,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun AudioSpeakerButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    sizeDp: Int = 54,
    containerColor: Color = KidSunnyYellow,
    contentDescription: String = "Listen to pronunciation",
    testTag: String = "speaker_button"
) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = 1.08f,
        animationSpec = infiniteRepeatable(
            animation = tween(900, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )

    Box(
        modifier = modifier
            .size(sizeDp.dp)
            .scale(pulseScale)
            .shadow(6.dp, CircleShape)
            .clip(CircleShape)
            .background(containerColor)
            .border(3.dp, Color.White, CircleShape)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onClick() }
            .testTag(testTag),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.VolumeUp,
            contentDescription = contentDescription,
            tint = Color(0xFF3E2723),
            modifier = Modifier.size((sizeDp * 0.55).dp)
        )
    }
}

/**
 * Interactive 3D flip card:
 * Animates a 180-degree Y rotation.
 * When rotation passes 90 degrees, front content swaps cleanly to back content without mirroring!
 */
@Composable
fun FlipCard(
    isFlipped: Boolean,
    onCardClick: () -> Unit,
    frontContent: @Composable () -> Unit,
    backContent: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    val rotation = remember { Animatable(0f) }

    LaunchedEffect(isFlipped) {
        rotation.animateTo(
            targetValue = if (isFlipped) 180f else 0f,
            animationSpec = tween(durationMillis = 500, easing = LinearEasing)
        )
    }

    Card(
        modifier = modifier
            .testTag("interactive_flip_card")
            .graphicsLayer {
                rotationY = rotation.value
                cameraDistance = 14f * density
            }
            .clickable { onCardClick() },
        shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(containerColor = KidCardSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (rotation.value <= 90f) {
                frontContent()
            } else {
                // Counter-rotate back content so text is not reversed
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer { rotationY = 180f }
                ) {
                    backContent()
                }
            }
        }
    }
}

@Composable
fun QuizSuccessCelebrationDialog(
    score: Int,
    total: Int,
    starsEarned: Int,
    onPlayAgain: () -> Unit,
    onNext: () -> Unit
) {
    Dialog(onDismissRequest = onNext) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .testTag("quiz_success_dialog"),
            shape = RoundedCornerShape(32.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(text = "🎉", fontSize = 56.sp)

                Text(
                    text = if (score == total) "Super Little Genius!" else "Great Job!",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = KidSkyBlue,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "You scored $score out of $total questions correctly!",
                    fontSize = 16.sp,
                    color = Color(0xFF475569),
                    textAlign = TextAlign.Center
                )

                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = Color(0xFFFFF9C4),
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 18.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(text = "⭐", fontSize = 24.sp)
                        Text(
                            text = "+$starsEarned Stars Earned!",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = KidStarGoldDark
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    KidPrimaryButton(
                        text = "Try Again",
                        onClick = onPlayAgain,
                        modifier = Modifier.weight(1f),
                        containerColor = KidSunnyYellow,
                        contentColor = Color(0xFF3E2723),
                        testTag = "play_again_button"
                    )
                    KidPrimaryButton(
                        text = "Awesome!",
                        onClick = onNext,
                        modifier = Modifier.weight(1f),
                        containerColor = KidMintGreen,
                        testTag = "quiz_continue_button"
                    )
                }
            }
        }
    }
}

/**
 * Animated confetti overlay for celebrations!
 */
@Composable
fun ConfettiOverlay(modifier: Modifier = Modifier) {
    val particles = remember {
        List(40) {
            ConfettiParticle(
                xNorm = Random.nextFloat(),
                speed = Random.nextFloat() * 400f + 250f,
                color = listOf(
                    KidSkyBlue, KidSunnyYellow, KidBubblePink,
                    KidMintGreen, KidStarGoldDark, Color(0xFF9C27B0)
                ).random(),
                size = Random.nextFloat() * 14f + 8f,
                angle = Random.nextFloat() * 360f
            )
        }
    }

    val animTime = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        animTime.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 3500, easing = LinearEasing)
        )
    }

    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val width = constraints.maxWidth.toFloat()
        val height = constraints.maxHeight.toFloat()

        Canvas(modifier = Modifier.fillMaxSize()) {
            val progress = animTime.value
            particles.forEach { p ->
                val currentY = (p.speed * progress * 2.2f) % (height + 50f) - 30f
                val currentX = (p.xNorm * width) + kotlin.math.sin((progress * 10f) + p.angle) * 35f
                drawRect(
                    color = p.color.copy(alpha = (1f - progress * 0.4f).coerceIn(0f, 1f)),
                    topLeft = Offset(currentX, currentY),
                    size = Size(p.size, p.size * 0.65f)
                )
            }
        }
    }
}

private data class ConfettiParticle(
    val xNorm: Float,
    val speed: Float,
    val color: Color,
    val size: Float,
    val angle: Float
)

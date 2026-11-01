package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AchievementBadge
import com.example.ui.components.ConfettiOverlay
import com.example.ui.components.StarBadge
import com.example.ui.theme.KidMintGreen
import com.example.ui.theme.KidSkyBlue
import com.example.ui.theme.KidStarGold
import com.example.ui.theme.KidSunnyYellow

@Composable
fun AchievementsScreen(
    badges: List<AchievementBadge>,
    totalStars: Int,
    onBackToHome: () -> Unit
) {
    val unlockedCount = badges.count { it.isUnlocked }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F7FE))
            .testTag("achievements_screen")
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
                    modifier = Modifier.testTag("achievements_back_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back to Home",
                        tint = KidSkyBlue,
                        modifier = Modifier.size(28.dp)
                    )
                }

                Text(
                    text = "My Achievements 🏆",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp,
                    color = Color(0xFF1E293B)
                )

                StarBadge(stars = totalStars)
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Trophy Banner
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(text = "👑", fontSize = 52.sp)
                        Text(
                            text = "Little Genius Trophy Room",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = KidSkyBlue
                        )
                        Text(
                            text = "Unlocked $unlockedCount of ${badges.size} Badges",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = KidMintGreen
                        )
                    }
                }
            }

            // Badges List
            items(badges) { badge ->
                BadgeCard(badge = badge)
            }
        }
    }
}

@Composable
fun BadgeCard(badge: AchievementBadge) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("badge_card_${badge.id}"),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (badge.isUnlocked) Color.White else Color(0xFFF1F5F9)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = if (badge.isUnlocked) 4.dp else 1.dp),
        border = androidx.compose.foundation.BorderStroke(
            2.dp,
            if (badge.isUnlocked) KidStarGold else Color(0xFFE2E8F0)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Badge icon circle
            Surface(
                shape = CircleShape,
                color = if (badge.isUnlocked) Color(0xFFFFF9C4) else Color(0xFFE2E8F0),
                border = androidx.compose.foundation.BorderStroke(
                    2.dp,
                    if (badge.isUnlocked) KidStarGold else Color(0xFFCBD5E1)
                ),
                modifier = Modifier.size(64.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = if (badge.isUnlocked) badge.iconEmoji else "🔒",
                        fontSize = 32.sp
                    )
                }
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = badge.title,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = if (badge.isUnlocked) Color(0xFF1E293B) else Color(0xFF64748B)
                    )
                    if (badge.isUnlocked) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = Color(0xFFDCFCE7)
                        ) {
                            Text(
                                text = "Unlocked!",
                                color = KidMintGreen,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                }

                Text(
                    text = badge.description,
                    fontSize = 13.sp,
                    color = Color(0xFF64748B)
                )

                Spacer(modifier = Modifier.height(4.dp))

                LinearProgressIndicator(
                    progress = { badge.progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    color = if (badge.isUnlocked) KidMintGreen else KidSkyBlue,
                    trackColor = Color(0xFFCBD5E1)
                )

                Text(
                    text = badge.progressText,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF94A3B8)
                )
            }
        }
    }
}

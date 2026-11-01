package com.example.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.SportsCricket
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.local.WalletEntity
import com.example.data.model.CricketMatch
import com.example.data.model.MatchStatus
import com.example.ui.components.MatchCard
import com.example.ui.theme.ElectricCyan
import com.example.ui.theme.LiveRed
import com.example.ui.theme.PitchGreen
import com.example.ui.theme.StadiumBorder
import com.example.ui.theme.StadiumDarkNavy
import com.example.ui.theme.StadiumSurface
import com.example.ui.theme.StadiumSurfaceVariant
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TrophyGold

@Composable
fun HomeScreen(
    matches: List<CricketMatch>,
    wallet: WalletEntity?,
    activeFilter: MatchStatus?,
    onFilterChange: (MatchStatus?) -> Unit,
    onMatchClick: (CricketMatch) -> Unit,
    onWalletClick: () -> Unit,
    onOpenRules: () -> Unit,
    modifier: Modifier = Modifier
) {
    val filteredMatches = when (activeFilter) {
        null -> matches
        else -> matches.filter { it.status == activeFilter }
    }

    val liveCount = matches.count { it.status == MatchStatus.LIVE }
    val upcomingCount = matches.count { it.status == MatchStatus.UPCOMING }
    val completedCount = matches.count { it.status == MatchStatus.COMPLETED }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(StadiumDarkNavy)
            .testTag("home_screen")
    ) {
        // App Top Bar
        Surface(
            color = StadiumSurface,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.radialGradient(
                                    listOf(TrophyGold, Color(0xFFB45309))
                                )
                            )
                            .border(1.5.dp, TrophyGold, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "11",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 18.sp,
                            color = Color.Black
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column {
                        Text(
                            text = "FAN11",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold),
                            color = TextPrimary,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = "Fantasy Cricket Arena",
                            style = MaterialTheme.typography.labelSmall,
                            color = ElectricCyan
                        )
                    }
                }

                // Wallet Pill + Rules
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = onOpenRules,
                        modifier = Modifier.testTag("btn_rules")
                    ) {
                        Icon(
                            imageVector = Icons.Default.HelpOutline,
                            contentDescription = "Point System Guide",
                            tint = TextSecondary
                        )
                    }

                    Surface(
                        color = StadiumSurfaceVariant,
                        shape = RoundedCornerShape(20.dp),
                        border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(ElectricCyan, PitchGreen))),
                        modifier = Modifier
                            .clickable(onClick = onWalletClick)
                            .testTag("btn_home_wallet")
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.AccountBalanceWallet,
                                contentDescription = "Wallet",
                                tint = TrophyGold,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "₹${wallet?.totalBalance?.toInt() ?: 0}",
                                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                                color = TextPrimary
                            )
                        }
                    }
                }
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Hero Stadium Banner
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .testTag("hero_banner"),
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Image(
                            painter = painterResource(id = R.drawable.img_cricket_banner),
                            contentDescription = "Cricket Stadium",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )

                        // Gradient Scrim
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        listOf(
                                            Color.Transparent,
                                            Color(0xCC090D1A),
                                            Color(0xF0090D1A)
                                        )
                                    )
                                )
                        )

                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(16.dp)
                        ) {
                            Surface(
                                color = TrophyGold,
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text(
                                    text = "MEGA TOURNAMENT LEAGUE",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Play & Win ₹2.50 Crores Daily",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold),
                                color = Color.White
                            )
                            Text(
                                text = "Create your 11 • Earn 2X with Captain • Real Cash",
                                style = MaterialTheme.typography.bodySmall,
                                color = ElectricCyan
                            )
                        }
                    }
                }
            }

            // Quick Perks Bar
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Surface(
                        color = StadiumSurface,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Row(
                            modifier = Modifier.padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Bolt, contentDescription = null, tint = TrophyGold, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Instant UPI", style = MaterialTheme.typography.labelSmall, color = TextPrimary)
                        }
                    }
                    Surface(
                        color = StadiumSurface,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Row(
                            modifier = Modifier.padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Security, contentDescription = null, tint = PitchGreen, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("100% Legal", style = MaterialTheme.typography.labelSmall, color = TextPrimary)
                        }
                    }
                    Surface(
                        color = StadiumSurface,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Row(
                            modifier = Modifier.padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.EmojiEvents, contentDescription = null, tint = ElectricCyan, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Mega Pools", style = MaterialTheme.typography.labelSmall, color = TextPrimary)
                        }
                    }
                }
            }

            // Match Status Filter Tabs
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilterChip(
                        selected = activeFilter == null,
                        onClick = { onFilterChange(null) },
                        label = { Text("All (${matches.size})") },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = ElectricCyan,
                            selectedLabelColor = Color.Black,
                            containerColor = StadiumSurface,
                            labelColor = TextSecondary
                        )
                    )
                    FilterChip(
                        selected = activeFilter == MatchStatus.LIVE,
                        onClick = { onFilterChange(MatchStatus.LIVE) },
                        label = { Text("Live ($liveCount)") },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = LiveRed,
                            selectedLabelColor = Color.White,
                            containerColor = StadiumSurface,
                            labelColor = TextSecondary
                        )
                    )
                    FilterChip(
                        selected = activeFilter == MatchStatus.UPCOMING,
                        onClick = { onFilterChange(MatchStatus.UPCOMING) },
                        label = { Text("Upcoming ($upcomingCount)") },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = ElectricCyan,
                            selectedLabelColor = Color.Black,
                            containerColor = StadiumSurface,
                            labelColor = TextSecondary
                        )
                    )
                    FilterChip(
                        selected = activeFilter == MatchStatus.COMPLETED,
                        onClick = { onFilterChange(MatchStatus.COMPLETED) },
                        label = { Text("Completed ($completedCount)") },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = ElectricCyan,
                            selectedLabelColor = Color.Black,
                            containerColor = StadiumSurface,
                            labelColor = TextSecondary
                        )
                    )
                }
            }

            // Match Cards
            items(filteredMatches, key = { it.id }) { match ->
                MatchCard(
                    match = match,
                    onClick = { onMatchClick(match) }
                )
            }
        }
    }
}

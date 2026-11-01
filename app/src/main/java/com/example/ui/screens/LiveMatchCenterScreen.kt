package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.SportsCricket
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CricketMatch
import com.example.data.model.LeaderboardEntry
import com.example.data.model.LiveBallEvent
import com.example.data.repository.FantasyData
import com.example.ui.components.TeamLogoPill
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
fun LiveMatchCenterScreen(
    match: CricketMatch,
    onBack: () -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Fantasy Leaderboard", "Ball by Ball Feed", "Top Scorers")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(StadiumDarkNavy)
            .statusBarsPadding()
            .testTag("live_match_center_screen")
    ) {
        // App Bar
        Surface(
            color = StadiumSurface,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = onBack, modifier = Modifier.testTag("btn_back_live_center")) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = TextPrimary
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Circle,
                        contentDescription = null,
                        tint = LiveRed,
                        modifier = Modifier.size(10.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "LIVE MATCH CENTER",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold),
                        color = LiveRed,
                        letterSpacing = 1.sp
                    )
                }

                Spacer(modifier = Modifier.width(48.dp))
            }
        }

        // Live Cricket Scorecard Banner
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = StadiumSurface),
            border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(LiveRed, ElectricCyan)))
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = match.tournament,
                        style = MaterialTheme.typography.labelSmall,
                        color = TextSecondary
                    )
                    Text(
                        text = "CRR: 10.04",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = ElectricCyan
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Teams Score Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        TeamLogoPill(shortName = match.team1Short, color = Color(match.team1Color))
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = match.team1Short,
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = TextPrimary
                            )
                            Text(
                                text = match.team1Score ?: "184/4 (18.1 ov)",
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold),
                                color = TrophyGold
                            )
                        }
                    }

                    Text(
                        text = "VS",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextSecondary
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = match.team2Short,
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = TextPrimary
                            )
                            Text(
                                text = match.team2Score ?: "Yet to bat",
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextSecondary
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        TeamLogoPill(shortName = match.team2Short, color = Color(match.team2Color))
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Active Batsmen & Bowler
                Surface(
                    color = StadiumSurfaceVariant,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("🏏 Virat Kohli* 72 (44b, 6x4, 3x6)", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold), color = TextPrimary)
                            Text("Hardik* 36 (16b)", style = MaterialTheme.typography.labelSmall, color = TextSecondary)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("🎯 Mitchell Starc: 3.1 - 0 - 38 - 1", style = MaterialTheme.typography.labelSmall, color = ElectricCyan)
                            Text("Recent: 1 4 2 W 6 4", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold), color = TrophyGold)
                        }
                    }
                }
            }
        }

        // Sub Tabs
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = StadiumSurface,
            contentColor = ElectricCyan,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                    color = ElectricCyan
                )
            }
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = {
                        Text(
                            text = title,
                            fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal,
                            color = if (selectedTab == index) ElectricCyan else TextSecondary,
                            fontSize = 12.sp
                        )
                    }
                )
            }
        }

        // Tab Body
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            when (selectedTab) {
                0 -> LiveLeaderboardTab(entries = FantasyData.sampleLeaderboard)
                1 -> BallByBallTab(events = match.liveEvents)
                2 -> TopScorersTab()
            }
        }
    }
}

@Composable
fun LiveLeaderboardTab(entries: List<LeaderboardEntry>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(14.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(entries, key = { it.rank }) { entry ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (entry.isCurrentUser) StadiumSurfaceVariant else StadiumSurface
                ),
                border = if (entry.isCurrentUser) {
                    CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(ElectricCyan, PitchGreen)))
                } else {
                    CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(StadiumBorder, StadiumBorder)))
                }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Rank & change indicator
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.width(60.dp)
                    ) {
                        Text(
                            text = "#${entry.rank}",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 16.sp,
                            color = if (entry.rank <= 3) TrophyGold else TextPrimary
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        if (entry.rankChange > 0) {
                            Icon(Icons.Default.KeyboardArrowUp, contentDescription = "Rank up", tint = PitchGreen, modifier = Modifier.size(16.dp))
                        } else if (entry.rankChange < 0) {
                            Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Rank down", tint = LiveRed, modifier = Modifier.size(16.dp))
                        }
                    }

                    // Team Name & User
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = entry.userTeamName,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = if (entry.isCurrentUser) ElectricCyan else TextPrimary
                        )
                        Text(
                            text = if (entry.isCurrentUser) "★ YOU" else entry.userName,
                            style = MaterialTheme.typography.labelSmall,
                            color = if (entry.isCurrentUser) PitchGreen else TextSecondary
                        )
                    }

                    // Points & Winnings
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "${entry.points} pts",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = TrophyGold
                        )
                        if (entry.winningAmount != null) {
                            Text(
                                text = "Winning: ${entry.winningAmount}",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = PitchGreen
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BallByBallTab(events: List<LiveBallEvent>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(14.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(events) { event ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = StadiumSurface),
                border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(StadiumBorder, StadiumBorder)))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    // Ball badge
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(
                                when {
                                    event.isWicket -> LiveRed
                                    event.isBoundary -> TrophyGold
                                    else -> StadiumSurfaceVariant
                                }
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = event.ball,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (event.isBoundary || event.isWicket) Color.Black else TextPrimary
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = event.description,
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                            color = TextPrimary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = event.fantasyImpact,
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = ElectricCyan
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TopScorersTab() {
    val topPlayers = listOf(
        Triple("Jasprit Bumrah", "IND • BOWL", "88 pts (3 wkts, 1 maiden)"),
        Triple("Virat Kohli", "IND • BAT", "84 pts (72* runs, 6 boundaries)"),
        Triple("Pat Cummins", "AUS • BOWL", "76 pts (2 wkts, 1 catch)"),
        Triple("Hardik Pandya", "IND • AR", "68 pts (36* runs, 1 wkt)"),
        Triple("Suryakumar Yadav", "IND • BAT", "58 pts (48 runs, 2 sixes)")
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(14.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(topPlayers) { (name, meta, stats) ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = StadiumSurface)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(name, style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold), color = TextPrimary)
                        Text(meta, style = MaterialTheme.typography.labelSmall, color = TextSecondary)
                    }
                    Text(stats, style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold), color = TrophyGold)
                }
            }
        }
    }
}

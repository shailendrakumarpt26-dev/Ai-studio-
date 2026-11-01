package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.SportsCricket
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.JoinedContestEntity
import com.example.data.local.UserTeamEntity
import com.example.data.model.CricketMatch
import com.example.data.model.MatchStatus
import com.example.ui.components.MatchStatusBadge
import com.example.ui.components.TeamLogoPill
import com.example.ui.theme.ElectricCyan
import com.example.ui.theme.PitchGreen
import com.example.ui.theme.StadiumBorder
import com.example.ui.theme.StadiumDarkNavy
import com.example.ui.theme.StadiumSurface
import com.example.ui.theme.StadiumSurfaceVariant
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TrophyGold

@Composable
fun MyMatchesScreen(
    matches: List<CricketMatch>,
    joinedContests: List<JoinedContestEntity>,
    userTeams: List<UserTeamEntity>,
    onMatchClick: (CricketMatch) -> Unit,
    onExploreMatches: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedFilter by remember { mutableStateOf<MatchStatus?>(null) }

    // Matches where user has joined contests or created teams
    val userMatchIds = (joinedContests.map { it.matchId } + userTeams.map { it.matchId }).toSet()
    val myMatches = matches.filter { userMatchIds.contains(it.id) }

    val filteredMatches = when (selectedFilter) {
        null -> myMatches
        else -> myMatches.filter { it.status == selectedFilter }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(StadiumDarkNavy)
            .statusBarsPadding()
            .testTag("my_matches_screen")
    ) {
        // App Bar
        Surface(
            color = StadiumSurface,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
                Text(
                    text = "My Matches",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = TextPrimary
                )
                Text(
                    text = "Track your fantasy lineups, live scores, and tournament winnings",
                    style = MaterialTheme.typography.labelSmall,
                    color = TextSecondary
                )
            }
        }

        // Filter chips
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                selected = selectedFilter == null,
                onClick = { selectedFilter = null },
                label = { Text("All (${myMatches.size})") },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = ElectricCyan,
                    selectedLabelColor = Color.Black,
                    containerColor = StadiumSurface,
                    labelColor = TextSecondary
                )
            )
            FilterChip(
                selected = selectedFilter == MatchStatus.LIVE,
                onClick = { selectedFilter = MatchStatus.LIVE },
                label = { Text("Live") },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = ElectricCyan,
                    selectedLabelColor = Color.Black,
                    containerColor = StadiumSurface,
                    labelColor = TextSecondary
                )
            )
            FilterChip(
                selected = selectedFilter == MatchStatus.UPCOMING,
                onClick = { selectedFilter = MatchStatus.UPCOMING },
                label = { Text("Upcoming") },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = ElectricCyan,
                    selectedLabelColor = Color.Black,
                    containerColor = StadiumSurface,
                    labelColor = TextSecondary
                )
            )
            FilterChip(
                selected = selectedFilter == MatchStatus.COMPLETED,
                onClick = { selectedFilter = MatchStatus.COMPLETED },
                label = { Text("Completed") },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = ElectricCyan,
                    selectedLabelColor = Color.Black,
                    containerColor = StadiumSurface,
                    labelColor = TextSecondary
                )
            )
        }

        if (filteredMatches.isEmpty()) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.SportsCricket,
                        contentDescription = null,
                        tint = TextSecondary,
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "No matches found in this filter",
                        style = MaterialTheme.typography.titleMedium,
                        color = TextPrimary
                    )
                    Text(
                        text = "Join Mega Contests for upcoming fixtures and watch the leaderboard climb!",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary,
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 6.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = onExploreMatches,
                        colors = ButtonDefaults.buttonColors(containerColor = PitchGreen)
                    ) {
                        Text("Explore Matches", color = Color.Black, fontWeight = FontWeight.Bold)
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(filteredMatches, key = { it.id }) { match ->
                    val matchContests = joinedContests.filter { it.matchId == match.id }
                    val matchTeams = userTeams.filter { it.matchId == match.id }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onMatchClick(match) }
                            .testTag("my_match_card_${match.id}"),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = StadiumSurface),
                        border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(StadiumBorder, StadiumBorder)))
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            // Tournament & Status
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
                                MatchStatusBadge(status = match.status, countdown = match.countdownFormatted)
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            // Teams
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    TeamLogoPill(shortName = match.team1Short, color = Color(match.team1Color))
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(match.team1Short, fontWeight = FontWeight.Bold, color = TextPrimary)
                                }
                                Text("VS", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = TextSecondary)
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(match.team2Short, fontWeight = FontWeight.Bold, color = TextPrimary)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    TeamLogoPill(shortName = match.team2Short, color = Color(match.team2Color))
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            // User stats summary for this match
                            Surface(
                                color = StadiumSurfaceVariant,
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 12.dp, vertical = 8.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "${matchTeams.size} Team${if (matchTeams.size > 1) "s" else ""} • ${matchContests.size} Contest${if (matchContests.size > 1) "s" else ""}",
                                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                        color = ElectricCyan
                                    )

                                    if (match.status == MatchStatus.COMPLETED) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(Icons.Default.EmojiEvents, contentDescription = null, tint = TrophyGold, modifier = Modifier.size(16.dp))
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text(
                                                text = "Won ₹1,250",
                                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.ExtraBold),
                                                color = PitchGreen
                                            )
                                        }
                                    } else if (match.status == MatchStatus.LIVE && matchContests.isNotEmpty()) {
                                        Text(
                                            text = "Rank #${matchContests.first().currentRank} (${matchContests.first().points} pts)",
                                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                            color = TrophyGold
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

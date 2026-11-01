package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CricketMatch
import com.example.data.model.Player
import com.example.data.model.PlayerRole
import com.example.ui.components.PlayerSelectionRow
import com.example.ui.theme.ElectricCyan
import com.example.ui.theme.LiveRed
import com.example.ui.theme.PitchGreen
import com.example.ui.theme.StadiumDarkNavy
import com.example.ui.theme.StadiumSurface
import com.example.ui.theme.StadiumSurfaceVariant
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TrophyGold
import com.example.ui.viewmodel.TeamValidationResult

@Composable
fun CreateTeamScreen(
    match: CricketMatch,
    players: List<Player>,
    selectedPlayers: Set<Player>,
    activeRole: PlayerRole,
    validation: TeamValidationResult,
    onRoleSelect: (PlayerRole) -> Unit,
    onTogglePlayer: (Player) -> Unit,
    onPreviewPitch: () -> Unit,
    onNext: () -> Unit,
    onBack: () -> Unit
) {
    val rolePlayers = players.filter { it.role == activeRole }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(StadiumDarkNavy)
            .statusBarsPadding()
            .testTag("create_team_screen")
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
                IconButton(onClick = onBack, modifier = Modifier.testTag("btn_close_team_create")) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = TextPrimary
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Create Fan11 Team",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = TextPrimary
                    )
                    Text(
                        text = "${match.team1Short} vs ${match.team2Short} • Max 7 from one team",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextSecondary
                    )
                }

                OutlinedButton(
                    onClick = onPreviewPitch,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.testTag("btn_preview_pitch_top")
                ) {
                    Icon(Icons.Default.Visibility, contentDescription = null, tint = ElectricCyan, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Pitch", color = ElectricCyan, style = MaterialTheme.typography.labelSmall)
                }
            }
        }

        // Summary Scoreboard Tracker
        Surface(
            color = StadiumSurfaceVariant,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Players selected
                    Column {
                        Text(
                            text = "Players",
                            style = MaterialTheme.typography.labelSmall,
                            color = TextSecondary
                        )
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(
                                text = "${selectedPlayers.size}",
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold),
                                color = if (selectedPlayers.size == 11) PitchGreen else TextPrimary
                            )
                            Text(
                                text = "/11",
                                style = MaterialTheme.typography.titleSmall,
                                color = TextSecondary,
                                modifier = Modifier.padding(bottom = 2.dp)
                            )
                        }
                    }

                    // Team Breakdown
                    Surface(
                        color = StadiumDarkNavy,
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${match.team1Short}: ${validation.team1Count}",
                                fontWeight = FontWeight.Bold,
                                color = ElectricCyan,
                                fontSize = 12.sp
                            )
                            Text(
                                text = "  |  ",
                                color = TextSecondary,
                                fontSize = 12.sp
                            )
                            Text(
                                text = "${match.team2Short}: ${validation.team2Count}",
                                fontWeight = FontWeight.Bold,
                                color = TrophyGold,
                                fontSize = 12.sp
                            )
                        }
                    }

                    // Credits Left
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "Credits Left",
                            style = MaterialTheme.typography.labelSmall,
                            color = TextSecondary
                        )
                        Text(
                            text = String.format("%.1f", validation.creditsRemaining),
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold),
                            color = if (validation.totalCreditsUsed > 100.0) LiveRed else TrophyGold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // 11 Player Slot Dots
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    for (i in 0 until 11) {
                        val isFilled = i < selectedPlayers.size
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(6.dp)
                                .padding(horizontal = 2.dp)
                                .clip(RoundedCornerShape(3.dp))
                                .background(if (isFilled) PitchGreen else StadiumDarkNavy)
                        )
                    }
                }
            }
        }

        // Role Tabs (WK, BAT, AR, BOWL)
        TabRow(
            selectedTabIndex = activeRole.ordinal,
            containerColor = StadiumSurface,
            contentColor = ElectricCyan,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[activeRole.ordinal]),
                    color = ElectricCyan
                )
            }
        ) {
            PlayerRole.values().forEach { role ->
                val currentCount = when (role) {
                    PlayerRole.WK -> validation.wkCount
                    PlayerRole.BAT -> validation.batCount
                    PlayerRole.AR -> validation.arCount
                    PlayerRole.BOWL -> validation.bowlCount
                }
                Tab(
                    selected = activeRole == role,
                    onClick = { onRoleSelect(role) },
                    text = {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = role.shortLabel,
                                fontWeight = if (activeRole == role) FontWeight.Bold else FontWeight.Normal,
                                color = if (activeRole == role) ElectricCyan else TextSecondary
                            )
                            Text(
                                text = "($currentCount/${role.maxCount})",
                                fontSize = 10.sp,
                                color = if (currentCount in role.minCount..role.maxCount) PitchGreen else TextSecondary
                            )
                        }
                    }
                )
            }
        }

        // Guidance banner for selected role
        Surface(
            color = StadiumSurfaceVariant.copy(alpha = 0.5f),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Pick ${activeRole.minCount} - ${activeRole.maxCount} ${activeRole.label}s",
                style = MaterialTheme.typography.labelSmall,
                color = TextSecondary,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
            )
        }

        // Player List for active role
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(rolePlayers, key = { it.id }) { player ->
                val isSelected = selectedPlayers.any { it.id == player.id }
                PlayerSelectionRow(
                    player = player,
                    isSelected = isSelected,
                    onToggle = { onTogglePlayer(player) }
                )
            }
        }

        // Bottom CTA Bar
        Surface(
            color = StadiumSurface,
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding(),
            tonalElevation = 8.dp
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                if (validation.errorReason != null && selectedPlayers.isNotEmpty()) {
                    Text(
                        text = validation.errorReason,
                        style = MaterialTheme.typography.labelSmall,
                        color = if (validation.isValid) PitchGreen else LiveRed,
                        modifier = Modifier.padding(bottom = 6.dp)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = onPreviewPitch,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .testTag("btn_preview_pitch_bottom")
                    ) {
                        Icon(Icons.Default.Visibility, contentDescription = null, tint = ElectricCyan)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Team Preview", color = ElectricCyan, fontWeight = FontWeight.Bold)
                    }

                    Button(
                        onClick = onNext,
                        enabled = validation.isValid,
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PitchGreen,
                            disabledContainerColor = StadiumSurfaceVariant
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .testTag("btn_continue_c_vc")
                    ) {
                        Text(
                            text = "Next: Pick C & VC",
                            fontWeight = FontWeight.Bold,
                            color = if (validation.isValid) Color.Black else TextSecondary
                        )
                    }
                }
            }
        }
    }
}

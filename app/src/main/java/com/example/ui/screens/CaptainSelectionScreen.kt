package com.example.ui.screens

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
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Player
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
fun CaptainSelectionScreen(
    players: List<Player>,
    captain: Player?,
    viceCaptain: Player?,
    onSelectCaptain: (Player) -> Unit,
    onSelectViceCaptain: (Player) -> Unit,
    onPreviewPitch: () -> Unit,
    onSaveTeam: () -> Unit,
    onBack: () -> Unit
) {
    val isReadyToSave = captain != null && viceCaptain != null && captain.id != viceCaptain.id

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(StadiumDarkNavy)
            .statusBarsPadding()
            .testTag("captain_selection_screen")
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
                IconButton(onClick = onBack, modifier = Modifier.testTag("btn_back_captain")) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = TextPrimary
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Choose Captain & Vice-Captain",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = TextPrimary
                    )
                    Text(
                        text = "Captain 2x • Vice-Captain 1.5x Points",
                        style = MaterialTheme.typography.labelSmall,
                        color = ElectricCyan
                    )
                }

                OutlinedButton(
                    onClick = onPreviewPitch,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(Icons.Default.Visibility, contentDescription = null, tint = ElectricCyan, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Pitch", color = ElectricCyan, style = MaterialTheme.typography.labelSmall)
                }
            }
        }

        // Info Banner
        Surface(
            color = StadiumSurfaceVariant,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(TrophyGold),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("C", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color.Black)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text("Captain (C)", fontWeight = FontWeight.Bold, color = TextPrimary, fontSize = 12.sp)
                        Text("Gets 2X Points", color = TrophyGold, fontSize = 11.sp)
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(ElectricCyan),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("VC", fontWeight = FontWeight.Bold, fontSize = 10.sp, color = Color.Black)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text("Vice-Captain (VC)", fontWeight = FontWeight.Bold, color = TextPrimary, fontSize = 12.sp)
                        Text("Gets 1.5X Points", color = ElectricCyan, fontSize = 11.sp)
                    }
                }
            }
        }

        // List of 11 Players
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(players, key = { it.id }) { player ->
                val isC = captain?.id == player.id
                val isVC = viceCaptain?.id == player.id

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isC || isVC) StadiumSurfaceVariant else StadiumSurface
                    ),
                    border = if (isC) {
                        CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(TrophyGold, TrophyGold)))
                    } else if (isVC) {
                        CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(ElectricCyan, ElectricCyan)))
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
                        // Player details
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(CircleShape)
                                    .background(
                                        when (player.team) {
                                            "IND" -> Color(0xFF1E40AF)
                                            "AUS" -> Color(0xFFCA8A04)
                                            "CSK" -> Color(0xFFD97706)
                                            "MI" -> Color(0xFF0284C7)
                                            else -> Color(0xFF475569)
                                        }
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = player.shortName.take(2).uppercase(),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    color = Color.White
                                )
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column {
                                Text(
                                    text = player.name,
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                    color = TextPrimary
                                )
                                Text(
                                    text = "${player.team} • ${player.role.shortLabel} • ${player.points} pts",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = TextSecondary
                                )
                            }
                        }

                        // Captain & Vice Captain Buttons
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // C Button
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Box(
                                    modifier = Modifier
                                        .size(38.dp)
                                        .clip(CircleShape)
                                        .background(if (isC) TrophyGold else StadiumDarkNavy)
                                        .border(
                                            width = 1.5.dp,
                                            color = if (isC) TrophyGold else StadiumBorder,
                                            shape = CircleShape
                                        )
                                        .clickable { onSelectCaptain(player) }
                                        .testTag("btn_select_c_${player.id}"),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "C",
                                        fontWeight = FontWeight.ExtraBold,
                                        fontSize = 14.sp,
                                        color = if (isC) Color.Black else TextSecondary
                                    )
                                }
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "${(player.selectionRate * 0.4).toInt()}%",
                                    fontSize = 10.sp,
                                    color = TextTertiary(isC)
                                )
                            }

                            // VC Button
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Box(
                                    modifier = Modifier
                                        .size(38.dp)
                                        .clip(CircleShape)
                                        .background(if (isVC) ElectricCyan else StadiumDarkNavy)
                                        .border(
                                            width = 1.5.dp,
                                            color = if (isVC) ElectricCyan else StadiumBorder,
                                            shape = CircleShape
                                        )
                                        .clickable { onSelectViceCaptain(player) }
                                        .testTag("btn_select_vc_${player.id}"),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "VC",
                                        fontWeight = FontWeight.ExtraBold,
                                        fontSize = 12.sp,
                                        color = if (isVC) Color.Black else TextSecondary
                                    )
                                }
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "${(player.selectionRate * 0.3).toInt()}%",
                                    fontSize = 10.sp,
                                    color = TextTertiary(isVC)
                                )
                            }
                        }
                    }
                }
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
                if (!isReadyToSave) {
                    Text(
                        text = when {
                            captain == null -> "Please select a Captain (2x points)"
                            viceCaptain == null -> "Please select a Vice-Captain (1.5x points)"
                            captain.id == viceCaptain.id -> "Captain and Vice-Captain must be different players"
                            else -> ""
                        },
                        style = MaterialTheme.typography.labelSmall,
                        color = TrophyGold,
                        modifier = Modifier.padding(bottom = 6.dp)
                    )
                }

                Button(
                    onClick = onSaveTeam,
                    enabled = isReadyToSave,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PitchGreen,
                        disabledContainerColor = StadiumSurfaceVariant
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("btn_save_team")
                ) {
                    Text(
                        text = "Save & Create Team",
                        fontWeight = FontWeight.Bold,
                        color = if (isReadyToSave) Color.Black else TextSecondary
                    )
                }
            }
        }
    }
}

@Composable
private fun TextTertiary(isActive: Boolean): Color {
    return if (isActive) TrophyGold else TextSecondary
}

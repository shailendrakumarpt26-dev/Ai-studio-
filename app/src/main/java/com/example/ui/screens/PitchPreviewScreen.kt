package com.example.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Player
import com.example.data.model.PlayerRole
import com.example.ui.theme.ElectricCyan
import com.example.ui.theme.PitchGreenGrass
import com.example.ui.theme.PitchTurfLight
import com.example.ui.theme.StadiumDarkNavy
import com.example.ui.theme.StadiumSurface
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TrophyGold

@Composable
fun PitchPreviewScreen(
    players: List<Player>,
    captain: Player?,
    viceCaptain: Player?,
    teamName: String = "Fan11 Pitch Preview",
    onClose: () -> Unit
) {
    val wkList = players.filter { it.role == PlayerRole.WK }
    val batList = players.filter { it.role == PlayerRole.BAT }
    val arList = players.filter { it.role == PlayerRole.AR }
    val bowlList = players.filter { it.role == PlayerRole.BOWL }

    val totalCredits = players.sumOf { it.credits }
    val team1Count = players.count { it.team == "IND" || it.team == "CSK" || it.team == "RCB" }
    val team2Count = players.size - team1Count

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(StadiumDarkNavy)
            .statusBarsPadding()
            .navigationBarsPadding()
            .testTag("pitch_preview_screen")
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Top Header Bar
            Surface(
                color = StadiumSurface,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onClose,
                        modifier = Modifier.testTag("pitch_close_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = TextPrimary
                        )
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = teamName,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = TextPrimary
                        )
                        Text(
                            text = "${players.size}/11 Players • $totalCredits Credits Used",
                            style = MaterialTheme.typography.labelSmall,
                            color = TrophyGold
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(ElectricCyan.copy(alpha = 0.2f))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "T1: $team1Count | T2: $team2Count",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = ElectricCyan
                        )
                    }
                }
            }

            // Cricket Field Graphic with Tactical Formation
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clip(RoundedCornerShape(16.dp))
            ) {
                // Pitch Turf Canvas Background
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val w = size.width
                    val h = size.height

                    // Draw grass gradient
                    drawRect(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF166534),
                                Color(0xFF15803D),
                                Color(0xFF166534)
                            )
                        ),
                        size = size
                    )

                    // Outer Boundary Oval
                    drawOval(
                        color = Color.White.copy(alpha = 0.35f),
                        topLeft = Offset(w * 0.04f, h * 0.02f),
                        size = Size(w * 0.92f, h * 0.96f),
                        style = Stroke(width = 2.5f)
                    )

                    // 30-Yard Circle Inner Oval
                    drawOval(
                        color = Color.White.copy(alpha = 0.22f),
                        topLeft = Offset(w * 0.12f, h * 0.10f),
                        size = Size(w * 0.76f, h * 0.80f),
                        style = Stroke(
                            width = 2f,
                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(12f, 10f), 0f)
                        )
                    )

                    // Center Pitch Dirt Strip
                    val pitchWidth = w * 0.18f
                    val pitchHeight = h * 0.38f
                    val pitchLeft = (w - pitchWidth) / 2f
                    val pitchTop = (h - pitchHeight) / 2f

                    drawRoundRect(
                        color = Color(0xFFD97706).copy(alpha = 0.45f),
                        topLeft = Offset(pitchLeft, pitchTop),
                        size = Size(pitchWidth, pitchHeight),
                        cornerRadius = androidx.compose.ui.geometry.CornerRadius(6f, 6f)
                    )

                    // Bowling Creases
                    drawLine(
                        color = Color.White.copy(alpha = 0.5f),
                        start = Offset(pitchLeft - 8f, pitchTop + 14f),
                        end = Offset(pitchLeft + pitchWidth + 8f, pitchTop + 14f),
                        strokeWidth = 2f
                    )
                    drawLine(
                        color = Color.White.copy(alpha = 0.5f),
                        start = Offset(pitchLeft - 8f, pitchTop + pitchHeight - 14f),
                        end = Offset(pitchLeft + pitchWidth + 8f, pitchTop + pitchHeight - 14f),
                        strokeWidth = 2f
                    )
                }

                // Four Tactical Bands: Wicket-Keepers, Batters, All-Rounders, Bowlers
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 12.dp),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Row 1: WK
                    TacticalPlayerRow(
                        sectionTitle = "WICKET-KEEPERS",
                        players = wkList,
                        captainId = captain?.id,
                        viceCaptainId = viceCaptain?.id
                    )

                    // Row 2: BAT
                    TacticalPlayerRow(
                        sectionTitle = "BATTERS",
                        players = batList,
                        captainId = captain?.id,
                        viceCaptainId = viceCaptain?.id
                    )

                    // Row 3: AR
                    TacticalPlayerRow(
                        sectionTitle = "ALL-ROUNDERS",
                        players = arList,
                        captainId = captain?.id,
                        viceCaptainId = viceCaptain?.id
                    )

                    // Row 4: BOWL
                    TacticalPlayerRow(
                        sectionTitle = "BOWLERS",
                        players = bowlList,
                        captainId = captain?.id,
                        viceCaptainId = viceCaptain?.id
                    )
                }
            }

            // Legend / Caption bar
            Surface(
                color = StadiumSurface,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(18.dp)
                                .clip(CircleShape)
                                .background(TrophyGold),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("C", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Captain (2x Pts)", style = MaterialTheme.typography.labelSmall, color = TextSecondary)
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(18.dp)
                                .clip(CircleShape)
                                .background(ElectricCyan),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("VC", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Vice-Captain (1.5x Pts)", style = MaterialTheme.typography.labelSmall, color = TextSecondary)
                    }
                }
            }
        }
    }
}

@Composable
fun TacticalPlayerRow(
    sectionTitle: String,
    players: List<Player>,
    captainId: String?,
    viceCaptainId: String?
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = sectionTitle,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White.copy(alpha = 0.75f),
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(4.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            players.forEach { player ->
                PitchPlayerBadge(
                    player = player,
                    isCaptain = player.id == captainId,
                    isViceCaptain = player.id == viceCaptainId
                )
            }
        }
    }
}

@Composable
fun PitchPlayerBadge(
    player: Player,
    isCaptain: Boolean,
    isViceCaptain: Boolean
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 2.dp)
    ) {
        Box(contentAlignment = Alignment.TopEnd) {
            // Jersey / Avatar Circle
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(
                        when (player.team) {
                            "IND" -> Color(0xFF1E40AF)
                            "AUS" -> Color(0xFFCA8A04)
                            "CSK" -> Color(0xFFD97706)
                            "MI" -> Color(0xFF0284C7)
                            else -> Color(0xFF475569)
                        }
                    )
                    .border(
                        width = 1.5.dp,
                        color = when {
                            isCaptain -> TrophyGold
                            isViceCaptain -> ElectricCyan
                            else -> Color.White.copy(alpha = 0.5f)
                        },
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = player.shortName.take(2).uppercase(),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            // Captain or Vice Captain indicator
            if (isCaptain) {
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .clip(CircleShape)
                        .background(TrophyGold),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "C",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.Black
                    )
                }
            } else if (isViceCaptain) {
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .clip(CircleShape)
                        .background(ElectricCyan),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "VC",
                        fontSize = 8.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.Black
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(2.dp))

        // Player Name Pill
        Surface(
            color = Color.Black.copy(alpha = 0.75f),
            shape = RoundedCornerShape(4.dp)
        ) {
            Text(
                text = player.shortName,
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
            )
        }

        // Credits Pill
        Text(
            text = "${player.credits} Cr",
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            color = TrophyGold
        )
    }
}

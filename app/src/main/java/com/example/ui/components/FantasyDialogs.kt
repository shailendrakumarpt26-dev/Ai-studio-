package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.SportsCricket
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.UserTeamEntity
import com.example.data.model.Contest
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
fun AddCashDialog(
    onDismiss: () -> Unit,
    onAddCash: (Double) -> Unit
) {
    var amountText by remember { mutableStateOf("100") }
    val quickAmounts = listOf(50, 100, 200, 500)

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = StadiumSurface,
        title = {
            Text(
                text = "Add Cash to Wallet",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = TextPrimary
            )
        },
        text = {
            Column {
                Text(
                    text = "Deposit money instantly via UPI / NetBanking to enter real cash contests.",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )

                Spacer(modifier = Modifier.height(14.dp))

                OutlinedTextField(
                    value = amountText,
                    onValueChange = { amountText = it.filter { char -> char.isDigit() } },
                    label = { Text("Amount in ₹", color = TextSecondary) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary,
                        focusedBorderColor = ElectricCyan,
                        unfocusedBorderColor = StadiumBorder
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_add_cash_amount")
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Quick chips
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    quickAmounts.forEach { preset ->
                        Surface(
                            color = if (amountText == "$preset") ElectricCyan else StadiumSurfaceVariant,
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .weight(1f)
                                .clickable { amountText = "$preset" }
                        ) {
                            Text(
                                text = "+₹$preset",
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                color = if (amountText == "$preset") Color.Black else TextPrimary,
                                modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp),
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val amount = amountText.toDoubleOrNull() ?: 100.0
                    onAddCash(amount)
                    onDismiss()
                },
                colors = ButtonDefaults.buttonColors(containerColor = PitchGreen),
                modifier = Modifier.testTag("btn_confirm_add_cash")
            ) {
                Text("Add ₹${amountText.ifEmpty { "0" }}", color = Color.Black, fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = TextSecondary)
            }
        }
    )
}

@Composable
fun WithdrawDialog(
    maxWithdrawable: Double,
    onDismiss: () -> Unit,
    onWithdraw: (Double) -> Unit
) {
    var amountText by remember { mutableStateOf(maxWithdrawable.toInt().coerceAtMost(500).toString()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = StadiumSurface,
        title = {
            Text(
                text = "Instant Bank Withdrawal",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = TextPrimary
            )
        },
        text = {
            Column {
                Text(
                    text = "Transfer your tournament winnings directly to your bank account or UPI.",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Max Withdrawable: ₹${maxWithdrawable.toInt()}",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    color = TrophyGold
                )

                Spacer(modifier = Modifier.height(14.dp))

                OutlinedTextField(
                    value = amountText,
                    onValueChange = { amountText = it.filter { char -> char.isDigit() } },
                    label = { Text("Withdraw Amount (₹)", color = TextSecondary) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary,
                        focusedBorderColor = TrophyGold,
                        unfocusedBorderColor = StadiumBorder
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_withdraw_amount")
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val amount = amountText.toDoubleOrNull() ?: 0.0
                    onWithdraw(amount)
                    onDismiss()
                },
                colors = ButtonDefaults.buttonColors(containerColor = TrophyGold),
                modifier = Modifier.testTag("btn_confirm_withdraw")
            ) {
                Text("Withdraw Now", color = Color.Black, fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = TextSecondary)
            }
        }
    )
}

@Composable
fun JoinContestDialog(
    contest: Contest,
    userTeams: List<UserTeamEntity>,
    onDismiss: () -> Unit,
    onCreateTeamFirst: () -> Unit,
    onConfirmJoin: (UserTeamEntity) -> Unit
) {
    var selectedTeam by remember { mutableStateOf(userTeams.firstOrNull()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = StadiumSurface,
        title = {
            Column {
                Text(
                    text = "Join Contest",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = TextPrimary
                )
                Text(
                    text = contest.title,
                    style = MaterialTheme.typography.labelSmall,
                    color = TrophyGold
                )
            }
        },
        text = {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Entry Fee:", color = TextSecondary, style = MaterialTheme.typography.bodySmall)
                    Text(if (contest.entryFee == 0) "Free" else "₹${contest.entryFee}", fontWeight = FontWeight.Bold, color = PitchGreen)
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Total Prize Pool:", color = TextSecondary, style = MaterialTheme.typography.bodySmall)
                    Text(contest.totalPrizePool, fontWeight = FontWeight.Bold, color = TrophyGold)
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "Select Team to Enter With:",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    color = TextPrimary
                )

                Spacer(modifier = Modifier.height(8.dp))

                if (userTeams.isEmpty()) {
                    Surface(
                        color = StadiumSurfaceVariant,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = "You haven't created a team for this match yet.",
                                style = MaterialTheme.typography.bodySmall,
                                color = TextSecondary
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(
                                onClick = {
                                    onDismiss()
                                    onCreateTeamFirst()
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = PitchGreen)
                            ) {
                                Text("Create Team 11 Now", color = Color.Black, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                } else {
                    userTeams.forEach { team ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { selectedTeam = team }
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = selectedTeam?.id == team.id,
                                onClick = { selectedTeam = team },
                                colors = RadioButtonDefaults.colors(selectedColor = ElectricCyan)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Column {
                                Text(team.teamName, fontWeight = FontWeight.Bold, color = TextPrimary)
                                Text("${team.totalCredits} Cr Used", fontSize = 11.sp, color = TextSecondary)
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            if (userTeams.isNotEmpty()) {
                Button(
                    onClick = {
                        selectedTeam?.let { onConfirmJoin(it) }
                        onDismiss()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = PitchGreen),
                    modifier = Modifier.testTag("btn_confirm_join_contest")
                ) {
                    Text("Join with ${selectedTeam?.teamName ?: "Team"}", color = Color.Black, fontWeight = FontWeight.Bold)
                }
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = TextSecondary)
            }
        }
    )
}

@Composable
fun FantasyRulesDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = StadiumSurface,
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Fan11 Point System (T20)",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = TextPrimary
                )
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.Close, contentDescription = "Close", tint = TextSecondary)
                }
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(vertical = 4.dp)
            ) {
                RuleSection(
                    title = "Batting Points",
                    rules = listOf(
                        "Run" to "+1 pt",
                        "Boundary Bonus (4s)" to "+1 pt",
                        "Six Bonus (6s)" to "+2 pts",
                        "30 Run Bonus" to "+4 pts",
                        "Half-century (50 runs)" to "+8 pts",
                        "Century (100 runs)" to "+16 pts",
                        "Duck (out on 0 - top 7)" to "-2 pts"
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                RuleSection(
                    title = "Bowling Points",
                    rules = listOf(
                        "Wicket (excl. run out)" to "+25 pts",
                        "LBW / Bowled Bonus" to "+8 pts",
                        "3 Wicket Haul Bonus" to "+4 pts",
                        "4 Wicket Haul Bonus" to "+8 pts",
                        "5 Wicket Haul Bonus" to "+16 pts",
                        "Maiden Over" to "+12 pts"
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                RuleSection(
                    title = "Fielding Points",
                    rules = listOf(
                        "Catch" to "+8 pts",
                        "3 Catch Bonus" to "+4 pts",
                        "Stumping" to "+12 pts",
                        "Run Out (Direct Hit)" to "+12 pts",
                        "Run Out (Catcher/Thrower)" to "+6 pts each"
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                RuleSection(
                    title = "Multiplier Roles",
                    rules = listOf(
                        "Captain (C)" to "2X Points (Doubled)",
                        "Vice-Captain (VC)" to "1.5X Points (150%)",
                        "Starting 11 Announcement" to "+4 pts to each player"
                    )
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = ElectricCyan)
            ) {
                Text("Got It", color = Color.Black, fontWeight = FontWeight.Bold)
            }
        }
    )
}

@Composable
fun RuleSection(title: String, rules: List<Pair<String, String>>) {
    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = StadiumSurfaceVariant),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                color = TrophyGold
            )
            Spacer(modifier = Modifier.height(6.dp))
            rules.forEach { (action, pts) ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(action, fontSize = 12.sp, color = TextPrimary)
                    Text(pts, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = ElectricCyan)
                }
            }
        }
    }
}

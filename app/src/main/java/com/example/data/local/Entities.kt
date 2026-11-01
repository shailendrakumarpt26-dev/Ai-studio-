package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_teams")
data class UserTeamEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val matchId: String,
    val teamName: String,
    val captainId: String,
    val viceCaptainId: String,
    val playerIds: String, // Comma separated player IDs
    val totalCredits: Double,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "joined_contests")
data class JoinedContestEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val contestId: String,
    val matchId: String,
    val userTeamId: Long,
    val teamName: String,
    val entryFeePaid: Int,
    val points: Double,
    val currentRank: Int,
    val potentialWinnings: String,
    val joinedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "user_wallet")
data class WalletEntity(
    @PrimaryKey val id: Int = 1,
    val depositBalance: Double,
    val winningBalance: Double,
    val bonusBalance: Double
) {
    val totalBalance: Double get() = depositBalance + winningBalance + bonusBalance
}

@Entity(tableName = "wallet_transactions")
data class WalletTransactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val description: String,
    val amount: Double,
    val isCredit: Boolean,
    val timestamp: Long = System.currentTimeMillis(),
    val tag: String
)

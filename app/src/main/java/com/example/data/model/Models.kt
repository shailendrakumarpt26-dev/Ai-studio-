package com.example.data.model

enum class PlayerRole(
    val label: String,
    val shortLabel: String,
    val minCount: Int,
    val maxCount: Int
) {
    WK("Wicket-Keeper", "WK", 1, 4),
    BAT("Batter", "BAT", 3, 6),
    AR("All-Rounder", "AR", 1, 4),
    BOWL("Bowler", "BOWL", 3, 6)
}

data class Player(
    val id: String,
    val name: String,
    val shortName: String,
    val team: String,
    val role: PlayerRole,
    val credits: Double,
    val points: Int,
    val selectionRate: Double,
    val recentPoints: List<Int>,
    val isPlaying: Boolean = true,
    val style: String = ""
)

enum class MatchStatus(val label: String) {
    UPCOMING("Upcoming"),
    LIVE("Live"),
    COMPLETED("Completed")
}

data class LiveBallEvent(
    val ball: String,
    val runs: String,
    val description: String,
    val fantasyImpact: String,
    val isBoundary: Boolean = false,
    val isWicket: Boolean = false
)

data class PitchReport(
    val pitchType: String,
    val avgScore: Int,
    val paceRatio: Int,
    val spinRatio: Int,
    val weather: String,
    val tossAdvice: String
)

data class CricketMatch(
    val id: String,
    val tournament: String,
    val matchType: String,
    val team1: String,
    val team1Short: String,
    val team1Color: Long,
    val team2: String,
    val team2Short: String,
    val team2Color: Long,
    val venue: String,
    val status: MatchStatus,
    val startTimeFormatted: String,
    val countdownFormatted: String,
    val megaPrizePool: String,
    val team1Score: String? = null,
    val team2Score: String? = null,
    val statusSummary: String,
    val isLineupAnnounced: Boolean = true,
    val pitchReport: PitchReport? = null,
    val liveEvents: List<LiveBallEvent> = emptyList()
)

enum class ContestCategory(val label: String) {
    ALL("All Contests"),
    MEGA("Mega Contests"),
    HEAD_TO_HEAD("Head to Head"),
    WINNER_TAKES_ALL("Winner Takes All"),
    PRACTICE("Practice")
}

data class ContestPrizeBreakup(
    val rankRange: String,
    val prizeAmount: String
)

data class Contest(
    val id: String,
    val matchId: String,
    val title: String,
    val category: ContestCategory,
    val totalPrizePool: String,
    val entryFee: Int,
    val totalSpots: Int,
    val spotsFilled: Int,
    val firstPrize: String,
    val winnerPercentage: Int,
    val maxTeamsPerUser: Int = 11,
    val isGuaranteed: Boolean = true,
    val prizeBreakup: List<ContestPrizeBreakup> = emptyList()
)

data class LeaderboardEntry(
    val rank: Int,
    val userTeamName: String,
    val userName: String,
    val points: Double,
    val rankChange: Int = 0, // >0 moved up, <0 moved down
    val isCurrentUser: Boolean = false,
    val winningAmount: String? = null
)

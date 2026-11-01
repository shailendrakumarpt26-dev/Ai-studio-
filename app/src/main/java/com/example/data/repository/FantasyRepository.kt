package com.example.data.repository

import com.example.data.local.Fan11Dao
import com.example.data.local.JoinedContestEntity
import com.example.data.local.UserTeamEntity
import com.example.data.local.WalletEntity
import com.example.data.local.WalletTransactionEntity
import com.example.data.model.Contest
import com.example.data.model.CricketMatch
import com.example.data.model.Player
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class FantasyRepository(private val dao: Fan11Dao) {

    suspend fun initializeDefaultsIfNeeded() {
        // Init wallet if null
        val currentWallet = dao.getWallet().firstOrNull()
        if (currentWallet == null) {
            val initialWallet = WalletEntity(
                id = 1,
                depositBalance = 250.0,
                winningBalance = 500.0,
                bonusBalance = 150.0
            )
            dao.setWallet(initialWallet)

            dao.insertTransaction(
                WalletTransactionEntity(
                    title = "Welcome Bonus Added",
                    description = "Special welcome bonus for signing up on Fan11",
                    amount = 150.0,
                    isCredit = true,
                    tag = "BONUS"
                )
            )
            dao.insertTransaction(
                WalletTransactionEntity(
                    title = "Deposit Added",
                    description = "Instant UPI Deposit via Google Pay",
                    amount = 250.0,
                    isCredit = true,
                    tag = "DEPOSIT"
                )
            )
            dao.insertTransaction(
                WalletTransactionEntity(
                    title = "Winnings Credited",
                    description = "IND vs ENG Semi-Final Mega League Rank #84",
                    amount = 500.0,
                    isCredit = true,
                    tag = "WINNINGS"
                )
            )

            // Seed an initial team for IND vs AUS live match
            val indAusPlayers = FantasyData.indVsAusPlayers
            val selected11 = listOf(
                "p_kl_rahul", "p_virat_kohli", "p_rohit_sharma", "p_travis_head",
                "p_suryakumar", "p_hardik_pandya", "p_glenn_maxwell", "p_marcus_stoinis",
                "p_jasprit_bumrah", "p_pat_cummins", "p_mitchell_starc"
            )
            val teamId = dao.insertTeam(
                UserTeamEntity(
                    matchId = "match_ind_aus",
                    teamName = "My Dream 11 (T1)",
                    captainId = "p_virat_kohli",
                    viceCaptainId = "p_jasprit_bumrah",
                    playerIds = selected11.joinToString(","),
                    totalCredits = 99.0
                )
            )

            // Seed a joined contest for the live match
            dao.insertJoinedContest(
                JoinedContestEntity(
                    contestId = "match_ind_aus_mega",
                    matchId = "match_ind_aus",
                    userTeamId = teamId,
                    teamName = "My Dream 11 (T1)",
                    entryFeePaid = 49,
                    points = 398.0,
                    currentRank = 4,
                    potentialWinnings = "₹50,000"
                )
            )

            // Seed a completed match contest
            val completedTeamId = dao.insertTeam(
                UserTeamEntity(
                    matchId = "match_ind_eng_completed",
                    teamName = "Champions XI",
                    captainId = "p_virat_kohli",
                    viceCaptainId = "p_jasprit_bumrah",
                    playerIds = selected11.joinToString(","),
                    totalCredits = 98.5
                )
            )
            dao.insertJoinedContest(
                JoinedContestEntity(
                    contestId = "match_ind_eng_completed_mega",
                    matchId = "match_ind_eng_completed",
                    userTeamId = completedTeamId,
                    teamName = "Champions XI",
                    entryFeePaid = 49,
                    points = 482.0,
                    currentRank = 84,
                    potentialWinnings = "₹1,250"
                )
            )
        }
    }

    fun getMatches(): List<CricketMatch> = FantasyData.matches

    fun getMatchById(matchId: String): CricketMatch? = FantasyData.matches.find { it.id == matchId }

    fun getPlayersForMatch(matchId: String): List<Player> = FantasyData.getPlayersForMatch(matchId)

    fun getContestsForMatch(matchId: String): List<Contest> = FantasyData.getContestsForMatch(matchId)

    fun getUserTeams(matchId: String): Flow<List<UserTeamEntity>> = dao.getTeamsForMatch(matchId)

    fun getAllUserTeams(): Flow<List<UserTeamEntity>> = dao.getAllTeams()

    suspend fun getTeamById(teamId: Long): UserTeamEntity? = dao.getTeamById(teamId)

    suspend fun saveTeam(
        matchId: String,
        teamName: String,
        captainId: String,
        viceCaptainId: String,
        playerIds: List<String>,
        totalCredits: Double
    ): Long {
        return dao.insertTeam(
            UserTeamEntity(
                matchId = matchId,
                teamName = teamName,
                captainId = captainId,
                viceCaptainId = viceCaptainId,
                playerIds = playerIds.joinToString(","),
                totalCredits = totalCredits
            )
        )
    }

    fun getJoinedContests(matchId: String): Flow<List<JoinedContestEntity>> =
        dao.getJoinedContestsForMatch(matchId)

    fun getAllJoinedContests(): Flow<List<JoinedContestEntity>> = dao.getAllJoinedContests()

    suspend fun joinContest(
        contest: Contest,
        team: UserTeamEntity
    ): Result<String> {
        val currentWallet = dao.getWallet().firstOrNull() ?: WalletEntity(1, 0.0, 0.0, 0.0)
        val entry = contest.entryFee.toDouble()

        if (entry > currentWallet.totalBalance && entry > 0) {
            return Result.failure(Exception("Insufficient wallet balance. Please add cash."))
        }

        // Deduct balance (prefer bonus up to 10%, then deposit, then winning)
        var remainingToDeduct = entry
        var newBonus = currentWallet.bonusBalance
        var newDeposit = currentWallet.depositBalance
        var newWinning = currentWallet.winningBalance

        if (remainingToDeduct > 0 && newBonus > 0) {
            val bonusDeduct = (entry * 0.1).coerceAtMost(newBonus).coerceAtMost(remainingToDeduct)
            newBonus -= bonusDeduct
            remainingToDeduct -= bonusDeduct
        }
        if (remainingToDeduct > 0 && newDeposit > 0) {
            val depositDeduct = newDeposit.coerceAtMost(remainingToDeduct)
            newDeposit -= depositDeduct
            remainingToDeduct -= depositDeduct
        }
        if (remainingToDeduct > 0 && newWinning > 0) {
            val winDeduct = newWinning.coerceAtMost(remainingToDeduct)
            newWinning -= winDeduct
            remainingToDeduct -= winDeduct
        }

        dao.updateWallet(
            currentWallet.copy(
                depositBalance = newDeposit,
                winningBalance = newWinning,
                bonusBalance = newBonus
            )
        )

        dao.insertTransaction(
            WalletTransactionEntity(
                title = "Contest Joined: ${contest.title}",
                description = "Entry fee for ${contest.category.label}",
                amount = entry,
                isCredit = false,
                tag = "CONTEST_ENTRY"
            )
        )

        dao.insertJoinedContest(
            JoinedContestEntity(
                contestId = contest.id,
                matchId = contest.matchId,
                userTeamId = team.id,
                teamName = team.teamName,
                entryFeePaid = contest.entryFee,
                points = 0.0,
                currentRank = contest.spotsFilled + 1,
                potentialWinnings = contest.firstPrize
            )
        )

        return Result.success("Successfully joined ${contest.title}!")
    }

    fun getWallet(): Flow<WalletEntity?> = dao.getWallet()

    suspend fun addCash(amount: Double): Boolean {
        val current = dao.getWallet().firstOrNull() ?: WalletEntity(1, 0.0, 0.0, 0.0)
        dao.updateWallet(
            current.copy(depositBalance = current.depositBalance + amount)
        )
        dao.insertTransaction(
            WalletTransactionEntity(
                title = "Added Cash to Wallet",
                description = "Instant UPI transfer successful",
                amount = amount,
                isCredit = true,
                tag = "DEPOSIT"
            )
        )
        return true
    }

    suspend fun withdraw(amount: Double): Result<Boolean> {
        val current = dao.getWallet().firstOrNull() ?: WalletEntity(1, 0.0, 0.0, 0.0)
        if (amount > current.winningBalance) {
            return Result.failure(Exception("Only winning balance can be withdrawn. Max withdrawable: ₹${current.winningBalance.toInt()}"))
        }
        dao.updateWallet(
            current.copy(winningBalance = current.winningBalance - amount)
        )
        dao.insertTransaction(
            WalletTransactionEntity(
                title = "Withdrawal to Bank Account",
                description = "Transferred to verified Bank A/C ending in 4921",
                amount = amount,
                isCredit = false,
                tag = "WITHDRAWAL"
            )
        )
        return Result.success(true)
    }

    fun getAllTransactions(): Flow<List<WalletTransactionEntity>> = dao.getAllTransactions()
}

package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface Fan11Dao {
    // User Teams
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTeam(team: UserTeamEntity): Long

    @Query("SELECT * FROM user_teams WHERE matchId = :matchId ORDER BY id DESC")
    fun getTeamsForMatch(matchId: String): Flow<List<UserTeamEntity>>

    @Query("SELECT * FROM user_teams WHERE id = :teamId")
    suspend fun getTeamById(teamId: Long): UserTeamEntity?

    @Query("SELECT * FROM user_teams ORDER BY id DESC")
    fun getAllTeams(): Flow<List<UserTeamEntity>>

    @Query("DELETE FROM user_teams WHERE id = :teamId")
    suspend fun deleteTeam(teamId: Long)

    // Joined Contests
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJoinedContest(contest: JoinedContestEntity): Long

    @Query("SELECT * FROM joined_contests WHERE matchId = :matchId")
    fun getJoinedContestsForMatch(matchId: String): Flow<List<JoinedContestEntity>>

    @Query("SELECT * FROM joined_contests ORDER BY joinedAt DESC")
    fun getAllJoinedContests(): Flow<List<JoinedContestEntity>>

    @Query("SELECT COUNT(*) FROM joined_contests WHERE contestId = :contestId AND matchId = :matchId")
    suspend fun getJoinedCountForContest(contestId: String, matchId: String): Int

    // Wallet
    @Query("SELECT * FROM user_wallet WHERE id = 1")
    fun getWallet(): Flow<WalletEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setWallet(wallet: WalletEntity)

    @Update
    suspend fun updateWallet(wallet: WalletEntity)

    // Transactions
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: WalletTransactionEntity)

    @Query("SELECT * FROM wallet_transactions ORDER BY timestamp DESC")
    fun getAllTransactions(): Flow<List<WalletTransactionEntity>>
}

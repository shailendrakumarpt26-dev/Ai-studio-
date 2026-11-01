package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.Fan11Database
import com.example.data.local.JoinedContestEntity
import com.example.data.local.UserTeamEntity
import com.example.data.local.WalletEntity
import com.example.data.local.WalletTransactionEntity
import com.example.data.model.Contest
import com.example.data.model.ContestCategory
import com.example.data.model.CricketMatch
import com.example.data.model.MatchStatus
import com.example.data.model.Player
import com.example.data.model.PlayerRole
import com.example.data.repository.FantasyRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class TeamValidationResult(
    val isValid: Boolean,
    val wkCount: Int,
    val batCount: Int,
    val arCount: Int,
    val bowlCount: Int,
    val team1Count: Int,
    val team2Count: Int,
    val totalCreditsUsed: Double,
    val creditsRemaining: Double,
    val errorReason: String? = null
)

class FantasyViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: FantasyRepository
    init {
        val db = Fan11Database.getDatabase(application)
        repository = FantasyRepository(db.fan11Dao())
        viewModelScope.launch {
            repository.initializeDefaultsIfNeeded()
        }
    }

    // Matches & Selection
    private val _matches = MutableStateFlow<List<CricketMatch>>(emptyList())
    val matches: StateFlow<List<CricketMatch>> = _matches.asStateFlow()

    private val _selectedMatch = MutableStateFlow<CricketMatch?>(null)
    val selectedMatch: StateFlow<CricketMatch?> = _selectedMatch.asStateFlow()

    private val _matchStatusFilter = MutableStateFlow<MatchStatus?>(null)
    val matchStatusFilter: StateFlow<MatchStatus?> = _matchStatusFilter.asStateFlow()

    // Match Details & Contests
    private val _matchContests = MutableStateFlow<List<Contest>>(emptyList())
    val matchContests: StateFlow<List<Contest>> = _matchContests.asStateFlow()

    private val _contestCategoryFilter = MutableStateFlow(ContestCategory.ALL)
    val contestCategoryFilter: StateFlow<ContestCategory> = _contestCategoryFilter.asStateFlow()

    // Team Creation State
    private val _matchPlayers = MutableStateFlow<List<Player>>(emptyList())
    val matchPlayers: StateFlow<List<Player>> = _matchPlayers.asStateFlow()

    private val _selectedPlayers = MutableStateFlow<Set<Player>>(emptySet())
    val selectedPlayers: StateFlow<Set<Player>> = _selectedPlayers.asStateFlow()

    private val _captain = MutableStateFlow<Player?>(null)
    val captain: StateFlow<Player?> = _captain.asStateFlow()

    private val _viceCaptain = MutableStateFlow<Player?>(null)
    val viceCaptain: StateFlow<Player?> = _viceCaptain.asStateFlow()

    private val _activeRoleTab = MutableStateFlow(PlayerRole.WK)
    val activeRoleTab: StateFlow<PlayerRole> = _activeRoleTab.asStateFlow()

    // Previewing team on pitch
    private val _previewPlayers = MutableStateFlow<List<Player>>(emptyList())
    val previewPlayers: StateFlow<List<Player>> = _previewPlayers.asStateFlow()

    private val _previewCaptain = MutableStateFlow<Player?>(null)
    val previewCaptain: StateFlow<Player?> = _previewCaptain.asStateFlow()

    private val _previewViceCaptain = MutableStateFlow<Player?>(null)
    val previewViceCaptain: StateFlow<Player?> = _previewViceCaptain.asStateFlow()

    // User's Teams & Contests from Room
    val allUserTeams: StateFlow<List<UserTeamEntity>> = repository.getAllUserTeams()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allJoinedContests: StateFlow<List<JoinedContestEntity>> = repository.getAllJoinedContests()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Wallet
    val wallet: StateFlow<WalletEntity?> = repository.getWallet()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val transactions: StateFlow<List<WalletTransactionEntity>> = repository.getAllTransactions()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Event & Feedback
    private val _toastEvent = MutableSharedFlow<String>()
    val toastEvent: SharedFlow<String> = _toastEvent.asSharedFlow()

    init {
        loadMatches()
    }

    private fun loadMatches() {
        val list = repository.getMatches()
        _matches.value = list
        if (_selectedMatch.value == null && list.isNotEmpty()) {
            selectMatch(list.first())
        }
    }

    fun setMatchStatusFilter(status: MatchStatus?) {
        _matchStatusFilter.value = status
    }

    fun selectMatch(match: CricketMatch) {
        _selectedMatch.value = match
        _matchContests.value = repository.getContestsForMatch(match.id)
        _matchPlayers.value = repository.getPlayersForMatch(match.id)
        resetTeamCreation()
    }

    fun setContestCategoryFilter(category: ContestCategory) {
        _contestCategoryFilter.value = category
    }

    fun setRoleTab(role: PlayerRole) {
        _activeRoleTab.value = role
    }

    fun resetTeamCreation() {
        _selectedPlayers.value = emptySet()
        _captain.value = null
        _viceCaptain.value = null
        _activeRoleTab.value = PlayerRole.WK
    }

    fun togglePlayerSelection(player: Player) {
        val current = _selectedPlayers.value.toMutableSet()
        if (current.contains(player)) {
            current.remove(player)
            if (_captain.value?.id == player.id) _captain.value = null
            if (_viceCaptain.value?.id == player.id) _viceCaptain.value = null
            _selectedPlayers.value = current
        } else {
            if (current.size >= 11) {
                emitToast("You can only select 11 players for your team")
                return
            }
            val match = _selectedMatch.value ?: return
            val teamCount = current.count { it.team == player.team }
            if (teamCount >= 7) {
                emitToast("You can select max 7 players from ${player.team}")
                return
            }
            val creditsUsed = current.sumOf { it.credits } + player.credits
            if (creditsUsed > 100.0) {
                emitToast("Insufficient credits! Max budget is 100 credits.")
                return
            }
            current.add(player)
            _selectedPlayers.value = current
        }
    }

    fun setCaptain(player: Player) {
        if (_viceCaptain.value?.id == player.id) {
            _viceCaptain.value = null
        }
        _captain.value = player
    }

    fun setViceCaptain(player: Player) {
        if (_captain.value?.id == player.id) {
            _captain.value = null
        }
        _viceCaptain.value = player
    }

    fun getValidation(): TeamValidationResult {
        val current = _selectedPlayers.value
        val match = _selectedMatch.value
        val wkCount = current.count { it.role == PlayerRole.WK }
        val batCount = current.count { it.role == PlayerRole.BAT }
        val arCount = current.count { it.role == PlayerRole.AR }
        val bowlCount = current.count { it.role == PlayerRole.BOWL }
        val team1Count = if (match != null) current.count { it.team == match.team1Short } else 0
        val team2Count = if (match != null) current.count { it.team == match.team2Short } else 0
        val totalCreditsUsed = current.sumOf { it.credits }
        val creditsRemaining = (100.0 - totalCreditsUsed).coerceAtLeast(0.0)

        var error: String? = null
        val isValid = when {
            current.size != 11 -> {
                error = "Select exactly 11 players (${current.size}/11 selected)"
                false
            }
            totalCreditsUsed > 100.0 -> {
                error = "Credits exceeded budget of 100.0"
                false
            }
            wkCount < PlayerRole.WK.minCount -> {
                error = "Select at least ${PlayerRole.WK.minCount} Wicket-Keeper"
                false
            }
            batCount < PlayerRole.BAT.minCount -> {
                error = "Select at least ${PlayerRole.BAT.minCount} Batters"
                false
            }
            arCount < PlayerRole.AR.minCount -> {
                error = "Select at least ${PlayerRole.AR.minCount} All-Rounder"
                false
            }
            bowlCount < PlayerRole.BOWL.minCount -> {
                error = "Select at least ${PlayerRole.BOWL.minCount} Bowlers"
                false
            }
            team1Count > 7 || team2Count > 7 -> {
                error = "Max 7 players allowed from one team"
                false
            }
            else -> true
        }

        return TeamValidationResult(
            isValid = isValid,
            wkCount = wkCount,
            batCount = batCount,
            arCount = arCount,
            bowlCount = bowlCount,
            team1Count = team1Count,
            team2Count = team2Count,
            totalCreditsUsed = totalCreditsUsed,
            creditsRemaining = creditsRemaining,
            errorReason = error
        )
    }

    fun saveTeam(onSuccess: (Long) -> Unit) {
        val validation = getValidation()
        if (!validation.isValid) {
            emitToast(validation.errorReason ?: "Invalid team combination")
            return
        }
        val cap = _captain.value
        val vc = _viceCaptain.value
        if (cap == null || vc == null) {
            emitToast("Please choose Captain (2x) and Vice-Captain (1.5x)")
            return
        }
        val match = _selectedMatch.value ?: return

        viewModelScope.launch {
            val teamsCount = repository.getUserTeams(match.id).stateIn(viewModelScope).value.size
            val teamName = "Fan11 Team ${teamsCount + 1}"
            val newId = repository.saveTeam(
                matchId = match.id,
                teamName = teamName,
                captainId = cap.id,
                viceCaptainId = vc.id,
                playerIds = _selectedPlayers.value.map { it.id },
                totalCredits = validation.totalCreditsUsed
            )
            emitToast("Team '$teamName' created successfully!")
            resetTeamCreation()
            onSuccess(newId)
        }
    }

    fun previewCurrentSelection() {
        _previewPlayers.value = _selectedPlayers.value.toList()
        _previewCaptain.value = _captain.value
        _previewViceCaptain.value = _viceCaptain.value
    }

    fun previewSavedTeam(team: UserTeamEntity) {
        val playerMap = repository.getPlayersForMatch(team.matchId).associateBy { it.id }
        val ids = team.playerIds.split(",").map { it.trim() }
        _previewPlayers.value = ids.mapNotNull { playerMap[it] }
        _previewCaptain.value = playerMap[team.captainId]
        _previewViceCaptain.value = playerMap[team.viceCaptainId]
    }

    fun joinContest(contest: Contest, team: UserTeamEntity, onComplete: () -> Unit) {
        viewModelScope.launch {
            val result = repository.joinContest(contest, team)
            if (result.isSuccess) {
                emitToast("Contest Joined! Entry fee ₹${contest.entryFee} deducted.")
                onComplete()
            } else {
                emitToast(result.exceptionOrNull()?.message ?: "Failed to join contest")
            }
        }
    }

    fun addCash(amount: Double) {
        viewModelScope.launch {
            repository.addCash(amount)
            emitToast("₹${amount.toInt()} added to wallet successfully!")
        }
    }

    fun withdraw(amount: Double) {
        viewModelScope.launch {
            val res = repository.withdraw(amount)
            if (res.isSuccess) {
                emitToast("₹${amount.toInt()} transferred to bank account!")
            } else {
                emitToast(res.exceptionOrNull()?.message ?: "Withdrawal failed")
            }
        }
    }

    private fun emitToast(msg: String) {
        viewModelScope.launch {
            _toastEvent.emit(msg)
        }
    }
}

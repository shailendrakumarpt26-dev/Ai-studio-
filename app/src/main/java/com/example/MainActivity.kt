package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.local.UserTeamEntity
import com.example.data.model.Contest
import com.example.data.model.CricketMatch
import com.example.data.model.MatchStatus
import com.example.ui.components.AddCashDialog
import com.example.ui.components.FantasyRulesDialog
import com.example.ui.components.JoinContestDialog
import com.example.ui.components.WithdrawDialog
import com.example.ui.screens.CaptainSelectionScreen
import com.example.ui.screens.CreateTeamScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LiveMatchCenterScreen
import com.example.ui.screens.MatchDetailScreen
import com.example.ui.screens.MyMatchesScreen
import com.example.ui.screens.PitchPreviewScreen
import com.example.ui.screens.WalletScreen
import com.example.ui.theme.ElectricCyan
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.StadiumDarkNavy
import com.example.ui.theme.StadiumSurface
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.viewmodel.FantasyViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

enum class MainTab {
    HOME,
    MY_MATCHES,
    WALLET
}

enum class ActiveScreen {
    MAIN_TABS,
    MATCH_DETAIL,
    CREATE_TEAM,
    CAPTAIN_SELECTION,
    PITCH_PREVIEW,
    LIVE_CENTER
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Fan11App()
            }
        }
    }
}

@Composable
fun Fan11App(viewModel: FantasyViewModel = viewModel()) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    // Navigation & Screen States
    var currentTab by remember { mutableStateOf(MainTab.HOME) }
    var currentScreen by remember { mutableStateOf(ActiveScreen.MAIN_TABS) }
    var screenBeforePreview by remember { mutableStateOf(ActiveScreen.MAIN_TABS) }

    // Dialog States
    var showAddCashDialog by remember { mutableStateOf(false) }
    var showWithdrawDialog by remember { mutableStateOf(false) }
    var showRulesDialog by remember { mutableStateOf(false) }
    var contestToJoin by remember { mutableStateOf<Contest?>(null) }

    // Collect ViewModel States
    val matches by viewModel.matches.collectAsStateWithLifecycle()
    val selectedMatch by viewModel.selectedMatch.collectAsStateWithLifecycle()
    val matchStatusFilter by viewModel.matchStatusFilter.collectAsStateWithLifecycle()
    val matchContests by viewModel.matchContests.collectAsStateWithLifecycle()
    val contestCategoryFilter by viewModel.contestCategoryFilter.collectAsStateWithLifecycle()
    val matchPlayers by viewModel.matchPlayers.collectAsStateWithLifecycle()
    val selectedPlayers by viewModel.selectedPlayers.collectAsStateWithLifecycle()
    val activeRoleTab by viewModel.activeRoleTab.collectAsStateWithLifecycle()
    val captain by viewModel.captain.collectAsStateWithLifecycle()
    val viceCaptain by viewModel.viceCaptain.collectAsStateWithLifecycle()

    val previewPlayers by viewModel.previewPlayers.collectAsStateWithLifecycle()
    val previewCaptain by viewModel.previewCaptain.collectAsStateWithLifecycle()
    val previewViceCaptain by viewModel.previewViceCaptain.collectAsStateWithLifecycle()

    val allUserTeams by viewModel.allUserTeams.collectAsStateWithLifecycle()
    val allJoinedContests by viewModel.allJoinedContests.collectAsStateWithLifecycle()
    val wallet by viewModel.wallet.collectAsStateWithLifecycle()
    val transactions by viewModel.transactions.collectAsStateWithLifecycle()

    // Listen for Toast / Feedback events
    LaunchedEffect(Unit) {
        viewModel.toastEvent.collectLatest { msg ->
            snackbarHostState.showSnackbar(msg)
        }
    }

    // Handle system back navigation
    BackHandler(enabled = currentScreen != ActiveScreen.MAIN_TABS || currentTab != MainTab.HOME) {
        when (currentScreen) {
            ActiveScreen.PITCH_PREVIEW -> {
                currentScreen = screenBeforePreview
            }
            ActiveScreen.CAPTAIN_SELECTION -> {
                currentScreen = ActiveScreen.CREATE_TEAM
            }
            ActiveScreen.CREATE_TEAM -> {
                currentScreen = ActiveScreen.MATCH_DETAIL
            }
            ActiveScreen.LIVE_CENTER -> {
                currentScreen = ActiveScreen.MATCH_DETAIL
            }
            ActiveScreen.MATCH_DETAIL -> {
                currentScreen = ActiveScreen.MAIN_TABS
            }
            ActiveScreen.MAIN_TABS -> {
                if (currentTab != MainTab.HOME) {
                    currentTab = MainTab.HOME
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = StadiumDarkNavy,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            if (currentScreen == ActiveScreen.MAIN_TABS) {
                NavigationBar(
                    containerColor = StadiumSurface,
                    contentColor = ElectricCyan,
                    modifier = Modifier
                        .windowInsetsPadding(WindowInsets.navigationBars)
                        .testTag("bottom_nav_bar")
                ) {
                    NavigationBarItem(
                        selected = currentTab == MainTab.HOME,
                        onClick = { currentTab = MainTab.HOME },
                        icon = {
                            Icon(
                                imageVector = if (currentTab == MainTab.HOME) Icons.Filled.Home else Icons.Outlined.Home,
                                contentDescription = "Home"
                            )
                        },
                        label = { Text("Matches", fontWeight = FontWeight.SemiBold) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.Black,
                            selectedTextColor = ElectricCyan,
                            indicatorColor = ElectricCyan,
                            unselectedIconColor = TextSecondary,
                            unselectedTextColor = TextSecondary
                        ),
                        modifier = Modifier.testTag("nav_item_home")
                    )

                    NavigationBarItem(
                        selected = currentTab == MainTab.MY_MATCHES,
                        onClick = { currentTab = MainTab.MY_MATCHES },
                        icon = {
                            Icon(
                                imageVector = if (currentTab == MainTab.MY_MATCHES) Icons.Filled.EmojiEvents else Icons.Outlined.EmojiEvents,
                                contentDescription = "My Matches"
                            )
                        },
                        label = { Text("My Matches", fontWeight = FontWeight.SemiBold) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.Black,
                            selectedTextColor = ElectricCyan,
                            indicatorColor = ElectricCyan,
                            unselectedIconColor = TextSecondary,
                            unselectedTextColor = TextSecondary
                        ),
                        modifier = Modifier.testTag("nav_item_my_matches")
                    )

                    NavigationBarItem(
                        selected = currentTab == MainTab.WALLET,
                        onClick = { currentTab = MainTab.WALLET },
                        icon = {
                            Icon(
                                imageVector = if (currentTab == MainTab.WALLET) Icons.Filled.AccountBalanceWallet else Icons.Outlined.AccountBalanceWallet,
                                contentDescription = "Wallet"
                            )
                        },
                        label = { Text("Wallet", fontWeight = FontWeight.SemiBold) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.Black,
                            selectedTextColor = ElectricCyan,
                            indicatorColor = ElectricCyan,
                            unselectedIconColor = TextSecondary,
                            unselectedTextColor = TextSecondary
                        ),
                        modifier = Modifier.testTag("nav_item_wallet")
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentScreen) {
                ActiveScreen.MAIN_TABS -> {
                    when (currentTab) {
                        MainTab.HOME -> {
                            HomeScreen(
                                matches = matches,
                                wallet = wallet,
                                activeFilter = matchStatusFilter,
                                onFilterChange = { viewModel.setMatchStatusFilter(it) },
                                onMatchClick = { match ->
                                    viewModel.selectMatch(match)
                                    currentScreen = ActiveScreen.MATCH_DETAIL
                                },
                                onWalletClick = { currentTab = MainTab.WALLET },
                                onOpenRules = { showRulesDialog = true }
                            )
                        }
                        MainTab.MY_MATCHES -> {
                            MyMatchesScreen(
                                matches = matches,
                                joinedContests = allJoinedContests,
                                userTeams = allUserTeams,
                                onMatchClick = { match ->
                                    viewModel.selectMatch(match)
                                    if (match.status == MatchStatus.LIVE) {
                                        currentScreen = ActiveScreen.LIVE_CENTER
                                    } else {
                                        currentScreen = ActiveScreen.MATCH_DETAIL
                                    }
                                },
                                onExploreMatches = { currentTab = MainTab.HOME }
                            )
                        }
                        MainTab.WALLET -> {
                            WalletScreen(
                                wallet = wallet,
                                transactions = transactions,
                                onAddCashClick = { showAddCashDialog = true },
                                onWithdrawClick = { showWithdrawDialog = true },
                                onBack = { currentTab = MainTab.HOME }
                            )
                        }
                    }
                }

                ActiveScreen.MATCH_DETAIL -> {
                    selectedMatch?.let { match ->
                        val currentMatchTeams = allUserTeams.filter { it.matchId == match.id }
                        val currentMatchContests = allJoinedContests.filter { it.matchId == match.id }

                        MatchDetailScreen(
                            match = match,
                            contests = matchContests,
                            userTeams = currentMatchTeams,
                            joinedContests = currentMatchContests,
                            selectedCategory = contestCategoryFilter,
                            onCategoryChange = { viewModel.setContestCategoryFilter(it) },
                            onJoinContest = { contest ->
                                contestToJoin = contest
                            },
                            onCreateTeam = {
                                viewModel.resetTeamCreation()
                                currentScreen = ActiveScreen.CREATE_TEAM
                            },
                            onPreviewTeam = { team ->
                                viewModel.previewSavedTeam(team)
                                screenBeforePreview = ActiveScreen.MATCH_DETAIL
                                currentScreen = ActiveScreen.PITCH_PREVIEW
                            },
                            onOpenLiveCenter = {
                                currentScreen = ActiveScreen.LIVE_CENTER
                            },
                            onBack = { currentScreen = ActiveScreen.MAIN_TABS }
                        )
                    }
                }

                ActiveScreen.CREATE_TEAM -> {
                    selectedMatch?.let { match ->
                        CreateTeamScreen(
                            match = match,
                            players = matchPlayers,
                            selectedPlayers = selectedPlayers,
                            activeRole = activeRoleTab,
                            validation = viewModel.getValidation(),
                            onRoleSelect = { viewModel.setRoleTab(it) },
                            onTogglePlayer = { viewModel.togglePlayerSelection(it) },
                            onPreviewPitch = {
                                viewModel.previewCurrentSelection()
                                screenBeforePreview = ActiveScreen.CREATE_TEAM
                                currentScreen = ActiveScreen.PITCH_PREVIEW
                            },
                            onNext = {
                                currentScreen = ActiveScreen.CAPTAIN_SELECTION
                            },
                            onBack = {
                                currentScreen = ActiveScreen.MATCH_DETAIL
                            }
                        )
                    }
                }

                ActiveScreen.CAPTAIN_SELECTION -> {
                    CaptainSelectionScreen(
                        players = selectedPlayers.toList(),
                        captain = captain,
                        viceCaptain = viceCaptain,
                        onSelectCaptain = { viewModel.setCaptain(it) },
                        onSelectViceCaptain = { viewModel.setViceCaptain(it) },
                        onPreviewPitch = {
                            viewModel.previewCurrentSelection()
                            screenBeforePreview = ActiveScreen.CAPTAIN_SELECTION
                            currentScreen = ActiveScreen.PITCH_PREVIEW
                        },
                        onSaveTeam = {
                            viewModel.saveTeam { _ ->
                                currentScreen = ActiveScreen.MATCH_DETAIL
                            }
                        },
                        onBack = {
                            currentScreen = ActiveScreen.CREATE_TEAM
                        }
                    )
                }

                ActiveScreen.PITCH_PREVIEW -> {
                    PitchPreviewScreen(
                        players = previewPlayers,
                        captain = previewCaptain,
                        viceCaptain = previewViceCaptain,
                        onClose = {
                            currentScreen = screenBeforePreview
                        }
                    )
                }

                ActiveScreen.LIVE_CENTER -> {
                    selectedMatch?.let { match ->
                        LiveMatchCenterScreen(
                            match = match,
                            onBack = {
                                currentScreen = ActiveScreen.MATCH_DETAIL
                            }
                        )
                    }
                }
            }
        }
    }

    // Modal Dialogs
    if (showAddCashDialog) {
        AddCashDialog(
            onDismiss = { showAddCashDialog = false },
            onAddCash = { amount ->
                viewModel.addCash(amount)
            }
        )
    }

    if (showWithdrawDialog) {
        val maxWin = wallet?.winningBalance ?: 0.0
        WithdrawDialog(
            maxWithdrawable = maxWin,
            onDismiss = { showWithdrawDialog = false },
            onWithdraw = { amount ->
                viewModel.withdraw(amount)
            }
        )
    }

    if (showRulesDialog) {
        FantasyRulesDialog(
            onDismiss = { showRulesDialog = false }
        )
    }

    contestToJoin?.let { contest ->
        selectedMatch?.let { match ->
            val teamsForMatch = allUserTeams.filter { it.matchId == match.id }
            JoinContestDialog(
                contest = contest,
                userTeams = teamsForMatch,
                onDismiss = { contestToJoin = null },
                onCreateTeamFirst = {
                    contestToJoin = null
                    viewModel.resetTeamCreation()
                    currentScreen = ActiveScreen.CREATE_TEAM
                },
                onConfirmJoin = { team ->
                    viewModel.joinContest(contest, team) {
                        contestToJoin = null
                    }
                }
            )
        }
    }
}

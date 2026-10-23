package com.example.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.components.AboutDialog
import com.example.ui.components.AppNavigationDrawer
import com.example.ui.components.HelpDialog
import com.example.ui.components.MissionTopBar
import com.example.ui.components.StartMissionModal
import com.example.ui.screens.HistoryScreen
import com.example.ui.screens.MapScreen
import com.example.ui.screens.MapTutorialScreen
import com.example.ui.screens.Mortar120Screen
import com.example.ui.screens.Rocket107Screen
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: FireDirectionViewModel,
    modifier: Modifier = Modifier
) {
    var selectedScreen by remember { mutableIntStateOf(0) }
    var showStartMissionModal by remember { mutableStateOf(false) }
    var showHelpDialog by remember { mutableStateOf(false) }
    var showAboutDialog by remember { mutableStateOf(false) }
    var showConverterDialog by remember { mutableStateOf(false) }
    var showMapTutorialScreen by remember { mutableStateOf(false) }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    val activeMission by viewModel.activeMission.collectAsStateWithLifecycle()
    val currentThemeMode by viewModel.themeMode.collectAsStateWithLifecycle()
    val recentCommanders by viewModel.recentCommanders.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(activeMission?.id) {
        val m = activeMission
        if (m != null) {
            selectedScreen = if (m.system == "120") 1 else 0
        }
    }

    LaunchedEffect(Unit) {
        viewModel.feedbackMessage.collectLatest { msg ->
            snackbarHostState.showSnackbar(msg)
        }
    }

    if (showStartMissionModal) {
        StartMissionModal(
            currentSystem = if (selectedScreen == 1) "120" else "107",
            recentCommanders = recentCommanders,
            onDismiss = { showStartMissionModal = false },
            onConfirm = { name, desc, system, commander, crew, area ->
                viewModel.startMission(name, desc, system, commander, crew.joinToString("\n"), area)
                showStartMissionModal = false
                selectedScreen = if (system == "107") 0 else 1
            }
        )
    }

    if (showHelpDialog) {
        HelpDialog(onDismiss = { showHelpDialog = false })
    }

    if (showAboutDialog) {
        AboutDialog(onDismiss = { showAboutDialog = false })
    }

    if (showConverterDialog) {
        com.example.ui.components.UnitConverterDialog(onDismiss = { showConverterDialog = false })
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppNavigationDrawer(
                currentScreen = selectedScreen,
                onSelectScreen = { screen ->
                    selectedScreen = screen
                    coroutineScope.launch { drawerState.close() }
                },
                currentThemeMode = currentThemeMode,
                onSelectThemeMode = { mode ->
                    viewModel.setThemeMode(mode)
                },
                onOpenHelp = { showMapTutorialScreen = true },
                onOpenAbout = { showAboutDialog = true },
                onOpenConverter = { showConverterDialog = true },
                onOpenMapTutorial = { showMapTutorialScreen = true },
                onClearAllHistory = { viewModel.clearAllMissions() },
                onCloseDrawer = { coroutineScope.launch { drawerState.close() } }
            )
        }
    ) {
        Scaffold(
            modifier = modifier.fillMaxSize(),
            topBar = {
                MissionTopBar(
                    activeMission = activeMission,
                    onStartMissionClick = { showStartMissionModal = true },
                    onEndMissionClick = { viewModel.endMission() },
                    onOpenDrawer = { coroutineScope.launch { drawerState.open() } },
                    currentThemeMode = currentThemeMode,
                    onCycleTheme = { viewModel.cycleThemeMode() }
                )
            },
            bottomBar = {
                NavigationBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .windowInsetsPadding(WindowInsets.navigationBars)
                        .testTag("bottom_nav_bar")
                ) {
                    NavigationBarItem(
                        selected = !showMapTutorialScreen && selectedScreen == 0,
                        onClick = {
                            selectedScreen = 0
                            showMapTutorialScreen = false
                        },
                        icon = { Icon(Icons.Default.RocketLaunch, contentDescription = "راکت ۱۰۷") },
                        label = { Text("راکت ۱۰۷", fontWeight = FontWeight.Bold) },
                        modifier = Modifier.testTag("nav_item_107")
                    )
                    NavigationBarItem(
                        selected = !showMapTutorialScreen && selectedScreen == 1,
                        onClick = {
                            selectedScreen = 1
                            showMapTutorialScreen = false
                        },
                        icon = { Icon(Icons.Default.MyLocation, contentDescription = "خمپاره ۱۲۰") },
                        label = { Text("خمپاره ۱۲۰", fontWeight = FontWeight.Bold) },
                        modifier = Modifier.testTag("nav_item_120")
                    )
                    NavigationBarItem(
                        selected = !showMapTutorialScreen && selectedScreen == 2,
                        onClick = {
                            selectedScreen = 2
                            showMapTutorialScreen = false
                        },
                        icon = { Icon(Icons.Default.Map, contentDescription = "نقشه آنلاین و آفلاین") },
                        label = { Text("نقشه", fontWeight = FontWeight.Bold) },
                        modifier = Modifier.testTag("nav_item_map")
                    )
                    NavigationBarItem(
                        selected = !showMapTutorialScreen && selectedScreen == 3,
                        onClick = {
                            selectedScreen = 3
                            showMapTutorialScreen = false
                        },
                        icon = { Icon(Icons.Default.History, contentDescription = "تاریخچه") },
                        label = { Text("تاریخچه", fontWeight = FontWeight.Bold) },
                        modifier = Modifier.testTag("nav_item_history")
                    )
                }
            },
            snackbarHost = { SnackbarHost(snackbarHostState) },
            contentWindowInsets = WindowInsets(0, 0, 0, 0)
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                if (showMapTutorialScreen) {
                    MapTutorialScreen(
                        onNavigateBack = { showMapTutorialScreen = false }
                    )
                } else {
                    when (selectedScreen) {
                        0 -> Rocket107Screen(
                            viewModel = viewModel,
                            onStartMissionClick = { showStartMissionModal = true }
                        )
                        1 -> Mortar120Screen(
                            viewModel = viewModel,
                            onStartMissionClick = { showStartMissionModal = true }
                        )
                        2 -> MapScreen(
                            viewModel = viewModel,
                            onNavigateToScreen = { screenIdx -> selectedScreen = screenIdx },
                            onOpenTutorialScreen = { showMapTutorialScreen = true }
                        )
                        3 -> HistoryScreen(viewModel = viewModel)
                    }
                }
            }
        }
    }
}

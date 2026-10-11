package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.theme.RosewoodPrimary
import com.example.ui.viewmodel.BakalSuamiViewModel

sealed class AppTab(val title: String, val icon: ImageVector, val tag: String) {
    object Checklist : AppTab("Checklist", Icons.Default.Checklist, "tab_checklist")
    object ThirtyDays : AppTab("30 Hari", Icons.Default.CalendarMonth, "tab_thirty_days")
    object Analysis : AppTab("Analisis", Icons.Default.Analytics, "tab_analysis")
    object Journal : AppTab("Jurnal", Icons.Default.EditNote, "tab_journal")
    object Questions : AppTab("Soalan", Icons.Default.QuestionAnswer, "tab_questions")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppScaffold(
    viewModel: BakalSuamiViewModel,
    modifier: Modifier = Modifier
) {
    var currentTabIndex by remember { mutableIntStateOf(0) }
    var showCandidateManager by remember { mutableStateOf(false) }
    var showAddCandidateDialog by remember { mutableStateOf(false) }

    val candidates by viewModel.candidates.collectAsStateWithLifecycle()
    val selectedCandidate by viewModel.selectedCandidate.collectAsStateWithLifecycle()
    val checklistEntries by viewModel.checklistEntries.collectAsStateWithLifecycle()
    val journals by viewModel.journals.collectAsStateWithLifecycle()
    val thirtyDayProgress by viewModel.thirtyDayProgress.collectAsStateWithLifecycle()
    val analysis by viewModel.candidateAnalysis.collectAsStateWithLifecycle()

    val tabs = listOf(
        AppTab.Checklist,
        AppTab.ThirtyDays,
        AppTab.Analysis,
        AppTab.Journal,
        AppTab.Questions
    )

    // BackHandler: if not on first tab, go to first tab
    BackHandler(enabled = currentTabIndex != 0) {
        currentTabIndex = 0
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = RosewoodPrimary
                ),
                title = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = null,
                                tint = RosewoodPrimary,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Checklist Bakal Suami",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = RosewoodPrimary
                            )
                        }
                        Text(
                            text = "Kenal dulu sebelum buat keputusan besar",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                actions = {
                    // Candidate Switcher Pill
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .clickable { showCandidateManager = true }
                            .testTag("btn_candidate_switcher")
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = selectedCandidate?.name?.take(10) ?: "Calon",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "Tukar Calon",
                                modifier = Modifier.size(18.dp),
                                tint = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 6.dp
            ) {
                tabs.forEachIndexed { index, tab ->
                    NavigationBarItem(
                        selected = currentTabIndex == index,
                        onClick = { currentTabIndex = index },
                        icon = { Icon(imageVector = tab.icon, contentDescription = tab.title) },
                        label = { Text(tab.title, fontSize = 11.sp, fontWeight = if (currentTabIndex == index) FontWeight.Bold else FontWeight.Normal) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = RosewoodPrimary,
                            selectedTextColor = RosewoodPrimary,
                            unselectedIconColor = androidx.compose.ui.graphics.Color(0xFF8E8E93),
                            unselectedTextColor = androidx.compose.ui.graphics.Color(0xFF8E8E93),
                            indicatorColor = androidx.compose.ui.graphics.Color(0xFFFDE8EF)
                        ),
                        modifier = Modifier.testTag(tab.tag)
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
            when (currentTabIndex) {
                0 -> ChecklistScreen(
                    candidate = selectedCandidate,
                    checklistEntries = checklistEntries,
                    onRatingChange = { critId, rating, note ->
                        viewModel.setRating(critId, rating, note)
                    }
                )
                1 -> ThirtyDayGuideScreen(
                    candidate = selectedCandidate,
                    progressList = thirtyDayProgress,
                    onToggleDay = { day, isDone, note ->
                        viewModel.toggleDay(day, isDone, note)
                    }
                )
                2 -> AnalysisScreen(
                    candidate = selectedCandidate,
                    analysis = analysis
                )
                3 -> JournalScreen(
                    candidate = selectedCandidate,
                    journals = journals,
                    onAddJournal = { date, title, action, words, isConsistent, rating ->
                        viewModel.addJournal(date, title, action, words, isConsistent, rating)
                    },
                    onDeleteJournal = { entry ->
                        viewModel.deleteJournal(entry)
                    }
                )
                4 -> QuestionsScreen()
            }
        }
    }

    if (showCandidateManager) {
        CandidateManagerDialog(
            candidates = candidates,
            selectedId = selectedCandidate?.id,
            onSelect = { viewModel.selectCandidate(it) },
            onAddNew = {
                showCandidateManager = false
                showAddCandidateDialog = true
            },
            onDelete = { viewModel.deleteCandidate(it) },
            onDismiss = { showCandidateManager = false }
        )
    }

    if (showAddCandidateDialog) {
        AddCandidateDialog(
            onDismiss = { showAddCandidateDialog = false },
            onConfirm = { name, age, occ, status, dur, bio ->
                viewModel.addCandidate(name, age, occ, status, dur, bio)
                showAddCandidateDialog = false
            }
        )
    }
}

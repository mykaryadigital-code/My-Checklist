package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.guide.DayGuide
import com.example.data.guide.ThirtyDayGuideCatalog
import com.example.data.model.Candidate
import com.example.data.model.ThirtyDayProgress
import com.example.ui.theme.GreenFlagColor
import com.example.ui.theme.GreenFlagContainer
import com.example.ui.theme.PinkCardBorder
import com.example.ui.theme.RedFlagColor
import com.example.ui.theme.RedFlagContainer
import com.example.ui.theme.RosewoodPrimary
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ThirtyDayGuideScreen(
    candidate: Candidate?,
    progressList: List<ThirtyDayProgress>,
    onToggleDay: (dayNumber: Int, isCompleted: Boolean, note: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    // Filter modes: 0 = Semua (30), 1..4 = Minggu 1..4, 5 = Keputusan (H29-30), 6 = Belum Selesai, 7 = Selesai
    var selectedFilter by remember { mutableIntStateOf(0) }
    var highlightedDay by remember { mutableIntStateOf(-1) }

    val progressMap = remember(progressList) {
        progressList.associateBy { it.dayNumber }
    }

    val completedCount by remember(progressList) {
        derivedStateOf { progressList.count { it.isCompleted } }
    }

    val nextUncompletedDay by remember(progressList) {
        derivedStateOf {
            val completedSet = progressList.filter { it.isCompleted }.map { it.dayNumber }.toSet()
            (1..30).firstOrNull { it !in completedSet } ?: 30
        }
    }

    val filteredDays = remember(selectedFilter, progressList) {
        val completedSet = progressList.filter { it.isCompleted }.map { it.dayNumber }.toSet()
        when (selectedFilter) {
            0 -> ThirtyDayGuideCatalog.days // Cukup semua 30 hari
            1 -> ThirtyDayGuideCatalog.days.filter { it.weekPhase == 1 } // Hari 1-7
            2 -> ThirtyDayGuideCatalog.days.filter { it.weekPhase == 2 } // Hari 8-14
            3 -> ThirtyDayGuideCatalog.days.filter { it.weekPhase == 3 } // Hari 15-21
            4 -> ThirtyDayGuideCatalog.days.filter { it.weekPhase == 4 } // Hari 22-28
            5 -> ThirtyDayGuideCatalog.days.filter { it.weekPhase == 5 } // Hari 29-30
            6 -> ThirtyDayGuideCatalog.days.filter { it.dayNumber !in completedSet } // Belum selesai
            7 -> ThirtyDayGuideCatalog.days.filter { it.dayNumber in completedSet } // Selesai
            else -> ThirtyDayGuideCatalog.days
        }
    }

    LazyColumn(
        state = listState,
        modifier = modifier
            .fillMaxSize()
            .testTag("thirty_day_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Hero Progress Header
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Panduan 30 Hari Kenal Calon",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = RosewoodPrimary,
                            contentColor = Color.White
                        ) {
                            Text(
                                text = "Lengkap 30 Hari",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Text(
                        text = "Sistem 30 hari berpandukan buku untuk menilai lelaki melalui tindakan sebenar, bukan sekadar janji manis.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f),
                        modifier = Modifier.padding(top = 4.dp)
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Kemajuan: $completedCount daripada 30 Hari",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text(
                            text = "${(completedCount * 100) / 30}%",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    LinearProgressIndicator(
                        progress = { (completedCount / 30f).coerceIn(0f, 1f) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = RosewoodPrimary,
                        trackColor = MaterialTheme.colorScheme.surface
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Quick Action Button
                    if (completedCount < 30) {
                        Button(
                            onClick = {
                                val targetDay = nextUncompletedDay
                                onToggleDay(targetDay, true, "Selesai ditanda secara pantas.")
                                Toast.makeText(context, "Hari ke-$targetDay telah ditanda selesai!", Toast.LENGTH_SHORT).show()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = RosewoodPrimary),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(imageVector = Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = "Tanda Hari Ke-$nextUncompletedDay Selesai", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                        }
                    } else {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = GreenFlagContainer,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(imageVector = Icons.Default.DoneAll, contentDescription = null, tint = GreenFlagColor, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Tahniah! Semua 30 hari telah lengkap dinilai.",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = GreenFlagColor
                                )
                            }
                        }
                    }
                }
            }
        }

        // 30-Day Matrix Grid (Peta Penuh 30 Hari: Cukup 1 sampai 30)
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = androidx.compose.foundation.BorderStroke(1.dp, PinkCardBorder)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.CalendarMonth,
                                contentDescription = null,
                                tint = RosewoodPrimary,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Peta Matriks 30 Hari",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = RosewoodPrimary
                            )
                        }
                        Text(
                            text = "Tekan nombor untuk lompat",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // 6 items per row x 5 rows = Exactly 30 days
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        (1..30).forEach { dayNum ->
                            val isDone = progressMap[dayNum]?.isCompleted == true
                            val isHighlighted = highlightedDay == dayNum

                            val btnBg by animateColorAsState(
                                targetValue = when {
                                    isHighlighted -> RosewoodPrimary
                                    isDone -> GreenFlagColor
                                    else -> MaterialTheme.colorScheme.secondaryContainer
                                },
                                label = "bg"
                            )
                            val textColor = when {
                                isHighlighted || isDone -> Color.White
                                else -> MaterialTheme.colorScheme.onSecondaryContainer
                            }

                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = btnBg,
                                modifier = Modifier
                                    .size(width = 46.dp, height = 34.dp)
                                    .clickable {
                                        highlightedDay = dayNum
                                        selectedFilter = 0 // Tunjuk semua supaya item sentiasa ada
                                        coroutineScope.launch {
                                            // Index 0 = header, 1 = matrix, 2 = filter tabs, 3 = first day
                                            val targetIndex = 3 + (dayNum - 1)
                                            listState.animateScrollToItem(targetIndex)
                                        }
                                    }
                                    .testTag("day_grid_btn_$dayNum")
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = "$dayNum",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp,
                                        color = textColor
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Legend
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(GreenFlagColor))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "Selesai: $completedCount", style = MaterialTheme.typography.labelSmall)
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(MaterialTheme.colorScheme.secondaryContainer))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "Belum: ${30 - completedCount}", style = MaterialTheme.typography.labelSmall)
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(RosewoodPrimary))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "Dipilih", style = MaterialTheme.typography.labelSmall)
                        }
                    }
                }
            }
        }

        // Filter Tabs
        item {
            Column {
                Text(
                    text = "Tapisan Fasa & Status",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = RosewoodPrimary,
                    modifier = Modifier.padding(bottom = 6.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    FilterChip(
                        selected = selectedFilter == 0,
                        onClick = { selectedFilter = 0 },
                        label = { Text("Semua (30 Hari)") },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                    FilterChip(
                        selected = selectedFilter == 1,
                        onClick = { selectedFilter = 1 },
                        label = { Text("Minggu 1 (H1-7)") },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                    FilterChip(
                        selected = selectedFilter == 2,
                        onClick = { selectedFilter = 2 },
                        label = { Text("Minggu 2 (H8-14)") },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                    FilterChip(
                        selected = selectedFilter == 3,
                        onClick = { selectedFilter = 3 },
                        label = { Text("Minggu 3 (H15-21)") },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                    FilterChip(
                        selected = selectedFilter == 4,
                        onClick = { selectedFilter = 4 },
                        label = { Text("Minggu 4 (H22-28)") },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                    FilterChip(
                        selected = selectedFilter == 5,
                        onClick = { selectedFilter = 5 },
                        label = { Text("Keputusan (H29-30)") },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                    FilterChip(
                        selected = selectedFilter == 6,
                        onClick = { selectedFilter = 6 },
                        label = { Text("Belum (${30 - completedCount})") },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                    FilterChip(
                        selected = selectedFilter == 7,
                        onClick = { selectedFilter = 7 },
                        label = { Text("Selesai ($completedCount)") },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                }
            }
        }

        // Days List
        itemsIndexed(
            items = filteredDays,
            key = { _, item -> item.dayNumber }
        ) { _, day ->
            val prog = progressMap[day.dayNumber]
            val isDone = prog?.isCompleted ?: false
            val note = prog?.userNotes ?: ""
            val isHighlighted = highlightedDay == day.dayNumber

            DayGuideCard(
                guide = day,
                isCompleted = isDone,
                isHighlighted = isHighlighted,
                currentNote = note,
                onToggle = { done, newNote ->
                    onToggleDay(day.dayNumber, done, newNote)
                }
            )
        }

        // Generous bottom spacer to prevent bottom navigation bar overlay
        item {
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

@Composable
fun DayGuideCard(
    guide: DayGuide,
    isCompleted: Boolean,
    isHighlighted: Boolean,
    currentNote: String,
    onToggle: (Boolean, String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var expanded by remember(isHighlighted) { mutableStateOf(isHighlighted) }
    var noteEditing by remember { mutableStateOf(false) }
    var noteText by remember(currentNote) { mutableStateOf(currentNote) }

    val borderColor = when {
        isHighlighted -> RosewoodPrimary
        isCompleted -> GreenFlagColor
        else -> PinkCardBorder
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = if (isHighlighted || isCompleted) 1.5.dp else 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(16.dp)
            )
            .testTag("day_card_${guide.dayNumber}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isCompleted) GreenFlagContainer.copy(alpha = 0.25f) else MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Day badge
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = if (isCompleted) GreenFlagColor else if (isHighlighted) RosewoodPrimary else MaterialTheme.colorScheme.secondaryContainer,
                    modifier = Modifier.size(42.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = "H${guide.dayNumber}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = if (isCompleted || isHighlighted) Color.White else MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Hari ${guide.dayNumber} daripada 30",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = RosewoodPrimary
                        )
                    }
                    Text(
                        text = guide.title,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = guide.phaseTitle,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Checkbox(
                    checked = isCompleted,
                    onCheckedChange = { onToggle(it, noteText) },
                    colors = CheckboxDefaults.colors(
                        checkedColor = GreenFlagColor
                    ),
                    modifier = Modifier.testTag("checkbox_day_${guide.dayNumber}")
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Action Item Text
            Surface(
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.Top) {
                    Text(
                        text = "📌",
                        fontSize = 14.sp,
                        modifier = Modifier.padding(end = 6.dp)
                    )
                    Text(
                        text = guide.actionItem,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 19.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // Expand trigger
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded }
                    .padding(top = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = if (expanded) "Tutup soalan ta'aruf & nasihat" else "Lihat soalan ta'aruf & tanda amaran",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold
                )
                Icon(
                    imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
            }

            AnimatedVisibility(visible = expanded) {
                Column(modifier = Modifier.padding(top = 10.dp)) {
                    // Question to ask with Copy Button
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.QuestionAnswer,
                                        contentDescription = null,
                                        tint = RosewoodPrimary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "Ayat / Soalan Untuk Ditanya:",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = RosewoodPrimary
                                    )
                                }
                                IconButton(
                                    onClick = {
                                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                        clipboard.setPrimaryClip(ClipData.newPlainText("Soalan Ta'aruf", guide.questionToAsk))
                                        Toast.makeText(context, "Soalan disalin!", Toast.LENGTH_SHORT).show()
                                    },
                                    modifier = Modifier.size(24.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ContentCopy,
                                        contentDescription = "Salin soalan",
                                        tint = RosewoodPrimary,
                                        modifier = Modifier.size(14.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = guide.questionToAsk,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    // Red Flag Warning
                    Surface(
                        color = RedFlagContainer.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = null,
                                tint = RedFlagColor,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = "Amaran Red Flag:",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = RedFlagColor
                                )
                                Text(
                                    text = guide.redFlagWarning,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFF7F1D1D)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    // Wise Quote
                    Surface(
                        color = Color(0xFFFFFBEB),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Icon(
                                imageVector = Icons.Default.FormatQuote,
                                contentDescription = null,
                                tint = Color(0xFFB45309),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = guide.wiseQuote,
                                style = MaterialTheme.typography.bodySmall,
                                fontStyle = FontStyle.Italic,
                                color = Color(0xFF92400E)
                            )
                        }
                    }

                    // Notes Section
                    Spacer(modifier = Modifier.height(8.dp))
                    if (noteEditing) {
                        OutlinedTextField(
                            value = noteText,
                            onValueChange = { noteText = it },
                            placeholder = { Text("Catatan tindakan hari ini...") },
                            modifier = Modifier.fillMaxWidth(),
                            textStyle = MaterialTheme.typography.bodySmall,
                            shape = RoundedCornerShape(10.dp),
                            trailingIcon = {
                                IconButton(onClick = {
                                    noteEditing = false
                                    onToggle(isCompleted, noteText)
                                }) {
                                    Icon(Icons.Filled.Check, contentDescription = "Simpan", tint = GreenFlagColor)
                                }
                            }
                        )
                    } else {
                        Text(
                            text = if (noteText.isBlank()) "✏️ Tulis catatan pemerhatian hari ini..." else "📝 Nota: $noteText",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .clickable { noteEditing = true }
                                .padding(vertical = 4.dp)
                        )
                    }
                }
            }
        }
    }
}

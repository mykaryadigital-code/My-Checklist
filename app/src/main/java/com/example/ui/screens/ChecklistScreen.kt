package com.example.ui.screens

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.criteria.CriteriaCatalog
import com.example.data.criteria.CriterionAspect
import com.example.data.model.Candidate
import com.example.data.model.ChecklistEntry
import com.example.data.model.ChecklistRating
import com.example.ui.components.CriterionCard
import com.example.ui.components.ScoreProgressCard
import com.example.ui.theme.RosewoodPrimary

@Composable
fun ChecklistScreen(
    candidate: Candidate?,
    checklistEntries: List<ChecklistEntry>,
    onRatingChange: (criterionId: String, rating: ChecklistRating, note: String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedAspect by remember { mutableStateOf<CriterionAspect?>(null) }

    val entryMap = remember(checklistEntries) {
        checklistEntries.associateBy { it.criterionId }
    }

    val filteredCriteria = remember(selectedAspect) {
        if (selectedAspect == null) {
            CriteriaCatalog.allCriteria
        } else {
            CriteriaCatalog.allCriteria.filter { it.aspect == selectedAspect }
        }
    }

    val evaluatedCount by remember(checklistEntries) {
        derivedStateOf {
            checklistEntries.count { it.rating != ChecklistRating.UNRATED }
        }
    }

    val greenCount by remember(checklistEntries) {
        derivedStateOf {
            checklistEntries.count { it.rating == ChecklistRating.GREEN_FLAG }
        }
    }

    val cautionCount by remember(checklistEntries) {
        derivedStateOf {
            checklistEntries.count { it.rating == ChecklistRating.CAUTION }
        }
    }

    val redCount by remember(checklistEntries) {
        derivedStateOf {
            checklistEntries.count { it.rating == ChecklistRating.RED_FLAG }
        }
    }

    val percentage by remember(checklistEntries) {
        derivedStateOf {
            val rated = checklistEntries.filter { it.rating != ChecklistRating.UNRATED }
            val maxPts = rated.size * 3
            if (maxPts > 0) {
                val pts = rated.sumOf {
                    when (it.rating) {
                        ChecklistRating.GREEN_FLAG -> 3
                        ChecklistRating.CAUTION -> 1
                        ChecklistRating.RED_FLAG -> -2
                        ChecklistRating.UNRATED -> 0
                    }
                }
                ((maxOf(0, pts) * 100) / maxPts)
            } else 0
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("checklist_screen"),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Top score summary card
        item {
            ScoreProgressCard(
                percentage = percentage,
                evaluatedCount = evaluatedCount,
                totalCriteria = CriteriaCatalog.allCriteria.size,
                greenCount = greenCount,
                cautionCount = cautionCount,
                redCount = redCount
            )
        }

        // Category Filter Chips
        item {
            Column {
                Text(
                    text = "Aspek Penilaian",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = RosewoodPrimary,
                    modifier = Modifier.padding(bottom = 6.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilterChip(
                        selected = selectedAspect == null,
                        onClick = { selectedAspect = null },
                        label = { Text("Semua (${CriteriaCatalog.allCriteria.size})") },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                        )
                    )

                    CriterionAspect.values().forEach { aspect ->
                        val count = CriteriaCatalog.allCriteria.count { it.aspect == aspect }
                        FilterChip(
                            selected = selectedAspect == aspect,
                            onClick = { selectedAspect = aspect },
                            label = { Text("${aspect.title} ($count)") },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primary,
                                selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                            )
                        )
                    }
                }
            }
        }

        // Criteria Cards
        items(
            items = filteredCriteria,
            key = { it.id }
        ) { criterion ->
            val entry = entryMap[criterion.id]
            val rating = entry?.rating ?: ChecklistRating.UNRATED
            val note = entry?.note ?: ""

            CriterionCard(
                criterion = criterion,
                currentRating = rating,
                currentNote = note,
                onRatingSelected = { newRating, newNote ->
                    onRatingChange(criterion.id, newRating, newNote)
                }
            )
        }

        item {
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

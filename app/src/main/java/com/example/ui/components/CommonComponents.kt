package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.criteria.CriterionItem
import com.example.data.model.ChecklistRating
import com.example.ui.theme.CautionFlagColor
import com.example.ui.theme.CautionFlagContainer
import com.example.ui.theme.GreenFlagColor
import com.example.ui.theme.GreenFlagContainer
import com.example.ui.theme.PinkCardBorder
import com.example.ui.theme.RedFlagColor
import com.example.ui.theme.RedFlagContainer
import com.example.ui.theme.RosewoodPrimary
import com.example.ui.theme.UnratedColor

@Composable
fun RatingChip(
    rating: ChecklistRating,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val (bgColor, contentColor, icon) = when (rating) {
        ChecklistRating.GREEN_FLAG -> Triple(
            if (isSelected) GreenFlagColor else GreenFlagContainer,
            if (isSelected) Color.White else GreenFlagColor,
            Icons.Filled.CheckCircle
        )
        ChecklistRating.CAUTION -> Triple(
            if (isSelected) CautionFlagColor else CautionFlagContainer,
            if (isSelected) Color.White else CautionFlagColor,
            Icons.Filled.Warning
        )
        ChecklistRating.RED_FLAG -> Triple(
            if (isSelected) RedFlagColor else RedFlagContainer,
            if (isSelected) Color.White else RedFlagColor,
            Icons.Filled.Error
        )
        ChecklistRating.UNRATED -> Triple(
            if (isSelected) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.surfaceVariant,
            if (isSelected) Color.White else UnratedColor,
            Icons.Filled.Help
        )
    }

    Surface(
        color = bgColor,
        contentColor = contentColor,
        shape = RoundedCornerShape(20.dp),
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .clickable(onClick = onClick)
            .testTag("rating_chip_${rating.name}")
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = rating.label,
                modifier = Modifier.size(16.dp),
                tint = contentColor
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = when (rating) {
                    ChecklistRating.GREEN_FLAG -> "Green Flag"
                    ChecklistRating.CAUTION -> "Ragu / Pantau"
                    ChecklistRating.RED_FLAG -> "Red Flag"
                    ChecklistRating.UNRATED -> "Belum"
                },
                fontSize = 12.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = contentColor
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CriterionCard(
    criterion: CriterionItem,
    currentRating: ChecklistRating,
    currentNote: String,
    onRatingSelected: (ChecklistRating, String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    var noteEditing by remember { mutableStateOf(false) }
    var noteText by remember(currentNote) { mutableStateOf(currentNote) }

    val borderColor = when (currentRating) {
        ChecklistRating.GREEN_FLAG -> GreenFlagColor
        ChecklistRating.CAUTION -> CautionFlagColor
        ChecklistRating.RED_FLAG -> RedFlagColor
        ChecklistRating.UNRATED -> PinkCardBorder
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize()
            .border(
                width = if (currentRating != ChecklistRating.UNRATED) 1.5.dp else 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(16.dp)
            )
            .testTag("criterion_card_${criterion.id}"),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header: Book reference badge & expand toggle
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = criterion.bookRef,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        fontWeight = FontWeight.SemiBold
                    )
                }

                IconButton(
                    onClick = { expanded = !expanded },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = "Buka panduan",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Title & Subtitle
            Text(
                text = criterion.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = criterion.subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 2.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // "Ayat & Soalan Penting" Box
            Surface(
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = "💬 Ayat & Soalan Penting:",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = RosewoodPrimary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = criterion.importantQuestion,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 20.sp
                    )
                }
            }

            // Expandable details: Tips, Green Flags & Red Flags
            AnimatedVisibility(visible = expanded) {
                Column(modifier = Modifier.padding(top = 12.dp)) {
                    // Tip Pemerhatian
                    Surface(
                        color = Color(0xFFF0FDF4),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = "👀 Tindakan Untuk Diperhatikan:",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = GreenFlagColor
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = criterion.observationTip,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF166534)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Green Flag Indicators
                    Surface(
                        color = GreenFlagContainer.copy(alpha = 0.6f),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = "✅ Tanda Green Flag (Positif):",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = GreenFlagColor
                            )
                            criterion.greenFlagPoints.forEach { pt ->
                                Row(
                                    modifier = Modifier.padding(top = 4.dp),
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.Check,
                                        contentDescription = null,
                                        tint = GreenFlagColor,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = pt,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color(0xFF1F2937)
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Red Flag Indicators
                    Surface(
                        color = RedFlagContainer.copy(alpha = 0.6f),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = "⚠️ Amaran Red Flag (Bahaya):",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = RedFlagColor
                            )
                            criterion.redFlagPoints.forEach { pt ->
                                Row(
                                    modifier = Modifier.padding(top = 4.dp),
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.Close,
                                        contentDescription = null,
                                        tint = RedFlagColor,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = pt,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color(0xFF1F2937)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Rating Chips (Selection Bar)
            Text(
                text = "Penilaian Anda:",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ChecklistRating.values().forEach { r ->
                    RatingChip(
                        rating = r,
                        isSelected = currentRating == r,
                        onClick = {
                            onRatingSelected(r, noteText)
                        }
                    )
                }
            }

            // Note Section
            Spacer(modifier = Modifier.height(8.dp))
            if (noteEditing) {
                OutlinedTextField(
                    value = noteText,
                    onValueChange = { noteText = it },
                    placeholder = { Text("Catatan tindakan atau sebab penilaian...") },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = MaterialTheme.typography.bodySmall,
                    shape = RoundedCornerShape(12.dp),
                    trailingIcon = {
                        IconButton(onClick = {
                            noteEditing = false
                            onRatingSelected(currentRating, noteText)
                        }) {
                            Icon(Icons.Filled.CheckCircle, contentDescription = "Simpan nota", tint = GreenFlagColor)
                        }
                    }
                )
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { noteEditing = true }
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Edit,
                        contentDescription = "Tulis nota",
                        modifier = Modifier.size(14.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (noteText.isBlank()) "Tambah catatan peribadi..." else noteText,
                        style = MaterialTheme.typography.bodySmall,
                        color = if (noteText.isBlank()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
fun ScoreProgressCard(
    percentage: Int,
    evaluatedCount: Int,
    totalCriteria: Int,
    greenCount: Int,
    cautionCount: Int,
    redCount: Int,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, com.example.ui.theme.PinkCardBorder, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Skor Keserasian & Kematangan",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "$evaluatedCount daripada $totalCriteria kriteria telah dinilai",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Surface(
                    shape = CircleShape,
                    color = when {
                        redCount >= 3 -> RedFlagContainer
                        percentage >= 75 -> GreenFlagContainer
                        else -> CautionFlagContainer
                    }
                ) {
                    Text(
                        text = "$percentage%",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 18.sp,
                        color = when {
                            redCount >= 3 -> RedFlagColor
                            percentage >= 75 -> GreenFlagColor
                            else -> CautionFlagColor
                        },
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            LinearProgressIndicator(
                progress = { (percentage / 100f).coerceIn(0f, 1f) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = when {
                    redCount >= 3 -> RedFlagColor
                    percentage >= 75 -> GreenFlagColor
                    else -> CautionFlagColor
                },
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(GreenFlagColor))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Green: $greenCount", style = MaterialTheme.typography.bodySmall)
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(CautionFlagColor))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Ragu: $cautionCount", style = MaterialTheme.typography.bodySmall)
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(RedFlagColor))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Red Flag: $redCount", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold, color = RedFlagColor)
                }
            }
        }
    }
}

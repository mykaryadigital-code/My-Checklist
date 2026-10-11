package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.criteria.CriterionAspect
import com.example.data.local.CandidateAnalysis
import com.example.data.model.Candidate
import com.example.ui.theme.CautionFlagColor
import com.example.ui.theme.GreenFlagColor
import com.example.ui.theme.GreenFlagContainer
import com.example.ui.theme.RedFlagColor
import com.example.ui.theme.RedFlagContainer
import com.example.ui.theme.RosewoodPrimary

@Composable
fun AnalysisScreen(
    candidate: Candidate?,
    analysis: CandidateAnalysis?,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var showIstikharah by remember { mutableStateOf(false) }

    if (candidate == null || analysis == null) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Pilih calon untuk melihat analisis",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        return
    }

    val verdictColor = Color(analysis.verdictColorHex)

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("analysis_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Main Verdict Hero Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.5.dp, verdictColor, RoundedCornerShape(20.dp)),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = "KEPUTUSAN PENILAIAN",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                letterSpacing = 1.sp
                            )
                            Text(
                                text = candidate.name,
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        // Big Score Badge
                        Surface(
                            shape = CircleShape,
                            color = verdictColor.copy(alpha = 0.15f),
                            modifier = Modifier.size(64.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = "${analysis.overallPercentage}%",
                                    fontWeight = FontWeight.Black,
                                    fontSize = 20.sp,
                                    color = verdictColor
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = verdictColor.copy(alpha = 0.12f),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = if (analysis.redCount >= 3) Icons.Default.Warning else Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = verdictColor,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = analysis.verdictTitle,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = verdictColor
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = analysis.verdictRecommendation,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 22.sp
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Mini Stats Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "${analysis.greenCount}", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = GreenFlagColor)
                            Text(text = "Green Flag", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "${analysis.cautionCount}", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = CautionFlagColor)
                            Text(text = "Ragu / Pantau", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "${analysis.redCount}", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = RedFlagColor)
                            Text(text = "Red Flag", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }
        }

        // Category Breakdown Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, com.example.ui.theme.PinkCardBorder, RoundedCornerShape(18.dp)),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "Analisis Mengikut Kategori Kriteria",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = RosewoodPrimary
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    CriterionAspect.values().forEach { aspect ->
                        val score = analysis.aspectScores[aspect]
                        if (score != null) {
                            AspectBreakdownRow(score = score)
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    }
                }
            }
        }

        // Action Buttons: Copy Summary & Share
        item {
            Button(
                onClick = {
                    val summaryText = buildString {
                        appendLine("📋 LAPORAN PENILAIAN CALON SUAMI")
                        appendLine("Aplikasi: Checklist Bakal Suami")
                        appendLine("----------------------------------------")
                        appendLine("Calon: ${candidate.name} (${candidate.age} tahun)")
                        appendLine("Status: ${candidate.maritalStatus} | Pekerjaan: ${candidate.occupation}")
                        appendLine("Tempoh Kenal: ${candidate.durationKnown}")
                        appendLine("")
                        appendLine("📊 KEPUTUSAN ANALISIS:")
                        appendLine("Skor Keserasian: ${analysis.overallPercentage}%")
                        appendLine("Status: ${analysis.verdictTitle}")
                        appendLine("Ringkasan: ${analysis.verdictRecommendation}")
                        appendLine("Green Flag: ${analysis.greenCount} | Ragu-ragu: ${analysis.cautionCount} | Red Flag: ${analysis.redCount}")
                        appendLine("")
                        appendLine("🔍 SKOR MENGIKUT ASPEK:")
                        analysis.aspectScores.forEach { (asp, sc) ->
                            appendLine("- ${asp.title}: ${sc.percentage}% (${sc.greenCount} Hijau, ${sc.redCount} Merah)")
                        }
                        appendLine("----------------------------------------")
                        appendLine("Panduan: Kenal dulu sebelum ambil keputusan besar. Janda Bukan Akhir.")
                    }

                    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    clipboard.setPrimaryClip(ClipData.newPlainText("Rumusan Calon", summaryText))
                    Toast.makeText(context, "Rumusan berjaya disalin ke papan keratan!", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("btn_copy_summary"),
                colors = ButtonDefaults.buttonColors(
                    containerColor = RosewoodPrimary
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(imageVector = Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Salin Laporan Rumusan Calon", fontWeight = FontWeight.Bold)
            }
        }

        // Panduan Istikharah & Doa Penenang Hati (Spiritual Peace Card)
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, com.example.ui.theme.PinkCardBorder, RoundedCornerShape(18.dp)),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFFFF7FA)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { showIstikharah = !showIstikharah },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = null,
                                tint = RosewoodPrimary,
                                modifier = Modifier.size(22.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Panduan Solat Istikharah & Doa",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = RosewoodPrimary
                            )
                        }

                        Icon(
                            imageVector = if (showIstikharah) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                            contentDescription = null,
                            tint = RosewoodPrimary
                        )
                    }

                    AnimatedVisibility(visible = showIstikharah) {
                        Column(modifier = Modifier.padding(top = 12.dp)) {
                            Text(
                                text = "Doa Memohon Petunjuk Pilihan Pasangan:",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Surface(
                                color = Color.White,
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "\"Ya Allah, sesungguhnya aku memohon petunjuk dengan ilmu-Mu, dan aku memohon kekuatan dengan kekuasaan-Mu. Sekiranya Engkau tahu bahawa perkara (pernikahan dengan calon ini) adalah baik untuk agamaku, kehidupanku dan kesudahan urusanku, maka takdirkanlah ia bagiku dan permudahkanlah. Dan sekiranya ia buruk untukku, maka jauhkanlah ia daripadaku dan jauhkanlah aku daripadanya...\"",
                                    style = MaterialTheme.typography.bodySmall,
                                    modifier = Modifier.padding(12.dp),
                                    lineHeight = 20.sp,
                                    color = Color(0xFF374151)
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "💡 Tip: Istikharah bukan bererti menunggu mimpi melihat warna tertentu, tetapi merasai kelapangan jiwa dan melihat kemudahan urusan tanpa paksaan.",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF78350F)
                            )
                        }
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
fun AspectBreakdownRow(score: com.example.data.local.AspectScore) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = score.aspect.title,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "${score.percentage}% (${score.ratedCount}/${score.totalCriteria})",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = when {
                    score.redCount > 0 -> RedFlagColor
                    score.percentage >= 70 -> GreenFlagColor
                    else -> CautionFlagColor
                }
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        LinearProgressIndicator(
            progress = { (score.percentage / 100f).coerceIn(0f, 1f) },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp)),
            color = when {
                score.redCount > 0 -> RedFlagColor
                score.percentage >= 70 -> GreenFlagColor
                else -> CautionFlagColor
            },
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )
    }
}

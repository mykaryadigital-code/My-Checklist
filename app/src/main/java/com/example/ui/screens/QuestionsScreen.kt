package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.GreenFlagColor
import com.example.ui.theme.GreenFlagContainer
import com.example.ui.theme.RedFlagColor
import com.example.ui.theme.RedFlagContainer
import com.example.ui.theme.RosewoodPrimary

data class ImportantQuestionModel(
    val id: String,
    val category: String,
    val questionText: String,
    val purpose: String,
    val goodAnswer: String,
    val badAnswer: String
)

val sampleImportantQuestions = listOf(
    ImportantQuestionModel(
        id = "q1",
        category = "Niat & Matlamat",
        questionText = "\"Kenapa awak nak berkahwin semula sekarang, dan apa sebenarnya yang awak cari dalam diri seorang isteri?\"",
        purpose = "Mengenal pasti sama ada niatnya untuk membina ibadah sakinah atau mencari keuntungan peribadi / pembantu percuma.",
        goodAnswer = "Mahu berkongsi hidup, mencari ketenangan jiwa (sakinah), saling menyokong ibadah dan memikul amanah nafkah.",
        badAnswer = "\"Mak saya suruh kahwin cepat\", atau \"Saya perlukan orang untuk uruskan rumah dan anak saya.\""
    ),
    ImportantQuestionModel(
        id = "q2",
        category = "Kewangan & Nafkah",
        questionText = "\"Bagaimana perancangan awak tentang nafkah rumah tangga dan perbelanjaan jika isteri turut berpendapatan?\"",
        purpose = "Menguji kefahaman hak nafkah zahir menurut syarak dan mengelakkan eksploitasi wang isteri pasca nikah.",
        goodAnswer = "Faham bahawa nafkah asas (makan, pakai, kediaman) adalah kewajipan suami. Bantuan isteri adalah ihsan tanpa paksaan.",
        badAnswer = "Menuntut perkongsian '50-50' secara keras atau mengharapkan isteri menyara keluarga sepenuhnya."
    ),
    ImportantQuestionModel(
        id = "q3",
        category = "Emosi & Konflik",
        questionText = "\"Bila awak tengah sangat marah atau rasa kecewa, apa yang awak perlukan daripada orang sekeliling?\"",
        purpose = "Melihat cara calon mengendalikan rasa tidak puas hati dan cara berdamai.",
        goodAnswer = "\"Saya ambil masa sekejap untuk bertenang, lepas reda baru kita bincang elok-elok tanpa menengking.\"",
        badAnswer = "\"Jangan tegur saya, biar saya yang tentukan\" atau menghempas barang dan memulaukan berhari-hari."
    ),
    ImportantQuestionModel(
        id = "q4",
        category = "Anak & Kekeluargaan",
        questionText = "\"Bagaimana pandangan dan kesediaan awak untuk menerima anak saya sebagai sebahagian daripada keluarga awak?\"",
        purpose = "Sangat mustahak untuk wanita berstatus ibu tunggal agar anak tidak teraniaya selepas bernikah.",
        goodAnswer = "Ikhlas menyayangi, menghormati hak anak, sabar dengan karenah anak dan tidak berkira dalam nafkah.",
        badAnswer = "Menyarankan anak diserahkan kepada bekas suami / nenek atau menunjukkan rasa rimas."
    ),
    ImportantQuestionModel(
        id = "q5",
        category = "Masa Lalu",
        questionText = "\"Apa pengajaran terbesar yang awak dapat daripada hubungan atau perkahwinan awak yang lalu?\"",
        purpose = "Melihat kematangan refleksi diri calon (adakah dia sentiasa menyalahkan bekas isteri).",
        goodAnswer = "Mengakui kelemahan diri yang telah diperbaiki, bercakap dengan adab tanpa fitnah melulu.",
        badAnswer = "Menyalahkan bekas pasangannya 100%, beremosi dan masih menyimpan dendam."
    ),
    ImportantQuestionModel(
        id = "q6",
        category = "Komitmen & Langkah",
        questionText = "\"Bila masa yang awak rasa sesuai untuk kita aturkan pertemuan rasmi bersama wali dan keluarga saya?\"",
        purpose = "Menguji kesungguhan dan ketelusan untuk melangkah ke fasa akad nikah yang sah.",
        goodAnswer = "Menyambut baik, menghormati wali dan bersedia menetapkan tarikh pertemuan keluarga.",
        badAnswer = "Berdolak-dalih 'belum bersedia', 'tengok dulu' atau mengajak nikah sempadan secara rahsia."
    )
)

@Composable
fun QuestionsScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var selectedCat by remember { mutableStateOf("Semua") }

    val categories = listOf("Semua", "Niat & Matlamat", "Kewangan & Nafkah", "Emosi & Konflik", "Anak & Kekeluargaan", "Masa Lalu", "Komitmen & Langkah")

    val filtered = remember(selectedCat) {
        if (selectedCat == "Semua") sampleImportantQuestions
        else sampleImportantQuestions.filter { it.category == selectedCat }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("questions_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Koleksi Ayat & Soalan Penting",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = "Soalan bernas untuk menguji pemikiran, integriti dan kesediaan calon semasa sesi ta'aruf.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                    )
                }
            }
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                categories.forEach { cat ->
                    FilterChip(
                        selected = selectedCat == cat,
                        onClick = { selectedCat = cat },
                        label = { Text(cat) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                }
            }
        }

        items(items = filtered, key = { it.id }) { item ->
            QuestionCard(
                item = item,
                onCopy = {
                    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    clipboard.setPrimaryClip(ClipData.newPlainText("Soalan Ta'aruf", item.questionText))
                    Toast.makeText(context, "Soalan disalin!", Toast.LENGTH_SHORT).show()
                }
            )
        }

        item {
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
fun QuestionCard(
    item: ImportantQuestionModel,
    onCopy: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, com.example.ui.theme.PinkCardBorder, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Surface(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = item.category,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                IconButton(onClick = onCopy, modifier = Modifier.size(32.dp)) {
                    Icon(
                        imageVector = Icons.Default.ContentCopy,
                        contentDescription = "Salin soalan",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = item.questionText,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = RosewoodPrimary,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Tujuan: ${item.purpose}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = if (expanded) "Tutup panduan jawapan" else "Lihat petanda jawapan (Green/Red Flag)",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold
                )
                Icon(
                    imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(18.dp)
                )
            }

            AnimatedVisibility(visible = expanded) {
                Column(modifier = Modifier.padding(top = 10.dp)) {
                    Surface(
                        color = GreenFlagContainer.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(
                                text = "✅ Jawapan Matang (Green Flag):",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = GreenFlagColor
                            )
                            Text(text = item.goodAnswer, style = MaterialTheme.typography.bodySmall)
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Surface(
                        color = RedFlagContainer.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(
                                text = "⚠️ Jawapan Meragukan (Red Flag):",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = RedFlagColor
                            )
                            Text(text = item.badAnswer, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        }
    }
}

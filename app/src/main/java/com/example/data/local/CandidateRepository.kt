package com.example.data.local

import com.example.data.criteria.CriteriaCatalog
import com.example.data.criteria.CriterionAspect
import com.example.data.model.Candidate
import com.example.data.model.ChecklistEntry
import com.example.data.model.ChecklistRating
import com.example.data.model.JournalEntry
import com.example.data.model.ThirtyDayProgress
import kotlinx.coroutines.flow.Flow

data class AspectScore(
    val aspect: CriterionAspect,
    val totalCriteria: Int,
    val ratedCount: Int,
    val greenCount: Int,
    val cautionCount: Int,
    val redCount: Int,
    val percentage: Int
)

data class CandidateAnalysis(
    val candidateId: Long,
    val totalCriteria: Int,
    val evaluatedCount: Int,
    val greenCount: Int,
    val cautionCount: Int,
    val redCount: Int,
    val overallPercentage: Int,
    val aspectScores: Map<CriterionAspect, AspectScore>,
    val verdictTitle: String,
    val verdictRecommendation: String,
    val verdictColorHex: Long
)

class CandidateRepository(private val db: AppDatabase) {

    fun getAllCandidates(): Flow<List<Candidate>> = db.candidateDao().getAllCandidates()

    fun getCandidateById(id: Long): Flow<Candidate?> = db.candidateDao().getCandidateById(id)

    suspend fun insertCandidate(candidate: Candidate): Long = db.candidateDao().insertCandidate(candidate)

    suspend fun updateCandidate(candidate: Candidate) = db.candidateDao().updateCandidate(candidate)

    suspend fun deleteCandidate(candidate: Candidate) {
        db.candidateDao().deleteCandidate(candidate)
        db.checklistDao().deleteEntriesForCandidate(candidate.id)
        db.journalDao().deleteJournalsForCandidate(candidate.id)
        db.thirtyDayProgressDao().deleteProgressForCandidate(candidate.id)
    }

    fun getChecklistEntries(candidateId: Long): Flow<List<ChecklistEntry>> =
        db.checklistDao().getEntriesForCandidate(candidateId)

    suspend fun updateChecklistRating(candidateId: Long, criterionId: String, rating: ChecklistRating, note: String = "") {
        db.checklistDao().insertOrUpdateEntry(
            ChecklistEntry(
                candidateId = candidateId,
                criterionId = criterionId,
                rating = rating,
                note = note,
                updatedAt = System.currentTimeMillis()
            )
        )
    }

    fun getJournals(candidateId: Long): Flow<List<JournalEntry>> =
        db.journalDao().getJournalsForCandidate(candidateId)

    suspend fun addJournal(entry: JournalEntry): Long = db.journalDao().insertJournal(entry)

    suspend fun deleteJournal(entry: JournalEntry) = db.journalDao().deleteJournal(entry)

    fun getThirtyDayProgress(candidateId: Long): Flow<List<ThirtyDayProgress>> =
        db.thirtyDayProgressDao().getProgressForCandidate(candidateId)

    suspend fun toggleDayProgress(candidateId: Long, dayNumber: Int, isCompleted: Boolean, note: String = "") {
        db.thirtyDayProgressDao().insertOrUpdateProgress(
            ThirtyDayProgress(
                candidateId = candidateId,
                dayNumber = dayNumber,
                isCompleted = isCompleted,
                userNotes = note,
                completedAt = if (isCompleted) System.currentTimeMillis() else 0L
            )
        )
    }

    suspend fun seedInitialSampleCandidateIfEmpty() {
        // Helpful first-time starter profile so user immediately sees full app functionality
        val candidate = Candidate(
            name = "Calon Hariz (Contoh)",
            age = 34,
            occupation = "Pengurus Logistik",
            maritalStatus = "Duda (Ada 1 Anak)",
            durationKnown = "2 Bulan",
            bio = "Perkenalan melalui ta'aruf keluarga. Matang, bersedia memikul tanggungjawab nafkah dan menerima anak pasangan."
        )
        val id = db.candidateDao().insertCandidate(candidate)

        // Pre-fill some starter ratings for demonstration
        val sampleEntries = listOf(
            ChecklistEntry(id, "sahsiah_niat", ChecklistRating.GREEN_FLAG, "Niat ikhlas nak bina keluarga sakinah dan cari teman hidup berkat."),
            ChecklistEntry(id, "sahsiah_konflik", ChecklistRating.GREEN_FLAG, "Bila berbeza pendapat, dia bertenang dan bawa berbincang secara matang."),
            ChecklistEntry(id, "sahsiah_masa_lalu", ChecklistRating.GREEN_FLAG, "Jujur berkongsi surat cerai dan urusan hak penjagaan anak kandung."),
            ChecklistEntry(id, "kerjaya_punca_pendapatan", ChecklistRating.GREEN_FLAG, "Pekerjaan tetap 7 tahun di syarikat swasta."),
            ChecklistEntry(id, "kerjaya_kefahaman_nafkah", ChecklistRating.GREEN_FLAG, "Faham kewajipan nafkah asas pakaian, makan dan tempat tinggal."),
            ChecklistEntry(id, "kerjaya_beban_hutang", ChecklistRating.CAUTION, "Ada pinjaman kereta & perumahan, perlu perincian baki bulanan."),
            ChecklistEntry(id, "keluarga_anak_pasangan", ChecklistRating.GREEN_FLAG, "Mesra bila berjumpa anak di taman, sabar dan tidak kekok.")
        )
        for (entry in sampleEntries) {
            db.checklistDao().insertOrUpdateEntry(entry)
        }

        // Add a sample journal entry
        db.journalDao().insertJournal(
            JournalEntry(
                candidateId = id,
                dateString = "15 Okt 2026",
                title = "Pertemuan santai di taman rekreasi",
                observedAction = "Membawa hadiah mainan kecil untuk anak, sabar melayan bila anak tumpahkan aiskrim tanpa marah.",
                wordsSaid = "\"Anak awak adalah amanah yang saya akan jaga macam anak saya sendiri.\"",
                isConsistent = true,
                rating = ChecklistRating.GREEN_FLAG
            )
        )

        // Mark day 1 & day 2 completed
        db.thirtyDayProgressDao().insertOrUpdateProgress(
            ThirtyDayProgress(id, 1, true, "Niat sudah disahkan jelas untuk nikah.", System.currentTimeMillis())
        )
        db.thirtyDayProgressDao().insertOrUpdateProgress(
            ThirtyDayProgress(id, 2, true, "Surat cerai rasmi telah disemak.", System.currentTimeMillis())
        )
    }

    companion object {
        fun calculateAnalysis(candidateId: Long, entries: List<ChecklistEntry>): CandidateAnalysis {
            val entryMap = entries.associateBy { it.criterionId }
            val allCriteria = CriteriaCatalog.allCriteria
            val total = allCriteria.size

            var greenCount = 0
            var cautionCount = 0
            var redCount = 0
            var ratedCount = 0
            var netPoints = 0

            val aspectGroups = allCriteria.groupBy { it.aspect }
            val aspectScoreMap = mutableMapOf<CriterionAspect, AspectScore>()

            for ((aspect, criteriaList) in aspectGroups) {
                var aGreen = 0
                var aCaution = 0
                var aRed = 0
                var aRated = 0
                var aPoints = 0

                for (c in criteriaList) {
                    val entry = entryMap[c.id]
                    if (entry != null && entry.rating != ChecklistRating.UNRATED) {
                        aRated++
                        when (entry.rating) {
                            ChecklistRating.GREEN_FLAG -> {
                                aGreen++
                                aPoints += 3
                            }
                            ChecklistRating.CAUTION -> {
                                aCaution++
                                aPoints += 1
                            }
                            ChecklistRating.RED_FLAG -> {
                                aRed++
                                aPoints -= 2
                            }
                            ChecklistRating.UNRATED -> {}
                        }
                    }
                }

                val maxPts = aRated * 3
                val pct = if (maxPts > 0) ((maxOf(0, aPoints) * 100) / maxPts) else 0

                aspectScoreMap[aspect] = AspectScore(
                    aspect = aspect,
                    totalCriteria = criteriaList.size,
                    ratedCount = aRated,
                    greenCount = aGreen,
                    cautionCount = aCaution,
                    redCount = aRed,
                    percentage = pct
                )

                greenCount += aGreen
                cautionCount += aCaution
                redCount += aRed
                ratedCount += aRated
                netPoints += aPoints
            }

            val maxPoints = ratedCount * 3
            val overallPercentage = if (maxPoints > 0) ((maxOf(0, netPoints) * 100) / maxPoints) else 0

            // Determine verdict
            val (vTitle, vRec, vColor) = when {
                redCount >= 3 -> Triple(
                    "Amaran Kritikal (Red Flag Tinggi)",
                    "Ditemui ${redCount} amaran bahaya kritikal. Berisiko tinggi kepada emosi, kewangan dan masa depan anda. Dinasihatkan untuk tidak meneruskan hubungan ini.",
                    0xFFDC2626
                )
                redCount in 1..2 -> Triple(
                    "Perhatian & Siasatan Lanjut",
                    "Ditemui ${redCount} tanda amaran (Red Flag). Perlu bincang dan selidik secara jujur sebelum mengambil sebarang komitmen perkahwinan.",
                    0xFFD97706
                )
                overallPercentage >= 80 && ratedCount >= 5 -> Triple(
                    "Sangat Berpotensi (Green Flag)",
                    "Menunjukkan kematangan emosi, kestabilan kerjaya dan komitmen kekeluargaan yang positif. Teruskan ke fasa pertemuan keluarga/wali dan solat istikharah.",
                    0xFF1B8755
                )
                overallPercentage >= 60 && ratedCount >= 3 -> Triple(
                    "Berpotensi Sederhana",
                    "Ciri-ciri asas agak memuaskan tetapi masih ada aspek yang belum dinilai sepenuhnya. Teruskan pemerhatian berpandukan Sistem 30 Hari.",
                    0xFF2563EB
                )
                else -> Triple(
                    "Dalam Proses Penilaian",
                    "Sila lengkapkan lebih banyak semakan checklist dan pemerhatian tindakan harian untuk mendapatkan analisis yang tepat.",
                    0xFF6B7280
                )
            }

            return CandidateAnalysis(
                candidateId = candidateId,
                totalCriteria = total,
                evaluatedCount = ratedCount,
                greenCount = greenCount,
                cautionCount = cautionCount,
                redCount = redCount,
                overallPercentage = overallPercentage,
                aspectScores = aspectScoreMap,
                verdictTitle = vTitle,
                verdictRecommendation = vRec,
                verdictColorHex = vColor
            )
        }
    }
}

package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class ChecklistRating(val score: Int, val label: String) {
    UNRATED(0, "Belum Dinilai"),
    RED_FLAG(-2, "Red Flag (Amaran)"),
    CAUTION(1, "Ragu / Perlu Siasat"),
    GREEN_FLAG(3, "Green Flag (Positif)")
}

@Entity(tableName = "candidates")
data class Candidate(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val age: Int = 30,
    val occupation: String = "",
    val maritalStatus: String = "Bujang", // Bujang, Duda (Tiada Anak), Duda (Ada Anak)
    val durationKnown: String = "Baru Kenal", // Baru Kenal, 1 Bulan, 3 Bulan, dsb.
    val bio: String = "",
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "checklist_entries", primaryKeys = ["candidateId", "criterionId"])
data class ChecklistEntry(
    val candidateId: Long,
    val criterionId: String,
    val rating: ChecklistRating = ChecklistRating.UNRATED,
    val note: String = "",
    val updatedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "journal_entries")
data class JournalEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val candidateId: Long,
    val dateString: String,
    val title: String,
    val observedAction: String, // Tindakan sebenar diperhatikan
    val wordsSaid: String,      // Janji / kata-kata manis
    val isConsistent: Boolean,  // Konsisten tindakan vs kata-kata
    val rating: ChecklistRating = ChecklistRating.GREEN_FLAG,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "thirty_day_progress", primaryKeys = ["candidateId", "dayNumber"])
data class ThirtyDayProgress(
    val candidateId: Long,
    val dayNumber: Int,
    val isCompleted: Boolean = false,
    val userNotes: String = "",
    val completedAt: Long = 0L
)

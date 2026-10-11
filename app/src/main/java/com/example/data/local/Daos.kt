package com.example.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.Candidate
import com.example.data.model.ChecklistEntry
import com.example.data.model.JournalEntry
import com.example.data.model.ThirtyDayProgress
import kotlinx.coroutines.flow.Flow

@Dao
interface CandidateDao {
    @Query("SELECT * FROM candidates ORDER BY createdAt DESC")
    fun getAllCandidates(): Flow<List<Candidate>>

    @Query("SELECT * FROM candidates WHERE id = :id LIMIT 1")
    fun getCandidateById(id: Long): Flow<Candidate?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCandidate(candidate: Candidate): Long

    @Update
    suspend fun updateCandidate(candidate: Candidate)

    @Delete
    suspend fun deleteCandidate(candidate: Candidate)
}

@Dao
interface ChecklistDao {
    @Query("SELECT * FROM checklist_entries WHERE candidateId = :candidateId")
    fun getEntriesForCandidate(candidateId: Long): Flow<List<ChecklistEntry>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateEntry(entry: ChecklistEntry)

    @Query("DELETE FROM checklist_entries WHERE candidateId = :candidateId")
    suspend fun deleteEntriesForCandidate(candidateId: Long)
}

@Dao
interface JournalDao {
    @Query("SELECT * FROM journal_entries WHERE candidateId = :candidateId ORDER BY timestamp DESC")
    fun getJournalsForCandidate(candidateId: Long): Flow<List<JournalEntry>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJournal(entry: JournalEntry): Long

    @Delete
    suspend fun deleteJournal(entry: JournalEntry)

    @Query("DELETE FROM journal_entries WHERE candidateId = :candidateId")
    suspend fun deleteJournalsForCandidate(candidateId: Long)
}

@Dao
interface ThirtyDayProgressDao {
    @Query("SELECT * FROM thirty_day_progress WHERE candidateId = :candidateId")
    fun getProgressForCandidate(candidateId: Long): Flow<List<ThirtyDayProgress>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateProgress(progress: ThirtyDayProgress)

    @Query("DELETE FROM thirty_day_progress WHERE candidateId = :candidateId")
    suspend fun deleteProgressForCandidate(candidateId: Long)
}

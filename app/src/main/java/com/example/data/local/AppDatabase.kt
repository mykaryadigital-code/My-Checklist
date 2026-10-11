package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.data.model.Candidate
import com.example.data.model.ChecklistEntry
import com.example.data.model.ChecklistRating
import com.example.data.model.JournalEntry
import com.example.data.model.ThirtyDayProgress

class Converters {
    @TypeConverter
    fun fromRating(rating: ChecklistRating): String = rating.name

    @TypeConverter
    fun toRating(value: String): ChecklistRating {
        return try {
            ChecklistRating.valueOf(value)
        } catch (_: Exception) {
            ChecklistRating.UNRATED
        }
    }
}

@Database(
    entities = [
        Candidate::class,
        ChecklistEntry::class,
        JournalEntry::class,
        ThirtyDayProgress::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun candidateDao(): CandidateDao
    abstract fun checklistDao(): ChecklistDao
    abstract fun journalDao(): JournalDao
    abstract fun thirtyDayProgressDao(): ThirtyDayProgressDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "bakal_suami_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

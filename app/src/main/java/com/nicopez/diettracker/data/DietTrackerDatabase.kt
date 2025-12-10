package com.nicopez.diettracker.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nicopez.diettracker.data.dao.FastingSessionDao
import com.nicopez.diettracker.data.dao.MealDao
import com.nicopez.diettracker.data.dao.WeightEntryDao
import com.nicopez.diettracker.data.entity.FastingSession
import com.nicopez.diettracker.data.entity.Meal
import com.nicopez.diettracker.data.entity.WeightEntry

@Database(
    entities = [Meal::class, FastingSession::class, WeightEntry::class],
    version = 1,
    exportSchema = false
)
abstract class DietTrackerDatabase : RoomDatabase() {
    abstract fun mealDao(): MealDao
    abstract fun fastingSessionDao(): FastingSessionDao
    abstract fun weightEntryDao(): WeightEntryDao

    companion object {
        @Volatile
        private var INSTANCE: DietTrackerDatabase? = null

        fun getDatabase(context: Context): DietTrackerDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DietTrackerDatabase::class.java,
                    "diet_tracker_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

package com.nicopez.diettracker

import android.app.Application
import com.nicopez.diettracker.data.DietTrackerDatabase
import com.nicopez.diettracker.data.repository.DietRepository

class DietTrackerApplication : Application() {
    private val database by lazy { DietTrackerDatabase.getDatabase(this) }
    val repository by lazy {
        DietRepository(
            database.mealDao(),
            database.fastingSessionDao(),
            database.weightEntryDao()
        )
    }
}

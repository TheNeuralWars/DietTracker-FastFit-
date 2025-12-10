package com.nicopez.diettracker.data.repository

import com.nicopez.diettracker.data.dao.FastingSessionDao
import com.nicopez.diettracker.data.dao.MealDao
import com.nicopez.diettracker.data.dao.WeightEntryDao
import com.nicopez.diettracker.data.entity.FastingSession
import com.nicopez.diettracker.data.entity.Meal
import com.nicopez.diettracker.data.entity.WeightEntry
import kotlinx.coroutines.flow.Flow

class DietRepository(
    private val mealDao: MealDao,
    private val fastingSessionDao: FastingSessionDao,
    private val weightEntryDao: WeightEntryDao
) {
    // Meal operations
    fun getAllMeals(): Flow<List<Meal>> = mealDao.getAllMeals()
    
    suspend fun getMealById(id: Long): Meal? = mealDao.getMealById(id)
    
    fun getMealsByDateRange(startTime: Long, endTime: Long): Flow<List<Meal>> =
        mealDao.getMealsByDateRange(startTime, endTime)
    
    suspend fun insertMeal(meal: Meal): Long = mealDao.insertMeal(meal)
    
    suspend fun updateMeal(meal: Meal) = mealDao.updateMeal(meal)
    
    suspend fun deleteMeal(meal: Meal) = mealDao.deleteMeal(meal)
    
    suspend fun getTotalCaloriesInRange(startTime: Long, endTime: Long): Int =
        mealDao.getTotalCaloriesInRange(startTime, endTime) ?: 0
    
    // Fasting operations
    fun getAllFastingSessions(): Flow<List<FastingSession>> =
        fastingSessionDao.getAllFastingSessions()
    
    fun getActiveFastingSession(): Flow<FastingSession?> =
        fastingSessionDao.getActiveFastingSession()
    
    suspend fun getFastingSessionById(id: Long): FastingSession? =
        fastingSessionDao.getFastingSessionById(id)
    
    suspend fun insertFastingSession(session: FastingSession): Long =
        fastingSessionDao.insertFastingSession(session)
    
    suspend fun updateFastingSession(session: FastingSession) =
        fastingSessionDao.updateFastingSession(session)
    
    suspend fun deleteFastingSession(session: FastingSession) =
        fastingSessionDao.deleteFastingSession(session)
    
    // Weight operations
    fun getAllWeightEntries(): Flow<List<WeightEntry>> =
        weightEntryDao.getAllWeightEntries()
    
    fun getWeightEntriesByDateRange(startTime: Long, endTime: Long): Flow<List<WeightEntry>> =
        weightEntryDao.getWeightEntriesByDateRange(startTime, endTime)
    
    suspend fun getLatestWeightEntry(): WeightEntry? =
        weightEntryDao.getLatestWeightEntry()
    
    suspend fun insertWeightEntry(entry: WeightEntry): Long =
        weightEntryDao.insertWeightEntry(entry)
    
    suspend fun updateWeightEntry(entry: WeightEntry) =
        weightEntryDao.updateWeightEntry(entry)
    
    suspend fun deleteWeightEntry(entry: WeightEntry) =
        weightEntryDao.deleteWeightEntry(entry)
}

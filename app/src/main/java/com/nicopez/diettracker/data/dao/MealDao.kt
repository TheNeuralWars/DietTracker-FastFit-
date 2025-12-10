package com.nicopez.diettracker.data.dao

import androidx.room.*
import com.nicopez.diettracker.data.entity.Meal
import kotlinx.coroutines.flow.Flow

@Dao
interface MealDao {
    @Query("SELECT * FROM meals ORDER BY timestamp DESC")
    fun getAllMeals(): Flow<List<Meal>>
    
    @Query("SELECT * FROM meals WHERE id = :id")
    suspend fun getMealById(id: Long): Meal?
    
    @Query("SELECT * FROM meals WHERE timestamp >= :startTime AND timestamp <= :endTime ORDER BY timestamp DESC")
    fun getMealsByDateRange(startTime: Long, endTime: Long): Flow<List<Meal>>
    
    @Insert
    suspend fun insertMeal(meal: Meal): Long
    
    @Update
    suspend fun updateMeal(meal: Meal)
    
    @Delete
    suspend fun deleteMeal(meal: Meal)
    
    @Query("SELECT SUM(calories) FROM meals WHERE timestamp >= :startTime AND timestamp <= :endTime")
    suspend fun getTotalCaloriesInRange(startTime: Long, endTime: Long): Int?
}

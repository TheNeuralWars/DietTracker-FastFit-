package com.nicopez.diettracker.data.dao

import androidx.room.*
import com.nicopez.diettracker.data.entity.WeightEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface WeightEntryDao {
    @Query("SELECT * FROM weight_entries ORDER BY timestamp DESC")
    fun getAllWeightEntries(): Flow<List<WeightEntry>>
    
    @Query("SELECT * FROM weight_entries WHERE timestamp >= :startTime AND timestamp <= :endTime ORDER BY timestamp ASC")
    fun getWeightEntriesByDateRange(startTime: Long, endTime: Long): Flow<List<WeightEntry>>
    
    @Query("SELECT * FROM weight_entries ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLatestWeightEntry(): WeightEntry?
    
    @Insert
    suspend fun insertWeightEntry(entry: WeightEntry): Long
    
    @Update
    suspend fun updateWeightEntry(entry: WeightEntry)
    
    @Delete
    suspend fun deleteWeightEntry(entry: WeightEntry)
}

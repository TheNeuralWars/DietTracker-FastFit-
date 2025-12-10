package com.nicopez.diettracker.data.dao

import androidx.room.*
import com.nicopez.diettracker.data.entity.FastingSession
import kotlinx.coroutines.flow.Flow

@Dao
interface FastingSessionDao {
    @Query("SELECT * FROM fasting_sessions ORDER BY startTime DESC")
    fun getAllFastingSessions(): Flow<List<FastingSession>>
    
    @Query("SELECT * FROM fasting_sessions WHERE endTime IS NULL LIMIT 1")
    fun getActiveFastingSession(): Flow<FastingSession?>
    
    @Query("SELECT * FROM fasting_sessions WHERE id = :id")
    suspend fun getFastingSessionById(id: Long): FastingSession?
    
    @Insert
    suspend fun insertFastingSession(session: FastingSession): Long
    
    @Update
    suspend fun updateFastingSession(session: FastingSession)
    
    @Delete
    suspend fun deleteFastingSession(session: FastingSession)
}

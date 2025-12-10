package com.nicopez.diettracker.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fasting_sessions")
data class FastingSession(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val startTime: Long,
    val endTime: Long? = null, // null if fasting is currently active
    val notes: String = ""
) {
    fun isActive(): Boolean = endTime == null
    
    fun durationMillis(): Long = (endTime ?: System.currentTimeMillis()) - startTime
    
    fun durationHours(): Double = durationMillis() / (1000.0 * 60 * 60)
}

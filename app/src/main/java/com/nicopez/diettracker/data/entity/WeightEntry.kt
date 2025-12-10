package com.nicopez.diettracker.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weight_entries")
data class WeightEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val weightKg: Double,
    val timestamp: Long = System.currentTimeMillis()
)

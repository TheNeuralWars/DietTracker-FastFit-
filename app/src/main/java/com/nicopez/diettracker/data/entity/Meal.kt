package com.nicopez.diettracker.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meals")
data class Meal(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val calories: Int,
    val qualityRating: Int, // 1-5
    val timestamp: Long = System.currentTimeMillis()
)

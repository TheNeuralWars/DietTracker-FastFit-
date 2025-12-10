package com.nicopez.diettracker.ui.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    private val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    private val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    private val dateTimeFormat = SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault())
    
    fun formatDate(timestamp: Long): String {
        return dateFormat.format(Date(timestamp))
    }
    
    fun formatTime(timestamp: Long): String {
        return timeFormat.format(Date(timestamp))
    }
    
    fun formatDateTime(timestamp: Long): String {
        return dateTimeFormat.format(Date(timestamp))
    }
    
    fun formatDuration(durationMillis: Long): String {
        val hours = durationMillis / (1000 * 60 * 60)
        val minutes = (durationMillis / (1000 * 60)) % 60
        
        return when {
            hours >= 24 -> {
                val days = hours / 24
                val remainingHours = hours % 24
                "${days}d ${remainingHours}h ${minutes}m"
            }
            hours > 0 -> "${hours}h ${minutes}m"
            else -> "${minutes}m"
        }
    }
}

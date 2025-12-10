package com.nicopez.diettracker.ui.utils

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import com.nicopez.diettracker.data.entity.FastingSession
import com.nicopez.diettracker.data.entity.Meal
import com.nicopez.diettracker.data.entity.WeightEntry
import java.io.File
import java.io.FileWriter

object CsvExporter {
    fun exportData(
        context: Context,
        meals: List<Meal>,
        fastingSessions: List<FastingSession>,
        weightEntries: List<WeightEntry>
    ): Boolean {
        return try {
            val cacheDir = context.cacheDir
            val file = File(cacheDir, "diet_tracker_export_${System.currentTimeMillis()}.csv")
            
            FileWriter(file).use { writer ->
                // Export meals
                writer.append("MEALS\n")
                writer.append("Timestamp,Name,Calories,Quality Rating\n")
                meals.forEach { meal ->
                    writer.append("${DateUtils.formatDateTime(meal.timestamp)},${meal.name},${meal.calories},${meal.qualityRating}\n")
                }
                
                writer.append("\n")
                
                // Export fasting sessions
                writer.append("FASTING SESSIONS\n")
                writer.append("Start Time,End Time,Duration (hours),Status\n")
                fastingSessions.forEach { session ->
                    val endTime = if (session.endTime != null) DateUtils.formatDateTime(session.endTime) else "Active"
                    val duration = String.format("%.1f", session.durationHours())
                    writer.append("${DateUtils.formatDateTime(session.startTime)},$endTime,$duration,${if (session.isActive()) "Active" else "Completed"}\n")
                }
                
                writer.append("\n")
                
                // Export weight entries
                writer.append("WEIGHT ENTRIES\n")
                writer.append("Timestamp,Weight (kg)\n")
                weightEntries.forEach { entry ->
                    writer.append("${DateUtils.formatDateTime(entry.timestamp)},${entry.weightKg}\n")
                }
            }
            
            // Share the file
            val uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                file
            )
            
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/csv"
                putExtra(Intent.EXTRA_STREAM, uri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            
            context.startActivity(Intent.createChooser(shareIntent, "Export Diet Data"))
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}

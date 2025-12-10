package com.nicopez.diettracker.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.nicopez.diettracker.ui.utils.CsvExporter
import com.nicopez.diettracker.ui.viewmodel.FastingViewModel
import com.nicopez.diettracker.ui.viewmodel.MealViewModel
import com.nicopez.diettracker.ui.viewmodel.WeightViewModel
import kotlinx.coroutines.launch

@Composable
fun TrendsScreen(
    mealViewModel: MealViewModel,
    fastingViewModel: FastingViewModel,
    weightViewModel: WeightViewModel
) {
    val meals by mealViewModel.meals.collectAsState()
    val fastingSessions by fastingViewModel.fastingSessions.collectAsState()
    val weightEntries by weightViewModel.weightEntries.collectAsState()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var exportMessage by remember { mutableStateOf<String?>(null) }
    
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Statistics",
                style = MaterialTheme.typography.headlineMedium
            )
        }
        
        item {
            StatisticsCard(
                title = "Total Meals",
                value = meals.size.toString()
            )
        }
        
        item {
            val totalCalories = meals.sumOf { it.calories }
            val avgCalories = if (meals.isNotEmpty()) totalCalories / meals.size else 0
            StatisticsCard(
                title = "Total / Avg Calories",
                value = "$totalCalories / $avgCalories cal"
            )
        }
        
        item {
            val avgQuality = if (meals.isNotEmpty()) {
                String.format("%.1f", meals.map { it.qualityRating }.average())
            } else "N/A"
            StatisticsCard(
                title = "Average Meal Quality",
                value = "$avgQuality / 5"
            )
        }
        
        item {
            StatisticsCard(
                title = "Total Fasting Sessions",
                value = fastingSessions.filter { !it.isActive() }.size.toString()
            )
        }
        
        item {
            val completedFasts = fastingSessions.filter { !it.isActive() }
            val avgFastDuration = if (completedFasts.isNotEmpty()) {
                String.format("%.1f", completedFasts.map { it.durationHours() }.average())
            } else "N/A"
            StatisticsCard(
                title = "Avg Fast Duration",
                value = "$avgFastDuration hours"
            )
        }
        
        item {
            StatisticsCard(
                title = "Weight Entries",
                value = weightEntries.size.toString()
            )
        }
        
        item {
            val weightChange = if (weightEntries.size >= 2) {
                val oldest = weightEntries.last().weightKg
                val newest = weightEntries.first().weightKg
                val change = newest - oldest
                String.format("%+.1f kg", change)
            } else "N/A"
            StatisticsCard(
                title = "Weight Change",
                value = weightChange
            )
        }
        
        item {
            Button(
                onClick = {
                    scope.launch {
                        val success = CsvExporter.exportData(
                            context,
                            meals,
                            fastingSessions,
                            weightEntries
                        )
                        exportMessage = if (success) "Export successful" else "Export failed"
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Export All Data to CSV")
            }
            
            exportMessage?.let { message ->
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (message.contains("success")) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.error
                    },
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}

@Composable
fun StatisticsCard(title: String, value: String) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}

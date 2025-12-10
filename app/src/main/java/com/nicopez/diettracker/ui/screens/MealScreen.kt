package com.nicopez.diettracker.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.nicopez.diettracker.data.entity.Meal
import com.nicopez.diettracker.ui.utils.DateUtils
import com.nicopez.diettracker.ui.viewmodel.MealViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealScreen(viewModel: MealViewModel) {
    val meals by viewModel.meals.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add Meal")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(meals) { meal ->
                MealItem(
                    meal = meal,
                    onDelete = { viewModel.deleteMeal(meal) }
                )
            }
        }
        
        if (showDialog) {
            AddMealDialog(
                onDismiss = { showDialog = false },
                onSave = { name, calories, quality ->
                    viewModel.addMeal(name, calories, quality)
                    showDialog = false
                }
            )
        }
    }
}

@Composable
fun MealItem(meal: Meal, onDelete: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = meal.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "${meal.calories} cal • Quality: ${meal.qualityRating}/5",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = DateUtils.formatDateTime(meal.timestamp),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMealDialog(
    onDismiss: () -> Unit,
    onSave: (String, Int, Int) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var calories by remember { mutableStateOf("") }
    var quality by remember { mutableStateOf("3") }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Meal") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Meal Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = calories,
                    onValueChange = { calories = it },
                    label = { Text("Calories") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = quality,
                    onValueChange = { 
                        if (it.isEmpty() || (it.toIntOrNull()?.let { q -> q in 1..5 } == true)) {
                            quality = it
                        }
                    },
                    label = { Text("Quality (1-5)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val cal = calories.toIntOrNull() ?: 0
                    val qual = quality.toIntOrNull()?.coerceIn(1, 5) ?: 3
                    if (name.isNotBlank() && cal > 0) {
                        onSave(name, cal, qual)
                    }
                }
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

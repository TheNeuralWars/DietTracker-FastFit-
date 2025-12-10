package com.nicopez.diettracker.ui.screens

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
import com.nicopez.diettracker.data.entity.WeightEntry
import com.nicopez.diettracker.ui.utils.DateUtils
import com.nicopez.diettracker.ui.viewmodel.WeightViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeightScreen(viewModel: WeightViewModel) {
    val weightEntries by viewModel.weightEntries.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add Weight")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // Show current weight at top if available
            if (weightEntries.isNotEmpty()) {
                val latest = weightEntries.first()
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Current Weight",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = "${latest.weightKg} kg",
                            style = MaterialTheme.typography.displayMedium
                        )
                        Text(
                            text = DateUtils.formatDateTime(latest.timestamp),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
            
            Text(
                text = "Weight History",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(weightEntries) { entry ->
                    WeightEntryItem(
                        entry = entry,
                        onDelete = { viewModel.deleteWeightEntry(entry) }
                    )
                }
            }
        }
        
        if (showDialog) {
            AddWeightDialog(
                onDismiss = { showDialog = false },
                onSave = { weight ->
                    viewModel.addWeightEntry(weight)
                    showDialog = false
                }
            )
        }
    }
}

@Composable
fun WeightEntryItem(
    entry: WeightEntry,
    onDelete: () -> Unit
) {
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
                    text = "${entry.weightKg} kg",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = DateUtils.formatDateTime(entry.timestamp),
                    style = MaterialTheme.typography.bodyMedium,
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
fun AddWeightDialog(
    onDismiss: () -> Unit,
    onSave: (Double) -> Unit
) {
    var weight by remember { mutableStateOf("") }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Weight") },
        text = {
            OutlinedTextField(
                value = weight,
                onValueChange = { weight = it },
                label = { Text("Weight (kg)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val w = weight.toDoubleOrNull()
                    if (w != null && w > 0) {
                        onSave(w)
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

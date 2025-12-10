package com.nicopez.diettracker.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nicopez.diettracker.data.entity.FastingSession
import com.nicopez.diettracker.ui.utils.DateUtils
import com.nicopez.diettracker.ui.viewmodel.FastingViewModel
import kotlinx.coroutines.delay

@Composable
fun FastingScreen(viewModel: FastingViewModel) {
    val fastingSessions by viewModel.fastingSessions.collectAsState()
    val activeFastingSession by viewModel.activeFastingSession.collectAsState()
    
    // Timer update for active session
    var currentTime by remember { mutableStateOf(System.currentTimeMillis()) }
    
    LaunchedEffect(activeFastingSession) {
        while (activeFastingSession != null) {
            delay(1000) // Update every second
            currentTime = System.currentTimeMillis()
        }
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Active fasting card
        if (activeFastingSession != null) {
            ActiveFastingCard(
                session = activeFastingSession!!,
                currentTime = currentTime,
                onStopFast = { viewModel.stopFast(it) }
            )
        } else {
            Button(
                onClick = { viewModel.startFast() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
            ) {
                Text("Start Fast", style = MaterialTheme.typography.titleLarge)
            }
        }
        
        Text(
            text = "Fasting History",
            style = MaterialTheme.typography.titleMedium
        )
        
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(fastingSessions.filter { !it.isActive() }) { session ->
                FastingSessionItem(
                    session = session,
                    onDelete = { viewModel.deleteFastingSession(session) }
                )
            }
        }
    }
}

@Composable
fun ActiveFastingCard(
    session: FastingSession,
    currentTime: Long,
    onStopFast: (FastingSession) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Fasting In Progress",
                style = MaterialTheme.typography.titleLarge
            )
            
            Text(
                text = DateUtils.formatDuration(currentTime - session.startTime),
                style = MaterialTheme.typography.displayMedium
            )
            
            Text(
                text = "Started: ${DateUtils.formatDateTime(session.startTime)}",
                style = MaterialTheme.typography.bodyMedium
            )
            
            Button(
                onClick = { onStopFast(session) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Stop Fast")
            }
        }
    }
}

@Composable
fun FastingSessionItem(
    session: FastingSession,
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
                    text = DateUtils.formatDuration(session.durationMillis()),
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Started: ${DateUtils.formatDateTime(session.startTime)}",
                    style = MaterialTheme.typography.bodyMedium
                )
                if (session.endTime != null) {
                    Text(
                        text = "Ended: ${DateUtils.formatDateTime(session.endTime)}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}

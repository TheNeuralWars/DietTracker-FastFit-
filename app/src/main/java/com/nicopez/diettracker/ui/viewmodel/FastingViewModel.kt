package com.nicopez.diettracker.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.nicopez.diettracker.data.entity.FastingSession
import com.nicopez.diettracker.data.repository.DietRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FastingViewModel(private val repository: DietRepository) : ViewModel() {
    val fastingSessions: StateFlow<List<FastingSession>> = repository.getAllFastingSessions()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    
    val activeFastingSession: StateFlow<FastingSession?> = repository.getActiveFastingSession()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)
    
    fun startFast() {
        viewModelScope.launch {
            repository.insertFastingSession(
                FastingSession(
                    startTime = System.currentTimeMillis()
                )
            )
        }
    }
    
    fun stopFast(session: FastingSession) {
        viewModelScope.launch {
            repository.updateFastingSession(
                session.copy(endTime = System.currentTimeMillis())
            )
        }
    }
    
    fun updateFastingSession(session: FastingSession) {
        viewModelScope.launch {
            repository.updateFastingSession(session)
        }
    }
    
    fun deleteFastingSession(session: FastingSession) {
        viewModelScope.launch {
            repository.deleteFastingSession(session)
        }
    }
    
    companion object {
        fun provideFactory(repository: DietRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return FastingViewModel(repository) as T
                }
            }
    }
}

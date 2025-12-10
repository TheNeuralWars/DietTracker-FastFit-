package com.nicopez.diettracker.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.nicopez.diettracker.data.entity.WeightEntry
import com.nicopez.diettracker.data.repository.DietRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class WeightViewModel(private val repository: DietRepository) : ViewModel() {
    val weightEntries: StateFlow<List<WeightEntry>> = repository.getAllWeightEntries()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    
    fun addWeightEntry(weightKg: Double) {
        viewModelScope.launch {
            repository.insertWeightEntry(
                WeightEntry(weightKg = weightKg)
            )
        }
    }
    
    fun updateWeightEntry(entry: WeightEntry) {
        viewModelScope.launch {
            repository.updateWeightEntry(entry)
        }
    }
    
    fun deleteWeightEntry(entry: WeightEntry) {
        viewModelScope.launch {
            repository.deleteWeightEntry(entry)
        }
    }
    
    companion object {
        fun provideFactory(repository: DietRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return WeightViewModel(repository) as T
                }
            }
    }
}

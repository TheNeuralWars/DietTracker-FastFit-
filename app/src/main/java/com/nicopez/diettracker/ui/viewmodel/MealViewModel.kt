package com.nicopez.diettracker.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.nicopez.diettracker.data.entity.Meal
import com.nicopez.diettracker.data.repository.DietRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MealViewModel(private val repository: DietRepository) : ViewModel() {
    val meals: StateFlow<List<Meal>> = repository.getAllMeals()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    
    fun addMeal(name: String, calories: Int, qualityRating: Int) {
        viewModelScope.launch {
            repository.insertMeal(
                Meal(
                    name = name,
                    calories = calories,
                    qualityRating = qualityRating
                )
            )
        }
    }
    
    fun updateMeal(meal: Meal) {
        viewModelScope.launch {
            repository.updateMeal(meal)
        }
    }
    
    fun deleteMeal(meal: Meal) {
        viewModelScope.launch {
            repository.deleteMeal(meal)
        }
    }
    
    companion object {
        fun provideFactory(repository: DietRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return MealViewModel(repository) as T
                }
            }
    }
}

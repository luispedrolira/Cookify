package com.luispedrolira.cookify.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luispedrolira.cookify.data.model.Recipe
import com.luispedrolira.cookify.data.repository.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class RecipeViewModel(
    private val repository: RecipeRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(RecipeUiState())
    val uiState: StateFlow<RecipeUiState> = _uiState.asStateFlow()

    val allRecipes: StateFlow<List<Recipe>> = repository.allRecipes
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val favoriteRecipes: StateFlow<List<Recipe>> = repository.favoriteRecipes
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private var currentFiltering = RecipeFilter.ALL

    fun loadRecipe(id: Long) {
        viewModelScope.launch {
            val recipe = repository.getRecipe(id)
            _uiState.update { it.copy(
                currentRecipe = recipe,
                isLoading = false
            )}
        }
    }

    fun addRecipe(title: String, description: String, preparationTime: Int, imageUri: String?) {
        viewModelScope.launch {
            try {
                repository.addRecipe(title, description, preparationTime, imageUri)
                _uiState.update { it.copy(isSuccess = true) }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }

    fun toggleFavorite(id: Any?, isFavorite: Any?) {
        viewModelScope.launch {
            repository.toggleFavorite(id, isFavorite)
        }
    }

    fun setFilter(filter: RecipeFilter) {
        currentFiltering = filter
    }
}

data class RecipeUiState(
    val currentRecipe: Recipe? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false
)

enum class RecipeFilter {
    ALL, FAVORITES, PREPARATION_TIME
}
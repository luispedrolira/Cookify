package com.luispedrolira.cookify.data.repository

import com.luispedrolira.cookify.data.local.dao.RecipeDao
import com.luispedrolira.cookify.data.model.Recipe
import kotlinx.coroutines.flow.Flow

class RecipeRepository (
    private val recipeDao: RecipeDao
) {
    val allRecipes: Flow<List<Recipe>> = recipeDao.getAllRecipes()
    val favoriteRecipes: Flow<List<Recipe>> = recipeDao.getFavoriteRecipes()
    val recipesByTime: Flow<List<Recipe>> = recipeDao.getRecipesByPreparationTime()

    suspend fun addRecipe(
        title: String,
        description: String,
        preparationTime: Int,
        imageUri: String?
    ): Long {
        val recipe = Recipe(
            title = title,
            description = description,
            preparationTime = preparationTime,
            imageUri = imageUri
        )
        return recipeDao.insertRecipe(recipe)
    }

    suspend fun getRecipe(id: Long): Recipe? {
        return recipeDao.getRecipeById(id)
    }

    suspend fun toggleFavorite(id: Any?, isFavorite: Any?) {
        recipeDao.updateFavoriteStatus(id, isFavorite)
    }

    suspend fun deleteRecipe(recipe: Recipe) {
        recipeDao.deleteRecipe(recipe)
    }
}
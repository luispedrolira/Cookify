package com.luispedrolira.cookify

import android.app.Application
import com.luispedrolira.cookify.data.local.entities.AppDatabase
import com.luispedrolira.cookify.data.repository.RecipeRepository

class CookifyApplication : Application() {
    val database: AppDatabase by lazy {
        AppDatabase.getDatabase(this)
    }

    val repository: RecipeRepository by lazy {
        RecipeRepository(database.recipeDao())
    }
}
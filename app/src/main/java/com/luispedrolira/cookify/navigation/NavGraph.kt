package com.luispedrolira.cookify.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.luispedrolira.cookify.ui.screens.DashboardScreen
import com.luispedrolira.cookify.ui.screens.GetStartedScreen
import com.luispedrolira.cookify.viewmodel.RecipeViewModel
import com.luispedrolira.cookify.viewmodel.UserViewModel

sealed class Screen(val route: String) {
    object GetStarted : Screen("get_started")
    object Login : Screen("login")
    object Dashboard : Screen("dashboard")
    object RecipeDetail : Screen("recipe/{recipeId}") {
        fun createRoute(recipeId: Long) = "recipe/$recipeId"
    }
    object AddRecipe : Screen("add_recipe")
    object Profile : Screen("profile")
}

@Composable
fun CookifyNavigation(
    navController: NavHostController = rememberNavController(),
    userViewModel: UserViewModel,
    recipeViewModel: RecipeViewModel
) {
    val userState by userViewModel.userData.collectAsState()
    val isFirstTime by userViewModel.isFirstTimeUser.collectAsState()

    NavHost(
        navController = navController,
        startDestination = when {
            isFirstTime -> Screen.GetStarted.route
            !userState.isLoggedIn -> Screen.Login.route
            else -> Screen.Dashboard.route
        }
    ) {
        composable(Screen.GetStarted.route) {
            GetStartedScreen(
                onGetStarted = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.GetStarted.route) { inclusive = true }
                    }
                }
            )
        }

        /*composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = { username, email ->
                    userViewModel.login(username, email)
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }*/

        composable(Screen.Dashboard.route) {
            val recipes by recipeViewModel.allRecipes.collectAsState()
            DashboardScreen(
                recipes = recipes,
                onAddRecipeClick = { navController.navigate(Screen.AddRecipe.route) },
                onRecipeClick = { recipeId ->
                    navController.navigate(Screen.RecipeDetail.createRoute(recipeId))
                },
                onToggleFavorite = { recipeId, isFavorite ->
                    recipeViewModel.toggleFavorite(recipeId, isFavorite)
                }
            )
        }

        /*composable(
            route = Screen.RecipeDetail.route,
            arguments = listOf(navArgument("recipeId") { type = NavType.LongType })
        ) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getLong("recipeId") ?: return@composable
            recipeViewModel.loadRecipe(recipeId)

            val uiState by recipeViewModel.uiState.collectAsState()
            RecipeDetailScreen(
                recipe = uiState.currentRecipe,
                isLoading = uiState.isLoading,
                onBackClick = { navController.popBackStack() },
                onToggleFavorite = { id, isFavorite ->
                    recipeViewModel.toggleFavorite(id, isFavorite)
                }
            )
        }

        composable(Screen.AddRecipe.route) {
            val uiState by recipeViewModel.uiState.collectAsState()
            AddRecipeScreen(
                onRecipeAdded = { title, description, time, imageUri ->
                    recipeViewModel.addRecipe(title, description, time, imageUri)
                },
                onBackClick = { navController.popBackStack() },
                isSuccess = uiState.isSuccess,
                error = uiState.error
            )
        }

        composable(Screen.Profile.route) {
            val userData by userViewModel.userData.collectAsState()
            ProfileScreen(
                userData = userData,
                onLogout = {
                    userViewModel.logout()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0)
                    }
                }
            )
        }*/
    }
}
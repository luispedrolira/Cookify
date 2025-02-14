package com.luispedrolira.cookify.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.luispedrolira.cookify.data.model.Recipe
import com.luispedrolira.cookify.ui.theme.BackgroundColor
import com.luispedrolira.cookify.ui.theme.PrimaryColor
import com.luispedrolira.cookify.ui.theme.PrimaryContainerColor
import com.luispedrolira.cookify.ui.theme.TitleCard
import java.util.Calendar

@Composable
fun DashboardScreen(
    recipes: List<Recipe>,
    onAddRecipeClick: () -> Unit,
    onRecipeClick: (Long) -> Unit,
    onToggleFavorite: (Any?, Any?) -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundColor)
    ) {
        if (recipes.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "¡No hay recetas agregadas!",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "¿Quieres empezar a cocinar como un PRO?",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = onAddRecipeClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryColor
                    ),
                    shape = RoundedCornerShape(8.dp),
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Agregar receta")
                }
            }
        } else {
            // Populated State
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                // Greeting
                val greeting = remember {
                    when (Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) {
                        in 0..11 -> "Buenos dias"
                        in 12..18 -> "Buenas tardes"
                        else -> "Buenas noches"
                    }
                }

                Text(
                    text = greeting,
                    modifier = Modifier.padding(top = 24.dp, bottom = 16.dp),
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontFamily = FontFamily.Serif,
                        lineHeight = 36.sp
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )

                // Filter Buttons
                Row(
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState())
                        .padding(vertical = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilterChip(
                        selected = false,
                        onClick = { /* Handle favorites filter */ },
                        label = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.FavoriteBorder,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Favoritas")
                            }
                        }
                    )

                    FilterChip(
                        selected = false,
                        onClick = { /* Handle time filter */ },
                        label = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Outlined.AccessTime,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Tiempo de preparación")
                            }
                        }
                    )
                }

                // Recipe List
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(vertical = 16.dp)
                ) {
                    items(recipes) { recipe ->
                        RecipeCard(
                            recipe = recipe,
                            onRecipeClick = { onRecipeClick(recipe.id) },
                            onFavoriteClick = { onToggleFavorite(recipe.id) }
                        )
                    }
                }
            }

            // FAB
            FloatingActionButton(
                onClick = onAddRecipeClick,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.End),
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Recipe"
                )
            }
        }
    }
}

fun onToggleFavorite(id: Long) {

}

@Composable
fun FloatingActionButton(onClick: () -> Unit, modifier: Modifier, containerColor: Color, content: @Composable () -> Unit) {

}

@Composable
private fun RecipeCard(
    recipe: Recipe,
    onRecipeClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onRecipeClick),
        shape = RoundedCornerShape(12.dp),
    ) {
        Column(
            modifier = Modifier.background(PrimaryContainerColor)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                // Recipe Image
                AsyncImage(
                    model = recipe.imageUri,
                    contentDescription = recipe.title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Favorite Button
                IconButton(
                    onClick = onFavoriteClick,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .background(
                            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f),
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        imageVector = if (recipe.isFavorite)
                            Icons.Filled.Favorite
                        else
                            Icons.Outlined.FavoriteBorder,
                        contentDescription = "Toggle Favorite",
                        tint = if (recipe.isFavorite)
                            PrimaryColor
                        else
                            MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text(
                    text = recipe.title,
                    fontFamily = TitleCard,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = recipe.description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Timer,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = PrimaryColor
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${recipe.preparationTime} min",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
            }
        }
    }
}


@Preview
@Composable
private fun PreviewDashboardScreen(){
    MaterialTheme {
        DashboardScreen(
            recipes = listOf(
                Recipe(
                    id = 1,
                    title = "Pancakes Nutella",
                    description = "Pancakes esponjosos rellenos de nutella",
                    preparationTime = 40,
                    isFavorite = false,
                    imageUri = null
                ),
                Recipe(
                    id = 2,
                    title = "Pasta Alfredo",
                    description = "Cremosa pasta alfredo de pollo",
                    preparationTime = 150,
                    isFavorite = true,
                    imageUri = null
                ),
                Recipe(
                    id = 3,
                    title = "Lasaña de Carne",
                    description = "Lasaña de 5 capas llenas de carne, queso y crema",
                    preparationTime = 120,
                    isFavorite = true,
                    imageUri = null
                )
            ),
            onAddRecipeClick = {},
            onRecipeClick = {},
            onToggleFavorite = { any: Any?, any1: Any? -> }
        )
    }
}

@Preview
@Composable
private fun PreviewEmptyDashboardScreen(){
    MaterialTheme {
        DashboardScreen(
            recipes = listOf(),
            onAddRecipeClick = {},
            onRecipeClick = {},
            onToggleFavorite = { any: Any?, any1: Any? -> }
        )
    }
}
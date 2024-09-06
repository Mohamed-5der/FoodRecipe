package com.khedr.firebaselogin.presention.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khedr.firebaselogin.data.model.Recipe
import com.khedr.firebaselogin.data.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteDbViewModel@Inject constructor(val repository: RecipeRepository): ViewModel() {


    private val _favRecipes = MutableStateFlow<List<Recipe>>(emptyList())
    val favRecipes: StateFlow<List<Recipe>> get() = _favRecipes

    private val _favoriteIds = MutableStateFlow<List<String>>(emptyList())
    val favoriteIds: StateFlow<List<String>> get() = _favoriteIds

    init {
        getAllFavRecipes()
    }

    private fun getAllFavRecipes() {
        viewModelScope.launch() {
            try {
                val recipes = repository.getAllFavRecipes()
                _favRecipes.value = recipes
                val ids = recipes.map { it.idMeal.orEmpty() }
                _favoriteIds.value = ids
                getAllFavRecipes()


            } catch (e: Exception) {
                _favRecipes.value = emptyList()
                _favoriteIds.value = emptyList()
                getAllFavRecipes()

            }
        }
    }

    fun deleteFavRecipe(idMeal: String) {
        viewModelScope.launch {
            try {
                repository.deleteFavRecipe(idMeal)
                getAllFavRecipes()
            }catch (e: Exception){
                Log.d("TAG", "deleteFavRecipe: ${e.message}")
            }

        }
    }

    fun addFavRecipe(recipe: Recipe) {
        viewModelScope.launch {
            try {
                repository.addFavRecipe(recipe)
                getAllFavRecipes()
            }catch (e: Exception){
                Log.d("TAG", "addFavRecipe: ${e.message}")
            }

        }
    }

    fun checkIsFavorite(idMeal: String): Boolean {
        val isFavorite = _favoriteIds.value.contains(idMeal)
        return isFavorite
    }
}

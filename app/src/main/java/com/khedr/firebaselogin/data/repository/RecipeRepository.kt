package com.khedr.firebaselogin.data.repository

import com.khedr.firebaselogin.data.local.RecipeDao
import com.khedr.firebaselogin.data.model.Recipe
import javax.inject.Inject
import javax.inject.Singleton

@Singleton

class RecipeRepository@Inject constructor ( val recipeDao: RecipeDao){
    suspend fun addFavRecipe(recipe: Recipe) {
        recipeDao.addFavRecipe(recipe)
    }
    suspend fun deleteFavRecipe(id: String) {
        recipeDao.deleteFavRecipeById(id)
    }

    suspend fun getAllFavRecipes(): List<Recipe> {
        return recipeDao.getAllFavRecipes()
    }

}
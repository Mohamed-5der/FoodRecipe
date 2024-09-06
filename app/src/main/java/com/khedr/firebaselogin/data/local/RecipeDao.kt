package com.khedr.firebaselogin.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.khedr.firebaselogin.data.model.Recipe
@Dao
interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavRecipe(recipe: Recipe)

    @Query("DELETE FROM recipe_tb WHERE idMeal = :idMeal")
    suspend fun deleteFavRecipeById(idMeal: String)
    @Query("SELECT * FROM recipe_tb ")
    suspend fun getAllFavRecipes(): List<Recipe>

}
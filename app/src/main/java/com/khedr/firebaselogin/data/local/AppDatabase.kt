package com.khedr.firebaselogin.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.khedr.firebaselogin.data.model.Recipe


@Database(entities = [Recipe::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
}
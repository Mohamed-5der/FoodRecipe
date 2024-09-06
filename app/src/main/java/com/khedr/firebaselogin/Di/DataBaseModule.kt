package com.khedr.firebaselogin.Di

import android.content.Context
import androidx.room.Room
import com.khedr.firebaselogin.data.local.AppDatabase
import com.khedr.firebaselogin.data.local.RecipeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideYourDao(appDatabase: AppDatabase): RecipeDao {
        return appDatabase.recipeDao() // Replace with your actual DAO method
    }
}
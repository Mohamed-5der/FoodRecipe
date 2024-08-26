package com.khedr.firebaselogin.data.repository

import com.khedr.firebaselogin.data.network.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkRepository @Inject constructor(val apiService: ApiService) {

    suspend fun getAllCategories() = apiService.getAllCategories()
    suspend fun getRandomMeal() = apiService.getRandomMeal()
    suspend fun getMealBySearch(searchMeal: String) = apiService.getMealsBySearch(searchMeal)
    suspend fun getMealByCategory(category: String) = apiService.getMealsByCategory(category)
    suspend fun getMealById(id: String) = apiService.getMealById(id)


}
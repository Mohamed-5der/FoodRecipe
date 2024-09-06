package com.khedr.firebaselogin.data.network

import com.khedr.firebaselogin.data.model.CategoriesResponse
import com.khedr.firebaselogin.data.model.Food
import com.khedr.firebaselogin.data.model.MealsByCategoryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("categories.php")
    suspend fun getAllCategories(): Response<CategoriesResponse>

    @GET("random.php")
    suspend fun getRandomMeal(): Response<Food>

    @GET("search.php")
    suspend fun getMealsBySearch(@Query("s") searchMeal: String): Response<Food>
    @GET("filter.php")
    suspend fun getMealsByCategory(@Query("c") category: String): Response<MealsByCategoryResponse>

    @GET("lookup.php")
    suspend fun getMealById(@Query("i") id: String): Response<Food>
}

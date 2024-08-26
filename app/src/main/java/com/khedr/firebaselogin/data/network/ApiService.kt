package com.khedr.firebaselogin.data.network

import androidx.room.Query
import com.khedr.firebaselogin.data.model.CategoriesResponse
import com.khedr.firebaselogin.data.model.Food
import com.khedr.firebaselogin.data.model.Meals
import retrofit2.http.GET

interface ApiService  {
    @GET("categories.php")
    suspend fun getAllCategories() :retrofit2.Response<CategoriesResponse>
    @GET("random.php")
    suspend fun getRandomMeal() :retrofit2.Response<Food>
    @GET("search.php")
    suspend fun getMealsBySearch(@retrofit2.http.Query("s") searchMeal: String):retrofit2.Response <Food>
    @GET("filter.php")
    suspend fun getMealsByCategory(@retrofit2.http.Query("c") category: String):retrofit2.Response <Meals>
    @GET("lookup.php")
    suspend fun getMealById(@retrofit2.http.Query("i") id: String):retrofit2.Response <Food>

}
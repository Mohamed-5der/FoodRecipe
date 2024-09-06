package com.khedr.firebaselogin.data.model

import com.google.gson.annotations.SerializedName


data class MealsByCategoryResponse(
    @SerializedName("meals") var mealsByCategory: List<MealsByCategory> = listOf()
)
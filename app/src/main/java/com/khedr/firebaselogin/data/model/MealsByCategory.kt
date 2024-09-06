package com.khedr.firebaselogin.data.model

import com.google.gson.annotations.SerializedName

data class MealsByCategory(

    @SerializedName("strMeal"      ) var strMeal      : String? = null,
    @SerializedName("strMealThumb" ) var strMealThumb : String? = null,
    @SerializedName("idMeal"       ) var idMeal       : String? = null
)

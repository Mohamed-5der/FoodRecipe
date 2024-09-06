package com.khedr.firebaselogin.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "recipe_tb")
data class Recipe(
    @PrimaryKey @SerializedName("strMeal") var strMeal: String,
    @SerializedName("strMealThumb") var strMealThumb: String? = null,
    @SerializedName("idMeal") var idMeal: String? = null,
    var isSelected: Boolean = false,
)

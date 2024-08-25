package com.khedr.firebaselogin.data.model
import com.google.gson.annotations.SerializedName


data class CategoriesResponse (

    @SerializedName("categories" ) var categories : ArrayList<Categories> = arrayListOf()

)

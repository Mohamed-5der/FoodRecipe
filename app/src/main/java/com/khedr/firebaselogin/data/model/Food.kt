package com.khedr.firebaselogin.data.model
import com.google.gson.annotations.SerializedName


data class Food (

    @SerializedName("meals" ) var meals : ArrayList<Meals> = arrayListOf()

)
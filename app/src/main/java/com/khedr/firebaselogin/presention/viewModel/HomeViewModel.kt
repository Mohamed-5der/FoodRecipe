package com.khedr.firebaselogin.presention.viewModel

import android.app.Application
import android.widget.Toast
import androidx.annotation.OptIn
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import com.khedr.firebaselogin.data.model.Categories
import com.khedr.firebaselogin.data.model.CategoriesResponse
import com.khedr.firebaselogin.data.model.Food
import com.khedr.firebaselogin.data.model.Meals
import com.khedr.firebaselogin.data.repository.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@HiltViewModel
class HomeViewModel@Inject constructor(val repository: NetworkRepository,val application: Application) : ViewModel() {
    private val _categories = MutableStateFlow<ArrayList<Categories>?>(null)
    val categories :StateFlow<ArrayList<Categories>?> get() = _categories
    private val _meals = MutableStateFlow<ArrayList<Meals>?>(null)
    val meals :StateFlow<ArrayList<Meals>?> get() = _meals
    private val _dishOfDay = MutableStateFlow<ArrayList<Meals>?>(null)
    val dishOfDay :StateFlow<ArrayList<Meals>?> get() = _dishOfDay

    init {
        getCategories()
        getDishOfDay()
        getMeals("")
    }

    @OptIn(UnstableApi::class)
     fun getCategories(){
        viewModelScope.launch {
            try {
                val response = repository.getAllCategories()
                if (response.isSuccessful){
                    _categories.value = response.body()?.categories
                }else{
                    Toast.makeText(application, "Error Network", Toast.LENGTH_SHORT).show()
                }
            } catch (e:Exception)
            {
              Log.e("mohamed", "getCategories: ${e.message}")
            }

        }
    }

    @OptIn(UnstableApi::class)
    fun getDishOfDay(){
         try {
             viewModelScope.launch {
                 val response = repository.getRandomMeal()
                 if (response.isSuccessful){
                     _dishOfDay.value = response.body()?.meals
                 }else{
                     Toast.makeText(application, "Error Network", Toast.LENGTH_SHORT).show()
                 }
             }

         }catch (e:Exception){
             Log.d("mohamed", "getDishOfDay: ${e.message}")
         }

    }

    @OptIn(UnstableApi::class)
     fun getMeals(searchMeal:String){
        try {
            viewModelScope.launch {
                val response = repository.getMealBySearch(searchMeal)
                if (response.isSuccessful){
                    _meals.value = response.body()?.meals
                }else {
                    Toast.makeText(
                        application,
                        "Error Network",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }catch (e:Exception){
            Log.e("mohamed", "getMeals: ${e.message}")
        }

    }

}
package com.khedr.firebaselogin.presentation.viewModel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khedr.firebaselogin.data.model.Categories
import com.khedr.firebaselogin.data.model.Meals
import com.khedr.firebaselogin.data.model.MealsByCategory
import com.khedr.firebaselogin.data.repository.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: NetworkRepository,
    private val application: Application
) : ViewModel() {

    private val _categories = MutableStateFlow<List<Categories>>(emptyList())
    val categories: StateFlow<List<Categories>> get() = _categories

    private val _meals = MutableStateFlow<List<Meals>>(emptyList())
    val meals: StateFlow<List<Meals>> get() = _meals

    private val _mealsByCategory = MutableStateFlow<List<MealsByCategory>>(emptyList())
    val mealsByCategory: StateFlow<List<MealsByCategory>> get() = _mealsByCategory

    private val _dishOfDay = MutableStateFlow<List<Meals>>(emptyList())
    val dishOfDay: StateFlow<List<Meals>> get() = _dishOfDay

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading
    private val _isLoadingBottomSheet = MutableStateFlow(false)
    val isLoadingBottomSheet: StateFlow<Boolean> get() = _isLoadingBottomSheet

    init {
        getCategories()
        getDishOfDay()
        getMeals("")
    }

    fun getCategories() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.getAllCategories()
                if (response.isSuccessful) {
                    _categories.value = response.body()?.categories ?: emptyList()
                } else {
                    showToast("Error Network: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("HomeViewModel", "getCategories: ${e.message}", e)
                showToast("Error Network: ${e.localizedMessage}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getDishOfDay() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.getRandomMeal()
                if (response.isSuccessful) {
                    _dishOfDay.value = response.body()?.meals ?: emptyList()
                } else {
                    showToast("Error Network: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("HomeViewModel", "getDishOfDay: ${e.message}", e)
                showToast("Error Network: ${e.localizedMessage}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getMeals(searchMeal: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.getMealBySearch(searchMeal)
                if (response.isSuccessful) {
                    _meals.value = response.body()?.meals ?: emptyList()
                } else {
                    showToast("Error Network: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("HomeViewModel", "getMeals: ${e.message}", e)
                showToast("Error Network: ${e.localizedMessage}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getMealsByCategory(category: String) {
        viewModelScope.launch {

            try {
                val response = repository.getMealByCategory(category)
                if (response.isSuccessful) {
                    _mealsByCategory.value = response.body()?.mealsByCategory ?: emptyList()

                } else {
                    showToast("Error Network: ${response.message()}")
                    //_isLoadingBottomSheet.value = false
                }
            } catch (e: Exception) {
                Log.e("HomeViewModel", "getMealsByCategory: ${e.message}", e)
                showToast("Error Network: ${e.localizedMessage}")
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(application.applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}

package com.khedr.firebaselogin.presentation.viewModel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khedr.firebaselogin.data.model.Categories
import com.khedr.firebaselogin.data.model.Meals
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

    private val _categories = MutableStateFlow<ArrayList<Categories>?>(null)
    val categories: StateFlow<ArrayList<Categories>?> get() = _categories

    private val _meals = MutableStateFlow<ArrayList<Meals>?>(null)
    val meals: StateFlow<ArrayList<Meals>?> get() = _meals

    private val _dishOfDay = MutableStateFlow<ArrayList<Meals>?>(null)
    val dishOfDay: StateFlow<ArrayList<Meals>?> get() = _dishOfDay

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

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
                    _categories.value = response.body()?.categories
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
                    _dishOfDay.value = response.body()?.meals
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
                    _meals.value = response.body()?.meals
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

    private fun showToast(message: String) {
        viewModelScope.launch {
            Toast.makeText(application, message, Toast.LENGTH_SHORT).show()
        }
    }
}

package com.khedr.firebaselogin.presention.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khedr.firebaselogin.data.model.Meals
import com.khedr.firebaselogin.data.repository.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel@Inject constructor(private val repository: NetworkRepository):ViewModel() {
    private val _meals = MutableStateFlow<List<Meals>>(emptyList())
    val meals : StateFlow<List<Meals>> get() = _meals
    fun getMeals (query:String) {
        viewModelScope.launch {
            try {
                val meals = repository.getMealBySearch(query)
                _meals.value= meals.body()?.meals ?: emptyList()
            }catch (e:Exception){
                Log.e("mohamed",e.message.toString())
            }

        }
    }

}
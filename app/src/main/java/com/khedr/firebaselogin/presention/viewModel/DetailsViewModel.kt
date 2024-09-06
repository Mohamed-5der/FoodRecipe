package com.khedr.firebaselogin.presention.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khedr.firebaselogin.data.model.Food
import com.khedr.firebaselogin.data.repository.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel@Inject constructor(private val repository: NetworkRepository):ViewModel() {
    private val _mealDetails = MutableStateFlow<Food?>(null)
    val mealDetails : StateFlow<Food?> get() = _mealDetails
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading
    fun getMealDetails(id:String){
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = repository.getMealById(id)
                if (response.isSuccessful){
                    _mealDetails.value = response.body()
                    _isLoading.value = false
                }else{
                    Log.e("mohamed",response.message())
                }
        }catch (e:Exception){
            Log.e("mohamed",e.message.toString())
          }finally {
                _isLoading.value = false


            }
        }
    }
}
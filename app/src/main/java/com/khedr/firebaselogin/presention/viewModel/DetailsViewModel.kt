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

    fun getMealDetails(id:String){
        viewModelScope.launch {
            try {
                val response = repository.getMealById(id)
                if (response.isSuccessful){
                    _mealDetails.value = response.body()
                }else{
                    Log.e("mohamed",response.message())
                }
        }catch (e:Exception){
            Log.e("mohamed",e.message.toString())
          }
        }
    }
}
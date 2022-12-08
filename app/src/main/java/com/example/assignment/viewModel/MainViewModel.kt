package com.example.assignment.viewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment.model.MeteorData
import com.example.assignment.network.MeteorsApi
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _meteors = MutableLiveData<List<MeteorData>>()

    val meteors: LiveData<List<MeteorData>> = _meteors

    init {
        getMeteorsInfo()
    }

    private fun getMeteorsInfo() {
        viewModelScope.launch {
            try {
                _meteors.value = MeteorsApi.retrofitService.getMeteorsInfo()
            } catch (e: Exception) {
                _meteors.value = listOf()
            }
        }
    }

    fun refresh(){
        getMeteorsInfo()
    }
}
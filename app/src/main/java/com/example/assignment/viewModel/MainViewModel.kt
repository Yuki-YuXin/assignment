package com.example.assignment.viewModel

import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment.model.MeteorData
import com.example.assignment.network.MeteorsApi
import com.example.assignment.util.Constant
import com.example.assignment.view.MainActivity
import com.example.assignment.view.MapActivity
import kotlinx.coroutines.launch
import java.security.AccessController.getContext

enum class MarsApiStatus { LOADING, ERROR, DONE }

/**
 * The [ViewModel] that is attached to the [MainActivity].
 */
class MainViewModel : ViewModel() {

    // Internally, we use a MutableLiveData, because we will be updating the List of MarsPhoto
    // with new values
    private val _meteors = MutableLiveData<MutableList<MeteorData>>()

    // The external LiveData interface to the property is immutable, so only this class can modify
    val meteors: LiveData<MutableList<MeteorData>> = _meteors

    /**
     * Call getInfo() on init so we can display status immediately.
     */
    init {
        getMeteorsInfo()
    }

    /**
     * Gets Meteors photos information from the NASA's API Retrofit service and updates the
     * [MeteorData] [List] [LiveData].
     */
    private fun getMeteorsInfo() {
        viewModelScope.launch {
            try {
                _meteors.value = MeteorsApi.retrofitService.getMeteorsInfo()
            } catch (e: Exception) {
                _meteors.value = mutableListOf()
            }
        }
    }

    fun refresh(){
        getMeteorsInfo()
    }


}
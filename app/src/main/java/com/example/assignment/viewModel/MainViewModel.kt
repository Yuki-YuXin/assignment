package com.example.assignment.viewModel

import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment.model.MeteorData
import com.example.assignment.network.MeteorsApi
import com.example.assignment.view.MainActivity
import kotlinx.coroutines.launch
import java.lang.System.out

/**
 * The [ViewModel] that is attached to the [MainActivity].
 */
class MainViewModel : ViewModel() {

    // Internally, we use a MutableLiveData, because we will be updating the List of MarsPhoto
    // with new values
    private val _meteors = MutableLiveData<MutableList<MeteorData>>()
    val _itemPosition = MutableLiveData<Int>()
    val _radioChecked = MutableLiveData<Int>()

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
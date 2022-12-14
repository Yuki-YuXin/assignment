package com.example.assignment.viewModel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.assignment.data.OperationCallback
import com.example.assignment.model.MeteorData
import com.example.assignment.model.MeteorRepository
import com.example.assignment.util.FileHelper


class MainViewModel(
    private val repository: MeteorRepository) : ViewModel() {
    private val TAG: String = this.javaClass.name
    private val _meteors = MutableLiveData<List<MeteorData>>()
    val meteors: MutableLiveData<List<MeteorData>> = _meteors

    private val _onMessageError = MutableLiveData<Any?>()
    val onMessageError: MutableLiveData<Any?> = _onMessageError

    private val _isEmptyList = MutableLiveData<Boolean>()
    val isEmptyList: LiveData<Boolean> = _isEmptyList

//    init {
//        getMeteorsInfo()
//        Log.d(TAG, "Initial")
//    }

    fun getMeteorsInfo(context: Context) {
        repository.fetchMeteors(object : OperationCallback<MeteorData> {
            override fun onError(error: String?) {
                Log.d(TAG, "Error")
                _onMessageError.value = error
                val data = FileHelper.readFromFile(context)
                this@MainViewModel._meteors.value = data
            }

            override fun onSuccess(data: List<MeteorData>?) {
                if (data.isNullOrEmpty()) {
                     Log.d(TAG, "Empty")
                    _isEmptyList.value = true

                } else {
                    Log.d(TAG, "Success")
                    this@MainViewModel._meteors.value = data
                    FileHelper.writeToFile(data, context)
                }
            }
        })
    }


}
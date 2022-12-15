package com.example.assignment.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.example.assignment.data.OperationCallback
import com.example.assignment.model.MeteorData
import com.example.assignment.model.MeteorRepository
import com.example.assignment.util.Constant
import com.example.assignment.util.FileHelper
import com.orhanobut.hawk.Hawk


class MainViewModel(
    private val repository: MeteorRepository) : ViewModel(), DefaultLifecycleObserver {
    private val TAG: String = this.javaClass.name
    private val _meteors = MutableLiveData<List<MeteorData>>()
    val meteors: MutableLiveData<List<MeteorData>> = _meteors

    private val _onMessageError = MutableLiveData<Any?>()
    val onMessageError: MutableLiveData<Any?> = _onMessageError

    private val _isEmptyList = MutableLiveData<Boolean>()
    val isEmptyList: LiveData<Boolean> = _isEmptyList

//    override fun onResume(owner: LifecycleOwner) {
//        getMeteorsInfo() unsure about what the right way to do when need to pass the context from Activity
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

    fun sortMeteor(): List<MeteorData> {
        var sortList = listOf<MeteorData>()
        if (Hawk.get(Constant.Settings.SORT_ORDER, Constant.SortOrder.ASCENDING) == Constant.SortOrder.ASCENDING) {
            when (Hawk.get(Constant.Settings.SORT_FIELD, "Mass")) {
                "Mass" -> sortList = _meteors.value?.sortedBy { it.mass }?.toList() ?: emptyList()
                "Year" -> sortList = _meteors.value?.sortedBy { it.year }?.toList() ?: emptyList()
                "Name" -> sortList = _meteors.value?.sortedBy { it.name }?.toList() ?: emptyList()
            }
        } else {
            when (Hawk.get(Constant.Settings.SORT_FIELD, "Mass")) {
                "Mass" -> sortList = _meteors.value?.sortedByDescending { it.mass }?.toList() ?: emptyList()
                "Year" -> sortList = _meteors.value?.sortedByDescending { it.year }?.toList() ?: emptyList()
                "Name" -> sortList = _meteors.value?.sortedByDescending { it.name }?.toList() ?: emptyList()
            }
        }
        return sortList
    }
}
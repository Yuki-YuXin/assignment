package com.example.assignment.viewModel
import androidx.lifecycle.*
import com.example.assignment.model.MeteorData
import com.example.assignment.network.MeteorsApi
import com.example.assignment.util.Constant
import com.orhanobut.hawk.Hawk
import kotlinx.coroutines.launch

class MainViewModel : ViewModel(), DefaultLifecycleObserver {
    private val _meteors = MutableLiveData<List<MeteorData>>()

    val meteors: LiveData<List<MeteorData>> = _meteors

    override fun onResume(owner: LifecycleOwner) {
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
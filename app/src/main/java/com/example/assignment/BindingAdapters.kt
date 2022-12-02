package com.example.assignment

import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.widget.AppCompatSpinner
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.adapter.MeteorsListAdapter
import com.example.assignment.model.MeteorData
import java.text.SimpleDateFormat
import java.util.*

private val TAG = "BindingAdapter"
/**
 * Updates the data shown in the [RecyclerView].
 */
@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: MutableList<MeteorData>?) {
    val adapter = recyclerView.adapter as MeteorsListAdapter
    if (data != null) {
        adapter.submitList(data)
        Log.d("Binding", data.toString())
        adapter.notifyDataSetChanged();
    }
}

/**
 * Transform mass attribute from Double to String with kg.
 */
@BindingAdapter("app:massText")
fun massText(view: TextView, mass: Double) {
    view.text = String.format("%.1f", mass) + R.string.main_mass_unit
}

/**
 * Transform date format.
 */
@BindingAdapter("app:dateText")
fun dateText(view: TextView, text: String) {
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    val date: Date = format.parse(text)
    view.text = date.toString()
}

/**
 * Combine reclat and reclong together
 */
@BindingAdapter("app:locationText")
fun locationText(view: TextView, meteor: MeteorData) {
    view.text ='[' + meteor.reclat.toString() + ',' + meteor.reclong.toString() + ']'
}


@BindingAdapter("app:spinnerClicks")
fun listenClicks(spinner: AppCompatSpinner, result: MutableLiveData<Int>) {
    spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
            println("Nothing")
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            result.value = parent?.getItemAtPosition(position) as Int
            Log.d(TAG, parent.getItemAtPosition(position) as String)
        }
    }
}
package com.example.assignment

import android.provider.Settings.Global.getString
import android.util.Log
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.adapter.MeteorsListAdapter
import com.example.assignment.model.MeteorData
import java.text.SimpleDateFormat
import java.util.*


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


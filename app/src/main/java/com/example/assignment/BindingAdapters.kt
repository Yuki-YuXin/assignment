package com.example.assignment

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.adapter.MeteorsListAdapter
import com.example.assignment.model.MeteorData

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
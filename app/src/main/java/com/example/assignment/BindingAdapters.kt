package com.example.assignment

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.adapter.MeteorsListAdapter
import com.example.assignment.model.MeteorData

/**
 * Updates the data shown in the [RecyclerView].
 */
@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<MeteorData>?) {
    val adapter = recyclerView.adapter as MeteorsListAdapter
    adapter.submitList(data)
}
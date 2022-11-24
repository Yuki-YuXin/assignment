package com.example.assignment.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.databinding.ItemMeteoriteBinding
import com.example.assignment.model.MeteorData
import com.example.assignment.viewModel.MainViewModel

/**
 * This class implements a [RecyclerView] [ListAdapter] which uses Data Binding to present [List] data
 */
class MeteorsListAdapter(
    context: Context,
    recyclerItemClickListener: RecyclerItemClickListener,
) : RecyclerView.Adapter<MeteorsListAdapter.MeteorsViewHolder>() {

    private val TAG: String = this.javaClass.name

    var meteorDataList: MutableList<MeteorData> = mutableListOf()
    var context: Context
    var recyclerItemClickListener: RecyclerItemClickListener

    init {
        this.context = context
        this.recyclerItemClickListener = recyclerItemClickListener
    }

    interface RecyclerItemClickListener {
        fun onRecyclerItemClick(meteorItem: MeteorData?)
    }

    class MeteorsViewHolder private constructor(private var binding: ItemMeteoriteBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(meteorData: MeteorData) {
            binding.meteor = meteorData
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MeteorsViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemMeteoriteBinding.inflate(layoutInflater, parent, false)
                return MeteorsViewHolder(binding)
            }
        }
    }

//    /**
//     * Allows the RecyclerView to determine which items have changed when the [List] of
//     * [MeteorData] has been updated.
//     */
//    companion object DiffCallback : DiffUtil.ItemCallback<MeteorData>() {
//        override fun areItemsTheSame(oldItem: MeteorData, newItem: MeteorData): Boolean {
//            return oldItem.id == newItem.id
//        }
//
//        override fun areContentsTheSame(oldItem: MeteorData, newItem: MeteorData): Boolean {
//            return oldItem.name == newItem.name
//        }
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeteorsViewHolder {
        return MeteorsViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MeteorsViewHolder, position: Int) {
        val meteor: MeteorData = meteorDataList[position]
            holder.bind(meteor)
    }

    override fun getItemCount(): Int {
        return meteorDataList.size
    }

    fun submitList(data: MutableList<MeteorData>) {
        meteorDataList = data
    }
}
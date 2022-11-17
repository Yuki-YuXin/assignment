package com.example.assignment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.databinding.ItemMeteoriteBinding
import com.example.assignment.model.MeteorData


/**
 * This class implements a [RecyclerView] [ListAdapter] which uses Data Binding to present [List]
 * data, including computing diffs between lists.
 */
class MeteorsListAdapter() : ListAdapter<MeteorData, MeteorsListAdapter.MeteorsViewHolder>(DiffCallback) {

    private val TAG: String = this.javaClass.name

    /**
     * The MarsPhotosViewHolder constructor takes the binding variable from the associated
     * GridViewItem, which nicely gives it access to the full [MeteorData] information.
     */
    class MeteorsViewHolder(
        private var binding: ItemMeteoriteBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(meteorData: MeteorData) {
            binding.meteor = meteorData
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of
     * [MeteorData] has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<MeteorData>() {
        override fun areItemsTheSame(oldItem: MeteorData, newItem: MeteorData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MeteorData, newItem: MeteorData): Boolean {
            return oldItem.name == newItem.name
        }
    }

    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MeteorsViewHolder {
        return MeteorsViewHolder(
            ItemMeteoriteBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: MeteorsViewHolder, position: Int) {
        val marsPhoto = getItem(position)
        holder.bind(marsPhoto)
    }
}
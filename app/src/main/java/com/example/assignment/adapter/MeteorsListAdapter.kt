package com.example.assignment.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.R
import com.example.assignment.databinding.ItemMeteoriteBinding
import com.example.assignment.model.MeteorData

class MeteorsListAdapter(
    meteorList: List<MeteorData>,
    context: Context,
    recyclerItemClickListener: RecyclerItemClickListener
) : RecyclerView.Adapter<MeteorsListAdapter.MeteorsViewHolder>() {

    private val TAG: String = this.javaClass.name
    var meteorDataList: List<MeteorData>
    var context: Context
    var recyclerItemClickListener: RecyclerItemClickListener

    init {
        this.context = context
        meteorDataList = meteorList
        this.recyclerItemClickListener = recyclerItemClickListener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MeteorsViewHolder {
        Log.d(TAG, "onCreateViewHolder called!!")
        val li = LayoutInflater.from(context)
        return MeteorsViewHolder(ItemMeteoriteBinding.inflate(li))
    }

    override fun onBindViewHolder(
        holder: MeteorsViewHolder,
        position: Int
    ) {
        Log.d(TAG, "onBindViewHolder bind position:$position")
        val meteorDataData: MeteorData = meteorDataList[position]
        holder.bind(meteorDataData)
    }

    override fun getItemCount(): Int {
        Log.d(TAG, "MeteorsListAdapter getItemCount=" + meteorDataList.size)
        return meteorDataList.size
    }

    fun update(data: List<MeteorData>) {
        meteorDataList = data
        notifyDataSetChanged()
    }

    private fun getItem(position: Int): MeteorData {
        return meteorDataList[position]
    }

    interface RecyclerItemClickListener {
        fun onRecyclerItemClick(meteorItem: MeteorData?)
    }

    inner class MeteorsViewHolder(itemBinding: ItemMeteoriteBinding) :
        RecyclerView.ViewHolder(itemBinding.getRoot()) {
        var itemMeteoriteBinding: ItemMeteoriteBinding
        fun bind(meteorDataData: MeteorData) {
            Log.d(TAG, "MeteorsViewHolder bind started")
            itemMeteoriteBinding.name.setText(meteorDataData.name)
            itemMeteoriteBinding.mass.setText(context.getString(R.string.main_mass, meteorDataData.mass))
            itemMeteoriteBinding.location.setText(meteorDataData.reclat.toString() + ", " + meteorDataData.reclong.toString())
            itemMeteoriteBinding.date.setText(meteorDataData.year?.substring(0, meteorDataData.year!!.indexOf("-")))
        }

        init {
            itemMeteoriteBinding = itemBinding
            itemBinding.getRoot().setOnClickListener(View.OnClickListener {
                Log.d(TAG,"MeteorsViewHolder clicked pos=" + meteorDataList[adapterPosition])
                recyclerItemClickListener.onRecyclerItemClick(meteorDataList[adapterPosition])
            })
        }
    }
}
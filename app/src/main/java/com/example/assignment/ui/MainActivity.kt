package com.example.assignment.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.databinding.ActivityMainBinding
import com.example.assignment.R
import com.example.assignment.adapter.MeteorsListAdapter
import com.example.assignment.model.MeteorData
import com.example.assignment.util.Constant

class MainActivity : AppCompatActivity(), MeteorsListAdapter.RecyclerItemClickListener {
    private val TAG: String = this.javaClass.name
    lateinit var meteorsListAdapter: MeteorsListAdapter
    lateinit var activityMainBinding: ActivityMainBinding
    lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.context = getApplicationContext()

        //get layout view
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater())
        val view: View = activityMainBinding.getRoot()
        setContentView(view)

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        activityMainBinding.recycler.setLayoutManager(layoutManager)

        fillRecyclerViewData()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.menu, menu)
        return true
    }


    fun fillRecyclerViewData() {
        Log.d(TAG, "fillRecyclerViewData called!!")
        meteorsListAdapter = MeteorsListAdapter(this,this)
        activityMainBinding.recycler.setAdapter(meteorsListAdapter)
    }

    override fun onRecyclerItemClick(meteorItem: MeteorData?) {
        Log.d(TAG, "onRecyclerItemClick called!!")
        val mapActivityIntent = Intent(this, MapActivity::class.java)
        mapActivityIntent.putExtra(Constant.Intent.METEORITE_NAME, meteorItem?.name)
        mapActivityIntent.putExtra(Constant.Intent.METEORITE_LAT, meteorItem?.reclat)
        mapActivityIntent.putExtra(Constant.Intent.METEORITE_LNG, meteorItem?.reclong)
        startActivity(mapActivityIntent)
    }
}

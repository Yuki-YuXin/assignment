package com.example.assignment.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.AdapterView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.databinding.ActivityMainBinding
import com.example.assignment.R
import com.example.assignment.adapter.MeteorsListAdapter
import com.example.assignment.model.MeteorData
import com.example.assignment.util.Constant
import com.example.assignment.viewModel.MainViewModel

class MainActivity : AppCompatActivity(){
    private val TAG: String = this.javaClass.name
    private val viewModel: MainViewModel by viewModels()
    lateinit var meteorsListAdapter: MeteorsListAdapter
    lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.context = getApplicationContext()

        //get layout view
        val binding =ActivityMainBinding.inflate(getLayoutInflater())

        val view: View = binding.getRoot()
        setContentView(view)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        binding.recycler.setLayoutManager(layoutManager)

        // Sets the adapter of the photosGrid RecyclerView
        meteorsListAdapter = MeteorsListAdapter()
        binding.recycler.setAdapter(meteorsListAdapter)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.menu, menu)
        return true
    }

//    override fun onRecyclerItemClick(meteorItem: MeteorData?) {
//        Log.d(TAG, "onRecyclerItemClick called!!")
//        val mapActivityIntent = Intent(this, MapActivity::class.java)
//        mapActivityIntent.putExtra(Constant.Intent.METEORITE_NAME, meteorItem?.name)
//        mapActivityIntent.putExtra(Constant.Intent.METEORITE_LAT, meteorItem?.reclat)
//        mapActivityIntent.putExtra(Constant.Intent.METEORITE_LNG, meteorItem?.reclong)
//        startActivity(mapActivityIntent)
//    }
}

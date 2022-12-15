package com.example.assignment.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.R
import com.example.assignment.adapter.MeteorsListAdapter
import com.example.assignment.databinding.ActivityMainBinding
import com.example.assignment.model.MeteorData
import com.example.assignment.util.Constant
import com.example.assignment.viewModel.MainViewModel

class MainActivity : AppCompatActivity(), MeteorsListAdapter.RecyclerItemClickListener{
    private val TAG: String = this.javaClass.name
    private val viewModel: MainViewModel by viewModels()
    lateinit var meteorsListAdapter: MeteorsListAdapter
    lateinit var context: Context
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.context = getApplicationContext()

        //get layout view
        binding = ActivityMainBinding.inflate(getLayoutInflater())

        val view: View = binding.getRoot()
        setContentView(view)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel
        lifecycle.addObserver(viewModel)

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        binding.recycler.layoutManager = layoutManager

        meteorsListAdapter = MeteorsListAdapter(viewModel.meteors.value ?: emptyList(),this,this)
        binding.recycler.adapter = meteorsListAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.menu, menu)
        return true
    }

    override fun onRecyclerItemClick(meteorItem: MeteorData) {
        val mapActivityIntent = Intent(this, MapActivity::class.java)
        mapActivityIntent.putExtra(Constant.Intent.METEORITE_NAME, meteorItem.name)
        mapActivityIntent.putExtra(Constant.Intent.METEORITE_LAT, meteorItem.reclat)
        mapActivityIntent.putExtra(Constant.Intent.METEORITE_LNG, meteorItem.reclong)
        startActivity(mapActivityIntent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_sort -> {
                Log.d(TAG,"TODO")
            }
            R.id.action_refresh -> {
                viewModel.refresh()
                Toast.makeText(this, "List refreshed", Toast.LENGTH_SHORT).show()
            }
        }
        return true
    }

    override fun onDestroy(){
        super.onDestroy()
        binding.recycler.adapter = null
        lifecycle.removeObserver(viewModel)
    }
}

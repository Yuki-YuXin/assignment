package com.example.assignment.view

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.RadioButton
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.adapter.MeteorsListAdapter
import com.example.assignment.databinding.ActivityMainBinding
import com.example.assignment.di.Injection
import com.example.assignment.model.MeteorData
import com.example.assignment.util.*
import com.example.assignment.viewModel.MainViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class MainActivity : AppCompatActivity(), MeteorsListAdapter.RecyclerItemClickListener {
    private val TAG: String = this.javaClass.name
    private val viewModel by viewModels<MainViewModel> {
        Injection.provideViewModelFactory()
    }
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

        setupViewModel()
        fillRecyclerViewData()

    }

    override fun onResume() {
        super.onResume()
        viewModel.getMeteorsInfo(this.context)
    }

    override fun onDestroy() {
        super.onDestroy()
        Injection.destroy()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(com.example.assignment.R.menu.menu, menu)
        return true
    }

    private val renderMeteorData = Observer<List<MeteorData>> {
        activityMainBinding.layoutError.textViewError.visibility = View.GONE
        activityMainBinding.layoutEmpty.textViewEmptyList.visibility = View.GONE
        meteorsListAdapter.update(it)
    }

    private val onMessageErrorObserver = Observer<Any> {
        activityMainBinding.layoutError.textViewError.visibility = View.VISIBLE
        activityMainBinding.layoutEmpty.textViewEmptyList.visibility = View.GONE
        activityMainBinding.layoutError.textViewError.text = "Error $it"
    }

    private val emptyListObserver = Observer<Boolean> {
        activityMainBinding.layoutEmpty.textViewEmptyList.visibility = View.VISIBLE
        activityMainBinding.layoutError.textViewError.visibility = View.GONE
    }


    fun fillRecyclerViewData() {
        Log.d(TAG, viewModel.meteors.value.toString() )
        meteorsListAdapter = MeteorsListAdapter(viewModel.meteors.value ?: emptyList(),this,this)
        activityMainBinding.recycler.setAdapter(meteorsListAdapter)
    }

    private fun setupViewModel() {
        viewModel.meteors.observe(this, renderMeteorData)
        viewModel.onMessageError.observe(this, onMessageErrorObserver as Observer<in Any?>)
        viewModel.isEmptyList.observe(this, emptyListObserver)
    }

    override fun onRecyclerItemClick(meteorItem: MeteorData?) {
        val mapActivityIntent = Intent(this, MapActivity::class.java)
        mapActivityIntent.putExtra(Constant.Intent.METEORITE_NAME, meteorItem?.name)
        mapActivityIntent.putExtra(Constant.Intent.METEORITE_LAT, meteorItem?.reclat)
        mapActivityIntent.putExtra(Constant.Intent.METEORITE_LNG, meteorItem?.reclong)
        startActivity(mapActivityIntent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            com.example.assignment.R.id.action_sort -> {
                if (viewModel.meteors.value?.isNotEmpty() == true) {
                    showSortListDialog()
                } else {
                    viewModel.isEmptyList.observe(this, emptyListObserver)
                }
            }
            com.example.assignment.R.id.action_refresh -> {
                viewModel.getMeteorsInfo(this.context)
                Toast.makeText(this, "List refreshed", Toast.LENGTH_SHORT).show()
            }
        }
        return true
    }

    private fun showSortListDialog() {
        MaterialAlertDialogBuilder(this@MainActivity)
            .setTitle("Choose the filtering options:")
            .setView(com.example.assignment.R.layout.view_sort_dialog)
            .setPositiveButton(
                "Apply"
            ) { dialogInterface, _ -> applySortListDialog(dialogInterface)}
            .setNegativeButton(
                "CANCEL"
            ) { _, _ -> }
            .show()
    }

    fun applySortListDialog(dialog: DialogInterface) {
        val dialog = Dialog::class.java.cast(dialog)
        val fieldSpinner = dialog?.findViewById(com.example.assignment.R.id.sortFieldSpinner) as Spinner
        val radioAscendingButton = dialog.findViewById<View>(com.example.assignment.R.id.radioAscending) as RadioButton
        val compareText = fieldSpinner.getSelectedItem().toString() // get ID here
        var sortList = listOf<MeteorData>()

        if (radioAscendingButton.isChecked) {
            when (compareText) {
                "Mass" -> sortList = viewModel.meteors.value?.sortedBy { it.mass }?.toList() ?: emptyList()
                "Year" -> sortList = viewModel.meteors.value?.sortedBy { it.year }?.toList() ?: emptyList()
                "Name" -> sortList = viewModel.meteors.value?.sortedBy { it.name }?.toList() ?: emptyList()
            // sort function does not belong to the activity.  enmum click sort haddpen -> viewmodel (take the list, sort it and return it)
                // view model should hold the data but also view model handle business logic
                // activity hard to write unit test, activity -> Android specific
            }
        } else {
            when (compareText) {
                "Mass" -> sortList = viewModel.meteors.value?.sortedByDescending { it.mass }?.toList() ?: emptyList()
                "Year" -> sortList = viewModel.meteors.value?.sortedByDescending { it.year }?.toList() ?: emptyList()
                "Name" -> sortList = viewModel.meteors.value?.sortedByDescending { it.name }?.toList() ?: emptyList()
            }
        }
        meteorsListAdapter.update(sortList)
    }
}

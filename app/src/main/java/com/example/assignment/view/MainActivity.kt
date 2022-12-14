package com.example.assignment.view

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
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
import com.example.assignment.R
import com.example.assignment.adapter.MeteorsListAdapter
import com.example.assignment.databinding.ActivityMainBinding
import com.example.assignment.model.MeteorData
import com.example.assignment.util.*
import com.example.assignment.viewModel.MainViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.orhanobut.hawk.Hawk
import java.util.*


class MainActivity : AppCompatActivity(), MeteorsListAdapter.RecyclerItemClickListener {
    private val TAG: String = this.javaClass.name
    private val viewModel: MainViewModel by viewModels()
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

        lifecycle.addObserver(viewModel)

        fillRecyclerViewData()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(com.example.assignment.R.menu.menu, menu)
        return true
    }

    private val renderMuseums = Observer<List<MeteorData>> {
        meteorsListAdapter.update(it)
    }

    fun fillRecyclerViewData() {
        viewModel.meteors.observe(this, renderMuseums)
        meteorsListAdapter = MeteorsListAdapter(viewModel.meteors.value ?: emptyList(),this,this)
        activityMainBinding.recycler.setAdapter(meteorsListAdapter)
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
                    Toast.makeText(this@MainActivity, com.example.assignment.R.string.toast_no_data, Toast.LENGTH_SHORT).show()
                }
            }
            com.example.assignment.R.id.action_refresh -> {
                viewModel.refresh()
                Toast.makeText(this, "List refreshed", Toast.LENGTH_SHORT).show()
            }
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(viewModel)
    }

    private fun showSortListDialog() {
        MaterialAlertDialogBuilder(this@MainActivity)
            .setTitle(R.string.main_dialogue_title)
            .setView(R.layout.view_sort_dialog)
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
        val compareText = fieldSpinner.getSelectedItem().toString()

        Hawk.init(this.context).build()
        Hawk.put(Constant.Settings.SORT_FIELD, compareText)
        if (radioAscendingButton.isChecked) {
            Hawk.put(Constant.Settings.SORT_ORDER, Constant.SortOrder.ASCENDING)
        } else {
            Hawk.put(Constant.Settings.SORT_ORDER, Constant.SortOrder.DESCENDING)
        }
        val sortList = viewModel.sortMeteor()
        meteorsListAdapter.update(sortList)
    }
}

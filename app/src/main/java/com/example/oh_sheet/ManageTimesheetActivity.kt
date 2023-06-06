package com.example.oh_sheet



import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.ListView
import android.widget.SimpleAdapter
import androidx.appcompat.app.AppCompatActivity

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ManageTimesheetActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_timesheet)


        recyclerView = findViewById(R.id.recyclerView)
        setupRecyclerView()

        //val rootLayout = findViewById<View>(android.R.id.content) as FrameLayout
        //View.inflate(this, R.layout.custom_list_view, rootLayout);

        /*val adapter = SimpleAdapter(
            this,
            list,
            R.layout.custom_rows,
            arrayOf<String>("pen", "price", "color"),
            intArrayOf(R.id.textDesc, R.id.textStart, R.id.textEnd)
        )

        populateList()*/

        //val numbersListView: ListView = findViewById(R.id.view)
        //setListAdapter(adapter)
    }

    private fun setupRecyclerView() {
        val data = createSampleData() // Replace with your own ArrayList
        val adapter = TableAdapter(data)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
    }

    private fun createSampleData(): ArrayList<TableRowC> {

        val data = ArrayList<TableRowC>()

        // Populate the ArrayList with your data
        // Replace TableRow with your own data model class

        data.add(TableRowC("Description", "Start Date", "End Date", "Categories"))

        //put Daniel H data into the values in " ... "
        data.add(TableRowC("Value 1", "Value 2","Value 3", "Value 4"))
        // Add more data rows as needed

        return data
    }


    //--------------------------------------------------------------------------------------------------------------------










    /*val list = ArrayList<HashMap<String, String>>()

    //using sharedPreferences
    val array = ArrayList<String?>()

    val array2 = ArrayList<String?>()*/

    //method for saving timesheets to sharedpreferences
    /*fun saveArray(): Boolean {
        val appSharedPrefs = PreferenceManager
            .getDefaultSharedPreferences(this.applicationContext)
        val prefsEditor: SharedPreferences.Editor = appSharedPrefs.edit()

        prefsEditor.putInt("Status_size", array.size)

        for (i in 0 until array.size) {
            prefsEditor.remove("Status_$i")
            prefsEditor.putString("Status_$i", array.get(i))

        }
        return prefsEditor.commit();
    }*/

    //get array data out of sharedPreferernces
    /*fun loadArrayPref(mContext: Context?) {
        val mSharedPreference1 = PreferenceManager.getDefaultSharedPreferences(mContext)
        array.clear()

        val size = mSharedPreference1.getInt("Status_size", 0)

        for (i in 0 until size) {
            array.add(mSharedPreference1.getString("Status_$i", null))
        }
    }*/

    /*private fun populateList() {
        loadArrayPref(applicationContext)

        //using intents
        var b = this.intent.extras
        var array2 = b!!.getStringArrayList("key")



        //val index = array2?.indexOfFirst { it. }

        //because the for loop iterates through the array wierdly
        //had to pull some tricks to ensure that it only ran as many times as neccessary

        for (i in 0 until array.size step 6) {
            val temp = HashMap<String, String>()

            //this works assuming the data is stored as [description, date ,start time, end time, category]
            temp.put("Desciption", array.get(i).toString())
            temp.put("Date", array.get(i+1).toString())
            temp.put("Start", array.get(i+2).toString())
            temp.put("End", array.get(i+3).toString())
            temp.put("IMG", array.get(i+4).toString())

            list.add(temp)
        }

    }*/

}
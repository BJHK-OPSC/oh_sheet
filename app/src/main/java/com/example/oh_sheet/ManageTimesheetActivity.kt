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


    val obj = CreateTimesheetActivity()

    //------------------------------------------------------------------------------------------------\\
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_timesheet)

        recyclerView = findViewById(R.id.recyclerView)
        setupRecyclerView()

    }

    //------------------------------------------------------------------------------------------------\\
    private fun setupRecyclerView() {
        val data = createSampleData() // Replace with your own ArrayList
        val adapter = TableAdapter(data)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
    }

    //------------------------------------------------------------------------------------------------\\
    private fun createSampleData(): ArrayList<TableRowC> {

        val data = ArrayList<TableRowC>()

        //im assuming these become the column headings
        data.add(TableRowC("Description", "Start Date", "End Date", "Categories"))


        val array: ArrayList<CreateTimesheetActivity.TimesheetEntry> = obj.timesheetEntries
        //put Daniel H data into the values in " ... "


        data.add(TableRowC("Value 1", "Value 2","Value 3", "Value 4"))
        // Add more data rows as needed

        return data
    }
}
//------------------------------------------End of File------------------------------------------------------\\
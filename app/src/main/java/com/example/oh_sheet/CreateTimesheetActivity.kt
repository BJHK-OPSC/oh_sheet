package com.example.oh_sheet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class CreateTimesheetActivity : AppCompatActivity() {

    data class Category(val name: String)
    //list of timesheet entries\\
    //should be accessible from anywhere\\
    val timesheetEntries: ArrayList<TimesheetEntry> = ArrayList()

    data class TimesheetEntry(
        val date: String,
        val startTime: String,
        val endTime: String,
        val description: String,
        val category: Category,
        var photoPath: String? = ""
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_timesheet)
    }
}
package com.example.oh_sheet

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.HashMap


class StatisticsActivity : AppCompatActivity() {

    private lateinit var startDateEditText: TextInputEditText
    private lateinit var endDateEditText: TextInputEditText
    private lateinit var startDateButton: Button
    private lateinit var endDateButton: Button
    private lateinit var startDatePicker: MaterialDatePicker<Long>
    private lateinit var endDatePicker: MaterialDatePicker<Long>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics)

        // Retrieve current user ID
        val currentUser = FirebaseAuth.getInstance().currentUser
        val currentUserId = currentUser?.uid

        // Query Firebase and populate timesheetEntries
        val timesheetRef = FirebaseDatabase.getInstance().getReference("timesheets")

        timesheetRef.orderByChild("userId").equalTo(currentUserId).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Clear existing entries
                timesheetEntries.clear()

                for (entrySnapshot in dataSnapshot.children) {
                    val entry = entrySnapshot.getValue(TimesheetEntry::class.java)
                    entry?.let {
                        timesheetEntries.add(entry)
                    }
                }

                // Call your populateRecycleView function here to update the RecyclerView with the retrieved data
                populateRecycleView()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle the error case
                Log.e(TAG, "Error retrieving timesheet entries: ${databaseError.message}")
            }
        })

        startDateEditText = findViewById(R.id.startDateEditText)
        endDateEditText = findViewById(R.id.endDateEditText)
        startDateButton = findViewById(R.id.startDateButton)
        endDateButton = findViewById(R.id.endDateButton)

        setupDatePickers()
    }

    private fun populateRecycleView(){
        //Extract unique categories
        val uniqueCategories = HashSet<String>()
        for (entry in timesheetEntries) {
            uniqueCategories.add(entry.category.name)
        }

        //Create a data structure to store total time spent on each category
        val totalTimeByCategory = HashMap<String, Int>()

        //Calculate total time spent on each category
        for (category in uniqueCategories) {
            var totalTimeSpent = 0

            val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

            for (entry in timesheetEntries) {
                if (entry.category.name == category) {
                    val startTimeString = "${entry.startTime}"
                    val endTimeString = "${entry.endTime}"

                    val startTime = dateFormat.parse(startTimeString)
                    val endTime = dateFormat.parse(endTimeString)

                    // Calculate the time difference in milliseconds
                    val timeDifference = endTime.time - startTime.time

                    // Convert milliseconds to minutes (or any other desired unit)
                    val timeSpentMinutes = TimeUnit.MILLISECONDS.toHours(timeDifference).toInt()

                    totalTimeSpent += timeSpentMinutes
                }
            }

            totalTimeByCategory[category] = totalTimeSpent
        }

        val categoryList = ArrayList<dataCategory>()

        //Iterate over the totalTimeByCategory HashMap
        for ((category, totalTimeSpent) in totalTimeByCategory) {
            val categoryData = dataCategory(category, totalTimeSpent.toLong())
            categoryList.add(categoryData)
        }

        val recyclerView: RecyclerView = findViewById(R.id.categoryTotalsRecyclerView)
        val adapter = CategoryAdapter(categoryList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val backButton: ImageButton = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun setupDatePickers() {
        startDatePicker = createDatePicker { timestamp ->
            val selectedDate = Date(timestamp)
            startDateEditText.setText(formatDate(selectedDate))
        }

        endDatePicker = createDatePicker { timestamp ->
            val selectedDate = Date(timestamp)
            endDateEditText.setText(formatDate(selectedDate))
        }
    }

    private fun createDatePicker(onDateSelected: (Long) -> Unit): MaterialDatePicker<Long> {
        val builder = MaterialDatePicker.Builder.datePicker()
        val picker = builder.build()

        picker.addOnPositiveButtonClickListener { timestamp ->
            onDateSelected(timestamp)
        }

        return picker
    }

    fun showStartDatePickerDialog(view: View) {
        startDatePicker.show(supportFragmentManager, "StartDatePicker")
    }

    fun showEndDatePickerDialog(view: View) {
        endDatePicker.show(supportFragmentManager, "EndDatePicker")
    }

    private fun formatDate(date: Date): String {
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return format.format(date)
    }
}
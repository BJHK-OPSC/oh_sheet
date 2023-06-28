package com.example.oh_sheet

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import android.widget.Toast



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
//------------------------------------------------------------------------------------------------\\
class CreateTimesheetActivity : AppCompatActivity() {
    private lateinit var photoLauncher: ActivityResultLauncher<Intent>
    var selectedCategory: String? = null
    val categoryNames = listOf("Work", "Study", "Exercise")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_timesheet)



        val addPhotoButton = findViewById<Button>(R.id.addPhotoButton)
        val createEntryButton = findViewById<Button>(R.id.createEntryButton)

        photoLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val selectedPhotoUri = result.data?.data
                val photoPath = selectedPhotoUri?.toString()

                // Update the current timesheet entry with the photo path
                if (photoPath != null && timesheetEntries.isNotEmpty()) {
                    val lastEntry = timesheetEntries.last()
                    lastEntry.photoPath = photoPath
                }
            }
        }
        //------------------------------------------------------------------------------------------------\\
        // Button click listener to add a photograph
        addPhotoButton.setOnClickListener {
            // Open camera or gallery to select a photo


            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            photoLauncher.launch(intent)
        }
        //------------------------------------------------------------------------------------------------\\
        // Button click listener to create a new timesheet entry
        createEntryButton.setOnClickListener {
            val categorySpinner = findViewById<Spinner>(R.id.categorySpinner)
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categoryNames)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            categorySpinner.adapter = adapter // Set the adapter for the spinner
            categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    // Get the selected category
                    selectedCategory = categoryNames[position]
                    showToast("Selected category: $selectedCategory")
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Handle the case when nothing is selected
                    selectedCategory = "Work"
                }
            }

            val date = findViewById<EditText>(R.id.dateEditText).text.toString()
            val startTime = findViewById<EditText>(R.id.startTimeEditText).text.toString()
            val endTime = findViewById<EditText>(R.id.endTimeEditText).text.toString()
            val description = findViewById<EditText>(R.id.descriptionEditText).text.toString()
            val category = Category(selectedCategory ?: "")

            val entry = TimesheetEntry(date, startTime, endTime, description, category)
            timesheetEntries.add(entry)

            clearInputFields()
        }

    }


    //------------------------------------------------------------------------------------------------\\
    private fun clearInputFields() {
        //clears all fields
        val date = findViewById<EditText>(R.id.dateEditText)
        val startTime = findViewById<EditText>(R.id.startTimeEditText)
        val endTime = findViewById<EditText>(R.id.endTimeEditText)
        val description = findViewById<EditText>(R.id.descriptionEditText)
        date.text.clear()
        startTime.text.clear()
        endTime.text.clear()
        description.text.clear()

    }
    //------------------------------------------------------------------------------------------------\\
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}


//------------------------------------------End of File------------------------------------------------------\\

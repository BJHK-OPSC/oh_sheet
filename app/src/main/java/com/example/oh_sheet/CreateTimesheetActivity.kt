package com.example.oh_sheet

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.oh_sheet.databinding.ActivityCreateTimesheet2Binding
import com.example.oh_sheet.databinding.ActivityCreateTimesheetBinding


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
    private lateinit var binding: ActivityCreateTimesheetBinding
    private lateinit var bindingTimesheet2Binding: ActivityCreateTimesheet2Binding
    private lateinit var photoLauncher: ActivityResultLauncher<Intent>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateTimesheetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindingTimesheet2Binding = ActivityCreateTimesheet2Binding.inflate(layoutInflater)



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
        bindingTimesheet2Binding.addPhotoButton.setOnClickListener {
            // Open camera or gallery to select a photo
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            photoLauncher.launch(intent)
        }
        //------------------------------------------------------------------------------------------------\\
        // Button click listener to create a new timesheet entry
        bindingTimesheet2Binding.createEntryButton.setOnClickListener {
            val date = binding.dateEditText.text.toString()
            val startTime = binding.startTimeEditText.text.toString()
            val endTime = binding.endTimeEditText.text.toString()
            val description = binding.descriptionEditText.text.toString()
            val category = Category(binding.categorySpinner.toString())
            val entry =
                TimesheetEntry(date, startTime, endTime, description, category)
            timesheetEntries.add(entry)

            clearInputFields()

        }
        //------------------------------------------------------------------------------------------------\\
    }
//------------------------------------------------------------------------------------------------\\
    private fun clearInputFields() {
    //clears all fields
        binding.dateEditText.text.clear()
        binding.startTimeEditText.text.clear()
        binding.endTimeEditText.text.clear()
        binding.descriptionEditText.text.clear()
    }
}
//------------------------------------------End of File------------------------------------------------------\\

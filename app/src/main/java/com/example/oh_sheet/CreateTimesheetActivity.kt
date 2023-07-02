package com.example.oh_sheet

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


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
    private lateinit var datePicker: MaterialDatePicker<Long>
    private lateinit var dateButton: Button
    private lateinit var dateEditText: TextInputEditText

    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder : NotificationCompat.Builder
    private val channelID = "1"
    private val description = "OhSheet Entry Created"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_timesheet)

        dateEditText = findViewById(R.id.dateEditText)
        dateButton = findViewById(R.id.dateButton)
        setupDatePickers()

        val addPhotoButton = findViewById<Button>(R.id.addPhotoButton)
        val createEntryButton = findViewById<Button>(R.id.createEntryButton)
        val categorySpinner = findViewById<Spinner>(R.id.categorySpinner)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categoryNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categorySpinner.adapter = adapter
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


            val date = findViewById<EditText>(R.id.dateEditText).text.toString()
            val startTime = findViewById<EditText>(R.id.startTimeEditText).text.toString()
            val endTime = findViewById<EditText>(R.id.endTimeEditText).text.toString()
            val description = findViewById<EditText>(R.id.descriptionEditText).text.toString()
            val category = Category("Work")

            val entry = TimesheetEntry(date, startTime, endTime, description, category)
            timesheetEntries.add(entry)

            sendNotification()
            notificationManager.notify(2, builder.build())

            clearInputFields()
        }

        val backButton: ImageButton = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
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

    private fun sendNotification(){

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            notificationChannel = NotificationChannel(channelID, description, NotificationManager.IMPORTANCE_DEFAULT)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.GREEN
            notificationChannel.enableVibration(true)
            notificationManager.createNotificationChannel(notificationChannel)

            builder = NotificationCompat.Builder(this, channelID)
                .setContentTitle("OhSheet Entry")
                .setContentText("New Entry Added")
                .setSmallIcon(R.drawable.ohsheet_pic)
        }else{
            builder = NotificationCompat.Builder(this)
                .setContentTitle("OhSheet Entry")
                .setContentText("New Entry Added")
                .setSmallIcon(R.drawable.ohsheet_pic)
        }

    }

    fun showStartDatePickerDialog(view: View) {
        datePicker.show(supportFragmentManager, "datePicker")
    }

    private fun formatDate(date: Date): String {
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return format.format(date)
    }

    private fun setupDatePickers() {
        datePicker = createDatePicker { timestamp ->
            val selectedDate = Date(timestamp)
            dateEditText.setText(formatDate(selectedDate))
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
}


//------------------------------------------End of File------------------------------------------------------\\

package com.example.oh_sheet

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import java.text.SimpleDateFormat
import java.util.*
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.Calendar;
import java.util.Date;

class TimerActivity : AppCompatActivity() {

    var selectedCategory: String = "Work"
    val categoryNames = listOf("Work", "Study", "Exercise")

    private lateinit var timer: Chronometer
    private lateinit var toggleButton: Button

    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder : NotificationCompat.Builder
    private val channelID = "1"
    private val description = "OhSheet Timer"

    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    private var isTimerRunning = false
    private var elapsedMillis: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)

        // Initialize views
        timer = findViewById(R.id.timer)
        toggleButton = findViewById(R.id.toggle_button)

        database = FirebaseDatabase.getInstance().reference

        auth = FirebaseAuth.getInstance()

        // Set click listener for toggle button
        toggleButton.setOnClickListener {
            toggleTimer()
        }

        val categorySpinner = findViewById<Spinner>(R.id.categorySpinner)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categoryNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categorySpinner.adapter = adapter
        categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // Get the selected category
                selectedCategory = categoryNames[position]
                showToast("Selected category: $selectedCategory")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle the case when nothing is selected
                selectedCategory = "Work"
            }
        }

        val backButton: ImageButton = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

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
                .setContentTitle("Timer Started")
                .setContentText("Timer Is Running")
                .setSmallIcon(R.drawable.ohsheet_pic)
        }else{
            builder = NotificationCompat.Builder(this)
                .setContentTitle("Timer Started")
                .setContentText("Timer Is Running")
                .setSmallIcon(R.drawable.ohsheet_pic)
        }

    }


    private fun toggleTimer() {
        sendNotification()

        if (isTimerRunning) {
            // Stop the timer and calculate elapsed time
            timer.stop()
            elapsedMillis = SystemClock.elapsedRealtime() - timer.base
            isTimerRunning = false
            notificationManager.cancel(1)

            // Set the end time
            val endTime = System.currentTimeMillis()

            // Database Timesheet entry
            val currentUser: FirebaseUser? = auth.currentUser
            val userID: String? = currentUser?.uid

            if (userID != null) {
                val entryKey = database.child("timesheets").push().key
                if (entryKey != null) {
                    // Create a TimesheetEntry object with start and end times
                    val entry = TimesheetEntry(
                        date = getCurrentDate(),
                        startTime = getFormattedTime(timer.base),
                        endTime = getFormattedTime(endTime),
                        description = findViewById<EditText>(R.id.entry_name_edit_text).text.toString(),
                        category = Category(selectedCategory),
                        photoPath = "",
                        userId = userID
                    )
                    timesheetEntries.add(entry)

                    database.child("timesheets").child(entryKey).setValue(entry)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                // Entry saved successfully
                                showToast("Entry created successfully")
                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                // Error occurred while saving the entry
                                showToast("Failed to create entry")
                            }
                        }
                }
            } else {
                showToast("User not signed in")
            }

            // Update toggle button text and background
            toggleButton.text = getString(R.string.start)
            toggleButton.setBackgroundResource(R.drawable.button_start_bg)

        } else {
            // Start the timer
            if (elapsedMillis == 0L) {
                timer.base = SystemClock.elapsedRealtime()
            } else {
                timer.base = SystemClock.elapsedRealtime() - elapsedMillis
            }
            timer.start()
            isTimerRunning = true

            notificationManager.notify(1, builder.build())

            // Update toggle button text and background
            toggleButton.text = getString(R.string.stop)
            toggleButton.setBackgroundResource(R.drawable.button_stop_bg)
        }
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(Date())
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun getFormattedTime(time: Long): String {
        val dateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        return dateFormat.format(Date(time))
    }
}
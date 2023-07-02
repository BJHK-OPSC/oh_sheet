package com.example.oh_sheet

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.Chronometer
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class TimerActivity : AppCompatActivity() {

    private lateinit var timer: Chronometer
    private lateinit var toggleButton: Button

    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder : NotificationCompat.Builder
    private val channelID = "1"
    private val description = "OhSheet Timer"

    private var isTimerRunning = false
    private var elapsedMillis: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)

        // Initialize views
        timer = findViewById(R.id.timer)
        toggleButton = findViewById(R.id.toggle_button)

        // Set click listener for toggle button
        toggleButton.setOnClickListener {
            toggleTimer()
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

            // Create a TimesheetEntry object
            val entry = TimesheetEntry(
                date = getCurrentDate(),
                startTime = getFormattedTime(timer.base),
                endTime = getFormattedTime(SystemClock.elapsedRealtime()),
                description = findViewById<EditText>(R.id.entry_name_edit_text).text.toString(),
                category = Category(findViewById<EditText>(R.id.category_edit_text).text.toString())
            )

            // Add the entry to timesheetEntries
            timesheetEntries.add(entry)

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

    private fun getFormattedTime(time: Long): String {
        val dateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        return dateFormat.format(Date(time))
    }
}
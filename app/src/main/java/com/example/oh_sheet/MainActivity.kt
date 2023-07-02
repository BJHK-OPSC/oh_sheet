package com.example.oh_sheet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val createTimesheetButton: Button = findViewById(R.id.createTimesheet_button)
        createTimesheetButton.setOnClickListener {
            val intent = Intent(this, CreateTimesheetActivity::class.java)
            startActivity(intent)
            finish()
        }

        val manageTimesheetButton: Button = findViewById(R.id.manageTimesheet_button)
        manageTimesheetButton.setOnClickListener {
            val intent = Intent(this, ManageTimesheetActivity::class.java)
            startActivity(intent)
            finish()
        }

        val statisticsButton: Button = findViewById(R.id.statistics_button)
        statisticsButton.setOnClickListener {
            val intent = Intent(this, StatisticsActivity::class.java)
            startActivity(intent)
            finish()
        }

        val setGoalsButton: Button = findViewById(R.id.set_goals_button)
        setGoalsButton.setOnClickListener {
            val intent = Intent(this, SetGoalsActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
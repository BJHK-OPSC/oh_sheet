package com.example.oh_sheet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.time.LocalDate
import java.time.Duration

class StatisticsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics)

      /*  fun calculateTimeTotals(startDate: LocalDate, endDate: LocalDate): Map<String, Duration> {
            val timeTotals = mutableMapOf<String, Duration>()

            for (entry in timesheetEntries) {
                if (entry.date.isAfter(endDate) || entry.date.isBefore(startDate)) {
                    continue // Skip entries outside the selected date range
                }

                val duration = Duration.between(entry.startTime, entry.endTime)

                if (timeTotals.containsKey(entry.category)) {
                    val totalDuration = timeTotals[entry.category]?.plus(duration)
                    timeTotals[entry.category] = totalDuration
                } else {
                    timeTotals[entry.category] = duration
                }
            }

            return timeTotals
        }*/
    }
}
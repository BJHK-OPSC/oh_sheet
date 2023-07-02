package com.example.oh_sheet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.graphics.Color

import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

import kotlin.collections.ArrayList

class GraphActivity : AppCompatActivity() {

    private lateinit var lineChart: LineChart
    val labels = ArrayList<String>()

    private lateinit var mi: String
    private lateinit var ma: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph)

        lineChart = findViewById(R.id.lineChart)

        try {
            setUpChart()
            Log.d("setUp","done")
            setDataToLineChart()
            Log.d("setData","done")

            lineChart.xAxis.valueFormatter = MyAxisFormatter(labels)
            lineChart.invalidate()
        }catch (e: java.lang.Exception){
            Log.d("fail","failure")
        }

        if(!labels.indices.isEmpty()){
            Log.d("status","not empty")
        }else{
            Log.d("fail3","failure3")
        }
    }

    private fun setUpChart(){
        with(lineChart){
            axisRight.isEnabled = false
            animateX(1200, Easing.EaseInSine)

            description.isEnabled = false

            val chartPad40 = 40
            val chartPad20 = 20
            setPadding(chartPad40,chartPad20,chartPad40,chartPad20)

            setTouchEnabled(true)
            isDragEnabled = true
            setScaleEnabled(false)

            xAxis.position = XAxis.XAxisPosition.BOTTOM

            xAxis.labelCount = 5
            xAxis.setAvoidFirstLastClipping(true)

            xAxis.granularity = 1F
            xAxis.setDrawGridLines(false)
            xAxis.setDrawAxisLine(true)
            axisLeft.setDrawGridLines(false)
            extraRightOffset = 30f

            val visibleRange = 5f
            setVisibleXRangeMaximum(visibleRange)
            setVisibleXRangeMinimum(visibleRange)

            axisLeft.axisMaximum = 7f
            axisLeft.axisMinimum = 0f

            legend.isEnabled = true
            legend.orientation = Legend.LegendOrientation.VERTICAL
            legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
            legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
            legend.form = Legend.LegendForm.LINE
        }
    }

    private fun setDataToLineChart(): ArrayList<String> {

        //for 1st line
        val hours = ArrayList<Entry>()

        //for 2nd and 3rd
        val minList = ArrayList<Entry>()
        val maxList = ArrayList<Entry>()

        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val array: ArrayList<TimesheetEntry> = ArrayList()
        val GoalsArray: ArrayList<Goals> = ArrayList()

        //test
        val category = Category("Work")
        array.add(TimesheetEntry("2023-03-03", "15:00","18:00", "Value 4", category))
        array.add(TimesheetEntry("2023-03-04", "13:00","18:00", "Value 4", category))
        array.add(TimesheetEntry("2023-03-05", "12:00","18:00", "Value 4", category))
        array.add(TimesheetEntry("2023-03-06", "11:00","18:00", "Value 4", category))
        array.add(TimesheetEntry("2023-03-07", "16:00","18:00", "Value 4", category))
        array.add(TimesheetEntry("2023-03-08", "15:00","18:00", "Value 4", category))
        array.add(TimesheetEntry("2023-03-09", "17:00","18:00", "Value 4", category))

        if(!GoalsArray.indices.isEmpty()){
            mi = GoalsArray.get(0).min.toString()
            ma = GoalsArray.get(0).min.toString()
            Log.d("succ2", "succeedd")
        }
        else{
            mi = "2f"
            ma = "6f"
            Log.d("else", "else exec")
        }

        array.addAll(timesheetEntries)

        if(!array.indices.isEmpty()){
            for(i in array.indices){

                val day: String = array.get(i).date.toString() //date
                val end: String = array.get(i).endTime.toString()
                val start: String = array.get(i).startTime.toString()

                //val startTimeString = "${array.startTime}"
                //val endTimeString = "${array.endTime}"

                val startTime = dateFormat.parse(start)
                val endTime = dateFormat.parse(end)

                // Calculate the time difference in milliseconds
                val timeDifference = endTime.time - startTime.time

                // Convert milliseconds to minutes (or any other desired unit)
                val timeSpentMinutes = TimeUnit.MILLISECONDS.toHours(timeDifference).toInt()

                val entry = array[i]

                //replace val2 with hours
                hours.add(Entry(i.toFloat(),timeSpentMinutes.toFloat() ))

                minList.add(Entry(i.toFloat(), mi.toFloat()))
                Log.d("succ3", "succeedd")
                maxList.add(Entry(i.toFloat(), ma.toFloat()))
                labels.add(day)

            }
        }
        //dataset object 1
        val hoursWorked = LineDataSet(hours, "HoursWorked")
        hoursWorked.lineWidth = 3f
        hoursWorked.valueTextSize = 0f
        hoursWorked.mode = LineDataSet.Mode.LINEAR
        hoursWorked.setColor(Color.BLUE)
        hoursWorked.setCircleColor(Color.BLUE)
        hoursWorked.valueTextColor = Color.TRANSPARENT
        hoursWorked.enableDashedLine(20F, 10F, 0F)

        //dataset object 2
        val min = LineDataSet(minList, "Minimum Goal")
        min.lineWidth = 3f
        min.valueTextSize = 0f
        min.mode = LineDataSet.Mode.LINEAR
        min.setColor(Color.RED)
        min.setDrawCircles(false)
        min.valueTextColor = Color.TRANSPARENT
        min.enableDashedLine(20F, 15F, 0F)

        //dataset object 3
        val max = LineDataSet(maxList, "Maximum Goal")
        max.lineWidth = 3f
        max.valueTextSize = 0f
        max.mode = LineDataSet.Mode.LINEAR
        max.setColor(Color.RED)
        max.setDrawCircles(false)
        max.valueTextColor = Color.TRANSPARENT
        max.enableDashedLine(20F, 15F, 0F)

        val dataSet = ArrayList<ILineDataSet>()
        dataSet.add(hoursWorked)
        dataSet.add(min)
        dataSet.add(max)

        val lineData = LineData(dataSet)
        lineChart.data = lineData

        return labels
    }

    inner class MyAxisFormatter(private val items: ArrayList<String>): IndexAxisValueFormatter(){

        override fun getAxisLabel(value: Float, axis: AxisBase?): String? {
            try {
                val index = value.toInt()
                return if (index >= 0 && index < items.size) {
                    items[index]
                } else {
                    ""
                }
            }catch (e:Exception){
                Log.d("fail4","failure4")
            }
            return ""
        }
    }
}
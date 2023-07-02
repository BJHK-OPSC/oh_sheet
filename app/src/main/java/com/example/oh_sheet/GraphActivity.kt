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

import kotlin.collections.ArrayList

class GraphActivity : AppCompatActivity() {

    private lateinit var lineChart: LineChart
    val labels = ArrayList<String>()

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
        }finally {
            Log.d("status","finally")
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

            xAxis.position = XAxis.XAxisPosition.BOTTOM

            xAxis.granularity = 1F
            xAxis.setDrawGridLines(false)
            xAxis.setDrawAxisLine(false)
            axisLeft.setDrawGridLines(false)
            extraRightOffset = 30f

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


        val array: ArrayList<TimesheetEntry> = ArrayList()

        //test
        val category = Category("Work")
        array.add(TimesheetEntry("Value 1", "Value 2","18", "Value 4", category))

        try{
            labels.add("Value 1")
            Log.d("status","labels added")
        }catch (e: Exception){
            Log.d("fail5","failure5")
        }

        //array.addAll(timesheetEntries)

        if(!array.indices.isEmpty()){
            for(i in array.indices){

                val val1: String = array.get(i).category.name.toString()// category
                val val2: String = array.get(i).date.toString() //date
                val val3: String = array.get(i).endTime

                val entry = array[i]

                //replace val2 with hours
                hours.add(Entry(i.toFloat(),val3.toFloat() ))

                labels.add(val2)

                //data.add(TableRowC(val3, val2, val5,val4, val1))
            }
        }
        //dataset object 1
        val weekOneSales = LineDataSet(hours, "HoursWorked")
        weekOneSales.lineWidth = 3f
        weekOneSales.valueTextSize = 15f
        weekOneSales.mode = LineDataSet.Mode.CUBIC_BEZIER
        weekOneSales.setColor(Color.RED)
        weekOneSales.valueTextColor = Color.BLACK
        weekOneSales.enableDashedLine(20F, 10F, 0F)

        val dataSet = ArrayList<ILineDataSet>()
        dataSet.add(weekOneSales)

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
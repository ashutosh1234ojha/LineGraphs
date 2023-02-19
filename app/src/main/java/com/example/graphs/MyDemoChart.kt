package com.example.graphs

import android.graphics.Color
import android.os.Bundle
import android.util.LayoutDirection
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.graphs.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.IValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.ViewPortHandler

class MyDemoChart : AppCompatActivity() {
    lateinit var lineChart: LineChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_linechart_mp)

        lineChart = findViewById(R.id.chart1)
        setUp()
    }

    private fun setUp() {
        val lineDataSet = LineDataSet(dataValue(), "Balance chart")
        lineDataSet.color = Color.RED
        lineDataSet.setCircleColor(Color.RED)
        lineDataSet.setDrawCircleHole(false)
        lineDataSet.valueTextColor = Color.RED
        lineDataSet.mValueTextSize = 30f

        val iLineDataSet = ArrayList<ILineDataSet>()
        iLineDataSet.add(lineDataSet)
        val lineData = LineData(iLineDataSet)
        lineChart.data = lineData

        lineData.setValueFormatter(  MyValueFormatter())

//        lineChart.layoutDirection=LayoutDirection.RTL
        lineChart.setDrawGridBackground(false)

        val desc = Description()
        desc.text = "asfasd"
        lineChart.description = desc

        lineChart.axisRight.isEnabled = false

        val xAxis = lineChart.xAxis
        xAxis.valueFormatter = XAxisFormatter()
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
          xAxis.labelCount=30
        xAxis.granularity = 1f
//        lineChart.setVisibleXRange(3f,5f) // for scrolling


        val yAxisLeft = lineChart.axisLeft
        yAxisLeft.valueFormatter = YAxisFormatter()
        yAxisLeft.labelCount = 12
         yAxisLeft.mSpacePercentBottom=0f  //Imp
        yAxisLeft.granularity=10f

        lineChart.invalidate()

    }

    fun dataValue(): ArrayList<Entry> {
        val list = ArrayList<Entry>()
        list.add(Entry(0f, 20f))
        list.add(Entry(1f, 10f))
        list.add(Entry(1f, 40f))
        list.add(Entry(2f, 15f))
        list.add(Entry(30f, 8f))
        //    list.add(Entry(4f, 8000000f))
        //    list.add(Entry(5f, 80000f))
        //    list.add(Entry(6f, 58000f))
        //    list.add(Entry(7f, 800f))
        list.add(Entry(4f, 0f))
        return list
    }

    class MyValueFormatter : ValueFormatter() {

//        override fun getFormattedValue(
//            value: Float,
//            entry: Entry?,
//            dataSetIndex: Int,
//            viewPortHandler: ViewPortHandler?
//        ): String {
//            return "$ " + value
//        }

        override fun getFormattedValue(value: Float): String {
            return "$ " + value

        }

    }

    class YAxisFormatter : ValueFormatter() {
//        override fun getFormattedValue(value: Float, axis: AxisBase?): String {
//
//            return "$" + value
//        }
override fun getFormattedValue(value: Float): String {

    return "$" + value
}

    }

    class XAxisFormatter : ValueFormatter() {
//        override fun getFormattedValue(value: Float, axis: AxisBase?): String {
//
//            //   Log.d("XAxisFormatter",value.toString())
//            if (value == 5.0f) return "5 Feb";
//            if (value == 10.0f) return "15 Feb";
//            if (value == 30f) return "28 Feb";
//            return ""
//        }
        override fun getFormattedValue(value: Float): String {

            //   Log.d("XAxisFormatter",value.toString())
            if (value == 5.0f) return "5 Feb";
            if (value == 10.0f) return "15 Feb";
            if (value == 30f) return "28 Feb";
            return ""
        }

    }
}
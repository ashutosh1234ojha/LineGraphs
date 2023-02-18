package com.example.graphs.newchart

import android.graphics.Color
import android.os.Bundle
import android.util.LayoutDirection
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.graphs.R
import com.example.graphs.newchart.XAxis.XAxisPosition

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

        lineData.setValueFormatter(MyValueFormatter())

//        lineChart.layoutDirection=LayoutDirection.RTL
        lineChart.setDrawGridBackground(false)

        val desc = Description()
        desc.text = "asfasd"
        lineChart.description = desc

        lineChart.axisRight.isEnabled=false

        val xAxis = lineChart.xAxis
        xAxis.valueFormatter = XAxisFormatter()
        xAxis.position = XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
     //   xAxis.labelCount=12
        xAxis.granularity=5f




        val yAxisLeft = lineChart.axisLeft
        yAxisLeft.valueFormatter = YAxisFormatter()
        yAxisLeft.labelCount=12
        yAxisLeft.mSpacePercentBottom=0f  //Imp

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

    class MyValueFormatter : IValueFormatter {
        override fun getFormattedValue(
            value: Float,
            entry: Entry?,
            dataSetIndex: Int,
            viewPortHandler: ViewPortHandler?
        ): String {
            return "$ " + value
        }

    }

    class YAxisFormatter : IAxisValueFormatter {
        override fun getFormattedValue(value: Float, axis: AxisBase?): String {

            return "$"+value
        }

    }
    class XAxisFormatter : IAxisValueFormatter {
        override fun getFormattedValue(value: Float, axis: AxisBase?): String {

         //   Log.d("XAxisFormatter",value.toString())
            if(value==5f)  return "5 Feb";
            if(value==10f)  return "15 Feb";
            if(value==30f)  return "28 Feb";
            return ""
        }

    }
}
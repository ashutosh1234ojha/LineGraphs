//package com.example.graphs
//
//import android.graphics.Color
//import android.os.Bundle
//import androidx.appcompat.app.AppCompatActivity
//
//class MainActivity : AppCompatActivity() {
//    lateinit var chart: LineChart
//    lateinit var lineEntries: ArrayList<Entry>
//    lateinit var lineDataSet: LineDataSet
//    lateinit var lineData: LineData
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
////        chart = findViewById<LineChart>(R.id.chart1)
//
//
//    //    setData()
//
//        val graphView= findViewById<LineGraphViewKotlin>(R.id.chart1)
//        graphView.setAmountListLength(7)
//        graphView.setTimeListLength(8)
//
//
//    }
//
//    private fun setData() {
//        getEntries()
//
//        lineDataSet = LineDataSet(lineEntries, "")
//        lineData = LineData(lineDataSet)
//        chart.data = lineData
//        //  lineDataSet.setColors(ColorTemplate.JOYFUL_COLORS)
//        lineDataSet.valueTextColor = Color.BLACK
//        lineDataSet.valueTextSize = 18f
//        chart.setPinchZoom(true)
//        chart.setDrawGridBackground(true)
//
////        val l = chart.legend
////        l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
////        l.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
////        l.orientation = Legend.LegendOrientation.HORIZONTAL
////        l.setDrawInside(false)
////        l.form = Legend.LegendForm.SQUARE
////        l.formSize = 9f
////        l.textSize = 11f
////        l.xEntrySpace = 4f
//
//    }
//
//    private fun getEntries() {
//        lineEntries = ArrayList()
//        lineEntries.add(Entry(2f, 0f))
//        lineEntries.add(Entry(4f, 1f))
//        lineEntries.add(Entry(6f, 1f))
//        lineEntries.add(Entry(8f, 3f))
//        lineEntries.add(Entry(10f, 4f))
//        lineEntries.add(Entry(13f, 3f))
//    }
//
//}
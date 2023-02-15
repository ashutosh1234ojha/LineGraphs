//package com.example.graphs.experiment
//
//import android.graphics.Color
//import android.os.Bundle
//import androidx.appcompat.app.AppCompatActivity
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.example.graphs.R
//import com.example.graphs.line.CustomAdapter
//import com.github.mikephil.charting.charts.LineChart
//import com.github.mikephil.charting.components.Legend
//import com.github.mikephil.charting.data.Entry
//import com.github.mikephil.charting.data.LineData
//import com.github.mikephil.charting.data.LineDataSet
//
//
//class MyViewActivity : AppCompatActivity() {
//    lateinit var chart: LineChart
//    lateinit var lineEntries: ArrayList<Entry>
//    lateinit var lineDataSet: LineDataSet
//    lateinit var lineData: LineData
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_my_view)
//        val rv = findViewById(R.id.rv) as RecyclerView
//
//        rv.apply {
//            adapter = CustomAdapter()
//            layoutManager = LinearLayoutManager(this@MyViewActivity)
//        }
//
//    }
//
//
//
//}
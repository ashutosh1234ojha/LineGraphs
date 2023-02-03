package com.example.graphs.experiment

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.graphs.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet


class MyViewActivity : AppCompatActivity() {
    lateinit var chart: LineChart
    lateinit var lineEntries: ArrayList<Entry>
    lateinit var lineDataSet: LineDataSet
    lateinit var lineData: LineData


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_view)



    }



}
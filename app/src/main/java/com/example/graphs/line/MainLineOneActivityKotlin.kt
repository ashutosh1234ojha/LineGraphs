package com.example.graphs.line

import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.graphs.R

class MainLineOneActivityKotlin : AppCompatActivity() {


    var lineChartView: LineCharViewKotlin? = null

    //    private val dataArr = intArrayOf(
//        200, 100, 300, -20, 50, -80, 200, 100, 300, 50, 200, 150, 160, 100, 300, 50, 200, 150,
//        300, 50, 200, 100, 150, 150
//    )
    private val dataArr = intArrayOf(

        200, 100, 300, 20, 50, 80, 200, 100, 300, 50, 200, 150, 160, 100, 300, 50, 200, 150,
        300, 50, 200, 100, 150, 150
    )

    private val timeArr = arrayOf(

        "1 Jan", "2 Jan", "3 Jan", "4 Jan", "5 Jan", "6 Jan", "7 Jan", "8 Jan", "9 Jan", "10 Jan",
        "11 Jan", "12 Jan", "13 Jan", "14 Jan", "15 Jan", "16 Jan", "17 Jan", "18 Jan",
        "19 Jan", "20 Jan", "21 Jan", "22 Jan", "23 Jan", "24 Jan"
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_line_one)
        lineChartView = findViewById<View>(R.id.line_chart_view) as LineCharViewKotlin
        val rv = findViewById(R.id.rv) as RecyclerView

        val datas: MutableList<LineCharViewKotlin.Data> = ArrayList()
        val times: MutableList<LineCharViewKotlin.Time> = ArrayList()
        for (value in dataArr) {
            val data = LineCharViewKotlin.Data(value)
            datas.add(data)
        }
        for (value in timeArr) {
            val data = LineCharViewKotlin.Time(value)
            times.add(data)
        }
        lineChartView!!.setData(datas, times)
        lineChartView!!.setIsFilled(true)
        lineChartView!!.setRulerYSpace(30)
        lineChartView!!.setStepSpace(50)
        lineChartView!!.setPointTextVisibility(true)

        rv.apply {
            adapter = CustomAdapter()
            layoutManager = LinearLayoutManager(this@MainLineOneActivityKotlin)
        }


    }

}
package com.example.graphs.double

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.graphs.R

class MainLineOneActivityKotlin : AppCompatActivity() {


    var lineChartView: LineCharViewKotlin? = null


    private val dataArr = intArrayOf(
        100, 200, 300, 20, 50, 80, 200
    )

    private val timeArr = arrayOf(
        "1 Jan", "1 Jan", "3 Jan", "4 Jan", "5 Jan","4 Jan", "5 Jan"
    )
    private val timeArrUnique = arrayOf(
        "1 Jan", "3 Jan", "4 Jan", "5 Jan","4 Jan", "5 Jan"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_line_one_double)
        lineChartView = findViewById<View>(R.id.line_chart_view) as LineCharViewKotlin
        val datas: MutableList<LineCharViewKotlin.Data> = ArrayList()
        val times: MutableList<LineCharViewKotlin.Time> = ArrayList()
        val timesUnique: MutableList<LineCharViewKotlin.Time> = ArrayList()
        for (value in dataArr) {
            val data = LineCharViewKotlin.Data(value)
            datas.add(data)
        }
        for (value in timeArr) {
            val data = LineCharViewKotlin.Time(value)
            times.add(data)
        }

        for (value in timeArrUnique) {
            val data = LineCharViewKotlin.Time(value)
            timesUnique.add(data)
        }
    //    lineChartView!!.setData(datas, times,timesUnique)
        lineChartView!!.setIsFilled(true)
        lineChartView!!.setRulerYSpace(30)
        lineChartView!!.setStepSpace(50)
        lineChartView!!.setPointTextVisibility(true)


    }

}
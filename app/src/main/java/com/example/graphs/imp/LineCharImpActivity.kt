package com.example.graphs.imp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.graphs.R

class LineCharImpActivity : AppCompatActivity() {


    var lineChartView: LineCharImp? = null



    val dataArr = intArrayOf(
        200, 300, 100, 20, 50, 80, 200, 400, 500, 500, 40, 30, 30
    )

    val timeArr = arrayOf(
        "1 Jan",
        "1 Jan",
        "1 Jan",
        "2 Jan",
        "3 Jan",
        "4 Jan",
        "5 Jan",
        "6 Jan",
        "6 Jan",
        "7 Jan",
        "8 Jan",
        "9 Jan",
        "9 Jan"
    )
    val timeArrUnique = arrayOf(
        "1 Jan", "2 Jan", "3 Jan", "4 Jan", "5 Jan", "6 Jan", "7 Jan", "8 Jan", "9 Jan"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_line_one_double)
        lineChartView = findViewById<View>(R.id.line_chart_view) as LineCharImp
        val datas: MutableList<LineCharImp.Data> = ArrayList()
        val times: MutableList<LineCharImp.Time> = ArrayList()
        val timesUnique: MutableList<LineCharImp.Time> = ArrayList()
        for (value in dataArr) {
            val data = LineCharImp.Data(value)
            datas.add(data)
        }
        for (value in timeArr) {
            val data = LineCharImp.Time(value)
            times.add(data)
        }

        for (value in timeArrUnique) {
            val data = LineCharImp.Time(value)
            timesUnique.add(data)
        }
        lineChartView!!.setData(datas, times, timesUnique)
        lineChartView!!.setIsFilled(true)
        lineChartView!!.setRulerYSpace(30)
        lineChartView!!.setStepSpace(50)
        lineChartView!!.setPointTextVisibility(true)


    }

}
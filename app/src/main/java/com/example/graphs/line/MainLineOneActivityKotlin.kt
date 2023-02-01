package com.example.graphs.line

import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.graphs.R

class MainLineOneActivityKotlin :AppCompatActivity() {

    var tv_ruler_y: TextView? = null
    var sb_ruler_space: SeekBar? = null

    var tv_step_space: TextView? = null
    var sb_step_space: SeekBar? = null


    var lineChartView: LineCharViewKotlin? = null

    private val dataArr = intArrayOf(
        200, 100, 300, -20, 50, -80, 200, 100, 300, 50, 200, 150, 160, 100, 300, 50, 200, 150,
        300, 50, 200, 100, 150, 150
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_line_one)
        lineChartView = findViewById<View>(R.id.line_chart_view) as LineCharViewKotlin
        sb_ruler_space = findViewById<View>(R.id.sb_ruler_space) as SeekBar
        tv_ruler_y = findViewById<View>(R.id.tv_ruler_y) as TextView
        sb_step_space = findViewById<View>(R.id.sb_step_space) as SeekBar
        tv_step_space = findViewById<View>(R.id.tv_step_space) as TextView
        val datas: MutableList<LineCharViewKotlin.Data> = ArrayList()
        for (value in dataArr) {
            val data = LineCharViewKotlin.Data(value)
            datas.add(data)
        }
        lineChartView!!.setData(datas)
        sb_ruler_space!!.max = 70
        sb_ruler_space!!.progress = 20
        if (lineChartView != null) {
            lineChartView!!.setRulerYSpace(20)
            tv_ruler_y!!.text = 20.toString()
        }
        sb_ruler_space!!.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (lineChartView != null) {
                    lineChartView!!.setRulerYSpace(progress)
                    tv_ruler_y!!.text = progress.toString()
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
        sb_step_space!!.max = 70
        sb_step_space!!.progress = 15
        if (lineChartView != null) {
            lineChartView!!.setStepSpace(15)
            tv_step_space!!.text = 15.toString()
        }
        sb_step_space!!.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (lineChartView != null) {
                    lineChartView!!.setStepSpace(progress)
                    tv_step_space!!.text = progress.toString()
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
    }

}
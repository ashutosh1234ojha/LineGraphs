package com.example.graphs

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class LineGraphActivity :AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_line_graph)

        val lineGraphView = findViewById<LineGraphView>(R.id.chart1)
        val values = floatArrayOf(10f, 20f, 30f, 40f, 50f, 60f, 70f, 80f, 90f, 100f)
        val horLabels =
            arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct")
        val verLabels = arrayOf("0", "10", "20", "30", "40", "50", "60", "70", "80", "90", "100")
        lineGraphView.setValues(values)
        lineGraphView.setHorLabels(horLabels)
        lineGraphView.setVerLabels(verLabels)
    }
}
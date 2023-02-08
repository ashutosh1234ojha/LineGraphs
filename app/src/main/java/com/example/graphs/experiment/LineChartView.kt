package com.example.graphs.experiment

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class LineChartView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val dataArr = intArrayOf(0, 200, 100, 300, 20, 50, 80, 200, 100, 300, 50, 200, 150, 160, 100, 300, 50, 200, 150, 300, 50, 200, 100, 150, 150)
    private val timeArr = arrayOf("31 Dec", "1 Jan", "2 Jan", "3 Jan", "4 Jan", "5 Jan", "6 Jan", "7 Jan", "8 Jan", "9 Jan", "10 Jan", "11 Jan", "12 Jan", "13 Jan", "14 Jan", "15 Jan", "16 Jan", "17 Jan", "18 Jan", "19 Jan", "20 Jan", "21 Jan", "22 Jan", "23 Jan", "24 Jan")

    private val padding = 40f
    private val textSize = 14f

    private val linePaint = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 4f
    }

    private val axisPaint = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 2f
    }

    private val dottedPaint = Paint().apply {
        color = Color.GRAY
        style = Paint.Style.STROKE
        strokeWidth = 2f
        pathEffect = DashPathEffect(floatArrayOf(10f, 10f), 0f)
    }

    private val textPaint = Paint().apply {
        color = Color.BLACK
        textSize = this@LineChartView.textSize
    }

    private val textBounds = Rect()

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (canvas == null) return

        val maxData = dataArr.max() ?: 0
        val maxTextWidth = timeArr.map {
            textPaint.getTextBounds(it, 0, it.length, textBounds)
            textBounds.width()
        }.max() ?: 0

        val xStart = padding + maxTextWidth
        val xEnd = width.toFloat() - padding
        val yStart = height.toFloat() - padding
        val yEnd = padding

        // Draw X and Y axis
        canvas.drawLine(xStart, yStart, xStart, yEnd, axisPaint)
        canvas.drawLine(xStart, yStart, xEnd, yStart, axisPaint)

        // Draw dotted line parallel to X axis
        val path = Path()
        path.moveTo(xStart, yStart / 2)
        path.lineTo(xEnd, yStart / 2)

    }}
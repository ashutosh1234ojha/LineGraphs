package com.example.graphs.experiment

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.math.max
import kotlin.math.min

class LineChart @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var data = arrayOf<Int>()
    private var time = arrayOf<String>()
    private var minValue = 0f
    private var maxValue = 0f
    val padding = 100


    private val paint = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 4f
        isAntiAlias = true
    }

    private val gridPaint = Paint().apply {
        color = Color.GRAY
        style = Paint.Style.STROKE
        strokeWidth = 1f
    }

    init {
        setData()
    }

    private val axisPaint = Paint().apply {
        color = Color.GRAY
        style = Paint.Style.STROKE
        strokeWidth = 2f
        isAntiAlias = true
    }

    private val axisTextPaint = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.FILL
        textSize = 30f
        isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (data.isNotEmpty() && time.isNotEmpty()) {
            val width = width - padding * 2
            val height = height - padding * 2
            val intervalX = width.toFloat() / (time.size - 1)
            val intervalY = height.toFloat() / (maxValue - minValue)

            canvas?.drawLine(
                padding.toFloat(),
                height + padding.toFloat(),
                padding.toFloat(),
                padding.toFloat(),
                axisPaint
            )
            canvas?.drawLine(
                padding.toFloat(),
                height + padding.toFloat(),
                width + padding.toFloat(),
                height + padding.toFloat(),
                axisPaint
            )

            for (i in time.indices) {
                canvas?.drawText(
                    time[i],
                    padding + intervalX * i,
                    height + padding + 50f,
                    axisTextPaint
                )
            }

            for (i in 0 until maxValue.toInt() step 50) {
                canvas?.drawText(
                    i.toString(),
                    0f,
                    height - intervalY * (i - minValue) + padding,
                    axisTextPaint
                )
            }

            var startX = padding.toFloat()
            var startY = height - (data[0] - minValue) * intervalY + padding
            for (i in 1 until data.size) {
                val stopX = startX + intervalX
                val stopY = height - (data[i] - minValue) * intervalY + padding
                canvas?.drawLine(startX, startY, stopX, stopY, paint)
                startX = stopX
                startY = stopY
            }
        }

        linnes(canvas)
    }

    private fun linnes(canvas: Canvas?) {
        val xUnit = width / (time.size - 1)
        val yUnit = height / 300
        for (i in 0..time.size) {
            val x = i * xUnit.toFloat()
            canvas!!.drawLine(x, 0f, x.toFloat(), height.toFloat(), gridPaint)
        }

        for (i in 0..300 step 50) {
            val y = height - i * yUnit.toFloat()
            canvas!!.drawLine(0f, y, width.toFloat(), y, gridPaint)
        }
    }



    fun setData() {
        data = arrayOf(0,
            200, 100, 300, 20, 50,
        )

       time = arrayOf("31 Dec",
            "1 Jan", "2 Jan", "3 Jan", "4 Jan", "5 Jan",
        )
        minValue = 0f
        maxValue = 300f
    }
}

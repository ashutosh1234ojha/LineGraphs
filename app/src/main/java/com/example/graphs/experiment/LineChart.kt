package com.example.graphs.experiment

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
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
    private var tablePadding = 20
    private val tablePaddingDP = 20

    private val pointWidthDefault = 8f
    private var pointWidthDP = pointWidthDefault
    private val pointColor = Color.parseColor("#000080")


    private val paint = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 4f
        isAntiAlias = true
    }

    val pointPaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        color = pointColor
        strokeWidth = dip2px(pointWidthDP).toFloat()
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
                    height - intervalY * (i - minValue) + padding + 5,
                    axisTextPaint
                )
            }
            val pointWidth = (dip2px(pointWidthDP) / 2).toFloat()

            var startX = padding.toFloat()
            var startY = height - (data[0] - minValue) * intervalY + padding + 5
            for (i in 1 until data.size) {
                val stopX = startX + intervalX
                val stopY = height - (data[i] - minValue) * intervalY + padding + 5
                canvas?.drawLine(startX, startY, stopX, stopY, paint)
                canvas?.drawCircle(startX, startY, pointWidth, pointPaint)

                startX = stopX
                startY = stopY
            }
            canvas?.drawCircle(startX, startY, pointWidth, pointPaint) //for last point

        }

        linnes(canvas)
    }

    private fun dip2px(dipValue: Float): Int {
        val scale = resources.displayMetrics.density
        return (dipValue * scale + 0.5f).toInt()
    }

    private fun linnes(canvas: Canvas?) {
        val xUnit = width / (time.size - 1)
        val yUnit = height / maxValue.toInt()
//        for (i in 0..time.size) {
//            val x = i * xUnit.toFloat()
//            canvas!!.drawLine(x, 0f, x.toFloat(), height.toFloat(), gridPaint)
//        }

        for (i in 0..maxValue.toInt() step 50) {
            val y = height - i * yUnit.toFloat()
            Log.d("Tag", "Y " + y)
            //   if(i!=1){
            canvas!!.drawLine(100f, y, width.toFloat(), y, gridPaint)

        }
//        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val xUnit = width / (time.size - 1)

        val stepEnd = 2000 + xUnit * (time.size - 1)
        val width = tablePadding + stepEnd + paddingLeft + paddingRight
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        var height = MeasureSpec.getSize(heightMeasureSpec)
//        if (MeasureSpec.EXACTLY == heightMode) {
//            height += paddingTop + paddingBottom
//        }
        setMeasuredDimension(width, height)
    }


    fun setData() {
        data = arrayOf(
            0,
            200, 100, 300, 20, 50, 200, 100, 300, 20, 50, 500
        )

        time = arrayOf(
            "31 Dec",
            "1 Jan", "2 Jan", "3 Jan", "4 Jan", "5 Jan", "31 Dec",
            "1 Jan", "2 Jan", "3 Jan", "4 Jan", "5 Jan", "6 Jan"
        )
        minValue = 0f
        maxValue = 700f
    }
}

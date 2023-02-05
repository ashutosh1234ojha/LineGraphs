package com.example.graphs.experiment

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View


class MyView @JvmOverloads
constructor(
    private val ctx: Context,
    private val attributeSet: AttributeSet? = null,
    private val defStyleAttr: Int = 0
) : View(ctx, attributeSet, defStyleAttr) {

    var path = Path()
    var line = Path()
    var fill = Path()
    var pathPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#ffffff")
        style = Paint.Style.STROKE
        strokeWidth = 10f

    }
    var points = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#ffffff")
        style = Paint.Style.STROKE
        strokeWidth = 10f

    }
    var dark = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#FF0000")

//        style = Paint.Style.STROKE
//        strokeWidth = 10f

    }
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawCurvedPath(canvas)
    }

    private fun drawCurvedPath(canvas: Canvas?) {
        val centerX=0.2f * width
        val centerY=0.5f * height
        canvas?.translate(centerX, centerY)

        path.moveTo(0f, -(0.5.toFloat()*height.toFloat()-100))
        path.lineTo(0f, 0f)
        path.lineTo(0.9f * width, 0f)
//        path.lineTo(0.9f * width, 0.5f * height)
//        path.lineTo(0.9f * width, 0.1f * height)
//
//        path.lineTo(0.1f * width, 0.1f * height)

        canvas?.drawPath(path, pathPaint)

        line.moveTo(5f, -(0.5.toFloat()*height.toFloat()-200))
        line.lineTo(200f, -(0.5.toFloat()*height.toFloat()-500))
        line.lineTo(300f, -(0.5.toFloat()*height.toFloat()-400))
        canvas?.drawPath(line, points)

     //   fill.moveTo(5f, -(0.5.toFloat()*height.toFloat()-200))

        fill.lineTo(300f-50, -(0.5.toFloat()*height.toFloat()-500))
        fill.lineTo(50f, -(0.5.toFloat()*height.toFloat()-500))
     //   fill.lineTo(300f, -(0.5.toFloat()*height.toFloat()-400))
      //  fill.lineTo(5f, 0f)

        canvas?.drawPath(fill, dark)






    }

}
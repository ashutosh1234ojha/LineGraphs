package com.example.graphs

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.widget.ScrollView
import kotlin.math.max
import kotlin.math.min

class LineGraphViewKotlin(context: Context?, attrs: AttributeSet) : ScrollView(context, attrs) {
    // Paint object for coloring and styling
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    var w: Int = 0
    var h: Int = 0
    var amountListSize:Int=0
    var timeListSize:Int=0

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawGraph(canvas)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        h = max(measuredWidth, measuredHeight)
        w = min(measuredWidth, measuredHeight)

    }

    private fun drawGraph(canvas: Canvas?) {

        // 1
        paint.color = Color.YELLOW
        paint.strokeWidth = 2f
        paint.style = Paint.Style.FILL_AND_STROKE

        val x=w.toFloat()-5
        val y=h.toFloat()-5

        canvas?.drawLine(50f, 50f, 50f, y, paint);
        canvas?.drawLine(50f, y, w.toFloat() - 5, y, paint);
        paint.color = Color.RED
        paint.textSize = 30f

        val incrementAmount= (y+100)/amountListSize
        var next=y;

        for (i in 0..amountListSize) {
            canvas?.drawText("$i", 10f, next, paint);
            next-=incrementAmount
        }

        val incrementTime= (x+100)/timeListSize
        var nextTime=incrementTime;

        for (i in 1..timeListSize) {
            canvas?.drawText("$i", nextTime,y , paint);
            nextTime+=incrementTime
        }

    }

    fun setAmountListLength(n:Int){
        this.amountListSize=n;
    }

    fun setTimeListLength(n:Int){
        this.timeListSize=n;
    }

}
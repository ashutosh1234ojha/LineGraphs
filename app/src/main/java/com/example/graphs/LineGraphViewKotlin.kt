package com.example.graphs

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class LineGraphViewKotlin(context: Context?, attrs: AttributeSet) : View(context, attrs) {
    // Paint object for coloring and styling
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    // Some colors for the face background, eyes and mouth.
    private var faceColor = Color.YELLOW
    private var eyesColor = Color.BLACK
    private var mouthColor = Color.BLACK
    private var borderColor = Color.BLACK

    // Face border width in pixels
    private var borderWidth = 4.0f

    // View size in pixels
    private var size = 320
    private var size1 = 320
    var w: Int = 0
    var h: Int = 0

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawFaceBackground(canvas)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        h = Math.max(measuredWidth, measuredHeight)
        w = Math.min(measuredWidth, measuredHeight)

    }

    private fun drawFaceBackground(canvas: Canvas?) {

        // 1
        paint.color = Color.YELLOW
        paint.strokeWidth = 2f
        paint.style = Paint.Style.FILL_AND_STROKE

        val x=50f
        val y=h.toFloat()-5

        canvas?.drawLine(50f, 50f, 50f, y, paint);
        canvas?.drawLine(50f, y, w.toFloat() - 5, y, paint);
        paint.color = Color.RED
        paint.textSize = 30f

        val increment= (y+100)/5
        var next=y;

        for (i in 0..5) {
            canvas?.drawText("$i", 10f, next, paint);
            next-=increment

        }

    }

}
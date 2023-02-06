package com.example.graphs.my

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import java.util.*


class LineCharViewKotlinMy @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    View(context, attrs, defStyleAttr) {
    private var linePaint: Paint? = null
    private var pointPaint: Paint? = null
    private var tablePaint: Paint? = null
    private var textRulerPaint: Paint? = null
    private var textPointPaint: Paint? = null
    private var mainPath: Path? = null
    private var linePath: Path? = null
    private var tablePath: Path? = null
    private var mWidth = 0
    private var mHeight = 0
    private val dataList: MutableList<Data> = ArrayList()
    private val timeList: MutableList<Time> = ArrayList()
    private var linePoints: Array<Point?>? = null
    private var stepStart = 0
    private var stepEnd = 0
    private var stepSpace = 0
    private val stepSpaceDefault = 50
    private var stepSpaceDP = stepSpaceDefault
    private var topSpace = 0
    private var bottomSpace = 0
    private var tablePadding = 0
    private val tablePaddingDP = 20
    private var maxValue = 0
    private var minValue = 0
    private val rulerValueDefault = 30
    private var rulerValue = rulerValueDefault
    private var rulerValuePadding = 0
    private val rulerValuePaddingDP = 8
    private val heightPercent = 0.618f
    private val lineColor = Color.parseColor("#000080")
    private val lineWidthDP = 2f
    private val pointColor = Color.parseColor("#000080")
    private val pointWidthDefault = 8f
    private var pointWidthDP = pointWidthDefault
    private val tableColor = Color.parseColor("#BBBBBB")
    private val tableWidthDP = 0.5f
    private val rulerTextColor = tableColor
    private val rulerTextSizeSP = 10f
    private val pointTextColor = Color.parseColor("#000000")
    private val pointTextSizeSP = 10f
    private val isShowTable = true
    private val isBezierLine = false // false = line and true=  curve
    private var isCubePoint = false
    private var isInitialized = false
    private var isPlayAnim = false
    lateinit var valueAnimator: ValueAnimator
    private var currentValue = 0f
    private var isAnimating = false
    private var fillPaint: Paint? = null
    private var fillPath: Path? = null

    init {
        setupView()
    }

    private fun setupView() {
        linePaint = Paint()
        linePaint!!.isAntiAlias = true
        linePaint!!.style = Paint.Style.STROKE
        linePaint!!.color = lineColor
        linePaint!!.strokeWidth = dip2px(lineWidthDP).toFloat()
        pointPaint = Paint()
        pointPaint!!.isAntiAlias = true
        pointPaint!!.style = Paint.Style.FILL
        pointPaint!!.color = pointColor
        pointPaint!!.strokeWidth = dip2px(pointWidthDP).toFloat()
        tablePaint = Paint()
        tablePaint!!.isAntiAlias = true
        tablePaint!!.style = Paint.Style.STROKE
        tablePaint!!.color = tableColor
        tablePaint!!.strokeWidth = dip2px(tableWidthDP).toFloat()
        textRulerPaint = Paint()
        textRulerPaint!!.isAntiAlias = true
        textRulerPaint!!.style = Paint.Style.FILL
        textRulerPaint!!.textAlign = Paint.Align.CENTER
        textRulerPaint!!.color = rulerTextColor
        textRulerPaint!!.textSize = sp2px(rulerTextSizeSP).toFloat()
        textPointPaint = Paint()
        textPointPaint!!.isAntiAlias = true
        textPointPaint!!.style = Paint.Style.FILL
        textPointPaint!!.textAlign = Paint.Align.CENTER
        textPointPaint!!.color = pointTextColor
        textPointPaint!!.textSize = sp2px(pointTextSizeSP).toFloat()
        mainPath = Path()
        linePath = Path()
        tablePath = Path()


        fillPaint = Paint()
        fillPaint!!.isAntiAlias = true
        fillPaint!!.style = Paint.Style.FILL
        fillPaint!!.color = Color.parseColor("#BFD7ED")  // desired fill color
        fillPath = Path()
        resetParam()
    }


    private fun resetParam() {
        linePath!!.reset()
        tablePath!!.reset()
        mainPath!!.reset()
        stepSpace = dip2px(stepSpaceDP.toFloat())
        tablePadding = dip2px(tablePaddingDP.toFloat())
        rulerValuePadding = dip2px(rulerValuePaddingDP.toFloat())
        stepStart = tablePadding * if (isShowTable) 2 else 1
        stepEnd = stepStart + stepSpace * (dataList.size - 1)
        bottomSpace = tablePadding
        topSpace = bottomSpace
        linePoints = arrayOfNulls(dataList.size)
        isInitialized = false
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = tablePadding + tableEnd + paddingLeft + paddingRight
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        var height = MeasureSpec.getSize(heightMeasureSpec)
        if (MeasureSpec.EXACTLY == heightMode) {
            height = paddingTop + paddingBottom + height
        }
        setMeasuredDimension(width, height)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(Color.TRANSPARENT)
        canvas.translate(
            0f,
            mHeight / 2f + (viewDrawHeight + topSpace + bottomSpace) / 2f
        )
        if (!isInitialized) {
            setupLine() //X- axis
        }
        if (isShowTable) {
            drawTable(canvas)
        }
        drawLine(canvas)
        drawLinePoints(canvas)
        drawFill(canvas)
    }
    private fun drawFill(canvas: Canvas?) {
//        fillPath!!.reset()
//        fillPath?.addPath(linePath!!)
//        canvas?.drawPath(fillPath!!, fillPaint!!)

        val  offset = (-getValueHeight(minValue - if (minValue > 0) 0 else minValue % rulerValue)+rulerValueDefault).toFloat()
        fillPath!!.reset()
        fillPath?.moveTo(linePoints!![0]!!.x.toFloat(),offset)
        linePoints?.forEach {
            fillPath?.lineTo(it?.x!!.toFloat(),it?.y!!.toFloat())

        }
        fillPath?.lineTo(linePoints!![linePoints!!.size-1]!!.x.toFloat(),offset)


        canvas?.drawPath(fillPath!!, fillPaint!!)


    }


    private fun drawText(canvas: Canvas, textPaint: Paint?, text: String, x: Float, y: Float) {
        canvas.drawText(text, x, y, textPaint!!)
    }


    private fun drawRulerYText(canvas: Canvas, text: String, x: Float, y: Float) {
        textRulerPaint!!.textAlign = Paint.Align.RIGHT
        val fontMetrics = textRulerPaint!!.fontMetrics
        val fontTotalHeight = fontMetrics.bottom - fontMetrics.top
        val offsetY = fontTotalHeight / 2 - fontMetrics.bottom
        val newY = y + offsetY
        val newX = x - rulerValuePadding
        drawText(canvas, textRulerPaint, text, newX, newY)
    }


    private fun drawRulerXText(canvas: Canvas, text: String, x: Float, y: Float) {
        textRulerPaint!!.textAlign = Paint.Align.CENTER
        val fontMetrics = textRulerPaint!!.fontMetrics
        val fontTotalHeight = fontMetrics.bottom - fontMetrics.top
        val offsetY = fontTotalHeight / 2 - fontMetrics.bottom
        val newY = y + offsetY + rulerValuePadding
        drawText(canvas, textRulerPaint, text, x, newY)
    }


    private fun drawLinePointText(canvas: Canvas, text: String, x: Float, y: Float) {
        textPointPaint!!.textAlign = Paint.Align.CENTER
        val newY = y - rulerValuePadding*3
        drawText(canvas, textPointPaint, text, x, newY)
    }

    private val tableStart: Int
        private get() = if (isShowTable) stepStart + tablePadding else stepStart
    private val tableEnd: Int
        private get() = if (isShowTable) stepEnd + tablePadding else stepEnd


    private fun drawTable(canvas: Canvas) {
        val tableEnd = tableEnd
        val rulerCount = maxValue / rulerValue
        val rulerMaxCount = if (maxValue % rulerValue > 0) rulerCount + 1 else rulerCount
        val rulerMax = rulerValue * rulerMaxCount + rulerValueDefault
        tablePath!!.moveTo(stepStart.toFloat(), -getValueHeight(rulerMax).toFloat())
        tablePath!!.lineTo(stepStart.toFloat(), -20f)
        tablePath!!.lineTo(tableEnd.toFloat(), -20f)
        var startValue = rulerValueDefault - if (rulerValueDefault > 0) 0 else rulerValueDefault % rulerValue
        var startValueTemp = minValue - if (minValue > 0) 0 else minValue % rulerValue
        val endValue = maxValue + rulerValue



        do {
            val startHeight = -getValueHeight(startValue)
            tablePath!!.moveTo(stepStart.toFloat(), startHeight.toFloat())
            tablePath!!.lineTo(tableEnd.toFloat(), startHeight.toFloat())

            drawRulerYText(
                canvas,
                "$" + startValue.toString(),
                stepStart.toFloat(),
                startHeight.toFloat()
            )
            startValue += rulerValue
        } while (startValue < endValue)
        canvas.drawPath(tablePath!!, tablePaint!!)

        drawRulerYText(
            canvas,
            "$0",
            stepStart.toFloat(),
            -getValueHeight(startValueTemp).toFloat() + rulerValueDefault
        )
        drawRulerXValue(canvas)
    }


    private fun drawRulerXValue(canvas: Canvas) {
        if (linePoints == null) return
        for (i in linePoints!!.indices) {
            val point = linePoints!![i] ?: break
            val text1 = timeList[i];
            drawRulerXText(canvas, text1.value, linePoints!![i]!!.x.toFloat(), 0f)
//            drawRulerXText(canvas, i.toString(), linePoints!![i]!!.x.toFloat(), 0f)
        }
    }


    /**
     * This draw the lines of the
     */
    private fun drawLine(canvas: Canvas) {
        if (isPlayAnim) {
            val dst = Path()
            val measure = PathMeasure(linePath, false)
            measure.getSegment(0f, currentValue * measure.length, dst, true)
            canvas.drawPath(dst, linePaint!!)
        } else {
            canvas.drawPath(linePath!!, linePaint!!)
        }
    }


    private fun drawLinePoints(canvas: Canvas) {
        if (linePoints == null) return
        val pointWidth = (dip2px(pointWidthDP) / 2).toFloat()
        var pointCount = linePoints!!.size
        if (isPlayAnim) {
            pointCount = Math.round(currentValue * linePoints!!.size)
        }
        for (i in 0 until pointCount) {
            val point = linePoints!![i] ?: break
            if (isCubePoint) {
                canvas.drawPoint(point.x.toFloat(), point.y.toFloat(), pointPaint!!)
            } else {
                canvas.drawCircle(point.x.toFloat(), point.y.toFloat(), pointWidth, pointPaint!!)
            }

            drawLinePointText(
                canvas,
                dataList[i].value.toString(),
                point.x.toFloat(),
                point.y.toFloat()
            )
        }
    }


    private fun getValueHeight(value: Int): Int {
        val valuePercent =
            Math.abs(value - minValue) * 100f / (Math.abs(maxValue - minValue) * 100f)
        return (viewDrawHeight * valuePercent + bottomSpace + 0.5f).toInt()
    }


    private val viewDrawHeight: Float
        private get() = measuredHeight * heightPercent


    private fun setupLine() {
        if (dataList.isEmpty()) return
        var stepTemp = tableStart
        var pre = Point()
        pre[stepTemp] = -getValueHeight(dataList[0].value)
        linePoints!![0] = pre
        linePath!!.moveTo(pre.x.toFloat(), pre.y.toFloat())
        if (dataList.size == 1) {
            isInitialized = true
            return
        }
        for (i in 1 until dataList.size) {
            val data = dataList[i]
            val next = Point()
            next[stepSpace.let { stepTemp += it; stepTemp }] = -getValueHeight(data.value)
            if (isBezierLine) {
                val cW = pre.x + stepSpace / 2
                val p1 = Point()
                p1[cW] = pre.y
                val p2 = Point()
                p2[cW] = next.y
                linePath!!.cubicTo(
                    p1.x.toFloat(),
                    p1.y.toFloat(),
                    p2.x.toFloat(),
                    p2.y.toFloat(),
                    next.x.toFloat(),
                    next.y.toFloat()
                )
            } else {
                linePath!!.lineTo(next.x.toFloat(), next.y.toFloat())
            }
            pre = next
            linePoints!![i] = next
        }
        isInitialized = true
    }

    private fun dip2px(dipValue: Float): Int {
        val scale = resources.displayMetrics.density
        return (dipValue * scale + 0.5f).toInt()
    }

    private fun sp2px(spValue: Float): Int {
        val fontScale = resources.displayMetrics.scaledDensity
        return (spValue * fontScale + 0.5f).toInt()
    }

    private fun refreshLayout() {
        resetParam()
        requestLayout()
        postInvalidate()
    }


    fun setData(dataList: List<Data>?, timeList: List<Time>?) {
        if (dataList == null || timeList == null) {
            throw RuntimeException("dataList cannot is null!")
        }
        if (dataList.isEmpty()) return
        if (timeList.isEmpty()) return
        this.dataList.clear()
        this.timeList.clear()
        this.dataList.addAll(dataList)
        this.timeList.addAll(timeList)
        maxValue = Collections.max(
            this.dataList
        ) { o1, o2 -> o1.value - o2.value }.value
        minValue = Collections.min(
            this.dataList
        ) { o1, o2 -> o1.value - o2.value }.value
        refreshLayout()
    }


//    fun setCubePoint(isCube: Boolean) {
//        isCubePoint = isCube
//        refreshLayout()
//    }


//    fun setRulerYSpace(space: Int) {
//        var space = space
//        if (space <= 0) {
//            space = rulerValueDefault
//        }
//        rulerValue = space
//        refreshLayout()
//    }
//
//
//    fun setStepSpace(dp: Int) {
//        var dp = dp
//        if (dp < stepSpaceDefault) {
//            dp = stepSpaceDefault
//        }
//        stepSpaceDP = dp
//        refreshLayout()
//    }

    class Data(var value: Int)
    class Time(var value: String)
}
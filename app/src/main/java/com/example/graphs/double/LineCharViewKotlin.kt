package com.example.graphs.double

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import java.util.*


class LineCharViewKotlin @JvmOverloads constructor(
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
    private val filledColor = Color.parseColor("#BFD7ED")
    private val pointTextSizeSP = 10f
    private val isShowTable = true
    private var isInitialized = false
    private var isPlayAnim = false
    private var isFilled = false
    private var currentValue = 0f
    private var isPointTextVisible = false
    private var fillPaint: Paint? = null
    private var fillPath: Path? = null

    private val dataList = mutableMapOf<String, MutableList<Int>>()
    val timeList: MutableList<Time> = ArrayList()
    val dataList1: MutableList<Data> = ArrayList()

    init {
        setupView()
    }

    private fun setupView() {
        linePaint = Paint().apply {
            isAntiAlias = true
            style = Paint.Style.STROKE
            color = lineColor
            strokeWidth = dip2px(lineWidthDP).toFloat()
        }

        pointPaint = Paint().apply {
            isAntiAlias = true
            style = Paint.Style.FILL
            color = pointColor
            strokeWidth = dip2px(pointWidthDP).toFloat()
        }

        tablePaint = Paint().apply {
            isAntiAlias = true
            style = Paint.Style.STROKE
            color = tableColor
            strokeWidth = dip2px(tableWidthDP).toFloat()
        }

        textRulerPaint = Paint().apply {
            isAntiAlias = true
            style = Paint.Style.FILL
            color = rulerTextColor
            strokeWidth = dip2px(tableWidthDP).toFloat()
            textAlign = Paint.Align.CENTER
            textSize = sp2px(rulerTextSizeSP).toFloat()
        }

        textPointPaint = Paint().apply {
            isAntiAlias = true
            style = Paint.Style.FILL
            textAlign = Paint.Align.CENTER
            color = pointTextColor
            textSize = sp2px(pointTextSizeSP).toFloat()
        }
        fillPaint = Paint().apply {
            isAntiAlias = true
            style = Paint.Style.FILL
            color = filledColor
        }


        mainPath = Path()
        linePath = Path()
        tablePath = Path()
        fillPath = Path()

         val dataArr = intArrayOf(
            200, 200, 100, 20, 50, 80, 200
        )

         val timeArr = arrayOf(
            "1 Jan", "1 Jan", "1 Jan", "3 Jan", "4 Jan","5 Jan", "6 Jan"
        )
         val timeArrUnique = arrayOf(
             "1 Jan", "3 Jan", "4 Jan","5 Jan", "6 Jan"        )

        val datas: MutableList<LineCharViewKotlin.Data> = ArrayList()
        val times: MutableList<LineCharViewKotlin.Time> = ArrayList()
        val timesUnique: MutableList<LineCharViewKotlin.Time> = ArrayList()
        for (value in dataArr) {
            val data = LineCharViewKotlin.Data(value)
            datas.add(data)
        }
        for (value in timeArr) {
            val data = LineCharViewKotlin.Time(value)
            times.add(data)
        }

        for (value in timeArrUnique) {
            val data = LineCharViewKotlin.Time(value)
            timesUnique.add(data)
        }
       setData(datas, times,timesUnique)


        resetParam()
    }

    /**
     * stepStart= if isShowTable=true double the padding
     * stepEnd== stepStart+ (total space between each element of x-axis)
     * linePoints= here we created a empty Point list with null values
     */
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
        linePoints = arrayOfNulls(dataList1.size)  //Total values
        isInitialized = false
    }

    /**
     * Add padding to width and height
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = tablePadding + tableEnd + paddingLeft + paddingRight
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        var height = MeasureSpec.getSize(heightMeasureSpec)
        if (MeasureSpec.EXACTLY == heightMode) {
            height += paddingTop + paddingBottom
        }
        setMeasuredDimension(width, height)
    }

    /**
     * After onMeasure changes this method  is called as we have changed the width and height
     */
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h
    }

    /**
     * Here we move our canvas from (0,0) to new position to center the graph and make calculation easy
     */
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(Color.TRANSPARENT)
        canvas.translate(
            0f,
            mHeight / 2f + (viewDrawHeight + topSpace + bottomSpace) / 2f
        )
        if (!isInitialized) {
            setupLine()
        }
        if (isShowTable) {
            drawTable(canvas)
        }
        drawLine(canvas)

        drawLinePoints(canvas)

//        if (isFilled)
//            drawFill(canvas)
    }

//    private fun drawFill(canvas: Canvas?) {
//        val offset =
//            (-getValueHeight(minValue - if (minValue > 0) 0 else minValue % rulerValue) + rulerValue).toFloat()
//        fillPath!!.reset()
//        fillPath?.moveTo(linePoints!![0]!!.x.toFloat(), offset)
//        linePoints?.forEach {
//            fillPath?.lineTo(it?.x!!.toFloat(), it.y.toFloat())
//
//        }
//        fillPath?.lineTo(linePoints!![linePoints!!.size - 1]!!.x.toFloat(), offset)
//
//
//        canvas?.drawPath(fillPath!!, fillPaint!!)
//
//
//    }

    /**
     * Draw text on canvas on the given coordinate
     */
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
        val newY = y - rulerValuePadding * 3
        drawText(canvas, textPointPaint, text, x, newY)
    }

    /**
     * If isShowTable=true we have to add tablePadding in tableStart and tableEnd
     */
    private val tableStart: Int
        private get() = if (isShowTable) stepStart + tablePadding else stepStart
    private val tableEnd: Int
        private get() = if (isShowTable) stepEnd + tablePadding else stepEnd


    /**
     * It draws the table of the graphs
     */
    private fun drawTable(canvas: Canvas) {
        val tableEnd = tableEnd
        val rulerCount = maxValue / rulerValue
        val rulerMaxCount = if (maxValue % rulerValue > 0) rulerCount + 1 else rulerCount
        val rulerMax = rulerValue * rulerMaxCount + rulerValue
        tablePath!!.moveTo(
            stepStart.toFloat(),
            -getValueHeight(rulerMax).toFloat()
        ) //Move to left top of graph
        tablePath!!.lineTo(
            stepStart.toFloat(),
            -20f
        ) // draw y-axis, here  y value can be adjusted  as per need
        tablePath!!.lineTo(
            tableEnd.toFloat(),
            -20f
        )// draw x-axis and here  y value can be adjusted  as per need
        var startValue = rulerValue - if (rulerValue > 0) 0 else rulerValue % rulerValue
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

        //This Y text will draw $0
        drawRulerYText(
            canvas,
            "$0",
            stepStart.toFloat(),
            -getValueHeight(startValueTemp).toFloat() + rulerValueDefault
        )
        drawRulerXValue(canvas)
    }

    /**
     * Draw x-axis values
     */
    private fun drawRulerXValue(canvas: Canvas) {
        if (linePoints == null) return
        for (i in linePoints!!.indices) {
            val point = linePoints!![i] ?: break
         //   val text1 = timeList[i];
            drawRulerXText(canvas, "time", linePoints!![i]!!.x.toFloat(), 0f)
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

    /**
     * Draw points with value
     */
    private fun drawLinePoints(canvas: Canvas) {
        if (linePoints == null) return
        val pointWidth = (dip2px(pointWidthDP) / 2).toFloat()
        var pointCount = linePoints!!.size
        if (isPlayAnim) {
            pointCount = Math.round(currentValue * linePoints!!.size)
        }
        for (i in 0 until pointCount) {
            val point = linePoints!![i] ?: break
            canvas.drawCircle(point.x.toFloat(), point.y.toFloat(), pointWidth, pointPaint!!)
            if (isPointTextVisible) {
                drawLinePointText(
                    canvas,
                    dataList1[i].value.toString(),
                    point.x.toFloat(),
                    point.y.toFloat()
                )
            }

        }
    }


    /**
     * valuePercent= percentage of height  taken by @value
     * After this we apply this valuePercent to viewDrawHeight to get the position (y-axis) of the
     * given @value
     */
    private fun getValueHeight(value: Int): Int {
        val valuePercent =
            Math.abs(value - minValue) * 100f / (Math.abs(maxValue - minValue) * 100f)
        return (viewDrawHeight * valuePercent + bottomSpace + 0.5f).toInt()
    }

    /**
     * measuredHeight is the height of the view and heightPercent is a hit and trail value to get
     * the height of the view
     */
    private val viewDrawHeight: Float
        private get() = measuredHeight * heightPercent

    /**
     *  pre= it contains the cordinates of the 0th index
     *  after this we set all the value from 1st index till last to linePoints array
     */
    private fun setupLine() {
        if (dataList.isEmpty()) return
        var stepTemp = tableStart
        val list = dataList.get(key = timeList[0].value)
//        var pre = Point(stepTemp, -getValueHeight(list!![0]))
//        linePoints!![0] = pre
//        linePath!!.moveTo(pre.x.toFloat(), pre.y.toFloat())
        var i = 0
        var count=1;
        list!!.forEach {
            Log.d("CounntCounnt",count.toString())

            var pre = Point(stepTemp, -getValueHeight(it))
            linePoints!![i] = pre
            if(i==0){
                linePath!!.moveTo(pre.x.toFloat(), pre.y.toFloat())

            }else{
                linePath!!.lineTo(pre.x.toFloat(), pre.y.toFloat())

            }
            i++
            count++;
        }

        if (timeList.size == 1) {
            isInitialized = true
            return
        }
        var c=1;
        var c1=1;
        for (j in 1 until timeList.size) {
            //  val data = dataList[i]
            val list1 = dataList.get(key = timeList[j].value)
            Log.d("List",list1!!.size.toString())
         stepTemp=   stepSpace.let { stepTemp += it; stepTemp }
            list1!!.forEach {
                Log.d("Counnt",c.toString())

                Log.d("CounntCounnt",count.toString())

                val next =
                    Point(stepTemp, -getValueHeight(it))
                linePath!!.lineTo(next.x.toFloat(), next.y.toFloat())
                linePoints!![i] = next
                i++
                count++;
                c++;
            }
        }
        isInitialized = true
    }

    /**
     * Converts dp value to px
     */
    private fun dip2px(dipValue: Float): Int {
        val scale = resources.displayMetrics.density
        return (dipValue * scale + 0.5f).toInt()
    }

    /**
     * Converts sp value to px
     */
    private fun sp2px(spValue: Float): Int {
        val fontScale = resources.displayMetrics.scaledDensity
        return (spValue * fontScale + 0.5f).toInt()
    }

    private fun refreshLayout() {
        resetParam()
        requestLayout()
        postInvalidate()
    }

    /**
     * This method is called to set x and  y-axis data
     * Here maxValue and minValue of y-axis  data is calculated to get the top and bottom most values of the  graph
     */



    fun setData(
        dataList1: MutableList<Data>?,
        timeList1: MutableList<Time>?,
        timeListUnique: MutableList<Time>?
    ) {
//         val dataList: MutableList<Data> = ArrayList()
//         val timeList: MutableList<Time> = ArrayList()
        if (timeListUnique == null || dataList1 == null) {
            throw RuntimeException("dataList cannot is null!")
        }
//        if (dataList.isEmpty()) return
        if (timeListUnique.isEmpty()) return
//        this.dataList.clear()
        this.timeList.clear()
        this.dataList1.clear()
//        this.dataList.addAll(dataList)
        this.timeList.addAll(timeListUnique)
        this.dataList1.addAll(dataList1)
//        maxValue = Collections.max(
//            this.dataList
//        ) { o1, o2 -> o1.value - o2.value }.value
//        minValue = Collections.min(
//            this.dataList
//        ) { o1, o2 -> o1.value - o2.value }.value
//        refreshLayout()

        for (i in timeList1!!.indices) {
            if (dataList.containsKey(timeList1[i].value)) {
                dataList[timeList1[i].value!!]!!.add(dataList1[i]!!.value)
            } else {
                dataList[timeList1[i].value] = mutableListOf(dataList1[i].value)
            }
        }

        maxValue = Collections.max(
            dataList1
        ) { o1, o2 -> o1.value - o2.value }.value
        minValue = Collections.min(
            dataList1
        ) { o1, o2 -> o1.value - o2.value }.value
        refreshLayout()
    }

    /**
     * Is area below line curve  filled
     */
    fun setIsFilled(isFilled: Boolean) {
        this.isFilled = isFilled
        refreshLayout()
    }

    /**
     * Space between each line of Y-axis
     */
    fun setRulerYSpace(space: Int) {
        rulerValue = space
        refreshLayout()
    }

    /**
     * Space between each element of X-axis
     */
    fun setStepSpace(dp: Int) {
        stepSpaceDP = dp
        refreshLayout()
    }

    fun setPointTextVisibility(isPointTextVisible: Boolean) {
        this.isPointTextVisible = isPointTextVisible
        refreshLayout()
    }

    /**
     * Y-axis  data
     */
    class Data(var value: Int)

    /**
     * X-axis  data
     */
    class Time(var value: String)
}
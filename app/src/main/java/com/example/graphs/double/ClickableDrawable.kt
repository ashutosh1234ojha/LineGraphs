package com.example.graphs.double

import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.PixelFormat
import android.graphics.Rect
import android.graphics.drawable.Drawable

abstract class ClickableDrawable : Drawable() {

//    private var callback: Callback? = null

    abstract fun containsPoint(x: Float, y: Float): Boolean

    abstract fun onClick()

    override fun setAlpha(alpha: Int) {}

    override fun setColorFilter(colorFilter: ColorFilter?) {}

    override fun getOpacity(): Int {
        return PixelFormat.TRANSPARENT
    }

    override fun getIntrinsicWidth(): Int {
        return 0
    }

    override fun getIntrinsicHeight(): Int {
        return 0
    }

    override fun draw(canvas: Canvas) {
        onDraw(canvas)
    }

    abstract fun onDraw(canvas: Canvas)

    override fun setBounds(bounds: Rect) {
        super.setBounds(bounds)
    }

    override fun setBounds(left: Int, top: Int, right: Int, bottom: Int) {
        super.setBounds(left, top, right, bottom)
    }

//    override fun getBounds(): Rect {
//        return Rect(super.getBounds())
//    }
//
//    override fun setCallback(cb: Callback) {
//        callback = cb
//    }

    override fun unscheduleSelf(what: Runnable) {
        callback?.unscheduleDrawable(this, what)
    }

    override fun invalidateSelf() {
        callback?.invalidateDrawable(this)
    }

    override fun scheduleSelf(what: Runnable, `when`: Long) {
        callback?.scheduleDrawable(this, what, `when`)
    }
}

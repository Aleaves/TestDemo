package com.app.testdemo.star

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View

import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import kotlin.math.abs


class StarTextView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attributeSet, defStyleAttr) {

    private val mPaint: Paint = Paint()

    private var mWidth: Int
    private var mHeight: Int
    private val mBaseLineHeight: Float

    private var text = "幸运+1"


    init {
        mPaint.isAntiAlias = true
        mPaint.textSize = 48f
        mWidth = mPaint.measureText(text).toInt()
        mHeight = (mPaint.fontMetrics.bottom - mPaint.fontMetrics.top).toInt()
        mBaseLineHeight = mPaint.fontMetrics.top
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(mWidth, mHeight)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val mLinearGradient = LinearGradient(
            0f,
            0f,
            mWidth.toFloat(),
            0f,
            intArrayOf(Color.parseColor("#FF8818"), Color.parseColor("#FF500A")),
            null,
            Shader.TileMode.REPEAT
        )
        mPaint.shader = mLinearGradient
        canvas.drawText(
            text,
            0f, abs(mBaseLineHeight),
            mPaint
        )
    }

}
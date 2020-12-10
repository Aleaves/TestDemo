package com.app.testdemo.progress

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.app.testdemo.R

class VerticalProgressView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attributeSet, defStyleAttr) {

    private val mPaint: Paint = Paint()
    private val mBgPaint: Paint = Paint()

    private val defaultWidth: Int
    private val defaultHeight: Int

    private var mWidth: Int
    private var mHeight: Int

    private var mStrokeWidth: Int

    private val mStartColor : Int
    private val mEndColor :Int


    init {
        mPaint.isAntiAlias = true
        mBgPaint.isAntiAlias = true
        defaultWidth = resources.getDimensionPixelOffset(R.dimen.progress_with)
        defaultHeight = resources.getDimensionPixelOffset(R.dimen.progress_height)
        mWidth = defaultWidth
        mHeight = defaultHeight
        mStrokeWidth = resources.getDimensionPixelOffset(R.dimen.progress_stroke)
        val a = getContext().obtainStyledAttributes(attributeSet, R.styleable.VerticalProgressStyle)
        mBgPaint.color = a.getColor(R.styleable.VerticalProgressStyle_outColor, Color.RED)
        mStartColor = a.getColor(R.styleable.VerticalProgressStyle_startColor,Color.RED);
        mEndColor = a.getColor(R.styleable.VerticalProgressStyle_endColor,Color.RED);
        a.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(mWidth, mHeight)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)


        canvas.drawRoundRect(
            0f,
            0f,
            mWidth.toFloat(),
            mHeight.toFloat(),
            mWidth.toFloat() / 2.toFloat(),
            mWidth.toFloat() / 2.toFloat(),
            mBgPaint
        )

        val linearGradient = LinearGradient(
            mStrokeWidth.toFloat(),
            mStrokeWidth.toFloat(),
            mStrokeWidth.toFloat(),
            (defaultHeight - mStrokeWidth).toFloat(),
            intArrayOf(mStartColor, mEndColor),
            null,
            Shader.TileMode.REPEAT
        )

        mPaint.shader = linearGradient
        canvas.drawRoundRect(
            mStrokeWidth.toFloat(),
            mStrokeWidth.toFloat(),
            mWidth.toFloat() - mStrokeWidth,
            mHeight.toFloat() - mStrokeWidth,
            (mWidth.toFloat() - mStrokeWidth * 2) / 2.toFloat(),
            (mWidth.toFloat() - mStrokeWidth * 2) / 2.toFloat(),
            mPaint
        )

        mPaint.shader = null

    }

    private var percent: Double = 0.0

    fun setProgress(percent: Double) {
        //进度条最大值 1
        this.percent = percent
        if (percent >= 1) {
            this.percent = 1.0
        }
        mHeight = (defaultHeight * this.percent).toInt()
        //重新测量大小 重绘
        requestLayout()
    }


}
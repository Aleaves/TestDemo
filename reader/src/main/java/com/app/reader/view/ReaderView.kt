package com.app.reader.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.FrameLayout

class ReaderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    FrameLayout(context, attrs, defStyleAttr) {

    private var mBgColor = -0x313d64
    private var mPaint: Paint = Paint()

    private var mCurBitmap: Bitmap

    private var mNextBitmap: Bitmap

    init {
        setWillNotDraw(false)
        mPaint.isAntiAlias = true
        mPaint.color = Color.RED
        mPaint.textSize = 50f

        mCurBitmap = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888)
        mNextBitmap = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(mBgColor)
        canvas.drawBitmap(mCurBitmap, 0f, 0f, null)
        canvas.drawBitmap(mNextBitmap, 0f, 0f, null)
    }

    fun drawCurPage() {
        val canvas = Canvas(mCurBitmap)
        canvas.drawColor(Color.GREEN)
        canvas.drawText("我的世界",0f,100f,mPaint)
    }

    fun drawNextPage(){
        val canvas = Canvas(mNextBitmap)
        canvas.drawColor(Color.BLUE)
    }

}
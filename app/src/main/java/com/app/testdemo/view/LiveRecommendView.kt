package com.cootek.livemodule.widget

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import com.app.testdemo.R
import kotlinx.android.synthetic.main.layout_live_recommend.view.*

/**
 * 主播推荐作品UI
 */
class LiveRecommendView(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) :
    FrameLayout(context, attributeSet, defStyleAttr) {

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)
    constructor(context: Context) : this(context, null, 0)


    init {
        View.inflate(context, R.layout.layout_live_recommend, this)
    }

    fun getTextHeight() {
        val str = tv3.text
        val rect = Rect()
        tv3.paint.getTextBounds(str, 0, str.length, rect)
        Log.i("=======", (rect.right - rect.left).toString())
        Log.i("=======", (rect.bottom - rect.top).toString())

        tv4.measure(0,0)

        Log.i("=======", tv4.measuredWidth.toString())
        Log.i("=======", tv4.measuredHeight.toString())
    }


}
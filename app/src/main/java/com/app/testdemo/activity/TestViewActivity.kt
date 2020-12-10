package com.app.testdemo.activity

import android.graphics.Color
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.app.testdemo.R
import com.cootek.livemodule.widget.LiveRecommendView
import kotlinx.android.synthetic.main.activity_test_view.*
import org.w3c.dom.Text

class TestViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_view)

        val text = TextView(this)
        text.text = "111"
        text.textSize = 18f

        val str = "玩弄文化刚刚好"
        var bounds = Rect()
        text.paint.getTextBounds("玩弄文化刚刚好",0,str.length,bounds)
        //val a = m.bottom - m.top
        Log.i("======",(bounds.right - bounds.left).toString() )
        Log.i("======",(bounds.bottom - bounds.top).toString() )

        fl_container.addView(text)
        text.layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,FrameLayout.LayoutParams.WRAP_CONTENT)
        text.post {
            Log.i("======1",text.measuredWidth.toString())
            Log.i("======1",text.measuredHeight.toString())
        }
    }

    fun star(view: View) {
        val liveView = LiveRecommendView(this)
        liveView.getTextHeight()
    }
}

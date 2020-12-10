package com.app.reader

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.app.reader.test.MyProduct
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val str = "浊者  "
        Log.i("=====",str.length.toString())
        val pro = MyProduct()
        Log.i("======",pro.getName())
    }

    fun addText(view: View) {
        readerView.drawCurPage()
    }

    fun addText1(view: View) {
        readerView.drawNextPage()
    }
}

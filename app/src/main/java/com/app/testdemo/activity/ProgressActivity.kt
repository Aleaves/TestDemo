package com.app.testdemo.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.app.testdemo.R
import kotlinx.android.synthetic.main.activity_progress.*

class ProgressActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progress)
        Log.i("======","1111")
    }

    private var p : Double = 0.0

    fun add(view: View) {
        p += 0.1
        verticalViewRed.setProgress(p)
    }
}

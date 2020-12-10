package com.app.floatview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        bt_float.setOnClickListener {
            FloatWindowManager.getInstance().applyOrShowFloatWindow(this)
        }
        bt_dismiss.setOnClickListener {
            FloatWindowManager.getInstance().dismissWindow()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}

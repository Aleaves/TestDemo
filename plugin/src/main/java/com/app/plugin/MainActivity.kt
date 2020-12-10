package com.app.plugin

import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("========","我是插件")
        Log.i("=======",resources.getString(R.string.test_name))
        setContentView(R.layout.activity_t)
        //tv_content.text = resources.getString(R.string.app_name)
        //Log.i("=======",Test().getAge().toString())
    }
}

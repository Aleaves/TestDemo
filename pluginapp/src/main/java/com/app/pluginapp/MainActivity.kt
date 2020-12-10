package com.app.pluginapp

import android.content.ComponentName
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bt_start.setOnClickListener {
            startActivity(Intent(this, TestActivity::class.java))
        }
        Log.i("=======", externalCacheDir?.absolutePath.toString())
        bt_plugin.setOnClickListener {
            val intent = Intent()
            intent.component = ComponentName("com.app.plugin", "com.app.plugin.MainActivity")
            startActivity(intent)
        }
        bt_method.setOnClickListener {
            val test = Class.forName("com.app.plugin.Test")
            val t = test.newInstance()
            val getNameMethod = test.getDeclaredMethod("getAge")
            val name = getNameMethod.invoke(t)
            Log.i("=======",name.toString())
        }
    }

}

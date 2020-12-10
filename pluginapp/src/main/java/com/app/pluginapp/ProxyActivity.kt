package com.app.pluginapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ProxyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_proxy)
        val testIntent = intent.getParcelableExtra<Intent>("actionIntent")
    }
}

package com.app.floatview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bt_btn_float.setOnClickListener {
            startFloatingVideoService()
        }

        bt_next.setOnClickListener {
            startActivity(Intent(this, TestActivity::class.java))
        }

        bt_dismiss.setOnClickListener {
            FloatWindowManager.getInstance().dismissWindow()
        }

    }

    private fun startFloatingVideoService() {
//        if(!Settings.canDrawOverlays(this)){
//            Toast.makeText(this,"当前无权限，请授权", Toast.LENGTH_SHORT).show()
//            startActivityForResult(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,Uri.parse("package:" + getPackageName())), 2)
//        }else{
//            startService(Intent(this,FloatingButtonService::class.java))
//        }
        FloatWindowManager.getInstance().applyOrShowFloatWindow(this)
    }

}

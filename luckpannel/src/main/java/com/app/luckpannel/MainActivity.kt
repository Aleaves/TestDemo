package com.app.luckpannel

import android.animation.ValueAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.app.luckpannel.luck.LuckRotateListener
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import java.text.DecimalFormat


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        wheelLuckView.setRotateListener(object : LuckRotateListener {
            override fun rotateEnd(position: Int, des: String) {
                Toast.makeText(this@MainActivity, "结束了 位置：$position   描述：$des", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun rotating(valueAnimator: ValueAnimator) {

            }

            override fun rotateBefore() {

                wheelLuckView.startRotate()
            }
        })

        bt_reward.setOnClickListener {
            //模拟位置
//            val position = Random().nextInt(6)
//            wheelLuckView.updateRewardResult(position)
            val text = et_test.text
            Log.i("==========",text.toString())

        }

        for(i in 10 downTo  0 step 2){
            Log.i("======",i.toString())
        }

    }

    private fun getStr(count: Int) {
        val numStr = DecimalFormat("#.0").format(count / 10000.0)
        Log.i("=====", numStr)
    }
}

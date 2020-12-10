package com.app.testdemo.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.app.testdemo.R
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_cons_layout.*
import java.util.concurrent.TimeUnit

class ConsLayoutActivity : AppCompatActivity() {

    companion object {
        const val TAG = "ConsLayoutActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cons_layout)
        red_layout.setOnClickListener {
            Log.i(TAG, "red")
        }
        blue_layout.setOnClickListener {
            Log.i(TAG, "blue")
        }
//        test1()
        test2()
    }


    private fun test1() {
        Observable.interval(5, 1, TimeUnit.SECONDS)
            .subscribeBy(
                onComplete = {
                    println("onComplete")
                },
                onError = {
                    println("onError")
                },
                onNext = {
                    println(it)

                })

    }

    private fun test2(){
        Observable.timer(3,TimeUnit.SECONDS)
            .subscribeBy(
                onNext = {
                    println(it)
                },
                onComplete = {
                    println("complete")
                }
            )

    }
}

package com.app.testdemo.proxy

import android.util.Log

class RealSubject : Subject {
    override fun visit() {
        Log.i("=======", "2222")
    }

    override fun play(str: String): String = "${str}${str}"
}
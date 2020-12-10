package com.app.testplugin

import android.app.Application

class HookApp : Application() {


    override fun onCreate() {
        super.onCreate()

    }

    private fun hookAmsAction(){
        val mActivityTaskManager   = Class.forName("android.app.ActivityTaskManager")

    }

}
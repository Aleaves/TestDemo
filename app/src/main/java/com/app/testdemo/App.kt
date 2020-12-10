package com.app.testdemo

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import java.lang.reflect.Proxy
import android.content.Intent
import com.app.testdemo.activity.PluginActivity


class App : Application() {


    override fun onCreate() {
        super.onCreate()

        try {
            hookAmsAction()
        } catch (e: Exception) {
            Log.i("=====", "hook 失败")
        }

    }

    @SuppressLint("PrivateApi", "DiscouragedPrivateApi")
    private fun hookAmsAction() {

        val mIActivityManagerClass = Class.forName("android.app.IActivityManager")

        val mActivityManagerNativeClass2 = Class.forName("android.app.ActivityManagerNative")
        val mIActivityManager = mActivityManagerNativeClass2.getMethod("getDefault").invoke(null)


        val mIActivityManagerProxy = Proxy.newProxyInstance(
            classLoader, arrayOf(mIActivityManagerClass)
        ) { proxy, method, args ->
            if ("startActivity" == method.name) {
                // 做自己的业务逻辑
                // 换成 可以 通过 AMS检查的 ProxyActivity

                // 用ProxyActivity 绕过了 AMS检查
                val intent = Intent(this, PluginActivity::class.java)
                intent.putExtra("actionIntent", args[2] as Intent) // 把之前TestActivity保存 携带过去
                args[2] = intent
            }
            return@newProxyInstance method.invoke(mIActivityManager, args)
        }


        val mActivityManagerNativeClass = Class.forName("android.app.ActivityManagerNative")
        val gDefaultField = mActivityManagerNativeClass.getDeclaredMethod("getDefault")
        gDefaultField.isAccessible = true
        val gDefault = gDefaultField.invoke(null)

        val mSingletonClass = Class.forName("android.util.Singleton")
        val mInstanceField = mSingletonClass.getDeclaredField("mInstance")
        mInstanceField.isAccessible = true


        mInstanceField.set(gDefault, mIActivityManagerProxy)
    }

}
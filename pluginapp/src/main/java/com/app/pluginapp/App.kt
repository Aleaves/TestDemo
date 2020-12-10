package com.app.pluginapp

import android.app.Application
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.AssetManager
import android.content.res.Resources
import android.os.Handler
import android.os.Message
import android.util.Log
import java.lang.Exception
import java.lang.reflect.Proxy


class App : Application() {
    private val TAG = "=========="

    private lateinit var dexElementFuse: DexElementFuse
    lateinit var loadApkUse: LoadApkUse

    override fun onCreate() {
        super.onCreate()

        //在AMS之前  替换可用的Activity  绕过manifest检测
        try {

            hookAmsAction()

            hookLuanchActivity()

            dexElementFuse = DexElementFuse()
            dexElementFuse.loadResource(this)
//
//            dexElementFuse.mainPluginFuse(this)

            // loadapk式
            loadApkUse = LoadApkUse()
            loadApkUse.customLoadedApkAction(this)

        } catch (e: Exception) {
            Log.i(TAG, "hook失败")
        }

    }

    private fun hookAmsAction() {

        val mIActivityManagerClass = Class.forName("android.app.IActivityManager")

        val mActivityManagerNativeClass2 = Class.forName("android.app.ActivityManagerNative")
        val mIActivityManager =
            mActivityManagerNativeClass2.getDeclaredMethod("getDefault").invoke(null)


        val mIActivityManagerProxy = Proxy.newProxyInstance(
            classLoader, arrayOf(mIActivityManagerClass)
        ) { proxy, method, args ->
            Log.i(TAG, method.name)
            if ("startActivity".equals(method.name)) {
                val intent = Intent(this@App, ProxyActivity::class.java)
                intent.putExtra("actionIntent", args[2] as Intent)
                args[2] = intent
            }
            return@newProxyInstance method?.invoke(mIActivityManager, *args.orEmpty())
        }


        val mActivityManagerNativeClass = Class.forName("android.app.ActivityManagerNative")
        val gDefaultField = mActivityManagerNativeClass.getDeclaredField("gDefault")
        gDefaultField.isAccessible = true
        val gDefault = gDefaultField.get(null)

        val mSingletonClass = Class.forName("android.util.Singleton")
        val mInstanceField = mSingletonClass.getDeclaredField("mInstance")
        mInstanceField.isAccessible = true


        mInstanceField.set(gDefault, mIActivityManagerProxy)


    }

    //在启动之前  换回来以前的Activity
    private fun hookLuanchActivity() {

        val mCallbackFiled = Handler::class.java.getDeclaredField("mCallback")
        mCallbackFiled.isAccessible = true

        val mActivityThreadClass = Class.forName("android.app.ActivityThread")
        val mActivityThread = mActivityThreadClass.getMethod("currentActivityThread").invoke(null)
        val mHField = mActivityThreadClass.getDeclaredField("mH")
        mHField.isAccessible = true

        val mH = mHField.get(mActivityThread) as Handler

        mCallbackFiled.set(mH, MyCallback(mH))

    }


    inner class MyCallback(val mH: Handler) : Handler.Callback {
        override fun handleMessage(msg: Message): Boolean {

            when (msg.what) {
                100 -> {
                    val obj = msg.obj
                    val intentField = obj.javaClass.getDeclaredField("intent")
                    intentField.isAccessible = true

                    val intent = intentField.get(obj) as Intent
                    val actionIntent = intent.getParcelableExtra<Intent>("actionIntent")
                    if (actionIntent != null) {
                        intentField.set(obj, actionIntent)


                        val activityInfoField = obj.javaClass.getDeclaredField("activityInfo")
                        activityInfoField.isAccessible = true
                        val activityInfo = activityInfoField.get(obj) as ActivityInfo

                        if (actionIntent.`package` == null) {
                            //说明是插件
                            activityInfo.applicationInfo.packageName =
                                actionIntent.component?.packageName

                            loadApkUse.hookGetPackageInfo(this@App)
                        } else {
                            activityInfo.applicationInfo.packageName = actionIntent.`package`
                        }

                    }
                }
            }
            //或者返回false 系统会往下执行   或者主动调用handleMessage
            mH.handleMessage(msg)
            // 让系统继续正常往下执行
            //return false;
            return true; // 系统不会往下执行
        }

    }


    private fun hookAmsAction1() {

        val mActivityTaskManagerClass = Class.forName("android.app.ActivityTaskManager")


        val mIActivityManagerSingletonField =
            mActivityTaskManagerClass.getDeclaredField("IActivityTaskManagerSingleton")


        mIActivityManagerSingletonField.isAccessible = true
        val mIActivityManagerSingleton = mIActivityManagerSingletonField.get(null)


//        val mIActivityManagerProxy = Proxy.newProxyInstance(
//            classLoader, arrayOf(mActivityManagerClass)
//        ) { proxy, method, args ->
//            Log.i(TAG, method.name)
//            if ("startActivity".equals(method.name)) {
//                val intent = Intent(this@App, ProxyActivity::class.java)
//                intent.putExtra("actionIntent", args[2] as Intent)
//                args[2] = intent
//            }
//            return@newProxyInstance method?.invoke(mIActivityManager, *args.orEmpty())
//        }


    }

    override fun getResources(): Resources {
        if (dexElementFuse.resources != null) {
            return dexElementFuse.resources
        }
        return super.getResources()
    }


}
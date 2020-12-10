package com.app.testdemo.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.app.testdemo.R
import com.app.testdemo.proxy.*
import kotlinx.android.synthetic.main.activity_plugin.*
import java.lang.reflect.Proxy

class PluginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plugin)
        bt_hook.setOnClickListener {
            Toast.makeText(this, (it as Button).text, Toast.LENGTH_SHORT).show()
        }

        hook()

        bt_proxy.setOnClickListener {
            testProxy()
            //testProxy1()
        }
    }

    @SuppressLint("DiscouragedPrivateApi", "PrivateApi")
    private fun hook() {

        val mViewClass = Class.forName("android.view.View")
        val getListenerInfoMethod = mViewClass.getDeclaredMethod("getListenerInfo")
        getListenerInfoMethod.isAccessible = true
        val mListenerInfo = getListenerInfoMethod.invoke(bt_hook)

        val mListenerInfoClass = Class.forName("android.view.View\$ListenerInfo")
        val mOnClickListenerField = mListenerInfoClass.getField("mOnClickListener")
        val mOnClickListenerObj = mOnClickListenerField.get(mListenerInfo)

        val mOnClickListenerProxy = Proxy.newProxyInstance(
            classLoader, arrayOf(View.OnClickListener::class.java)
        ) { proxy, method, args ->


            val button = Button(this)
            button.text = "12333"
            return@newProxyInstance method.invoke(mOnClickListenerObj, button)
        }

        mOnClickListenerField.set(mListenerInfo, mOnClickListenerProxy)

    }

    private fun testProxy() {
//        val realSubject = RealSubject()
//        val subject = Proxy.newProxyInstance(
//            classLoader, arrayOf(Subject::class.java)
//        ) { proxy, method, args ->
//            method.invoke(proxy, args)
//        } as Subject
//        subject.visit()

        val realSubject = RealSubject()
        val proxyHandler = ProxyHandler(realSubject)
        val p = Proxy.newProxyInstance(classLoader, arrayOf(Subject::class.java),proxyHandler) as Subject

        p.visit()
        Log.i("=======",p.play("3232"))

    }

    private fun testProxy1() {
        val hello = Hello()

        val handler = HelloHandler(hello)

        val proxyHello = Proxy.newProxyInstance(
            classLoader,
            arrayOf(HelloInterface::class.java),
            handler
        ) as HelloInterface

        proxyHello.sayHello()
    }
}

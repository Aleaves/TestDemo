package com.app.testdemo.proxy
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
class ProxyHandler(var proxyObj: Any) : InvocationHandler {

    override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any? {
        return method?.invoke(proxy,*args.orEmpty())
    }

}
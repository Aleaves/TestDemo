package com.app.testdemo.proxy

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

class DynamicProxyHandler :InvocationHandler{
    private var traget: Any

    constructor(target: Any){
        this.traget = target
    }


    override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any? {
        println("执行之前....")
        //由于传来的参数是不确定的，这里用*args.orEmpty()传参
        val result = method?.invoke(traget,*args.orEmpty())

        println("执行之后....")

        return result
    }
}
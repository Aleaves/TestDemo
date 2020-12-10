package com.app.testdemo

class B(var name: String, var age: Int = 18) {

    fun setOnNetCallBack(onNetCallBack: (Int) -> Unit) {
        onNetCallBack(23)
    }

    fun setOnItemCliked(onItemClicked: (String) -> String) {
        val callString = onItemClicked("12344")
        println(callString)
    }

    fun setOnItemClicked1(onItemClicked: (String, Int) -> Unit) {
        onItemClicked("kang", 12)
    }

    var add: (Int, Int) -> Int = { a, b -> a + b }

    var add1 = { a: Int, b: Int -> a + b }

}
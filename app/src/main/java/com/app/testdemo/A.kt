package com.app.testdemo

class A {

    var name: String = ""
    var age: Int = 0

    constructor(name: String, age: Int) {
        this.name = name
        this.age = age

    }

    fun setOnClickListener(onCliked:()->Void){
        onCliked()
    }
}
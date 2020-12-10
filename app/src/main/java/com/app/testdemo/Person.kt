package com.app.testdemo

class Person {
    var name: String = "1"
        get() = field + "_"
        set(value) {
            field = value + "_"
        }

}
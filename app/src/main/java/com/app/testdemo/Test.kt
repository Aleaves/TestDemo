package com.app.testdemo

import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.subscribeBy
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import java.text.DateFormat
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.function.Consumer
import kotlin.math.abs

fun main() {
//    println("122222")
//    test1()
//    test2()
//    test3()
//    test4()
//    test6()
//    test7()
//    test8()
//    test10()
//    test11()
//    test12()
    test13()
}

fun test1() {
    val p = Person()
    println(p.name)
    p.name = "33333"
    println(p.name)
}

fun test2() {
    val a = A("jack", 18)
    println(a.name)
    println(a.age)
}

fun test3() {
    val b = B("kangkang", age = 30)
    println("${b.name}===${b.age}")
    b.setOnNetCallBack {
        println(it)
    }
    b.setOnItemCliked {
        println(it)
        return@setOnItemCliked "11111"
    }
    b.setOnItemClicked1 { a: String, b: Int ->
        println(a + b)
    }
    b.setOnItemClicked1 { s, i ->
        println(s + i)
    }
}

fun test4() {
    GlobalScope.launch {
        println("11111")
        val result = fetchData()
        println("2222")
        println(result)
    }
}

private suspend fun fetchData(): String {
    delay(2000)
    println("=======")
    return "net content"
}

fun test5() {
    val x = 1
    var result = when {
        x in 1..10 -> true
        else -> false
    }
}

fun test6() {
    val maybeValue: Maybe<Int> = Maybe.just(14)
    maybeValue.subscribeBy(
        onComplete = {
            println("onComplete")
        },
        onError = {
            println("onError")
        },
        onSuccess = {
            println(it)
        }
    )
}

fun test7() {

    Observable.create<String> {
        it.onNext("Hello Rx!")
        it.onComplete()
    }.subscribeBy(
        onComplete = {
            println("onComplete")
        },
        onError = {
            println("onError")
        },
        onNext = {
            println(it)
        }
    )

    Observable.create<Int> {
        it.onNext(1)
        it.onNext(2)
        it.onNext(3)
        it.onComplete()
    }.subscribe {
        println(it)
    }
}

fun test8() {
    Observable.just(1, 2, 3, 4)
        .map {
            when (it) {
                1 -> "test1"
                2 -> "test2"
                3 -> "test3"
                4 -> "test4"
                else -> "none"
            }
        }.subscribe {
            println(it)
        }
}

fun test9() {
    Observable.just(1, 2, 3, 4)
        .flatMap {
            getInfoById(it)
        }.subscribe {
            println(it)
        }
}

fun getInfoById(id: Int): Observable<Int> = Observable.just(id)


fun test10() {
    val ob = Observable.just(1, 2, 3, 4)
    //println(System.currentTimeMillis())
    ob.filter {
        it > 2
    }.subscribe {
        println(it)
    }
    //println(System.currentTimeMillis())
    ob.subscribe {
        println(it)
    }


    val date = Date(1602753324000)

    DateFormat.getInstance()
}

fun test11() {
    val currentTime = System.currentTimeMillis()
    val beforeTime = 1602864000000

    val flag = abs(beforeTime - currentTime) <= 2 * 24 * 3600 * 1000

    println(flag)
}

fun test12() {

    val calendar = Calendar.getInstance()
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    println(day)
    calendar.time = Date(1602939593000)
    // 给定时间2天前
    calendar.add(Calendar.DAY_OF_YEAR, -2)
    val twoDay = calendar.get(Calendar.DAY_OF_MONTH)
    println(twoDay)

}

fun test13() {
    println(13)
    Observable.interval(1, 1, TimeUnit.SECONDS)
        .subscribeBy(
            onComplete = {
                println("onComplete")
            },
            onError = {
                println("onError")
            },
            onNext = {
                println(it)
            }
        )
}

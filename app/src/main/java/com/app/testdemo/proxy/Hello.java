package com.app.testdemo.proxy;

import android.util.Log;

public class Hello implements HelloInterface{
    @Override
    public void sayHello() {
        Log.i("=======","sayhellow");
    }
}

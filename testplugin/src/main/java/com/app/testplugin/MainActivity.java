package com.app.testplugin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;

import com.morgoo.droidplugin.PluginHelper;
import com.morgoo.droidplugin.PluginPatchManager;
import com.morgoo.droidplugin.core.PluginClassLoader;
import com.morgoo.droidplugin.pm.PluginManager;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import me.weishu.reflection.Reflection;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.bt_hide_api).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test();
            }
        });
        findViewById(R.id.bt_reflect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Reflection.unseal(MainActivity.this);
            }
        });
        findViewById(R.id.bt_install).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    PluginManager.getInstance().installPackage(App.pluginPath,0);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        findViewById(R.id.bt_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    PluginManager.getInstance().deletePackage("com.shenghe.wzcq.samsung",0);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        findViewById(R.id.bt_start_pay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent();
                    intent.setComponent(new ComponentName("com.shenghe.wzcq.samsung", "com.sdk.sxdemo.MainActivity"));
                    startActivity(intent);
//                    intent.setComponent(new ComponentName("com.app.plugin", "com.app.plugin.MainActivity"));
//                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //startActivity(new Intent(MainActivity.this,TestActivity.class));

//                PackageManager pm = getPackageManager();
//
//                Intent intent = pm.getLaunchIntentForPackage("com.sdk.sxdemo");
//
//                if (intent == null){
//                    Log.d("sss","intent是空的，没法使用啊");
//                }
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//                startActivity(intent);
            }
        });
        //PluginPatchManager.getInstance().startPluginActivity()
        Log.i("=====",getExternalCacheDir().getAbsolutePath());
    }

    private void test() {
        try {

            Class<?> activityClass = Class.forName("dalvik.system.VMRuntime");
            Method field = activityClass.getDeclaredMethod("setHiddenApiExemptions", String[].class);
            field.setAccessible(true);

            Log.i("=====", "call success!!");
        } catch (Throwable e) {
            Log.e("====", "error:", e);

        }
    }
}

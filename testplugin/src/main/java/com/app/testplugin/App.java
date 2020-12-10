package com.app.testplugin;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;

import com.morgoo.droidplugin.PluginHelper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class App extends Application {

    public static String pluginPath = "/storage/emulated/0/Android/data/com.app.testplugin/cache/pulgin1.apk";

    //DexElementFuse dexElementFuse;

    @Override
    public void onCreate() {
        super.onCreate();
        PluginHelper.getInstance().applicationOnCreate(getBaseContext());
        // TODO 同学们只需要知道，下面这行代码，可以拿到 插件的路径
        //pluginPath = ApkCopyAssetsToDir.copyAssetToCache(this, Parameter.PLUGIN_FILE_NAME);
//        Log.i("======1",pluginPath);
//        try {
//            AMSCheckEngine.mHookAMS(this);
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
//        try {
//            ActivityThreadmHRestore.mActivityThreadmHAction(this);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        // TODO 进行融合
//        dexElementFuse = new DexElementFuse();
//        try {
//            dexElementFuse.mainPluginFuse(this);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

//    @Override
//    public Resources getResources() {
//        return dexElementFuse.getResources() == null ? super.getResources() : dexElementFuse.getResources();
//    }

    @Override
    protected void attachBaseContext(Context base) {
        PluginHelper.getInstance().applicationAttachBaseContext(getBaseContext());
        super.attachBaseContext(base);
    }

    /**
     * 要在执行 AMS之前，替换可用的 Activity，替换在AndroidManifest里面配置的Activity
     */
    private void hookAmsAction() throws Exception {

        // 动态代理
        Class mIActivityManagerClass = Class.forName("android.app.IActivityManager");

        // 我们要拿到IActivityManager对象，才能让动态代理里面的 invoke 正常执行下
        // 执行此方法 static public IActivityManager getDefault()，就能拿到 IActivityManager
        Class mActivityManagerNativeClass2 = Class.forName("android.app.ActivityManagerNative");
        final Object mIActivityManager = mActivityManagerNativeClass2.getMethod("getDefault").invoke(null);

        // 本质是IActivityManager
        Object mIActivityManagerProxy = Proxy.newProxyInstance(

                App.class.getClassLoader(),

                new Class[]{mIActivityManagerClass}, // 要监听的接口

                new InvocationHandler() { // IActivityManager 接口的回调方法

                    /**
                     * @param proxy
                     * @param method IActivityManager里面的方法
                     * @param args IActivityManager里面的参数
                     * @return
                     * @throws
                     */

                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                        if ("startActivity".equals(method.getName())) {
                            // 做自己的业务逻辑
                            // 换成 可以 通过 AMS检查的 ProxyActivity

                            // 用ProxyActivity 绕过了 AMS检查
                            Intent intent = new Intent(App.this, MainActivity.class);
                            intent.putExtra("actionIntent", ((Intent) args[2])); // 把之前TestActivity保存 携带过去
                            args[2] = intent;
                        }

                        Log.d("hook", "拦截到了IActivityManager里面的方法" + method.getName());

                        // 让系统继续正常往下执行
                        return method.invoke(mIActivityManager, args);
                    }
                });

        /**
         * 为了拿到 gDefault
         * 通过 ActivityManagerNative 拿到 gDefault变量(对象)
         */
        Class mActivityManagerNativeClass = Class.forName("android.app.ActivityManagerNative");
        Method method = mActivityManagerNativeClass.getMethod("getDefault");
        //Field gDefaultField = mActivityManagerNativeClass.getDeclaredField("gDefault");
        //gDefaultField.setAccessible(true); // 授权
        Object gDefault = method.invoke(null);


        // 替换点
        Class mSingletonClass = Class.forName("android.util.Singleton");
        // 获取此字段 mInstance
        Field mInstanceField = mSingletonClass.getDeclaredField("mInstance");
        mInstanceField.setAccessible(true); // 让虚拟机不要检测 权限修饰符
        // 替换
        mInstanceField.set(gDefault, mIActivityManagerProxy); // 替换是需要gDefault
    }
}

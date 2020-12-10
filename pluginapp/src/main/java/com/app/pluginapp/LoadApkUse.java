package com.app.pluginapp;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;

import java.io.File;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

public class LoadApkUse {

    public static final String pulginPath = "/storage/emulated/0/Android/data/com.app.pluginapp/cache/plugin-debug.apk";

    /**
     * 自己创造一个LoadedApk.ClassLoader 添加到 mPackages，此LoadedApk 专门用来加载插件里面的 class
     */
    public void customLoadedApkAction(Context context) throws Exception {

        // mPackages 添加 自定义的LoadedApk
        // final ArrayMap<String, WeakReference<LoadedApk>> mPackages 添加自定义LoadedApk
        Class mActivityThreadClass = Class.forName("android.app.ActivityThread");

        // 执行此方法 public static ActivityThread currentActivityThread() 拿到 ActivityThread对象
        Object mActivityThread = mActivityThreadClass.getMethod("currentActivityThread").invoke(null);

        Field mPackagesField = mActivityThreadClass.getDeclaredField("mPackages");
        mPackagesField.setAccessible(true);
        // 拿到mPackages对象
        Object mPackagesObj = mPackagesField.get(mActivityThread);

        Map mPackages = (Map) mPackagesObj;

        // 如何自定义一个 LoadedApk，系统是如何创造LoadedApk的，我们就怎么去创造LoadedApk
        // 执行此 public final LoadedApk getPackageInfoNoCheck(ApplicationInfo ai, CompatibilityInfo compatInfo)
        Class mCompatibilityInfoClass = Class.forName("android.content.res.CompatibilityInfo");
        Field defaultField = mCompatibilityInfoClass.getDeclaredField("DEFAULT_COMPATIBILITY_INFO");
        defaultField.setAccessible(true);
        Object defaultObj = defaultField.get(null);

        /**
         * ApplicationInfo 如何获取，我们之前学习 APK解析源码分析
         */
        ApplicationInfo applicationInfo = getApplicationInfoAction();

        Method mLoadedApkMethod = mActivityThreadClass.getMethod("getPackageInfoNoCheck", ApplicationInfo.class, mCompatibilityInfoClass); // 类类型
        // 执行 才能拿到 LoedApk 对象
        Object mLoadedApk = mLoadedApkMethod.invoke(mActivityThread, applicationInfo, defaultObj);

        // 自定义加载器 加载插件
        // String dexPath, String optimizedDirectory, String librarySearchPath, ClassLoader parent

        File fileDir = context.getDir("pulginPathDir", Context.MODE_PRIVATE);

        // 自定义 加载插件的 ClassLoader
        ClassLoader classLoader = new PluginClassLoader(pulginPath, fileDir.getAbsolutePath(), null, context.getClassLoader());

        Field mClassLoaderField = mLoadedApk.getClass().getDeclaredField("mClassLoader");
        mClassLoaderField.setAccessible(true);
        mClassLoaderField.set(mLoadedApk, classLoader); // 替换 LoadedApk 里面的 ClassLoader

        // 添加自定义的 LoadedApk 专门加载 插件里面的 class

        // 最终的目标 mPackages.put(插件的包名，插件的LoadedApk);
        WeakReference weakReference = new WeakReference(mLoadedApk); // 放入 自定义的LoadedApk --》 插件的
        mPackages.put(applicationInfo.packageName, weakReference); // 增加了我们自己的LoadedApk
    }

    /**
     * 获取 ApplicationInfo 为插件服务的
     *
     * @return
     * @throws
     */
    private ApplicationInfo getApplicationInfoAction() throws Exception {
        // 执行此public static ApplicationInfo generateApplicationInfo方法，拿到ApplicationInfo
        Class mPackageParserClass = Class.forName("android.content.pm.PackageParser");

        Object mPackageParser = mPackageParserClass.newInstance();

        // generateApplicationInfo方法的类类型
        Class $PackageClass = Class.forName("android.content.pm.PackageParser$Package");
        Class mPackageUserStateClass = Class.forName("android.content.pm.PackageUserState");

        Method mApplicationInfoMethod = mPackageParserClass.getMethod("generateApplicationInfo", $PackageClass,
                int.class, mPackageUserStateClass);
//
//        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "p.apk");
//        String pulginPath = file.getAbsolutePath();

        File file = new File(pulginPath);

        // 执行此public Package parsePackage(File packageFile, int flags)方法，拿到 Package
        // 获得执行方法的对象
        Method mPackageMethod = mPackageParserClass.getMethod("parsePackage", File.class, int.class);
        Object mPackage = mPackageMethod.invoke(mPackageParser, file, PackageManager.GET_ACTIVITIES);

        // 参数 Package p, int flags, PackageUserState state
        ApplicationInfo applicationInfo = (ApplicationInfo)
                mApplicationInfoMethod.invoke(mPackageParser, mPackage, 0, mPackageUserStateClass.newInstance());

        // 获得的 ApplicationInfo 就是插件的 ApplicationInfo
        // 我们这里获取的 ApplicationInfo
        // applicationInfo.publicSourceDir = 插件的路径；
        // applicationInfo.sourceDir = 插件的路径；
        applicationInfo.publicSourceDir = pulginPath;
        applicationInfo.sourceDir = pulginPath;

        return applicationInfo;
    }

    // Hook 拦截此 getPackageInfo 做自己的逻辑
    public void hookGetPackageInfo(Context context) {
        try {
            // sPackageManager 替换  我们自己的动态代理
            Class mActivityThreadClass = Class.forName("android.app.ActivityThread");
            Field sCurrentActivityThreadField = mActivityThreadClass.getDeclaredField("sCurrentActivityThread");
            sCurrentActivityThreadField.setAccessible(true);

            Field sPackageManagerField = mActivityThreadClass.getDeclaredField("sPackageManager");
            sPackageManagerField.setAccessible(true);
            final Object packageManager = sPackageManagerField.get(null);

            /**
             * 动态代理
             */
            Class mIPackageManagerClass = Class.forName("android.content.pm.IPackageManager");

            Object mIPackageManagerProxy = Proxy.newProxyInstance(context.getClassLoader(),

                    new Class[]{mIPackageManagerClass}, // 要监听的接口

                    new InvocationHandler() {
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            if ("getPackageInfo".equals(method.getName())) {
                                // 如何才能绕过 PMS, 欺骗系统

                                // pi != null
                                return new PackageInfo(); // 成功绕过 PMS检测
                            }
                            // 让系统正常继续执行下去
                            return method.invoke(packageManager, args);
                        }
                    });


            // 替换  狸猫换太子   换成我们自己的 动态代理
            sPackageManagerField.set(null, mIPackageManagerProxy);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

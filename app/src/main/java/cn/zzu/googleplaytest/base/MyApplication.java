package cn.zzu.googleplaytest.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Process;

/**
 * Created by yangg on 2017/7/8.
 */

public class MyApplication extends Application {


    private static Context mContext;
    private static Handler mMainThreadHandlet;
    private static int mMainThread;

    public static Context getmContext() {
        return mContext;
    }

    public static Handler getmMainThreadHandlet() {
        return mMainThreadHandlet;
    }

    public static int getmMainThread() {
        return mMainThread;
    }

    @Override
    public void onCreate() {
        /**
         * 得到上下文的context
         */
        mContext = getApplicationContext();
        /**
         * 得到主线程的handler
         */
        mMainThreadHandlet = new Handler();
        /**
         * 得到主线程的id
         * myTid :Thread
         * myPid: Process
         * myUid :User
         */
        mMainThread = Process.myTid();
        super.onCreate();
    }
}

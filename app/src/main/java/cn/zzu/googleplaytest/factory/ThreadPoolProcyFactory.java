package cn.zzu.googleplaytest.factory;

import cn.zzu.googleplaytest.proxy.ThreadPoolProxy;

/**
 * Created by yangg on 2017/7/11.
 */

public class ThreadPoolProcyFactory {
    //普通类型的线程池代理

    static ThreadPoolProxy mNormalThreadPoolProxy;

    //下载类型的线程池代理
    static ThreadPoolProxy mDownLoadThreadPoolProxy;

    /**
     * 得到普通类型的线程池代理
     */
    public static ThreadPoolProxy getmNormalPoolProxy(){
        if (mNormalThreadPoolProxy  == null){
            synchronized (ThreadPoolProcyFactory.class){
                if (mNormalThreadPoolProxy == null){
                    mNormalThreadPoolProxy = new ThreadPoolProxy(5,5);
                }
            }
        }
        return mNormalThreadPoolProxy;
    }

    /**
     * 得到普通类型,的线程池代理
     */
    public static ThreadPoolProxy getDownLoadThreadPoolProxy(){
        if (mDownLoadThreadPoolProxy ==null){
            synchronized (ThreadPoolProcyFactory.class){
                if (mDownLoadThreadPoolProxy == null){
                    mDownLoadThreadPoolProxy = new ThreadPoolProxy(5,5);
                }
            }
        }
        return mDownLoadThreadPoolProxy;
    }

}

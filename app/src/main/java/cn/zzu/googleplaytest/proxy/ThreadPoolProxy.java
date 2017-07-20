package cn.zzu.googleplaytest.proxy;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by yangg on 2017/7/11.
 * 线程池代理类,代替线线程池完成相关的操作
 * 1,代理模式就是多一个代理类出来,替原对象进行一些操作
 * 2,只需要爱
 */

public class ThreadPoolProxy {

    ThreadPoolExecutor mExecutor;
    private int mCorePoolSize;//核心线程池的大小
    private int mMaximumPoolSize;//线程最大线程数量

    public ThreadPoolProxy(int mMaximumPoolSize,int mCorePoolSize) {
        this.mCorePoolSize = mCorePoolSize;
        this.mMaximumPoolSize = mMaximumPoolSize;
    }

    public void initThreadPoolExecutor() {
        if (mExecutor == null || mExecutor.isShutdown() || mExecutor.isTerminated()) {

            //枷锁,,多个线程最后只进去一次,所以,只创建一次,线程池对象
            synchronized (ThreadPoolProxy.class) {
                if (mExecutor == null || mExecutor.isShutdown() || mExecutor.isTerminated()) {

                    long keepAliveTime = 0;
                    TimeUnit unit = TimeUnit.MILLISECONDS;

                    BlockingQueue<Runnable> workQueue = new LinkedBlockingDeque<>();
                    ThreadFactory threadFactory = Executors.defaultThreadFactory();

                    RejectedExecutionHandler handler = new ThreadPoolExecutor.DiscardPolicy();

                    mExecutor = new ThreadPoolExecutor(
                            mCorePoolSize, mMaximumPoolSize, keepAliveTime
                            , unit, workQueue, threadFactory, handler);
                }
            }
        }

    }

    /**
     * 提交任务
     */
    public Future submit(Runnable task) {
        initThreadPoolExecutor();
        Future<?> funture =mExecutor.submit(task);
        return funture;
    }

    /**
     * 执行任务
     */
    public void execute(Runnable task) {
        initThreadPoolExecutor();

        mExecutor.execute(task);
    }

    public void remove(Runnable task) {
        initThreadPoolExecutor();
        mExecutor.remove(task);
    }
}

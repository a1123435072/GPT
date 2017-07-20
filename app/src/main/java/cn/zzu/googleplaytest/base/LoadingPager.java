package cn.zzu.googleplaytest.base;

import android.content.Context;

import android.view.View;
import android.widget.FrameLayout;

import cn.zzu.googleplaytest.R;
import cn.zzu.googleplaytest.factory.ThreadPoolProcyFactory;

import cn.zzu.googleplaytest.utils.LogUtils;
import cn.zzu.googleplaytest.utils.UIUtils;


/**
 * Created by yangg on 2017/7/9.
 * 提供视图==>4中视图中的一种(加载中视图,错误视图,空视图,成功视图)-->把自身提供出去就可以; e
 * 2,加载数据
 * 3,数据的视图的绑定
 */

public abstract class LoadingPager extends FrameLayout {

    //定义集中状态
    public static final int STATE_LOADING = 0;//加载中
    public static final int STATE_ERROR = 1;//错误
    public static final int STATE_SUCCESS = 2;//成功
    public static final int STATE_EMPTY = 3;//空
    public int mCurState = STATE_LOADING;//默认的状态是加载中的情况

    private View mLoadingView;
    private View mErrorView;
    private View mEmptyView;
    private View mSuccessView;
    private LoadDataTask loadDataTask;

    public LoadingPager( Context context) {
        super(context);
        initCommonView();
    }

    /**
     * 初始化常规视图:静态视图,不许要进行加载数据的
     * LoadingPager被创建的时候
     */
    private void initCommonView() {
        //加载中视图
        mLoadingView = View.inflate(UIUtils.getContext(), R.layout.pager_loading, null);
        this.addView(mLoadingView);

        //错误视图
        mErrorView = View.inflate(UIUtils.getContext(), R.layout.pager_error, null);
        this.addView(mErrorView);

        //空视图
        mEmptyView = View.inflate(UIUtils.getContext(), R.layout.pager_empty, null);
        this.addView(mEmptyView);


        //根据当前页面的状态舒刷新ui
        refreshViewByStaste();
    }

    /**
     * 根据状态刷新新ui(决定LoadingPager最后显示的的四种视图中的哪一种)
     * 1,LoadingPager 创建的时候
     */

    private void refreshViewByStaste() {
        //控制  记载中视图  显示隐藏
        if (mCurState == STATE_LOADING) {
            mLoadingView.setVisibility(View.VISIBLE);
        } else {
            mLoadingView.setVisibility(View.GONE);
        }
        //错误视图的显示和隐藏
        if (mCurState == STATE_ERROR) {
            mErrorView.setVisibility(View.VISIBLE);
        } else {
            mErrorView.setVisibility(View.GONE);
        }
        //空视图的显示和隐藏
        if (mCurState == STATE_EMPTY) {
            mEmptyView.setVisibility(View.VISIBLE);
        } else {
            mEmptyView.setVisibility(View.GONE);
        }

        /**
         * 可有可有加载成功的视图了因为,数据已经加载完成了,而且数据记载成功了\
         *
         */
        if (mSuccessView == null && mCurState == STATE_SUCCESS) {
            mSuccessView = initSuccessView();
            addView(mSuccessView);
        }

        //控制显示成功视图的显示和隐藏
        if (mSuccessView != null) {
            if (mCurState == STATE_SUCCESS) {
                mSuccessView.setVisibility(View.VISIBLE);
            }else {
                mSuccessView.setVisibility(View.GONE);
            }
        }

    }


    /**
     * 触发加载数据
     * 外界 想让LoadingPager触发加载数据的时候调用
     */
    public void tiggerLoadingData() {
        //若当前已经加载成功,则无需再次加载
        if (mCurState != STATE_SUCCESS) {
            if (loadDataTask == null) {
                LogUtils.s("####触发加载数据tiggerLoadData()");
                mCurState = STATE_LOADING;
                refreshViewByStaste();
                //异步加载
                loadDataTask = new LoadDataTask();
                //new Thread(loadDataTask).start();
                //我们使用单例的方式,,查un关键一个线程
                ThreadPoolProcyFactory.getmNormalPoolProxy().submit(loadDataTask);
            }
        }
    }

    /**
     * 加载数据的
     */
     class LoadDataTask implements Runnable {

        @Override
        public void run() {

            //真正的子线程中加载具体的数据--?得到数据
            LoadedResult loadedResult = initData();
            //处理数据
            mCurState = loadedResult.getState();

            //在主线程中
            MyApplication.getmMainThreadHandlet().post(new Runnable() {
                @Override
                public void run() {
                    //刷新Ui(决定到提供4中视图中的那以一种视图)
                    refreshViewByStaste();//mSurState-->int
                }
            });

            //置空任务
            loadDataTask = null;
        }


    }

    /**
     * 在子线程 中真正的加载具体的数据
     * 在LoadingPager中,不知道如何具体架子啊数据,交给子类,子类必须实现
     * 必须实现,但是不知道具体的显示定义喂抽象的方法,交给子类 具体实现
     * tiggerLloadData() 方法方法在调用的时候
     *
     * @return
     */
    public abstract LoadedResult initData();

    /**
     * 决定成功视图张什么样子(需要定义成功视图)
     * 数据和视图的绑定过程
     * 在loadingPager中,其实不知道成功视图具体张什么样子,更加不知道,视图和数据是如何绑定的,交给子类
     * 必须实现
     * 必须实现,但是不知道具体体现,定义为抽象方法,交给子类去实现
     * tiggerloadData()方法被调用,而且数据加载完成了,而且数据加载成功
     *
     * @return
     */
    public abstract View initSuccessView();

    public enum LoadedResult {
        /**
         * STATE_ERROR = 1;//错误
         * STATE_SUCCESS = 2;//成功
         * STATE_EMPTY = 3;//空
         */
        SUCCESS(STATE_SUCCESS), ERREOR(STATE_ERROR), EMPTY(STATE_EMPTY);


        private int state;

        public int getState() {
            return state;
        }

        LoadedResult(int state) {
            this.state = state;
        }
    }


}

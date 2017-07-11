package cn.zzu.googleplaytest.base;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.zzu.googleplaytest.utils.UIUtils;

/**
 * Created by yangg on 2017/7/9.
 */

public abstract class BaseFragmet extends Fragment {
    private LoadingPager mLoadingPager;

    public LoadingPager getmLoadingPager() {
        return mLoadingPager;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mLoadingPager = new LoadingPager(UIUtils.getContext()){

            /**
             * 在子线程中真正的加载具体的数据
             * tifferloadingData()方法被嗲用的时候
             */
            @Override
            public LoadedResult initData() {
                return BaseFragmet.this.initDate();
            }

            /**
             * 决定成功视图整什么样子(需要定义成功的视图)
             * 数据和属兔的绑定过程
             * tiggerloadData()方法被嗲用,而且数据记载完成了,额偷窃数据加载成功
             *
             * @return
             */
            @Override
            public View initSuccessView() {
                return BaseFragmet.this.initSuccessVeiw();
            }

        };

        //触发加载数据
        //loadingPager.tiggerLoadingData();

        //四种视图中的一种(加载中,错误,空,成功)
        return mLoadingPager;
    }

    /**
     * 在子线程中真正的加载具体的数据
     * 在BaseFragment 不知道具体的加载数据,只能交给子类,子类必须实现
     * 必须实现,但是不知道具体的实现,定义为抽象方法,交给子类具体的实现
     * tiggetrloadingData方法调用的时候
     *
     * @return
     */


    public abstract LoadingPager.LoadedResult initDate();

    /**
     * 决定成功视图张什么样子,(需要定义成功的视图)
     * 数据和视图的band定过程
     * 在BaseFragment 中不知道成功的视图具体是啥,不知道具体的数据是啥,不知道数据和视图如何绑定的,
     * 交给子类去实现
     * 必须实现,但是不知道具体的 是西安,定义为 抽象方法,交给子类去实现
     * tiggerloadingdata方法被调用,而且数据加载完成了,而且数据记载成功
     * @return
     */
    protected abstract View initSuccessVeiw();

}

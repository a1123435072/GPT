package cn.zzu.googleplaytest.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by yangg on 2017/7/9.
 */

public abstract class BaseFragmentCommon extends Fragment {
    /**
     * Fraagment 被创建的时候执行
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
       init();

        super.onCreate(savedInstanceState);
    }



    /**
     * 返回Fragment 被创建的的布局
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return intiView();
    }


    /**
     * 宿主activity被创建的时候
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        intiData();
        intiListener();
        super.onActivityCreated(savedInstanceState);
    }


    /**
     * 进行先关的初始化的操作
     * 在baseFragmentCommom中,不知道进行是, 什么杨的初始化的操作,交给子类,子类是选择星实现
     * Fragmemt被创建
     */
    private void init() {


    }

    /**
     * 初始化对应的视图,返回给Fragment 进行展示
     * 在BaseFragmentCommon中,不知道如何具体初始化对应的视图,交给子类,子类是必须实现的
     * 针对initView  方法,必须实现,但是不知道具体实现,所以定义抽象方法,交给自i类具体实现
     * Fragment 需要i一个布局的时候
     * @return
     */
    public abstract View intiView() ;


    /**
     * 初始化Fragment 里面的数据架子啊
     * 在BaseFragmentCommon中,不知道如何具体的额尽享数据加载,交给子类进行选择星实现
     * 宿主Activity被创建的时候
     */
    protected  void intiData(){

    }

    /**
     * 初始化Fragment 里面的相关的监听
     * 在BaseFragmentCommon中,不知道如何具体的额添加时间的监听,交给子类,子类选择星的实现
     * 宿主Activity被创建的时候
     *
     */
    protected  void intiListener(){

    }

}

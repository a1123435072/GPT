package cn.zzu.googleplaytest.base;

import android.view.View;

/**
 * Created by yangg on 2017/7/11.
 * VeiwHolder 的基类
 *      1,提供视图
 *      2,接收/加载
 *
 *     3,数据和视图的绑定
 *
 */

public abstract class BaseHolder<T>  {

    public View mHolderView;//view
    public T mData;//mode

    public BaseHolder(){
        //初始化视图
        mHolderView = initHolderView();

        //找到一个符合条件的holder,绑定在自己 身上
        mHolderView.setTag(this);
    }


    public void setDataAndRefreshHolderView(T data){
        //保存到数据到成员变量
        mData = data;
        //进行数据段额视图的绑定
        refreshHolderView(data);
    }


    /**
     * 初始化holderview ,决定所能提供的视图张什么样子
     * 在BaseHolder中,不知道如何初始化能够提供的视图,只能交给子类,子类必须去实习那
     * 必须实现,但是不知道具体的实现,定义喂抽象方法,交给子类具体实现
     * HomeHolder 一但创建的时候
     * @return
     */
    public abstract View initHolderView() ;

    /**
     *
     * @param data
     *      1,接收数据
     *      2,数据和视图的绑定
     */
    protected abstract void refreshHolderView(T data);



}

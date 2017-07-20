package cn.zzu.googleplaytest.base;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;

import java.util.List;

import butterknife.OnItemClick;
import cn.zzu.googleplaytest.factory.ThreadPoolProcyFactory;
import cn.zzu.googleplaytest.holder.LoadMoreHolder;
import cn.zzu.googleplaytest.utils.LogUtils;

import static android.R.attr.data;
import static android.R.attr.id;
import static android.R.attr.pointerIcon;
import static android.R.attr.theme;

/**
 * Created by yangg on 2017/7/11.
 * 针对MyBaseAdapter里面的getView方法,在getView 方法中引入了BaseHloder类
 */

public abstract class SupterBaseAdapter<T> extends MyBaseAdapter implements AdapterView.OnItemClickListener {

    /**
     * 针对  MyBsdeAdapter 里面的额getView方法,在getVeiw
     */
    private AbsListView mAbsListView ;

    private int state;

    public SupterBaseAdapter(List<T> mDataSets, AbsListView mAbsListViews ) {
        super(mDataSets);
        mAbsListView = mAbsListViews;

        mAbsListView.setOnItemClickListener(this);
        LogUtils.s("SupterBaseAdapter----mAbsListView-->"+mAbsListView);
    }

    /**
     * 加载更多
     */
    public static final int VIEWTYPE_LOADMODE = 0;
    /**
     * 普通的 item  条目
     */
    public static final int VIEWTYPE_NORMAL = 1;
    /**
     * 加载更多的,ViewHolder
     */

    private LoadMoreHolder loadMoreHolder;
    private LoadMoreTask loadMoreTask;




    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        /**
         * 决定跟布局itemView
         */
        BaseHolder holder = null;
        int curViewType = getItemViewType(i);

        if (view == null) {
            if (curViewType == VIEWTYPE_LOADMODE) {
                //当前的条目,是加载更多的类型
                holder = getLoadMoreHolder();
            } else {
                //创建holder对象
                holder = getSpecialBaseHolder(i);

            }
        } else {
            holder = (BaseHolder) view.getTag();
        }

        /***------------得到数据,然后绑定数据--------------*/
        if (curViewType == VIEWTYPE_LOADMODE) {
            if (hasLoadMore()) {
//            当前的条目是加载更多的类型
                loadMoreHolder.setDataAndRefreshHolderView(loadMoreHolder.LOADMORE_LOADING);

                //触发加载更多数据
                tiggerLoadMoreData();
            } else {
                //隐藏加载更多的属兔,以及重试视图
                loadMoreHolder.setDataAndRefreshHolderView(loadMoreHolder.LOADMORE_NONE);

            }
        } else {

            Object data = mDataSets.get(i);
            holder.setDataAndRefreshHolderView(data);
        }

        return holder.mHolderView;
    }

    //触发  加载更多的数据
    private void tiggerLoadMoreData() {


        //保证这个一部任务只会创建一次
        if (loadMoreTask  == null) {
            LogUtils.s("###tiggerLoadMoreData 出发加在数据");

            //加载之前先是则很难过子啊加载更多
            int sstate = LoadMoreHolder.LOADMORE_LOADING;
            loadMoreHolder.setDataAndRefreshHolderView(sstate);

            //异步 加载

            loadMoreTask = new LoadMoreTask();
            ThreadPoolProcyFactory.getmNormalPoolProxy().submit(loadMoreTask);
        }
    }

    /**
     * 给listview添加点击事件
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int curViewType  = getItemViewType(position);
        LogUtils.s("curViewType -->"+curViewType);
        if (curViewType == VIEWTYPE_LOADMODE){

            if (state == LoadMoreHolder.LOADMORE_ERROR){
                LogUtils.s("出现异常的时候点击加载更多   方法-->");
                //再次出发加载更多
                tiggerLoadMoreData();

            }
        }else{
            //点击了普通的条目
            onNormalitemClick(parent,view,position,id);
        }
    }






    /**普通条目的点击事件*
     * 在SuperBaseAdapter中国不知道如何处理普通条目的点击事件,只能交给子类去实习那
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    public  void onNormalitemClick(AdapterView<?> parent, View view, int position, long id){

        LogUtils.s("onNormalitemClick  方法-->");

    }

    class LoadMoreTask implements Runnable {

        private static final int PAGESIZE = 20;//每页请求的总数

        @Override
        public void run() {
            List loadMoreList = null;

            try {

                /*** 定义刷新ui 需要用到的两个值*/

                /**--------------真正的在子线程中 加载更多的数据  得到数据,处理数据*/
                loadMoreList = onLoadMore();
                //处理数据
                if (loadMoreList == null){
                    //没有家挨更多
                    state = LoadMoreHolder.LOADMORE_NONE;

                }else {
                    if (loadMoreList.size() == PAGESIZE){
                        //有加载更多,
                        state =  LoadMoreHolder.LOADMORE_LOADING;//有加载过呢个多就显示正在加载的界面
                    }else {
                        //没有加载更多,,因为一页最多显示,20行,,如果超出了20条说明服务器有错误了
                        state = LoadMoreHolder .LOADMORE_NONE;

                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                state = LoadMoreHolder.LOADMORE_ERROR;//加载数据失败
            }
            /**生成两个临时变量*/
            final  List finalLoadMoreList = loadMoreList;
            final int  finalState = state;

            /**具体的刷新ui*/
            MyApplication.getmMainThreadHandlet().post(new Runnable() {
                @Override
                public void run() {
                    //刷新ui -->listView-->修改数据级敢喝
                    if (finalLoadMoreList != null){
                         mDataSets.addAll(finalLoadMoreList);
                        notifyDataSetChanged();
                    }

                    //刷新ui
                    loadMoreHolder.setDataAndRefreshHolderView(finalState);
                }
            });

            //代表走到run方法体的最后了,任务已经执行晚了,置空任务
            loadMoreTask = null;
        }


    }

    /**
     * Exception 在 加载更多过程中,出现了异常
     * 在子线程中真正的加载更多的数据'
     * 在super Base Adapter 中不摘到如何加载更多的数据,只能交给子类'
     * 子类是线则星实现,只有子类有家挨更多的时候菜会复写改方法吗,,完成具体加载更多
     * @return
     */
    public List onLoadMore() throws  Exception{
        return null;//默认是不加在数据 返回null
    }


    /**
     * 得到BaseHolder具体的子类的对象
     * 在SuperBaseAdapter中不知道如果创建BaseHolder的子类对象,所以只能交给 子类,子类必须实现
     * 必须实现名单是不知道具体实现,定义为抽象方法,交给自i类具体实习那
     *
     * @return
     */
    public abstract BaseHolder getSpecialBaseHolder(int position);

    /**
     * ListView中显示集中ViewType如何做
     * 1,复写方法
     * 2,在getView中分别处理
     * <p>
     * getViewTypeCount()默认的总是是1中类型的视图
     */
    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount() + 1;//普通类型+1  加载更多 =2
    }

    /**
     * get(得到)item(指定条目)ViewType(viewType类型)(int position)默认是0
     * 范围  0-getviewtypecount()-1
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if (position == getCount() - 1) {
            return VIEWTYPE_LOADMODE;
        } else {
//            return VIEWTYPE_NORMAL;

           return getNormalItemViewType(position);
        }
    }

    /**
     * 得到普通条目的ViewType类型
     * 子类可以复写改方法,返回给你更多的额普通条没有的viewtype类型
     * @param position
     * @return
     */
    public int getNormalItemViewType(int position) {
        return VIEWTYPE_NORMAL;//默认值是1
    }


    @Override
    public int getCount() {
        return super.getCount() + 1;//其实就是个体的加载更多的条目
    }

    /**
     * 属于 BaseHolder  的子类对象
     * 记载更多 Holder对象
     *
     * @return
     */
    public LoadMoreHolder getLoadMoreHolder() {
        if (loadMoreHolder == null) {
            loadMoreHolder = new LoadMoreHolder();
        }
        return loadMoreHolder;
    }

    /**
     * 是否加载更多,默认没有加载更多
     * 子类可以复写改方法,可以决定有加载更多
     */
    public boolean hasLoadMore() {
        return false;//默认没有加载更多
    }
}

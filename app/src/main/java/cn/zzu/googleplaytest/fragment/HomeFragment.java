package cn.zzu.googleplaytest.fragment;

import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zzu.googleplaytest.base.BaseFragmet;
import cn.zzu.googleplaytest.base.BaseHolder;
import cn.zzu.googleplaytest.base.LoadingPager;
import cn.zzu.googleplaytest.base.SupterBaseAdapter;
import cn.zzu.googleplaytest.bean.HomeBean;
import cn.zzu.googleplaytest.bean.ListBean;
import cn.zzu.googleplaytest.conf.Constants;
import cn.zzu.googleplaytest.holder.HomeHolder;
import cn.zzu.googleplaytest.holder.HomePictureHolder;
import cn.zzu.googleplaytest.protocol.HomeProtocol;
import cn.zzu.googleplaytest.utils.HttpUtils;
import cn.zzu.googleplaytest.utils.LogUtils;
import cn.zzu.googleplaytest.utils.UIUtils;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by yangg on 2017/7/8.
 */

public class HomeFragment extends BaseFragmet {


    private List<String> mDatas;
    private List<ListBean> mItemBeans;
    private List<String> mPictures;
    private HomeBean homeBean;
    private HomeProtocol mProtocol;

    /**
     * 在子类中真正的实现加载具体的数据
     * tiggerloaddata方法被调用的时候
     *
     * @return
     */
    @Override
    public LoadingPager.LoadedResult initDate() {

        try {
            mProtocol = new HomeProtocol();
            homeBean = mProtocol.loadData(0);

            LoadingPager.LoadedResult state = checkResult(homeBean);
            //说明 homeBean 有问题homeBean   == null

            if (state != LoadingPager.LoadedResult.SUCCESS) {
                return state;
            }
            state = checkResult(homeBean.list);
            //说明list有问题,list.size==0
            if (state != LoadingPager.LoadedResult.SUCCESS) {
                return state;

            }

            //return LoadingPager.LoadedResult.EMPTY;
            //走到这里来说明是成功的

            mItemBeans = homeBean.list;
            mPictures = homeBean.picture;
            //LogUtils.s("resJsonString" + resJsonString);
            //Log.i("state", "state:" + state);
            return state;//successde 的状态
        } catch (Exception e) {
            e.printStackTrace();
            return LoadingPager.LoadedResult.ERREOR;
        }

    }


    /**
     * 决定成功的视图张什么样子,(需要定义成功的视图)
     * 数据和视图的绑定过程
     * tiggleloadingData()方法被调用,而且数据记载完成了,而且数据加载成功了
     *
     * @return
     */
    @Override
    protected View initSuccessVeiw() {
        Log.i("initSuccessVeiw-->", "initSuccessVeiw");
        //初始化成功的视图
        //view
        ListView listView = new ListView(UIUtils.getContext());


        //--------------------构建轮播如图
        HomePictureHolder homePictureHolder = new HomePictureHolder();
        homePictureHolder.setDataAndRefreshHolderView(mPictures);

        View headerVeiw = homePictureHolder.mHolderView;
        listView.addHeaderView(headerVeiw);


        //data -->dataSets-->在成员成员变量里面
        //data+ view
        listView.setAdapter(new HomeAdapter(mItemBeans,listView));

        LogUtils.s("HomeFragment中设置数据是配器-->执行后log");
        return listView;
    }


    /**
     * liastivew的临时的适配器
     */


    public class HomeAdapter extends SupterBaseAdapter<ListBean> {
        public HomeAdapter(List<ListBean> mDataSets, AbsListView absListView) {
            super(mDataSets,absListView);
        }

        /**
         * 得到BaseHolder 具体的子类对象
         *
         * @return
         */
        @Override
        public BaseHolder getSpecialBaseHolder(int postion) {
            return new HomeHolder();
        }

        @Override
        public boolean hasLoadMore() {
            return true;
        }

        /**
         * 执行实际的加载更多数据的逻辑
         * @return
         * @throws Exception
         */
        @Override
        public List onLoadMore() throws Exception {

            SystemClock.sleep(2000);
            HomeBean homeBean = mProtocol.loadData(mItemBeans.size());
            if (homeBean != null){
                return homeBean.list;
            }
            return super.onLoadMore();

        }

        @Override
        public void onNormalitemClick(AdapterView<?> parent, View view, int position, long id) {

            Toast.makeText(getActivity(),"条目被点击了",Toast.LENGTH_SHORT).show();


            LogUtils.s("onNormalitemClick 条目被点击了-->");

        }
    }






}

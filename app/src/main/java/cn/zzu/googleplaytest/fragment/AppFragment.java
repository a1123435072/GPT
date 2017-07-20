package cn.zzu.googleplaytest.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Random;

import cn.zzu.googleplaytest.base.BaseFragmet;
import cn.zzu.googleplaytest.base.BaseHolder;
import cn.zzu.googleplaytest.base.LoadingPager;
import cn.zzu.googleplaytest.base.SupterBaseAdapter;
import cn.zzu.googleplaytest.bean.HomeBean;
import cn.zzu.googleplaytest.bean.ListBean;
import cn.zzu.googleplaytest.factory.ListViewFactory;
import cn.zzu.googleplaytest.holder.ItemHolder;
import cn.zzu.googleplaytest.protocol.AppProtocol;
import cn.zzu.googleplaytest.utils.UIUtils;

import static android.R.attr.data;
import static android.R.attr.layout_scale;

/**
 * Created by yangg on 2017/7/8.
 */

public class AppFragment extends BaseFragmet {

    private AppProtocol protocol;
    private List<ListBean> mDatas;

    /**
     * 在子类中真正的实现加载具体的数据
     * tiggerloaddata方法被调用的时候
     * @return
     */
    @Override
    public LoadingPager.LoadedResult initDate() {
        protocol = new AppProtocol();


        try{
            //网络请求得到诗句
            mDatas = protocol.loadData(0);
            //椒盐请求回来的数据,返回对应的状态
            LoadingPager.LoadedResult loadedResult = checkResult(mDatas);

            return loadedResult;

        }catch (Exception e){
            e.printStackTrace();
            return LoadingPager.LoadedResult.ERREOR;
        }



    }

    /**
     * 决定成功的视图张什么样子,(需要定义成功的视图)
     * 数据和视图的绑定过程
     * tiggleloadingData()方法被调用,而且数据记载完成了,而且数据加载成功了
     * @return
     */
    @Override
    protected View initSuccessVeiw() {
      //vew
        ListView listView = ListViewFactory.createListView();

        listView.setAdapter(new apppAdapter(mDatas,listView));

        return listView;
    }

     class apppAdapter extends SupterBaseAdapter<ListBean> {

        public apppAdapter(List<ListBean> mDataSets, AbsListView mAbsListViews) {
            super(mDataSets, mAbsListViews);
        }

        @Override
        public BaseHolder getSpecialBaseHolder(int postion) {
            return new ItemHolder();
        }

        @Override
        public boolean hasLoadMore() {
            return true;
        }

        @Override
        public List onLoadMore() throws Exception {

            SystemClock.sleep(2000);
            List<ListBean> itemBean = protocol.loadData(mDatas.size());

            return itemBean;
        }

        @Override
        public void onNormalitemClick(AdapterView<?> parent, View view, int position, long id) {
            //data普通条目的加载数据
            ListBean itemBean = mDatas.get(position);

            Toast.makeText(UIUtils.getContext(),itemBean.name,Toast.LENGTH_SHORT).show();

        }
    }
}

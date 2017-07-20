package cn.zzu.googleplaytest.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import cn.zzu.googleplaytest.bean.ListBean;
import cn.zzu.googleplaytest.factory.ListViewFactory;
import cn.zzu.googleplaytest.holder.ItemHolder;
import cn.zzu.googleplaytest.protocol.GrameProtocol;
import cn.zzu.googleplaytest.utils.UIUtils;

/**
 * Created by yangg on 2017/7/8.
 */

public class GameFragment extends BaseFragmet {
    private GrameProtocol mProtocol;
    private List<ListBean> mDatas;


    /**
     * 在子类中真正的实现加载具体的数据
     * tiggerloaddata方法被调用的时候
     * @return
     */
    @Override
    public LoadingPager.LoadedResult initDate() {
        mProtocol = new GrameProtocol();
        try {
            mDatas = mProtocol.loadData(0);
            return checkResult(mDatas);
        } catch (Exception e) {
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
        //view
        ListView listView = ListViewFactory.createListView();
        //dataSets-->mDatas-->成员变量
        //data+view
        listView.setAdapter(new GameAdapter(mDatas, listView));
        return listView;

    }

    private class GameAdapter extends SupterBaseAdapter<ListBean> {

        public GameAdapter(List<ListBean> mDataSets, AbsListView mAbsListViews) {
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
            List<ListBean> itemBeans = mProtocol.loadData(mDatas.size());
            return itemBeans;

        }

        @Override
        public void onNormalitemClick(AdapterView<?> parent, View view, int position, long id) {
            ListBean listBean = mDatas.get(position);
            Toast.makeText(UIUtils.getContext(), listBean.name, Toast.LENGTH_SHORT).show();

        }
    }
}
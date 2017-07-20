package cn.zzu.googleplaytest.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

import cn.zzu.googleplaytest.base.BaseFragmet;
import cn.zzu.googleplaytest.base.BaseHolder;
import cn.zzu.googleplaytest.base.LoadingPager;
import cn.zzu.googleplaytest.base.SupterBaseAdapter;
import cn.zzu.googleplaytest.bean.CategoryInfoBean;
import cn.zzu.googleplaytest.factory.ListViewFactory;
import cn.zzu.googleplaytest.holder.CateGoryNormalHolder;
import cn.zzu.googleplaytest.holder.CategroryTitleHolder;
import cn.zzu.googleplaytest.protocol.CategoryProtocol;
import cn.zzu.googleplaytest.utils.LogUtils;
import okhttp3.Request;

/**
 * Created by yangg on 2017/7/8.
 */

public class CategoryFragment extends BaseFragmet {

    private List<CategoryInfoBean> mData;

    /**
     * 在子类中真正的实现加载具体的数据
     * tiggerloaddata方法被调用的时候
     * @return
     */
    @Override
    public LoadingPager.LoadedResult initDate() {
        CategoryProtocol protocol = new CategoryProtocol();

        try {
            mData = protocol.loadData(0);

            LogUtils.printList(mData);

            return checkResult(mData);

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
        ListView listView = new ListViewFactory().createListView();
        //data--?>成员变量
        //data+view绑定
        listView.setAdapter(new CategoryAdapter(mData,listView));


        return listView;
    }

    private class CategoryAdapter extends SupterBaseAdapter<CategoryInfoBean> {


        public CategoryAdapter(List<CategoryInfoBean> mDataSets, AbsListView mAbsListViews) {
            super(mDataSets, mAbsListViews);
        }

        @Override
        public BaseHolder getSpecialBaseHolder(int position) {
            CategoryInfoBean itemBean = mData.get(position);
            if (itemBean.isTitle){
                return new CategroryTitleHolder();
            }else {
                return new CateGoryNormalHolder();
            }
        }
    }
}


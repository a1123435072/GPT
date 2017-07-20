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
import cn.zzu.googleplaytest.bean.SubjectBean;
import cn.zzu.googleplaytest.factory.ListViewFactory;
import cn.zzu.googleplaytest.holder.SubjectHolder;
import cn.zzu.googleplaytest.protocol.SubjectProrocol;
import cn.zzu.googleplaytest.utils.UIUtils;

/**
 * Created by yangg on 2017/7/8.
 */

public class SubjectFragment extends BaseFragmet {

    private SubjectProrocol prorocol;
    private List<SubjectBean> data;



    /**
     * 在子类中真正的实现加载具体的数据
     * tiggerloaddata方法被调用的时候
     * @return
     */
    @Override
    public LoadingPager.LoadedResult initDate() {

       // SubjectProrocol
        prorocol = new SubjectProrocol();

        try {

            data = prorocol.loadData(0);

            return checkResult(data);

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
        //data+-->成员变量中有
        //data +view

        listView.setAdapter(new SubjectAdapter(data,listView));

        return listView;
    }



    private class SubjectAdapter  extends SupterBaseAdapter<SubjectBean>{
        public SubjectAdapter(List<SubjectBean> mDataSets, AbsListView mAbsListViews) {
            super(mDataSets, mAbsListViews);
        }


        @Override
        public BaseHolder getSpecialBaseHolder(int postion) {
            return new SubjectHolder();
        }
        /**
         * 有加载更多
         */
        @Override
        public boolean hasLoadMore() {
            return true;
        }

        @Override
        public List onLoadMore() throws Exception {
            SystemClock.sleep(2000);
            List<SubjectBean> subjectBeen = prorocol.loadData(data.size());

            return subjectBeen;
        }

        /**
         * 条目的点击时间
         */
        @Override
        public void onNormalitemClick(AdapterView<?> parent, View view, int position, long id) {

            //subjectBean
            SubjectBean subjectBean = data.get(position);
            Toast.makeText(UIUtils.getContext(),subjectBean.des,Toast.LENGTH_SHORT).show();
        }
    }
}

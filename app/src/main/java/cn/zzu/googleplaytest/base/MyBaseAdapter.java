package cn.zzu.googleplaytest.base;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by yangg on 2017/7/11.
 * 针对BaseAdapter  简单封装,针对的额是其中的3个方法\
 *
 *
 */

public  abstract class MyBaseAdapter<T> extends BaseAdapter {

    public   List<T> mDataSets ;



    public MyBaseAdapter(List<T> mDataSets) {
        this.mDataSets = mDataSets;
    }

    @Override
    public int getCount() {
        //Log.i("test", "getCount: "+mDataSets.size());
        if (mDataSets!= null){
            return mDataSets.size();
        }
        return  0;
    }

    @Override
    public Object getItem(int i) {
        if (mDataSets!= null){
            return mDataSets.get(i);
        }
        return null;
    }

    @Override
    public long getItemId(int i) {

        return i;
    }




}

package cn.zzu.googleplaytest.factory;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.ListView;

import cn.zzu.googleplaytest.bean.ListBean;
import cn.zzu.googleplaytest.utils.UIUtils;

/**
 * Created by yangg on 2017/7/15.
 */

public class ListViewFactory {
    /**
     * 得到一个listview的实力对象
     */
    public static ListView  createListView(){
        ListView listView = new ListView(UIUtils.getContext());
        //常规社会资
        //鸥分割线
        listView.setDividerHeight(0);
        //在3.0一下系统的一些兼容
        listView.setCacheColorHint(Color.TRANSPARENT);
        listView.setFastScrollEnabled(true);//快速话东条目打开
        listView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        return listView;
    }
}

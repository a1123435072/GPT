package cn.zzu.googleplaytest.holder;

import android.view.View;
import android.widget.TextView;

import cn.zzu.googleplaytest.base.BaseHolder;
import cn.zzu.googleplaytest.bean.CategoryInfoBean;
import cn.zzu.googleplaytest.utils.UIUtils;

/**
 * Created by yangg on 2017/7/17.
 */

public class CategroryTitleHolder extends BaseHolder<CategoryInfoBean> {

    private TextView mTitle;
    private int padding;

    /**
     * 决定对应holder 所对应的视图是啥
     * @return
     */
    @Override
    public View initHolderView() {

        mTitle = new TextView(UIUtils.getContext());
        padding = UIUtils.dip2Px(5);

        mTitle.setPadding(padding,padding,padding,padding);

        return mTitle;
    }

    /**
     * 进行数据的绑定
     * @param data
     *      1,接收数据
     */
    @Override
    protected void refreshHolderView(CategoryInfoBean data) {

        mTitle.setText(data.title);
    }
}

package cn.zzu.googleplaytest.holder;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.zzu.googleplaytest.R;
import cn.zzu.googleplaytest.base.BaseHolder;
import cn.zzu.googleplaytest.bean.CategoryInfoBean;
import cn.zzu.googleplaytest.conf.Constants;
import cn.zzu.googleplaytest.utils.LogUtils;
import cn.zzu.googleplaytest.utils.UIUtils;
import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by yangg on 2017/7/17.
 */

public class CateGoryNormalHolder extends BaseHolder<CategoryInfoBean> {
    @BindView(R.id.item_category_icon_1)
    ImageView itemCategoryIcon1;
    @BindView(R.id.item_category_name_1)
    TextView itemCategoryName1;
    @BindView(R.id.item_category_item_1)
    LinearLayout itemCategoryItem1;
    @BindView(R.id.item_category_icon_2)
    ImageView itemCategoryIcon2;
    @BindView(R.id.item_category_name_2)
    TextView itemCategoryName2;
    @BindView(R.id.item_category_item_2)
    LinearLayout itemCategoryItem2;
    @BindView(R.id.item_category_icon_3)
    ImageView itemCategoryIcon3;
    @BindView(R.id.item_category_name_3)
    TextView itemCategoryName3;
    @BindView(R.id.item_category_item_3)
    LinearLayout itemCategoryItem3;

    @Override
    public View initHolderView() {
        View holderView = View.inflate(UIUtils.getContext(), R.layout.item_category_normal, null);
        ButterKnife.bind(this, holderView);
        return holderView;
    }

    @Override
    protected void refreshHolderView(CategoryInfoBean data) {

        refreshUI(data.name1, data.url1, itemCategoryName1, itemCategoryIcon1);
        refreshUI(data.name2, data.url2, itemCategoryName2, itemCategoryIcon2);
        refreshUI(data.name3, data.url3, itemCategoryName3, itemCategoryIcon3);

    }

    public void refreshUI(final String name, String url, TextView tvName, ImageView ivIcon) {

        if (TextUtils.isEmpty(name) && TextUtils.isEmpty(url)) {

            ViewParent parent = tvName.getParent();
            ((ViewGroup) parent).setVisibility(View.INVISIBLE);
        } else {
            tvName.setText(name);
            Picasso.with(UIUtils.getContext()).load(
                    Constants.URLS.IMGBASEURL+url).into(ivIcon);
            LogUtils.s("分类的图片url地址-->"+url);
            ViewParent parent = tvName.getParent();
            ((ViewGroup)  parent).setVisibility(View.VISIBLE);
        }

    }
}

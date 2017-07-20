package cn.zzu.googleplaytest.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.zzu.googleplaytest.R;
import cn.zzu.googleplaytest.base.BaseHolder;
import cn.zzu.googleplaytest.bean.SubjectBean;
import cn.zzu.googleplaytest.conf.Constants;
import cn.zzu.googleplaytest.utils.UIUtils;
import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by yangg on 2017/7/15.
 * 这是subject的holder界面的创建和视图的绑定 holder
 */

public class SubjectHolder extends BaseHolder<SubjectBean> {
    @BindView(R.id.item_subject_iv_icon)
    ImageView itemSubjectIvIcon;
    @BindView(R.id.iten_subject_tv_title)
    TextView itenSubjectTvTitle;

    @Override
    public View initHolderView() {
        View holderView = View.inflate(UIUtils.getContext(), R.layout.item_subject, null);
        ButterKnife.bind(this, holderView);
        return holderView;
    }

    @Override
    protected void refreshHolderView(SubjectBean data) {

        itenSubjectTvTitle.setText(data.des);
        Picasso.with(UIUtils.getContext()).load(
                Constants.URLS.IMGBASEURL+data.url)
                .into(itemSubjectIvIcon);

    }
}

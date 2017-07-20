package cn.zzu.googleplaytest.holder;

import android.net.Uri;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.zzu.googleplaytest.R;
import cn.zzu.googleplaytest.base.BaseHolder;
import cn.zzu.googleplaytest.bean.ListBean;
import cn.zzu.googleplaytest.conf.Constants;
import cn.zzu.googleplaytest.utils.StringUtils;
import cn.zzu.googleplaytest.utils.UIUtils;

/**
 * Created by yangg on 2017/7/11.
 * 由HomeHolder完成那个填充布局,绑定数据等操作
 * 收取后HomeHolder就是Controller
 * HomeHolder的作用
 * 1,提供输入
 * 2,提供数据
 * 3,提供数据和视图的绑定
 */

public class ItemHolder extends BaseHolder<ListBean> {


    @BindView(R.id.item_appinfo_iv_icon)
    SimpleDraweeView itemAppinfoIvIcon;
    @BindView(R.id.item_appinfo_tv_title)
    TextView itemAppinfoTvTitle;
    @BindView(R.id.item_appinfo_rb_stars)
    RatingBar itemAppinfoRbStars;
    @BindView(R.id.item_appinfo_tv_size)
    TextView itemAppinfoTvSize;
    @BindView(R.id.item_appinfo_tv_des)
    TextView itemAppinfoTvDes;

    /**
     * @return
     * @des 初始化holderView, 决定所能提供的视图长什么样子
     * @called HomeHolder一旦创建的时候
     */

    @Override
    public View initHolderView() {

        View itemView = View.inflate(UIUtils.getContext(), R.layout.item_home, null);

        //找到子孩子,转换成成员变量
        ButterKnife.bind(this, itemView);

        return itemView;
    }

    /**
     * @param data
     * @des 数据和视图的绑定操作
     */

    @Override
    protected void refreshHolderView(ListBean data) {
        //view -->成员变量
        //data-->局部变量
        //data +_view
        itemAppinfoTvTitle.setText(data.name);
        itemAppinfoTvSize.setText(StringUtils.formatFileSize(data.size));
        itemAppinfoTvDes.setText(data.des);
        //ratingbar
        itemAppinfoRbStars.setRating(data.stars);

        //图片的加载
        String url = Constants.URLS.IMGBASEURL+data.iconUrl;


//        LogUtils.i("url---->"+url);
//        //静态图片的加载
//        Uri uri = Uri.parse(url);
//        itemAppinfoIvIcon.setImageURI(uri);
        /**
         * 支持gif图片的加载
         *
         */
        Uri uri= Uri.parse(url);
        DraweeController gifController= Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setAutoPlayAnimations(true)
                .build();
        itemAppinfoIvIcon.setController(gifController);

    }
}

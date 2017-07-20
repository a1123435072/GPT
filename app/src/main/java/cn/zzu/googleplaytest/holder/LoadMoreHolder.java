package cn.zzu.googleplaytest.holder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.zzu.googleplaytest.R;
import cn.zzu.googleplaytest.base.BaseHolder;
import cn.zzu.googleplaytest.utils.UIUtils;

import static cn.zzu.googleplaytest.base.SupterBaseAdapter.VIEWTYPE_LOADMODE;

/**
 * Created by yangg on 2017/7/14.
 */

public class LoadMoreHolder extends BaseHolder<Integer> {

    /**
     * 加载更多
     */
    public static final int LOADMORE_LOADING = 0;//正在加载更多
    /**
     * 普通的 item  条目
     */
    public static final int LOADMORE_ERROR = 1;//加载更多失败,点击重试

    /**
     * 没有加载更多
     */
    public static final int LOADMORE_NONE = 2;//没有加载更多
    @BindView(R.id.item_loadmore_container_loading)
    LinearLayout itemLoadmoreContainerLoading;
    @BindView(R.id.item_loadmore_tv_retry)
    TextView itemLoadmoreTvRetry;
    @BindView(R.id.item_loadmore_container_retry)
    LinearLayout itemLoadmoreContainerRetry;


    @Override
    public View initHolderView() {
        View holderview = View.inflate(UIUtils.getContext(), R.layout.item_loadmore, null);

        ButterKnife.bind(this, holderview);
        return holderview;
    }

    @Override
    protected void refreshHolderView(Integer curState) {

        //首先隐藏所有的视图
        itemLoadmoreContainerLoading.setVisibility(View.GONE);
        itemLoadmoreContainerRetry.setVisibility(View.GONE);

        switch (curState){
            case VIEWTYPE_LOADMODE:
                itemLoadmoreContainerLoading.setVisibility(View.VISIBLE);
                break;
            case LOADMORE_ERROR :
                itemLoadmoreContainerRetry.setVisibility(View.VISIBLE);
                break;
            case LOADMORE_NONE:
                break;
            default:
                break;
        }

    }
}

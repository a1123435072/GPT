package cn.zzu.googleplaytest.holder;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.zzu.googleplaytest.R;
import cn.zzu.googleplaytest.base.BaseHolder;
import cn.zzu.googleplaytest.base.MyApplication;
import cn.zzu.googleplaytest.conf.Constants;
import cn.zzu.googleplaytest.utils.UIUtils;
import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by yangg on 2017/7/15.
 */

public class HomePictureHolder extends BaseHolder<List<String>> implements ViewPager.OnPageChangeListener {
    @BindView(R.id.item_home_picture_pager)
    ViewPager itemHomePicturePager;
    @BindView(R.id.item_home_picture_container_indicator)
    LinearLayout itemHomePictureContainerIndicator;

    private List<String> mPrictureUrls ;

    /**  自动轮播的Task*/
    private AutoScrollTask mAutoScrollTask;

    @Override
    public View initHolderView() {

        View holderVeiw = View.inflate(UIUtils.getContext(), R.layout.item_home_pictures, null);

        ButterKnife.bind(this, holderVeiw);

        return holderVeiw;

    }

    @Override
    protected void refreshHolderView(List<String> pictureUrls) {
        //保存变量
        mPrictureUrls = pictureUrls;

//       data和view
        itemHomePicturePager.setAdapter(new HomePicturePagerAdapter());

        /*---------------mItemHomePictureContainerIndicator绑定  ---------------*/
        for (int i = 0; i < mPrictureUrls.size(); i++) {
            ImageView ivIndicator = new ImageView(UIUtils.getContext());
            //设置默认时候的点的src
            ivIndicator.setImageResource(R.drawable.indicator_normal);

            //选择默认选中第一个点
            if (i == 0){
                ivIndicator.setImageResource(R.drawable.indicator_selected);
            }
//            int sixDp = (int) UIUtils.getContext().getResources().getDisplayMetrics(R.dimen.width)+.5f;

            int sixDp =  (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6,
                    UIUtils.getResources().getDisplayMetrics()) + .5f);

            int width = UIUtils.dip2Px(6);//6dp
            int height = UIUtils.dip2Px(6);//6dp
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height);
            layoutParams.leftMargin  = UIUtils.dip2Px(6);
            layoutParams.bottomMargin = UIUtils.dip2Px(6);//6dp

            itemHomePictureContainerIndicator.addView(ivIndicator,layoutParams);
        }
        //监听
        itemHomePicturePager.setOnPageChangeListener(this);

        //这只viewPager页面的厨师位置
        int curItem = Integer.MAX_VALUE/2;
        //对curItem做偏差处理


        int diff = Integer.MAX_VALUE/2% mPrictureUrls.size();

        curItem = curItem - diff;
        itemHomePicturePager.setCurrentItem(curItem);


        //自动轮播
        if (mAutoScrollTask == null){
            mAutoScrollTask =new AutoScrollTask();
            mAutoScrollTask.start();
        }

        //点击的时候停止轮播
        itemHomePicturePager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        mAutoScrollTask.stop();
                        break;
                    case MotionEvent.ACTION_MOVE:

                        mAutoScrollTask.stop();
                        break;
                    case MotionEvent.ACTION_UP:

                        mAutoScrollTask.start();
                        break;
                }
                return false;
            }
        });



    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //处理position
        position = position% mPrictureUrls.size();


        //控制indecator的选中效果
        for (int i = 0; i < mPrictureUrls.size(); i++) {
            ImageView ivIndicator = (ImageView) itemHomePictureContainerIndicator.getChildAt(i);
            //还原默认效果
            ivIndicator.setImageResource(R.drawable.indicator_normal);

            //2,选中应该选中的效果
            if (position == i){
                ivIndicator.setImageResource(R.drawable.indicator_selected);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private class HomePicturePagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            if (mPrictureUrls!= null){



                //return mPrictureUrls.size();


                return Integer.MAX_VALUE;
            }
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //处理
            position = position% mPrictureUrls.size();


            ImageView iv = new ImageView(UIUtils.getContext());
            iv.setScaleType( ImageView.ScaleType.FIT_XY);//拉伸填充屏幕


            String url = mPrictureUrls.get(position);

            Picasso.with(UIUtils.getContext()).load(Constants.URLS.IMGBASEURL+url).into(iv);

            container.addView(iv);

            return iv;

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    /**
     * 实现自动轮播的Runnable对象
     */

    class  AutoScrollTask implements Runnable{

        @Override
        public void run() {
            //切换viewpager
            int currentItem = itemHomePicturePager.getCurrentItem();
            currentItem++;
            itemHomePicturePager.setCurrentItem(currentItem);
            start();
        }

        private void start() {
            stop();

            MyApplication.getmMainThreadHandlet().postDelayed(this,3000);
        }

        private void stop() {
            MyApplication.getmMainThreadHandlet().removeCallbacks(this);
        }
    }


}

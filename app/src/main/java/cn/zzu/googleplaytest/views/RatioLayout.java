package cn.zzu.googleplaytest.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import java.util.UUID;

import cn.zzu.googleplaytest.R;
import cn.zzu.googleplaytest.utils.LogUtils;
import cn.zzu.googleplaytest.utils.UIUtils;

/**
 * Created by yangg on 2017/7/15.
 */

public class RatioLayout extends FrameLayout {

    private static final String TAG = RatioLayout.class.getSimpleName();
    //已知宽度,动态计算高度
    public static final int RELATIVE_WIDTH = 0;//已知宽度,动态计算高度
    //已知高度,动态计算宽度
    public static final int RELATIVE_HEIGHT = 1;//一直高度,动态计算宽度

    private float mPicRatio = 2.42f;//定义宽高比例,通过构造方法传进来  暂时先写4死的  2.42f
    private int mRelative = RELATIVE_WIDTH;//相对于谁来计算值


    public RatioLayout(@NonNull Context context) {
        this(context, null);
    }

    public RatioLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        //取出自定的属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RationLayout);
        mPicRatio = typedArray.getFloat(R.styleable.RationLayout_picRatio,1);
        mRelative = typedArray.getInt(R.styleable.RationLayout_relative,RELATIVE_WIDTH);

        typedArray.recycle();
    }

    /**
     * 已知宽度,动态计算高度
     * MeasureSpec :
     * 是一个32 喂的合成值,,钱来归纳为代表Mode,后30喂代表size,我们可以ton故宫MeasureSpec.getMode().MeasureSpec.getSiz()来获取这来两个值
     * Mode分为三种
     * 公式:
     * 图片的的宽高比 = 档期那的空间的宽高比
     * 作用用:
     * 在一直宽的的情况下计算高度
     * 在一直高度的情况下,动态的额计算宽度
     */

//    设置图片的宽高比
    public void setmPicRatio(float mPicRatio) {
        this.mPicRatio = mPicRatio;
    }

    public void setmPelative(int mRelative) {
        this.mRelative = mRelative;
    }


    /**
     * EXACTLY 确定的
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //自己测量
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY&& mRelative == RELATIVE_WIDTH) {

            int selfWidth = MeasureSpec.getSize(widthMeasureSpec);
            //根据公式计算,迪昂前空间应用的高度
            //图片的卡un高逼 = 当前孔家的宽/档期那控件的高
            int selfHeight = (int) (selfWidth / mPicRatio + .5f);
            //保存测量结果
            setMeasuredDimension(selfWidth, selfHeight);

            int childWidth = selfWidth - getPaddingLeft() - getPaddingRight();
            int childHeight = selfHeight - getPaddingTop() - getPaddingBottom();

            //让孩子测量
            int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY);
            int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY);

            measureChildren(childWidthMeasureSpec, childHeightMeasureSpec);
            LogUtils.i("selfWidth-->" + UIUtils.px2Dip(selfWidth) + "dp");


        } else if (heightMode == MeasureSpec.EXACTLY && mPicRatio == RELATIVE_HEIGHT) {
            //得到空间的高度
            int selfHeight = MeasureSpec.getSize(heightMeasureSpec);
            //根据公式动态计算宽度
            //图片的宽高比例= 当前空间的额宽/当前空间的高

            int selfWidth= (int) (mPicRatio/selfHeight+.5f);

            //保存测量结果
            setMeasuredDimension(selfWidth,selfHeight);

            int childWidth = selfWidth  - getPaddingLeft() - getPaddingRight();
            int childHeight = selfHeight = getPaddingTop() - getPaddingBottom();
            //让孩子测量
            int  childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidth,MeasureSpec.EXACTLY);
            int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY);
            measureChildren(childWidthMeasureSpec,childHeightMeasureSpec);
            LogUtils.i("selfWidth-->"+ UIUtils.px2Dip(selfWidth)+"dp");

        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        }

    }
}

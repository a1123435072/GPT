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
import android.widget.TextView;

import java.util.Random;

import cn.zzu.googleplaytest.base.BaseFragmet;
import cn.zzu.googleplaytest.base.LoadingPager;

/**
 * Created by yangg on 2017/7/8.
 */

public class RecommendFragment extends BaseFragmet {

    /**
     * 在子类中真正的实现加载具体的数据
     * tiggerloaddata方法被调用的时候
     * @return
     */
    @Override
    public LoadingPager.LoadedResult initDate() {
        SystemClock.sleep(2000);//模拟耗时操作
        Random random = new Random();
        int index = random.nextInt(3);//0,1,2
        LoadingPager.LoadedResult[] loadedResults = {LoadingPager.LoadedResult.SUCCESS, LoadingPager.LoadedResult.EMPTY, LoadingPager.LoadedResult.ERREOR};

        return loadedResults[index];
    }

    /**
     * 决定成功的视图张什么样子,(需要定义成功的视图)
     * 数据和视图的绑定过程
     * tiggleloadingData()方法被调用,而且数据记载完成了,而且数据加载成功了
     * @return
     */
    @Override
    protected View initSuccessVeiw() {
        TextView successView = new TextView(getActivity());
        successView.setText("RecommendFragment" );
        successView.setTextColor(Color.RED);
        successView.setGravity(Gravity.CENTER);

        return successView;
    }
}
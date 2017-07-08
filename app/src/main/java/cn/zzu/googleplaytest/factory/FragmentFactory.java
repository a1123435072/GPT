package cn.zzu.googleplaytest.factory;

import android.support.v4.app.Fragment;

import cn.zzu.googleplaytest.fragment.AppFragment;
import cn.zzu.googleplaytest.fragment.CategoryFragment;
import cn.zzu.googleplaytest.fragment.GameFragment;
import cn.zzu.googleplaytest.fragment.HomeFragment;
import cn.zzu.googleplaytest.fragment.HotFragment;
import cn.zzu.googleplaytest.fragment.RecommendFragment;
import cn.zzu.googleplaytest.fragment.SubjectFragment;

/**
 * Created by yangg on 2017/7/8.
 * 描述  封装对Fragment的创建
 */

public class FragmentFactory {
    public static final int FRAGMENT_HOME = 0;//首页
    public static final int FRAGMENT_APP = 1;//应用
    public static final int FRAGMENT_GAME = 2;//游戏
    public static final int FRAGMENT_SUBJECT = 3;//专题
    public static final int FRAGMENT_RECOMMEND = 4;//推荐
    public static final int FRAGMENT_CATEGORY = 5;//分类
    public static final int FRAGMENT_HOT = 6;//排行

    public static Fragment createFragment(int position) {
        //定义Fragment对象
        Fragment fragment =null;
        switch (position){
            case FRAGMENT_HOME://返回 首页 对象的fragment
                fragment = new HomeFragment();
                break;
            case FRAGMENT_APP://返回 应用 对象的fragment
                fragment = new AppFragment();
                break;
            case FRAGMENT_GAME://返回 游戏 对象的fragment
                fragment = new GameFragment();
                break;
            case FRAGMENT_SUBJECT://返回 专题 对象的fragment
                fragment = new SubjectFragment();
                break;
            case FRAGMENT_RECOMMEND://返回 推荐 对象的fragment
                fragment = new RecommendFragment();
                break;
            case FRAGMENT_CATEGORY://返回 分类 对象的fragment
                fragment = new CategoryFragment();
                break;
            case FRAGMENT_HOT://返回 排行 对象的fragment
                fragment = new HotFragment();
                break;
            default:
                break;
        }
        return fragment;
    }




}

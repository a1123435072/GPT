package cn.zzu.googleplaytest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStripExtends;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.zzu.googleplaytest.factory.FragmentFactory;
import cn.zzu.googleplaytest.utils.LogUtils;
import cn.zzu.googleplaytest.utils.UIUtils;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_tabs)
    PagerSlidingTabStripExtends mainTabs;
    @BindView(R.id.main_viewpager)
    ViewPager mainViewpager;
    private DrawerLayout mDrawerLayotut;
    private ActionBarDrawerToggle toggle;
    private String[] mMainTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        intiActionBar();
        intiView();
        intiActionBarDrewerToggle();
        initData();

    }

    private void initData() {
        //模拟数据集
        mMainTitle = UIUtils.getStrings(R.array.main_titles);
        //为,viewPager设置适配器
        MainFragmentPagerAdapter mainFragmentPagerAdapter = new MainFragmentPagerAdapter(getSupportFragmentManager());

        mainViewpager.setAdapter(mainFragmentPagerAdapter);
        mainTabs.setViewPager(mainViewpager);

    }

    private void intiActionBarDrewerToggle() {
        toggle = new ActionBarDrawerToggle(this, mDrawerLayotut, R.string.open, R.string.close);

        //同步状态方法-->替换默认回退不放呢的ui效果
        toggle.syncState();
        //设置drawerLayout的监听-->DrawerLayout的拖动效果,toggle可以跟着该百年ui
        mDrawerLayotut.setDrawerListener(toggle);
    }


    /**
     * 初始化  视图
     */
    private void intiView() {

        mDrawerLayotut = (DrawerLayout) findViewById(R.id.main_drewerLayout);
    }

    private void intiActionBar() {
        //得到actionBar 的事例
        ActionBar supportActionBar = getSupportActionBar();

        //设置标题
        supportActionBar.setTitle("GooglePlay");
        //supportActionBar.setSubtitle("副标题");

        //设置图标
        supportActionBar.setIcon(R.drawable.ic_launcher);
        supportActionBar.setLogo(R.mipmap.ic_action_call);
        //显示logo/icon(图标) 默认是false,默认是隐藏图标
        supportActionBar.setDisplayShowHomeEnabled(true);
        //修改icon和logo显示的优先级,默认false 默认是没有logo,用的icon
        supportActionBar.setDisplayUseLogoEnabled(true);
        //显示回退部分//默认是false,默认隐藏了回退部分
        supportActionBar.setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Toast.makeText(getApplicationContext(), "点击了回退部分", Toast.LENGTH_SHORT).show();
                //点击togge可以控制drewerlayout的打开和关闭
                toggle.onOptionsItemSelected(item);
                break;
            default:

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private class MainFragmentPagerAdapter extends FragmentPagerAdapter {
        public MainFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        //自定Position所有的对应的页面的Fragment内容
        @Override
        public Fragment getItem(int position) {
            LogUtils.s("初始化=>" + mMainTitle[position]);
            String s = mMainTitle[position];
            Fragment fragment = FragmentFactory.createFragment(position);
            return fragment;
        }

        @Override
        public int getCount() {
            if (mMainTitle != null) {
                return mMainTitle.length;
            }
            return 0;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return mMainTitle[position];
        }
    }
}

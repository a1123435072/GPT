package cn.zzu.googleplaytest.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import cn.zzu.googleplaytest.R;
import cn.zzu.googleplaytest.base.LoadingPager;
import cn.zzu.googleplaytest.utils.UIUtils;

public class DetailActivity extends AppCompatActivity {

    private LoadingPager loadingPager;
    private String packageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //引入loadingploadingpager显示不同他几中加载状态
        loadingPager = new LoadingPager(this) {
            @Override
            public LoadedResult initData() {
                return null;
            }

            @Override
            public View initSuccessView() {
                return null;
            }
        };

        setContentView(R.layout.activity_detail);
        init();
    }

    private void init() {
        packageName = getIntent().getStringExtra("packageName");
        Toast.makeText(UIUtils.getContext(),packageName,Toast.LENGTH_SHORT).show();
    }
}
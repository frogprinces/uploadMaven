package com.example.zhaoshuai.mydemocollection.scroll;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.zhaoshuai.mydemocollection.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;

/**
 * Created by zs on 2017/9/15.
 *
 * 测试
 */

public class TestScrollActivity extends AppCompatActivity {

    private SmartRefreshLayout mRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_scroll);
        mRefreshLayout = (SmartRefreshLayout) findViewById(R.id.refresh_layout);
        mRefreshLayout.setEnableLoadmore(true);
        mRefreshLayout.setRefreshFooter(new ClassicsFooter(this));
    }
}

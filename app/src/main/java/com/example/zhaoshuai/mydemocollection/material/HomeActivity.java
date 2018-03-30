package com.example.zhaoshuai.mydemocollection.material;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.zhaoshuai.mydemocollection.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zs on 2017/8/31.
 *
 * Material Design设计风格的页面
 */

public class HomeActivity extends AppCompatActivity {

    private ListView mListView;
    private List<String> mList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fb);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Snackbar.make(v, "确认要删除数据？", Snackbar.LENGTH_LONG).setAction("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(HomeActivity.this, "数据已删除", Toast.LENGTH_SHORT).show();
                    }
                }).show();


            }
        });

        mListView = (ListView) findViewById(R.id.list_View);
        mListView.setDividerHeight(20);
        initData();
    }


    /**
     * 初始化数据
     */
    private void initData() {
        mList = new ArrayList<>();

        for (int i = 0; i < 20; i++){
            mList.add("张三");
        }
        mListView.setAdapter(new MyAdapter());
    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int i) {
            return mList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if(view == null){
                view = LayoutInflater.from(HomeActivity.this).inflate(R.layout.item_food,viewGroup ,false);
            }

            return view;
        }
    }


}

package com.example.zhaoshuai.mydemocollection.animation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.zhaoshuai.mydemocollection.R;

/**
 * Created by zs on 2017/11/16.
 *
 * 首页Activity
 */

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home_new);
        findViewById(R.id.tv_home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, GameActivity.class));
                overridePendingTransition(R.anim.scale_in, R.anim.scale_out);
            }
        });
    }
}

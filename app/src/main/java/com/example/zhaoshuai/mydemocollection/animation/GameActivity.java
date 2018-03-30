package com.example.zhaoshuai.mydemocollection.animation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;

import com.example.zhaoshuai.mydemocollection.R;
import com.example.zhaoshuai.mydemocollection.glide.GlideActivity;

/**
 * Created by zs on 2017/11/16.
 *
 * 游戏Activity
 */

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        findViewById(R.id.tv_game).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GameActivity.this, GlideActivity.class));
            }
        });
    }

    //处理后退键的情况
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){

            this.finish();  //finish当前activity
            overridePendingTransition(R.anim.scale_out,
                    R.anim.scale_in);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

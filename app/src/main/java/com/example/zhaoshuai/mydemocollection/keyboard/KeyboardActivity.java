package com.example.zhaoshuai.mydemocollection.keyboard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.zhaoshuai.mydemocollection.R;

/**
 * Created by zs on 2017/9/8.
 *
 * 键盘页面
 */

public class KeyboardActivity extends AppCompatActivity implements
        NumberKeyboardView.IOnKeyboardListener, NumberKeyboardView.IOnFunctionListener{

    private EditText mEtUserName;
    private NumberKeyboardView mNumberKeyboardView;
    private Button mShowNumberKeyboard;
    private Button mHideNumberKeyboard;
    private NumberKeyboardManager mNumberKeyboardManager;
    private EditText mEtPwd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyboard);

        mEtUserName = (EditText) findViewById(R.id.et_user_name);
        mEtPwd = (EditText) findViewById(R.id.et_pwd);
        mNumberKeyboardView = (NumberKeyboardView) findViewById(R.id.number_keyboard);
        mShowNumberKeyboard = (Button) findViewById(R.id.btn_show_number_keyboard);
        mHideNumberKeyboard = (Button) findViewById(R.id.btn_hide_number_keyboard);


        mNumberKeyboardManager = new NumberKeyboardManager(KeyboardActivity.this, mEtPwd);


        mNumberKeyboardView.setOnFunctionListener(this);

        mEtUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNumberKeyboardView.setFocusable(true);
                mNumberKeyboardView.setFocusableInTouchMode(true);
                mNumberKeyboardView.setVisibility(View.VISIBLE);
            }
        });


        mShowNumberKeyboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                NumberKeyboardDialog numberKeyboardDialog = new NumberKeyboardDialog(KeyboardActivity.this);
//                numberKeyboardDialog.show();

                mNumberKeyboardManager.showKeyboard();
            }
        });

        mHideNumberKeyboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNumberKeyboardManager.hideKeyboard();
            }
        });






    }

    @Override
    public void onInsertKeyEvent(String text) {}

    @Override
    public void onDeleteKeyEvent() {}

    @Override
    public void onFinishKeyEvent() {
        mNumberKeyboardView.setVisibility(View.GONE);
    }

    @Override
    public void onHideKeyEvent() {
        mNumberKeyboardView.setVisibility(View.GONE);
    }
}

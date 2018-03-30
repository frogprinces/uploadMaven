package com.example.zhaoshuai.mydemocollection.glide;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.zhaoshuai.mydemocollection.CommonUtils;
import com.example.zhaoshuai.mydemocollection.R;
import com.example.zhaoshuai.mydemocollection.mobile.DeviceInfoUtils;
import com.microquation.linkedme.android.LinkedME;
import com.microquation.linkedme.android.callback.LMLinkCreateListener;
import com.microquation.linkedme.android.indexing.LMUniversalObject;
import com.microquation.linkedme.android.referral.LMError;
import com.microquation.linkedme.android.util.LinkProperties;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by zs on 2017/10/11.
 *
 * Glide演示
 */

public class GlideActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks{

    private String imageCode = "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";


    private ImageView mIvBackground;

    /** 权限申请：手机状态权限 */
    private static final int REQUEST_PERMISSION_READ_PHONE_STATE = 0x1111;
    private String mUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);

        Button btnLoadImage = (Button) findViewById(R.id.btn_load_image);
        mIvBackground = (ImageView) findViewById(R.id.iv_background);
        findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtils.jumpWithUri(GlideActivity.this, mUri);
            }
        });

        btnLoadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //init();

//                getIMEI();
//
//                String ANDROID_ID = Settings.System.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
//                Log.d("glide_ANDROID_ID", ": " + ANDROID_ID);
//
//                String macAddr = DeviceInfoUtils.getMacAddress();
//                Log.e("glide_macAddr", ": " + macAddr);
//                Toast.makeText(GlideActivity.this, "glide_macAddr: " + macAddr, Toast.LENGTH_SHORT).show();
//
//                int i = testTryCatch();
//                Log.d("testTryCatch", "onClick: " + i);


//                createLinkedMe();


//                Intent intent = new Intent(GlideActivity.this, RoundProgressActivity.class);
//                mUri = intent.toUri(Intent.URI_INTENT_SCHEME);
//                Log.d("intent_uri", "onClick: uri: " + mUri);






                //mIvBackground.setImageBitmap(BitmapUtils.stringToBitmap(imageCode));

                String ip = DeviceInfoUtils.getIPAddress(GlideActivity.this);
                String userAgent = System.getProperty("http.agent");
                Log.d("device_ip: ", ip + "device_ua: " + userAgent);

            }
        });

    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LinkedME.getInstance().setImmediate(true);
    }

    /**
     * 初始化
     */
    private void init(){
        String url = "http://f.hiphotos.baidu.com/image/pic/item/64380cd7912397ddbc4ce8c55082b2b7d0a2873d.jpg";
        Glide.with(this).load(url).into(mIvBackground);

        String msg = "我是你的爸爸";
        Log.d("GlideActivity", "init: " + msg);
    }


    private void getIMEI(){


        String[] permissions = new String[]{Manifest.permission.READ_PHONE_STATE};

        if (Build.VERSION.SDK_INT < 23) {
            String imei = DeviceInfoUtils.getIMei(GlideActivity.this);
            Log.e("glide_imei", "-imei: " + imei);
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) ==
                    PackageManager.PERMISSION_GRANTED) {
                String imei = DeviceInfoUtils.getIMei(GlideActivity.this);
                Log.e("glide_imei", "-imei: " + imei);
            } else {

                if(shouldShowRequestPermissionRationale(Manifest.permission.READ_PHONE_STATE)){
                    Toast.makeText(this, "我觉得你最好打开这个权限，手机不然会爆", Toast.LENGTH_SHORT).show();
                }else {
                    requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE},
                            REQUEST_PERMISSION_READ_PHONE_STATE);
                }


            }
        }


//        if(EasyPermissions.hasPermissions(this, permissions)){
//            String imei = DeviceInfoUtils.getIMei(GlideActivity.this);
//            Log.e("glide_imei", "-imei: " + imei);
//        }else {
//            EasyPermissions.requestPermissions(this, "需要读取手机状态信息权限才能正常使用", REQUEST_PERMISSION_READ_PHONE_STATE, permissions);
//        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //        switch (requestCode) {
        //        case REQUEST_PERMISSION_READ_PHONE_STATE:
        //            if (grantResults.length > 0
        //                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        //                String imei = DeviceInfoUtils.getIMei(GlideActivity.this);
        //                Log.e("glide_imei", "-imei: " + imei);
        //            }
        //            break;
        //        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        String imei = DeviceInfoUtils.getIMei(GlideActivity.this);
        Log.e("glide_imei", "-imei: " + imei);
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.e("glide_imei", "-imei: 拒绝权限 ");
    }


    private int testTryCatch(){
        int i = 1;

        try {
            Log.d("testTryCatch", "testTryCatch: " + i);
            //int j = 1/0;
            return ++i;
        }catch (Exception ex){
            ++i;
            Log.d("testTryCatch- catch", "testTryCatch: " + i);
        }finally {
            ++i;
            Log.d("testTryCatch - finally", "testTryCatch: " + i);
        }
        return i;
    }


    private void createLinkedMe(){
        /**创建深度链接*/
        //  提示：虽然客户端可自行创建深度链接并分享，但是web端也需要对分享链接进行处理才可使用深度链接，
        //  需要将分享链接中的深度链接截取出来，并作为“打开app”按钮的跳转链接(因此，建议使用js sdk创建深度链接)。
        //  例如：
        //     原有的分享链接为：https://www.linkedme.cc/h5/partner
        //     追加深度链接的分享链接为：https://www.linkedme.cc/h5/partner?linkedme=https://lkme.cc/AfC/CeG9o5VH8
        //     web端需要将深度链接https://lkme.cc/AfC/CeG9o5VH8取出并作为“打开app”按钮的跳转链接。

        //深度链接属性设置
        final LinkProperties properties = new LinkProperties();
        //渠道
        properties.setChannel("");  //微信、微博、QQ
        //功能
        properties.setFeature("Share");
        //标签
        properties.addTag("LinkedME");
        properties.addTag("Demo");
        //阶段
        properties.setStage("Live");
        //设置该h5_url目的是为了iOS点击右上角lkme.cc时跳转的地址，一般设置为当前分享页面的地址
        properties.setH5Url("https://linkedme.cc/h5/feature");
        //自定义参数,用于在深度链接跳转后获取该数据
        properties.addControlParameter("LinkedME", "Demo");
        properties.addControlParameter("View", "Demo");
        LMUniversalObject universalObject = new LMUniversalObject();
        universalObject.setTitle("Demo");

        // Async Link creation example
        universalObject.generateShortUrl(this, properties, new LMLinkCreateListener() {
            @Override
            public void onLinkCreate(String url, LMError error) {
                if (error == null) {
                    //url为生成的深度链接
                    //demo_edit.setText(url);
                    //获取深度链接对应的自定义参数数据
                    //demo_link_view.setText(properties.getControlParams().toString());

                    Log.i("my_linked", "LinkedME onCreated " + url);
                } else {
                    Log.i("my_linked", "创建深度链接失败！失败原因：" + error.getMessage());
                }
            }
        });

    }

}
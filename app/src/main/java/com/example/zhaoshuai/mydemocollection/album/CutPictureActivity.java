package com.example.zhaoshuai.mydemocollection.album;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.example.zhaoshuai.mydemocollection.R;

/**
 * Created by zs on 2017/9/28.
 *
 * 裁减图片
 */

public class CutPictureActivity extends AppCompatActivity {

    private ImageView mIvPicture;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cut_picture);

        mIvPicture = (ImageView) findViewById(R.id.iv_picture);

        Uri imageUri = getIntent().getParcelableExtra("picture_path");
       // String imagePath = getIntent().getStringExtra("picture_absolute_path");
        Log.d("about_picture", "imageUri: " + imageUri);

        try {
            //Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));

            //Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            //mIvPicture.setImageBitmap(bitmap);

            mIvPicture.setImageURI(imageUri);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}

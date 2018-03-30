package com.example.zhaoshuai.mydemocollection.album;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.zhaoshuai.mydemocollection.R;
import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.io.IOException;

/**
 * Created by zs on 2017/9/28.
 *
 * 拍照
 */

public class PictureActivity extends AppCompatActivity implements View.OnClickListener{

    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;

    private Uri mImageUri;
    private File mOutputImage;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        Button btnTakePhoto = (Button) findViewById(R.id.btn_take_photo);
        Button btnOpenAlbum = (Button) findViewById(R.id.btn_open_album);

        btnTakePhoto.setOnClickListener(this);
        btnOpenAlbum.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
        case R.id.btn_take_photo:
            takePhoto();
            break;
        case R.id.btn_open_album:
            //openAlbum();
            Crop.pickImage(this);
            break;
        default:
            break;
        }
    }

    /**
     * 拍照
     */
    private void takePhoto(){
        mOutputImage = new File(Environment.getExternalStorageDirectory(), "le_yu_output_image.jpg");

        try {
            if(mOutputImage.exists()){
                mOutputImage.delete();
            }
            mOutputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(Build.VERSION.SDK_INT >= 24){
            mImageUri = FileProvider.getUriForFile(this, "com.example.zhaoshuai.mydemocollection",
                    mOutputImage);
        } else {
            mImageUri = Uri.fromFile(mOutputImage);
        }


        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
        startActivityForResult(intent, TAKE_PHOTO);
    }

    /**
     * 打开相册
     */
    private void openAlbum(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
        case TAKE_PHOTO:
            if(resultCode == RESULT_OK){
                //发送广播，通知系统相册更新图库照片
                Intent albumIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                albumIntent.setData(mImageUri);
                this.sendBroadcast(albumIntent);

//                Intent intent = new Intent(PictureActivity.this, CutPictureActivity.class);
//                intent.putExtra("picture_absolute_path", mOutputImage.getAbsolutePath());
//                startActivity(intent);

                beginCrop(mImageUri);
            }
            break;
        case Crop.REQUEST_PICK:
            if(resultCode == RESULT_OK){
//                if(Build.VERSION.SDK_INT >= 19){
//                    handleImageOnKitKat(data);
//                } else {
//                    handleImageBeforeKitKat(data);
//                }
                beginCrop(data.getData());

            }
            break;
        case Crop.REQUEST_CROP:
            handleCrop(resultCode, data);
            break;
        default:
            break;
        }
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data){
        String imagePath = null;
        Uri uri = data.getData();
        if(DocumentsContract.isDocumentUri(this, uri)){
            String docId = DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        }else if("content".equalsIgnoreCase(uri.getScheme())){
            imagePath = getImagePath(uri, null);
        } else if("file".equalsIgnoreCase(uri.getScheme())){
            imagePath= uri.getPath();
        }

        displayImage(imagePath);
    }

    private void handleImageBeforeKitKat(Intent data){
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }


    private String getImagePath(Uri uri, String selection){
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if(cursor != null){
            if(cursor.moveToFirst()){
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath){
        if(imagePath != null){
            Intent intent = new Intent(PictureActivity.this, CutPictureActivity.class);
            intent.putExtra("picture_absolute_path", imagePath);
            startActivity(intent);
        }
    }


    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"));
        Crop.of(source, destination).asSquare().start(this);
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            Uri uri = Crop.getOutput(result);
            //resultView.setImageURI(Crop.getOutput(result));
            Intent intent = new Intent(PictureActivity.this, CutPictureActivity.class);
            intent.putExtra("picture_path", uri);
            startActivity(intent);
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}

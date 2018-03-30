package com.hb.ctcf.update_version;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.text.TextUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by zs on 2018/2/5.
 *
 * 更新下载服务
 */

public class DownloadService extends IntentService {

    /**  取消下载任务*/
    public static boolean mStopDownload;

    public DownloadService() {
        super("DownloadService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if(intent == null){
            return;
        }
        String downloadUrl = intent.getStringExtra(UpdateManager.DOWNLOAD_URL);
        String downloadPath = intent.getStringExtra(UpdateManager.DOWNLOAD_PATH);
        ResultReceiver receiver = intent.getParcelableExtra(UpdateManager.DOWNLOAD_RECEIVER);
        Bundle resultData = new Bundle();
        if(TextUtils.isEmpty(downloadUrl) || TextUtils.isEmpty(downloadPath)){
            if(receiver != null){
                resultData.putString("error","下载初始化失败");
                receiver.send(UpdateManager.DOWNLOAD_ERROR,resultData);
            }
            return;
        }

        HttpURLConnection conn = null;
        InputStream inputStream = null;
        FileOutputStream fos = null;

        try {
            conn = (HttpURLConnection) new URL(downloadUrl).openConnection();
            conn.connect();
            resultData.putInt("progress",0);
            if(receiver != null){
                receiver.send(UpdateManager.DOWNLOAD_PROGRESS,resultData);
            }

            inputStream = conn.getInputStream();
            fos = new FileOutputStream(downloadPath);
            int totalSize = conn.getContentLength();

            byte[] buffer = new byte[4096];
            int length;
            long progress = 0;
            long progressUpdateTime = 0;
            while ((length = inputStream.read(buffer)) != -1) {
                if(mStopDownload){
                    return;
                }
                fos.write(buffer, 0, length);
                progress += length;

                // 通知下载进度更新
                long nowTime = System.currentTimeMillis();
                if (nowTime - progressUpdateTime > 200) {
                    progressUpdateTime = nowTime;
                    resultData.putInt("progress",(int)(progress * 100 / totalSize));
                    if(receiver != null){
                        receiver.send(UpdateManager.DOWNLOAD_PROGRESS,resultData);
                    }
                }
            }
            fos.flush();
            if(receiver != null){
                receiver.send(UpdateManager.DOWNLOAD_SUCCESS,resultData);
            }

            if(mStopDownload){
                return;
            }

        } catch (IOException e) {
            e.printStackTrace();
            resultData.putString("error","下载失败,请检查网络配置");
            if(receiver != null){
                receiver.send(UpdateManager.DOWNLOAD_ERROR,resultData);
            }
        } catch (Exception e){
            e.printStackTrace();
            resultData.putString("error","下载失败,请检查网络配置");
            if(receiver != null){
                receiver.send(UpdateManager.DOWNLOAD_ERROR,resultData);
            }
        } finally {
            try {
                if(inputStream != null){
                    inputStream.close();
                }
            } catch (IOException e) {
                // ignore this exception...
            }
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception e) {
                // ignore this exception...
            }
            try {
                if (conn != null) {
                    conn.disconnect();
                }
            } catch (Exception e) {
                // ignore this exception...
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 停止下载
     */
    public static void stopDownload(){
        mStopDownload = true;
    }
}

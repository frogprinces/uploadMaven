package com.hb.ctcf.update_version;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.text.TextUtils;

/**
 * Created by zs on 2018/2/5.
 *
 * 更新帮助类
 */

class UpdateHelper {

    /**
     * 检查配置信息
     *
     * @param downloadBuilder 配置信息
     */
    static boolean checkDownloadBuilder(DownloadBuilder downloadBuilder) throws Exception {
        if(downloadBuilder == null){
            throw  new Exception("downloadBuilder is null");
        }

        if(TextUtils.isEmpty(downloadBuilder.getDownloadUrl())){
            throw  new Exception("downloadUrl is empty");
        }

        if(TextUtils.isEmpty(downloadBuilder.getDownloadFileName())){
            throw  new Exception("downloadFileName is empty");
        }

        if(TextUtils.isEmpty(downloadBuilder.getAppName())){
            throw  new Exception("appName is empty");
        }

        if(downloadBuilder.getAppLogoResource() == 0){
            throw  new Exception("appLogoResource is empty");
        }

        if(downloadBuilder.getContext() == null){
            throw  new Exception("context is null");
        }

        if(downloadBuilder.getVersionCode() == 0){
            throw  new Exception("versionCode is empty");
        }

        if(downloadBuilder.getDownloadListener() == null){
            throw  new Exception("downloadListener is null");
        }

        return true;
    }

    /**
     * 判断是否连接网络
     *
     * @param context 上下文
     * @return true: 连接  false： 未连接
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (null == cm) {
            return false;
        }

        NetworkInfo info = cm.getActiveNetworkInfo();
        if (null != info && info.isConnected()) {
            if (info.getState() == NetworkInfo.State.CONNECTED) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断sdcard是否可用
     *
     * @return false:不可用 true:可用
     */
    public static boolean getSdcardState(){
        String storageState = Environment.getExternalStorageState();
        return storageState.equals(Environment.MEDIA_MOUNTED);
    }
}

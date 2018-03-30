package com.hb.ctcf.update_version;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

/**
 * Created by zs on 2018/2/2.
 *
 * 更新管理类
 */

public class UpdateManager {

    /**  通知栏ID*/
    private static final int NOTIFICATION_FLAG = 0x8;

    /**  下载apk状态码: 失败*/
    static final int DOWNLOAD_ERROR = 0x1;

    /**  下载apk状态码: 进度*/
    static final int DOWNLOAD_PROGRESS = 0x2;

    /**  下载apk状态码: 成功*/
    static final int DOWNLOAD_SUCCESS = 0x4;

    /**  页面参数: 下载网址*/
    static final String DOWNLOAD_URL = "downloadUrl";

    /**  页面参数: 下载地址*/
    static final String DOWNLOAD_PATH = "downloadPath";

    /**  页面参数: 下载回调*/
    static final String DOWNLOAD_RECEIVER = "downloadReceiver";

    /** 下载实例 */
    private static UpdateManager mInstance;

    /** 下载配置信息 */
    private DownloadBuilder mDownloadBuilder;

    /**  apk下载真实路径*/
    private String mDownloadPath = "";

    /**  apk下载临时路径*/
    private String mTempDownloadPath = "";

    /**  通知栏配置*/
    private NotificationCompat.Builder mBuilder;

    /**  通知管理*/
    private NotificationManager mNotificationManager;

    /**  上下文 */
    private Context mContext;

    /**
     * 私有构造方法
     */
    private UpdateManager(){}

    /**
     * 初始化
     *
     * @return UpdateManager
     */
    public static UpdateManager getInstance(){
        if(mInstance == null){
            mInstance = new UpdateManager();
        }
        return mInstance;
    }

    /**
     * 构建下载配置信息
     *
     * @return DownloadBuilder
     */
    public DownloadBuilder createBuilder(){
        mDownloadBuilder = new DownloadBuilder();
        return mDownloadBuilder;
    }

    /**
     * 开始下载
     */
    void startDownload(){
        if(!checkDownloadBuilder()){
            return;
        }

        mContext = mDownloadBuilder.getContext();
        createFile();
    }

    /**
     * 停止下载
     */
    public void stopDownload(){
        DownloadService.stopDownload();
    }

    /**
     * 创建文件
     */
    private void createFile(){
        String apkName = mDownloadBuilder.getDownloadFileName() + "_" + mDownloadBuilder.getVersionCode() + ".apk";
        String temptApkName = mDownloadBuilder.getDownloadFileName() + "_" + mDownloadBuilder.getVersionCode() + ".tmp";

        String downloadPath;
        if(UpdateHelper.getSdcardState()){
            if(mContext.getExternalCacheDir() != null){
                downloadPath = mContext.getExternalCacheDir().getAbsolutePath() + "/" + mDownloadBuilder.getDownloadFileName() + "/update/";
            } else {
                Toast.makeText(mContext, "sd卡暂不可用", Toast.LENGTH_SHORT).show();
                return;
            }
        } else {
            Toast.makeText(mContext, "sd卡暂不可用", Toast.LENGTH_SHORT).show();
            return;
        }

        File file = new File(downloadPath);
        if (!file.exists()) {
            file.mkdirs();
        }

        //创建下载路径
        mDownloadPath = downloadPath + apkName;
        mTempDownloadPath = downloadPath + temptApkName;

        //下载前操作
        if(mDownloadBuilder.getDownloadListener() != null){
            mDownloadBuilder.getDownloadListener().onDownloadStart();
        }

        //apk文件存在，直接安装
        File apkFile = new File(mDownloadPath);
        if (apkFile.exists()) {
            installApk();
            return;
        }

        //初始化通知栏
        initNotification();

        //启动下载服务
        startDownLoadService();
    }

    /**
     * 初始化通知栏
     */
    private void initNotification() {
        mBuilder = new NotificationCompat.Builder(mDownloadBuilder.getContext());
        mBuilder.setContentTitle(mDownloadBuilder.getAppName())
                .setContentIntent(getPendingIntent())
                .setAutoCancel(true)
                .setContentText("正在下载...")
                .setSmallIcon(mDownloadBuilder.getAppLogoResource());
        mNotificationManager = (NotificationManager) mContext
                .getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(NOTIFICATION_FLAG,mBuilder.build());
    }

    /**
     * 通知栏点击动作
     *
     * @return 意图Intent
     */
    private PendingIntent getPendingIntent() {
        File apkFile = new File(mDownloadPath);
        if (!apkFile.exists()) {
            return null;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri contentUri = FileProvider.getUriForFile(mContext, "update.version.file.provider", apkFile);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        }
        return PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_ONE_SHOT);
    }

    /**
     * 启动后台下载
     */
    private void startDownLoadService() {
        Intent intent = new Intent(mContext, DownloadService.class);
        intent.putExtra(DOWNLOAD_URL, mDownloadBuilder.getDownloadUrl());
        intent.putExtra(DOWNLOAD_PATH, mTempDownloadPath);
        intent.putExtra(DOWNLOAD_RECEIVER, new UpdateReceiver(new Handler()));
        mContext.startService(intent);
    }

    private class UpdateReceiver extends ResultReceiver {

        UpdateReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);
            switch (resultCode) {
            case DOWNLOAD_ERROR:
                String errorMessage = resultData.getString("error");
                mBuilder.setContentText("下载失败");
                mNotificationManager.notify(NOTIFICATION_FLAG, mBuilder.build());
                if(mDownloadBuilder.getDownloadListener() != null){
                    mDownloadBuilder.getDownloadListener().onDownloadFail(errorMessage);
                }
                break;
            case DOWNLOAD_PROGRESS:
                int progress = resultData.getInt("progress");
                mBuilder.setProgress(100, progress, false);
                mBuilder.setContentInfo(progress + "%");
                mNotificationManager.notify(NOTIFICATION_FLAG, mBuilder.build());
                if(mDownloadBuilder.getDownloadListener() != null){
                    mDownloadBuilder.getDownloadListener().onProgressChange(progress);
                }
                break;
            case DOWNLOAD_SUCCESS:
                if(mDownloadBuilder.getDownloadListener() != null){
                    mDownloadBuilder.getDownloadListener().onDownloadFinish();
                }
                downloadFinish();
                break;
            default:
                break;
            }
        }
    }

    /**
     * 下载完成
     */
    private void  downloadFinish() {
        File temFile = new File(mTempDownloadPath);
        File apkFile = new File(mDownloadPath);
        if(temFile.exists()){
            temFile.renameTo(apkFile);
        }
        mBuilder.setContentTitle(mDownloadBuilder.getAppName());
        mBuilder.setContentText("下载成功");
        mBuilder.setProgress(0, 0, false);
        mBuilder.setContentInfo(100+"%");
        mBuilder.setContentIntent(getPendingIntent());
        mNotificationManager.notify(NOTIFICATION_FLAG, mBuilder.build());

        try {
            //修改APK文件权限
            String[] command = {"chmod", "777", apkFile.toString()};
            ProcessBuilder builder = new ProcessBuilder(command);
            builder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        installApk();
    }

    /**
     * 安装Apk
     */
    private void installApk() {
        File apkFile = new File(mDownloadPath);
        if (!apkFile.exists()) {
            return;
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri contentUri = FileProvider.getUriForFile(mContext, "update.version.file.provider", apkFile);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        }
        mContext.startActivity(intent);
    }

    /**
     * 检查数据
     */
    private boolean checkDownloadBuilder(){
        try {
            return UpdateHelper.checkDownloadBuilder(mDownloadBuilder);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

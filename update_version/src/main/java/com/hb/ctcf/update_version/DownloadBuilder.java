package com.hb.ctcf.update_version;

import android.content.Context;

/**
 * Created by zs on 2018/2/2.
 *
 * 下载配置信息
 */

public class DownloadBuilder {

    /** 下载地址 */
    private String mDownloadUrl;

    /** 下载保存文件夹名称 */
    private String mDownloadFileName;

    /** 应用名称 */
    private String mAppName;

    /** 应用Logo资源 */
    private int mAppLogoResource;

    /** 版本号 */
    private int mVersionCode = 0;

    /** 下载监听回调 */
    private DownloadListener mDownloadListener;

    /** 上下文 */
    private Context mContext;


    /**
     * 设置下载地址
     *
     * @param downloadUrl 下载地址
     * @return DownloadBuilder
     */
    public DownloadBuilder setDownloadUrl(String downloadUrl){
        this.mDownloadUrl = downloadUrl;
        return this;
    }

    /**
     * 设置下载文件夹名称
     *
     * @param downloadFileName 文件夹名称
     * @return DownloadBuilder
     */
    public DownloadBuilder setDownloadFileName(String downloadFileName){
        this.mDownloadFileName = downloadFileName;
        return this;
    }

    /**
     * 设置上下文
     *
     * @param context 上下文
     * @return DownloadBuilder
     */
    public DownloadBuilder setContext(Context context){
        this.mContext = context;
        return this;
    }

    /**
     * 设置App名称
     *
     * @param appName app名称
     * @return DownloadBuilder
     */
    public DownloadBuilder setAppName(String appName){
        this.mAppName = appName;
        return this;
    }

    /**
     * 设置App logo资源
     *
     * @param appLogoResource logo资源
     * @return DownloadBuilder
     */
    public DownloadBuilder setAppLogoResource(int appLogoResource){
        this.mAppLogoResource = appLogoResource;
        return this;
    }

    /**
     * 设置版本号
     *
     * @param versionCode 版本号
     * @return DownloadBuilder
     */
    public DownloadBuilder setVersionCode(int versionCode){
        this.mVersionCode = versionCode;
        return this;
    }

    /**
     * 设置下载监听回调
     *
     * @param downloadListener 下载监听
     * @return DownloadBuilder
     */
    public DownloadBuilder setDownloadListener(DownloadListener downloadListener){
        this.mDownloadListener = downloadListener;
        return this;
    }

    /**
     * 获取下载地址
     *
     * @return 下载地址
     */
    public String getDownloadUrl() {
        return mDownloadUrl;
    }

    /**
     * 获取下载文件夹名称
     *
     * @return 文件夹名称
     */
    public String getDownloadFileName() {
        return mDownloadFileName;
    }

    /**
     * 获取应用名称
     *
     * @return 应用名称
     */
    public String getAppName() {
        return mAppName;
    }

    /**
     * 获取应用Logo资源
     *
     * @return Logo资源
     */
    public int getAppLogoResource() {
        return mAppLogoResource;
    }

    /**
     * 获取上下文
     *
     * @return 上下文
     */
    public Context getContext(){
        return mContext;
    }

    /**
     * 获取版本号
     *
     * @return 版本号
     */
    public int getVersionCode(){
        return mVersionCode;
    }

    /**
     * 获取下载监听
     *
     * @return 下载监听
     */
    public DownloadListener getDownloadListener(){
        return mDownloadListener;
    }
    /**
     * 开始构建
     *
     * @return DownloadBuilder
     */
    public DownloadBuilder build(){
        UpdateManager.getInstance().startDownload();
        return this;
    }
}

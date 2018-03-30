package com.hb.ctcf.update_version;

/**
 * Created by zs on 2018/2/2.
 *
 * 下载监听
 */

public interface DownloadListener {

    /**
     * 下载之前
     */
    void onDownloadStart();

    /**
     * 下载失败
     */
    void onDownloadFail(String message);

    /**
     * 下载进度改变
     *
     * @param progress 下载进度
     */
    void onProgressChange(int progress);

    /**
     * 下载完成
     */
    void onDownloadFinish();
}

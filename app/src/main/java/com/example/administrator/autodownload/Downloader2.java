package com.example.administrator.autodownload;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;

/**
 * Created by Administrator on 2016/12/26.
 */

public class Downloader2 {

    public static void download(Context context, String url) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        //设置文件存放目录
        request.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, "");

    }


}

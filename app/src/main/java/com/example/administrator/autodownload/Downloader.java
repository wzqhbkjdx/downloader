package com.example.administrator.autodownload;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2016/12/26.
 */

public class Downloader {

    private String sdCard;

    private HttpURLConnection urlConnection;

    private String dir;
    private String fileName;

    private DownloadHandler handler;

    public Downloader(String dir, String fileName, String urlStr) {
        this.sdCard = Environment.getExternalStorageDirectory() + "/";
        this.dir = dir;
        this.fileName = fileName;

        try {
            URL url = new URL(urlStr);
            urlConnection = (HttpURLConnection) url.openConnection();
            Log.i("wzq", "download opurlconnection");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setHandler(DownloadHandler handler) {
        this.handler = handler;
    }

    public void download() {

        //创建目录
        StringBuilder sb = new StringBuilder(sdCard).append(dir);
        File file = new File(sb.toString());
        if(!file.exists()) {
            file.mkdir();
        }

        //创建文件
        sb.append(fileName);
        File file2Download = new File(sb.toString());
        if(file2Download.isFile() && file2Download.exists()) {
            file2Download.delete();
        }
        Log.i("wzq", "download fileName" + sb);
        FileOutputStream fos = null;

        handler.getFileLength(getFileLength());

        //下载文件
        try {
            InputStream is = urlConnection.getInputStream();
            file2Download.createNewFile();
            fos = new FileOutputStream(file2Download);
            Log.i("wzq", "download create fileoutputstream");
            byte[] buf = new byte[1024];
            int count = 0;
            long total = 0;
            while((count = is.read(buf)) != -1) {
                total += count;
                if(getFileLength() > 0) {
                    handler.setSize(total * 100 / getFileLength());
                }
                fos.write(buf, 0, count);
            }
            fos.flush();
            is.close();

        } catch (IOException e) {
            e.printStackTrace();
            Log.i("wzq", "download " + e.toString());
        } finally {
            try {
                if(fos != null) {
                    fos.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            handler.downloadComplete();

        }

    }

    public int getFileLength() {
        if(urlConnection != null) {
            return urlConnection.getContentLength();
        } else {
            return 0;
        }
    }

    public interface DownloadHandler {
        void setSize(long size);
        void downloadComplete();
        void getFileLength(int length);
    }


}

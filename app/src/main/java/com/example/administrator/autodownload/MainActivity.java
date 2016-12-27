package com.example.administrator.autodownload;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private static final String url = "http://gdown.baidu.com/data/wisegame/30d88b11f5745157/baidushoujizhushou_16792523.apk";
//    private static final String url = "http://apk.miguvideo.com/apk/MiguVideo-ZYZ-Liebao-09.apk";
    private Button download;
    private Button allDownload;
    private TextView fileSize;

    private long result = 0;
    private int fileLength = 0;

    private boolean downloadComplete = false;

    private ProgressBar progressBar1;
    private ProgressBar progressBar2;
    private ProgressBar progressBar3;
    private ProgressBar progressBar4;
    private ProgressBar progressBar5;

    private TextView tv_process1;
    private TextView tv_process2;
    private TextView tv_process3;
    private TextView tv_process4;
    private TextView tv_process5;

    DecimalFormat df = new DecimalFormat("#.##");

    final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if(fileLength == 0) {
                        tv_process1.setText("0");
                        progressBar1.setProgress(0);
                    } else {
                        if(msg.obj.toString().equals("done")) {
                            tv_process1.setText("Done");
                        } else {
                            tv_process1.setText(msg.obj + " %");
                        }
                        progressBar1.setProgress(Integer.valueOf(msg.obj.toString()));
                    }
                    break;
                case 2:
                    if(fileLength == 0) {
                        tv_process2.setText("0");
                        progressBar1.setProgress(0);
                    } else {
                        tv_process2.setText(msg.obj + " %");
                        progressBar2.setProgress(Integer.valueOf(msg.obj.toString()));
                    }
                    break;
                case 3:
                    if(fileLength == 0) {
                        tv_process3.setText("0");
                        progressBar3.setProgress(0);
                    } else {
                        tv_process3.setText(msg.obj + " %");
                        progressBar3.setProgress(Integer.valueOf(msg.obj.toString()));
                    }
                    break;
                case 4:
                    if(fileLength == 0) {
                        tv_process4.setText("0");
                        progressBar4.setProgress(0);
                    } else {
                        tv_process4.setText(msg.obj + " %");
                        progressBar4.setProgress(Integer.valueOf(msg.obj.toString()));
                    }
                    break;
                case 5:
                    if(fileLength == 0) {
                        tv_process5.setText("0");
                        progressBar5.setProgress(0);
                    } else {
                        tv_process5.setText(msg.obj + " %");
                        progressBar5.setProgress(Integer.valueOf(msg.obj.toString()));
                    }
                    break;
                case 0:
                    fileLength = Integer.valueOf(msg.obj.toString());
                    fileSize.setText(String.valueOf(df.format((float) fileLength / 1000.0f / 1000.0f)) + "MB");
//                if(downloadComplete) {
//                    Toast.makeText(MainActivity.this, "下载完成 !", Toast.LENGTH_SHORT).show();
//                }
            }


        }
    };

    private int threadCount = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        download = (Button) findViewById(R.id.btn_download);
        allDownload = (Button) findViewById(R.id.btn_all_download);
        fileSize = (TextView) findViewById(R.id.file_size);

        tv_process1 = (TextView) findViewById(R.id.tv_process1);
        tv_process2 = (TextView) findViewById(R.id.tv_process2);
        tv_process3 = (TextView) findViewById(R.id.tv_process3);
        tv_process4 = (TextView) findViewById(R.id.tv_process4);
        tv_process5 = (TextView) findViewById(R.id.tv_process5);

        progressBar1 = (ProgressBar) findViewById(R.id.progressBar1);
        progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);
        progressBar3 = (ProgressBar) findViewById(R.id.progressBar3);
        progressBar4 = (ProgressBar) findViewById(R.id.progressBar4);
        progressBar5 = (ProgressBar) findViewById(R.id.progressBar5);

        progressBar1.setMax(100);
        progressBar2.setMax(100);
        progressBar3.setMax(100);
        progressBar4.setMax(100);
        progressBar5.setMax(100);

        final ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(threadCount > 0) {
                    String fileName = "/MiguVideo" + threadCount + ".apk";
                    fixedThreadPool.execute(new DownloadThread(fileName, threadCount));
                    threadCount--;
                    download.setText("DOWNLOAD" + "(" +(5 - threadCount) + ")");
                } else {
                    Toast.makeText(MainActivity.this, "线程已达上限，请等待下载完成", Toast.LENGTH_SHORT).show();
                }

            }
        });


        allDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(threadCount == 5) {
                    while (threadCount > 0) {
                        String fileName = "/MiguVideo" + threadCount + ".apk";
                        fixedThreadPool.execute(new DownloadThread(fileName, threadCount));
                        threadCount--;
                    }
                } else {
                    Toast.makeText(MainActivity.this, "仍有线程在下载，请稍后", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    class DownloadThread implements Runnable {
        private String fileName;
        private int threadNum;

        public DownloadThread(String fileName, int threadNum) {
            this.fileName = fileName;
            this.threadNum = threadNum;
        }

        @Override
        public void run() {
            final Downloader downloader = new Downloader("dlDemo", fileName, url);
            downloader.setHandler(new Downloader.DownloadHandler() {
                @Override
                public void setSize(long size) {
                    Message msg = handler.obtainMessage();
                    msg.what = threadNum;
                    msg.obj = size;
                    handler.sendMessage(msg);

                }

                @Override
                public void downloadComplete() {
                    Message msg = handler.obtainMessage();
                    msg.what = threadNum;
                    msg.obj = "done";
                    handler.sendMessage(msg);
                }

                @Override
                public void getFileLength(int length) {
                    Message msg = handler.obtainMessage();
                    msg.what = 0;
                    msg.obj = length;
                    handler.sendMessage(msg);
                }
            });
            downloader.download();
        }
    }


    /**
     * 直接下载
     */
    private void startDownloadThread() {
        result = 0;
        fileLength = 0;
        downloadComplete = false;

        new Thread(new Runnable() {
            @Override
            public void run() {

                final Downloader downloader = new Downloader("dlDemo","/MiguVideo.apk", url);
                fileLength = downloader.getFileLength();
                downloader.setHandler(new Downloader.DownloadHandler() {
                    @Override
                    public void setSize(long size) {
                        Message msg = handler.obtainMessage();
                        msg.what = 1;
                        handler.sendMessage(msg);
                        result = size;

                    }

                    @Override
                    public void downloadComplete() {
                        Message msg = handler.obtainMessage();
                        msg.what = 0;
                        downloadComplete = true;
                        handler.sendMessage(msg);
                    }

                    @Override
                    public void getFileLength(int length) {

                    }
                });
                downloader.download();


            }
        }).start();
    }

}

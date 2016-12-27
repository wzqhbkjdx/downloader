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
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

//    private static final String url = "http://gdown.baidu.com/data/wisegame/30d88b11f5745157/baidushoujizhushou_16792523.apk";
    private static final String url = "http://apk.miguvideo.com/apk/MiguVideo-ZYZ-Liebao-09.apk";
    private Button download;
    private Button allDownload;
    private Button btn_all_shutdown;
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

    private Map<Integer, Integer> threadMap = new HashMap<>();

    final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if(fileLength == 0) {
                        tv_process1.setText("0");
                        progressBar1.setProgress(0);
                        tv_thread1.setTextColor(getResources().getColor(R.color.my_text));
                        tv_thread1.setText("线程1");
                    } else {
                        if(msg.obj.toString().equals("done")) {
                            tv_process1.setText("Done");
                            progressBar1.setProgress(100);
                            threadCount++;
                            threadMap.put(msg.what, 1);
                            download.setText("DOWNLOAD" + "(" +(5 - threadCount) + ")");
                            tv_thread1.setTextColor(getResources().getColor(R.color.my_text));
                            tv_thread1.setText("线程1");
                        } else {
                            tv_process1.setText(msg.obj + " %");
                            progressBar1.setProgress(Integer.valueOf(msg.obj.toString()));
                            tv_thread1.setTextColor(getResources().getColor(R.color.my_blue));
                            tv_thread1.setText("下载ing");
                        }

                    }
                    break;
                case 2:
                    if(fileLength == 0) {
                        tv_process2.setText("0");
                        progressBar2.setProgress(0);
                        tv_thread2.setTextColor(getResources().getColor(R.color.my_text));
                        tv_thread2.setText("线程2");
                    } else {
                        if(msg.obj.toString().equals("done")) {
                            tv_process2.setText("Done");
                            progressBar2.setProgress(100);
                            threadCount++;
                            threadMap.put(msg.what, 1);
                            download.setText("DOWNLOAD" + "(" +(5 - threadCount) + ")");
                            tv_thread2.setTextColor(getResources().getColor(R.color.my_text));
                            tv_thread2.setText("线程2");
                        } else {
                            tv_process2.setText(msg.obj + " %");
                            progressBar2.setProgress(Integer.valueOf(msg.obj.toString()));
                            tv_thread2.setTextColor(getResources().getColor(R.color.my_blue));
                            tv_thread2.setText("下载ing");
                        }

                    }
                    break;
                case 3:
                    if(fileLength == 0) {
                        tv_process3.setText("0");
                        progressBar3.setProgress(0);
                        tv_thread3.setTextColor(getResources().getColor(R.color.my_text));
                        tv_thread3.setText("线程3");
                    } else {
                        if(msg.obj.toString().equals("done")) {
                            tv_process3.setText("Done");
                            progressBar3.setProgress(100);
                            threadCount++;
                            threadMap.put(msg.what, 1);
                            download.setText("DOWNLOAD" + "(" +(5 - threadCount) + ")");
                            tv_thread3.setTextColor(getResources().getColor(R.color.my_text));
                            tv_thread3.setText("线程3");
                        } else {
                            tv_process3.setText(msg.obj + " %");
                            progressBar3.setProgress(Integer.valueOf(msg.obj.toString()));
                            tv_thread3.setTextColor(getResources().getColor(R.color.my_blue));
                            tv_thread3.setText("下载ing");
                        }

                    }
                    break;
                case 4:
                    if(fileLength == 0) {
                        tv_process4.setText("0");
                        progressBar4.setProgress(0);
                        tv_thread4.setTextColor(getResources().getColor(R.color.my_text));
                        tv_thread4.setText("线程4");
                    } else {
                        if(msg.obj.toString().equals("done")) {
                            tv_process4.setText("Done");
                            progressBar4.setProgress(100);
                            threadCount++;
                            threadMap.put(msg.what, 1);
                            download.setText("DOWNLOAD" + "(" +(5 - threadCount) + ")");
                            tv_thread4.setTextColor(getResources().getColor(R.color.my_text));
                            tv_thread4.setText("线程4");
                        } else {
                            tv_process4.setText(msg.obj + " %");
                            progressBar4.setProgress(Integer.valueOf(msg.obj.toString()));
                            tv_thread4.setTextColor(getResources().getColor(R.color.my_blue));
                            tv_thread4.setText("下载ing");
                        }
                    }
                    break;
                case 5:
                    if(fileLength == 0) {
                        tv_process5.setText("0");
                        progressBar5.setProgress(0);
                        tv_thread5.setTextColor(getResources().getColor(R.color.my_text));
                        tv_thread5.setText("线程5");
                    } else {
                        if(msg.obj.toString().equals("done")) {
                            tv_process5.setText("Done");
                            progressBar5.setProgress(100);
                            threadCount++;
                            threadMap.put(msg.what, 1);
                            download.setText("DOWNLOAD" + "(" +(5 - threadCount) + ")");
                            tv_thread5.setTextColor(getResources().getColor(R.color.my_text));
                            tv_thread5.setText("线程5");
                        } else {
                            tv_process5.setText(msg.obj + " %");
                            progressBar5.setProgress(Integer.valueOf(msg.obj.toString()));
                            tv_thread5.setTextColor(getResources().getColor(R.color.my_blue));
                            tv_thread5.setText("下载ing");
                        }
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

    private int threadCount = 5;  //空余线程计数

    private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);

    private TextView tv_thread1;
    private TextView tv_thread2;
    private TextView tv_thread3;
    private TextView tv_thread4;
    private TextView tv_thread5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        download = (Button) findViewById(R.id.btn_download);
        allDownload = (Button) findViewById(R.id.btn_all_download);
        btn_all_shutdown = (Button) findViewById(R.id.btn_all_shutdown);
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

        tv_thread1 = (TextView) findViewById(R.id.tv_thread_1);
        tv_thread2 = (TextView) findViewById(R.id.tv_thread_2);
        tv_thread3 = (TextView) findViewById(R.id.tv_thread_3);
        tv_thread4 = (TextView) findViewById(R.id.tv_thread_4);
        tv_thread5 = (TextView) findViewById(R.id.tv_thread_5);

        for(int i = 1; i <= 5; i++) {
            threadMap.put(i, 1);
        }

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (fixedThreadPool.isShutdown()) {
                    fixedThreadPool = Executors.newFixedThreadPool(5);
                }

                if(threadCount == 5) {
                    String fileName = "/MiguVideo" + threadCount + ".apk";
                    fixedThreadPool.execute(new DownloadThread(fileName, threadCount));
                    threadMap.put(threadCount, 0);
                    threadCount--;
                    download.setText("DOWNLOAD" + "(" +(5 - threadCount) + ")");

                } else if(threadCount > 0 && threadCount < 5) {

                    for(Integer integer : threadMap.keySet()) {
                        if(threadMap.get(integer) == 1) {
                            String fileName = "/MiguVideo" + integer + ".apk";
                            fixedThreadPool.execute(new DownloadThread(fileName, integer));
                            threadMap.put(integer,0);
                            threadCount--;
                            download.setText("DOWNLOAD" + "(" +(5 - threadCount) + ")");
                            break;
                        }
                    }

                } else {
                    Toast.makeText(MainActivity.this, "线程已达上限，请等待下载完成", Toast.LENGTH_SHORT).show();
                }

            }
        });


        allDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (fixedThreadPool.isShutdown()) {
                    fixedThreadPool = Executors.newFixedThreadPool(5);
                }

                if(threadCount == 5) {
                    while (threadCount > 0) {
                        String fileName = "/MiguVideo" + threadCount + ".apk";
                        fixedThreadPool.execute(new DownloadThread(fileName, threadCount));
                        threadMap.put(threadCount, 0);
                        threadCount--;
                        download.setText("DOWNLOAD" + "(" +(5 - threadCount) + ")");
                    }
                } else {
                    Toast.makeText(MainActivity.this, "仍有线程在下载，请稍后", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btn_all_shutdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fixedThreadPool.shutdownNow();

                Toast.makeText(MainActivity.this, "正在停止所有线程，请稍后...", Toast.LENGTH_SHORT).show();

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(fixedThreadPool.isShutdown()) {
                            tv_process1.setText("0 %");
                            tv_process2.setText("0 %");
                            tv_process3.setText("0 %");
                            tv_process4.setText("0 %");
                            tv_process5.setText("0 %");

                            progressBar1.setProgress(0);
                            progressBar2.setProgress(0);
                            progressBar3.setProgress(0);
                            progressBar4.setProgress(0);
                            progressBar5.setProgress(0);

                            threadCount = 5;

                            for(int i = 1; i <= 5; i++) {
                                threadMap.put(i, 1);
                            }

                            download.setText("DOWNLOAD" + "(" +(5 - threadCount) + ")");

                            tv_thread1.setTextColor(getResources().getColor(R.color.my_text));
                            tv_thread1.setText("线程1");
                            tv_thread2.setTextColor(getResources().getColor(R.color.my_text));
                            tv_thread2.setText("线程2");
                            tv_thread3.setTextColor(getResources().getColor(R.color.my_text));
                            tv_thread3.setText("线程3");
                            tv_thread4.setTextColor(getResources().getColor(R.color.my_text));
                            tv_thread4.setText("线程4");
                            tv_thread5.setTextColor(getResources().getColor(R.color.my_text));
                            tv_thread5.setText("线程5");

                        }
                    }
                },2000);


            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        fixedThreadPool.shutdownNow();
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

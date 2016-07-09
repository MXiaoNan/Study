package com.mn.musicplayertest;

import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //播放本地音乐
        //playLocalMusic();

        //播放网络音乐
        //playNetMusic();

        //播放资源音乐
        playResMusic();
    }


    //播放本地音乐
    private void playLocalMusic() {
        String strPath = "/sdcard/Download/xn.mp3";
        //新建一个MediaPlayer类
        mediaPlayer = new MediaPlayer();
        try {
            //设置音乐路径
            mediaPlayer.setDataSource(strPath);
            //加载音乐
            mediaPlayer.prepare();
            //开始播放音乐
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //播放网络音乐
    private void playNetMusic() {
        String strUri = "http://ok.96x.cn/2015/6yue_2651.cn/%E4%B8%80%E4%B8%87%E4%B8%AA%E8%88%8D%E4%B8%8D%E5%BE%97Dj%E7%89%88%20%E5%BA%84%E5%BF%83%E5%A6%8D&%E7%A5%81%E9%9A%86.mp3";
        mediaPlayer = new MediaPlayer();
        /*try {
            mediaPlayer.setDataSource(strUri);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaPlayer.start();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        try {
            //设置音乐的路径
            mediaPlayer.setDataSource(strUri);
            //加载音乐,这个方法是同步执行的，如果加载速度过慢，
            //可能会造成主线程无响应（ANR），所以可以用异步的方式（线程，异步任务）来执行prepare
            new AsyncTask<Void, Void, Void>() {

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    Log.d("qf", "加载完毕");
                    //开始播放音乐
                    mediaPlayer.start();
                }

                @Override
                protected Void doInBackground(Void... params) {
                    try {
                        //加载音乐
                        mediaPlayer.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            }.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //播放资源文件
    private void playResMusic() {
        //如果使用MediaPlayer.create的方法创建MediaPlayer，就不用再prepare，直接start
        mediaPlayer = MediaPlayer.create(this, R.raw.xn);
        mediaPlayer.start();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            //一定要在程序退出之前，进行资源的释放，否则会造成内存溢出
            mediaPlayer.release();
        }
    }
}

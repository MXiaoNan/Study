package com.mn.videoviewplay;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    //    private VideoView mVv_show;
    private VideoViewTest mVv_show;
    Handler mHandler;
    private int mPosition = 0;//记录屏幕切换的位置
    private int misScrennWidth;//屏幕的宽
    private int misScrennHeight;//屏幕的高

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        misScrennWidth = getResources().getDisplayMetrics().widthPixels;
        misScrennHeight = getResources().getDisplayMetrics().heightPixels;

//        mVv_show = (VideoView) findViewById(R.id.vv_show);

        mVv_show = (VideoViewTest) findViewById(R.id.vv_show);

        mHandler = new Handler();

        //本地视频
        showLocalVideo();

        //资源视频
        //showRawVideo();

        //网络视频
        //showNetVideo();

        //暂停、继续、停止
        //OtherControl();

        //得到当前播放时长
        //getCurDuration();

        //对视频播放事件进行监听，监听播放完毕以及播放出错的事件
        initEvent();

        //获取视频总时长
        //在视频准备好，快要播放的时候回调OnPreparedListener里面的onPrepared方法
        setMyDuration();

        //设置全屏播放方法二，用代码控制
        //setFullScrenn();
    }

    //播放本地视频，即手机内存卡的视频
    protected void showLocalVideo() {
        //先设置播放文件的路径
        String strPath = "/sdcard/Download/splash.mp4";
        //然后设置要播放的路径
        mVv_show.setVideoPath(strPath);
        //开始播放
        mVv_show.start();
    }

    //播放资源视频
    private void showRawVideo() {
        //使用一个uri路径表示资源里的视频文件
        String strPath = "android.resource://" + getPackageName() + "/" + R.raw.splash;
        //进行uri转换
        Uri uri = Uri.parse(strPath);
        //给VideoView设置播放路径
        mVv_show.setVideoURI(uri);
        //开始播放
        mVv_show.start();
    }

    //播放网络视频
    private void showNetVideo() {
        //设置网络视频的地址
        String strUrl = "http://10.2.157.48/video/splash.mp4";
        mVv_show.setVideoPath(strUrl);
        //让视频从指定的时间开始播放，这个方法是异步的，在设置路径setVideoPath方法之后的任意地方调用都可以
        mVv_show.seekTo(4000);
        mVv_show.start();
    }

    //暂停、继续、停止
    protected void OtherControl() {
        //暂停
        mVv_show.postDelayed(new Runnable() {
            @Override
            public void run() {
                //如果正在播放，则暂停播放
                if (mVv_show.isPlaying()) {
                    mVv_show.pause();
                }
            }
        }, 4000);

        //继续
        mVv_show.postDelayed(new Runnable() {
            @Override
            public void run() {
                //如果不在播放，则开始播放
                if (!mVv_show.isPlaying()) {
                    mVv_show.start();
                }
            }
        }, 7000);

        //停止
        mVv_show.postDelayed(new Runnable() {
            @Override
            public void run() {
                //停止播放
                mVv_show.stopPlayback();
            }
        }, 9000);
    }

    //获取视频当前的播放时长
    protected void getCurDuration() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int position = mVv_show.getCurrentPosition();
                Log.d("print", "position==" + position);
            }
        }, 3000);
    }

    //对视频播放事件进行监听，监听播放完毕以及播放出错的事件
    protected void initEvent() {
        //播放完毕的事件监听，只有正常播放完毕才会回调，如果手动stop，不会调用这个方法
        mVv_show.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.d("print", "播放完毕");
                finish();
            }
        });

        mVv_show.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Log.d("print", "错误码：" + what);
                return false;
            }
        });
    }

    //横竖屏切换按钮监听事件
    public void onChange(View view) {
        //得到当前屏幕的方向
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    //在清单中配置三个属性后，会调用此方法，不会再走全部的生命周期
    //在这个方法里，对VideoView的宽高进行重新设置
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mVv_show.setSize(misScrennHeight, misScrennWidth);
            mVv_show.getHolder().setFixedSize(misScrennHeight, misScrennWidth);
        } else {
            mVv_show.setSize(misScrennWidth, misScrennHeight);
            //让我们设定的宽高起作用
            mVv_show.getHolder().setFixedSize(misScrennWidth, misScrennHeight);
        }
    }

    //切换后台后继续播放的方法
    @Override
    protected void onPause() {
        super.onPause();
        if (mVv_show.isPlaying()) {
            mPosition = mVv_show.getCurrentPosition();
            mVv_show.pause();
        } else {
            mPosition = 0;
        }
    }

    //切换后台后继续播放的方法
    @Override
    protected void onResume() {
        super.onResume();
        mVv_show.seekTo(mPosition);
        mVv_show.start();
    }

    //获取视频总时长
    private void setMyDuration() {
        mVv_show.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                //获取视频总时长，要在视频真正准备好了才能调用
                int duration = mVv_show.getDuration();
                Log.d("print", "duration==" + duration);
            }
        });
    }

    //设置全屏播放方法二，用代码控制
    protected void setFullScrenn() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        mVv_show.setLayoutParams(params);
    }

}

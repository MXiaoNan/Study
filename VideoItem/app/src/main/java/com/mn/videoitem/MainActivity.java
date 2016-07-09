package com.mn.videoitem;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.AbsListView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView mLv_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLv_show = (ListView) findViewById(R.id.lv_show);
        //播放资源中的视频文件
        String strURL = "android.resource://" + getPackageName() + "/" + R.raw.splash;
        List<String> iList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
//            iList.add("/sdcard/Download/splash.mp4");
            iList.add(strURL);
        }


        final MyAdapter myAdapter = new MyAdapter(iList, this);
        mLv_show.setAdapter(myAdapter);

        mLv_show.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            //firstVisibleItem第一个可见的item
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                //如果当前播放位置小于第一个可见的item
                //或者大于最后一个可见的item
                //而且这个item不等于空并且正在播放的话
                //直接暂停播放
                if (myAdapter.mPlayPosition < firstVisibleItem
                        || myAdapter.mPlayPosition > mLv_show.getLastVisiblePosition()) {
                    if (myAdapter.mPlayHolder != null
                            && myAdapter.mPlayHolder.mvv_Item.isPlaying()) {
                        myAdapter.mPlayHolder.mvv_Item.pause();
                    }
                }
            }
        });
    }
}

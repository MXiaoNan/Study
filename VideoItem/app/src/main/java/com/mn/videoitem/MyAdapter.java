package com.mn.videoitem;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.VideoView;

import java.util.List;

/**
 * Created by XiaoNan on 2016/7/4.
 */
public class MyAdapter extends BaseAdapter {

    //上下文
    Context mContext;
    //视频地址集合
    List<String> mList;

    public int mPlayPosition = -1;
    public MyHolder mPlayHolder = null;

    public MyAdapter(List<String> iList, Context iContext) {
        mList = iList;
        mContext = iContext;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final MyHolder myHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_videoitem,
                    parent,
                    false);
            myHolder = new MyHolder();
            myHolder.mvv_Item = (VideoView) convertView.findViewById(R.id.vv_show);
            myHolder.miv_imageView = (ImageView) convertView.findViewById(R.id.iv_video);
            myHolder.mib_imageButton = (ImageButton) convertView.findViewById(R.id.ib_btn);
            convertView.setTag(myHolder);
        } else {
            myHolder = (MyHolder) convertView.getTag();
        }

        //当播放结束后，重置所有控件属性
        myHolder.mvv_Item.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                myHolder.miv_imageView.setVisibility(View.VISIBLE);
                myHolder.mib_imageButton.setVisibility(View.VISIBLE);

                mPlayHolder = null;
                mPlayPosition = -1;
            }
        });

        //判断当前的position是不是正在播放的position，如果不是则显示按钮和图片，如果是则隐藏按钮和图片
        if (position != mPlayPosition) {
            myHolder.miv_imageView.setVisibility(View.VISIBLE);
            myHolder.mib_imageButton.setVisibility(View.VISIBLE);
        } else {
            myHolder.miv_imageView.setVisibility(View.GONE);
            myHolder.mib_imageButton.setVisibility(View.GONE);

            if (!myHolder.mvv_Item.isPlaying()) {
                myHolder.mvv_Item.start();
            }
        }

        myHolder.mib_imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果前面有播放的，则直接停止
                if (mPlayHolder != null) {
                    mPlayHolder.miv_imageView.setVisibility(View.VISIBLE);
                    mPlayHolder.mib_imageButton.setVisibility(View.VISIBLE);

                    mPlayHolder.mvv_Item.stopPlayback();
                }

                //点击按钮之后，要隐藏图片和按钮
                Log.e("onClick", "position=" + position);
                myHolder.miv_imageView.setVisibility(View.GONE);
                myHolder.mib_imageButton.setVisibility(View.GONE);
                //隐藏之后开始播放
                myHolder.mvv_Item.setVideoPath(mList.get(position));
                myHolder.mvv_Item.start();

                mPlayPosition = position;
                mPlayHolder = myHolder;

            }
        });
        return convertView;
    }

    class MyHolder {
        VideoView mvv_Item;
        ImageView miv_imageView;
        ImageButton mib_imageButton;
    }
}

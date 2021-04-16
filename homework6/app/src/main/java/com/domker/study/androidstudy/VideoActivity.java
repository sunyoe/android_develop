package com.domker.study.androidstudy;

import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.VideoView;

import static java.lang.Thread.sleep;

public class VideoActivity extends AppCompatActivity {
    private Button buttonPlay;
    private Button buttonPause;
    private Button buttonResume;
    private VideoView videoView;
    private SeekBar seekBar;
    private int videoLength;
    private float perLength;

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (videoView.isPlaying()) {
                int current = videoView.getCurrentPosition();
                Log.d("current", String.format("current: %f", 100*current/(videoLength*1f)));
                seekBar.setProgress((int)(100*current/(videoLength*1f)));
            }
            handler.postDelayed(runnable, 500);
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_video);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);

        buttonPause = findViewById(R.id.buttonPause);
        buttonPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.pause();
            }
        });

        buttonPlay = findViewById(R.id.buttonPlay);
        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.postDelayed(runnable, 0);
                videoView.start();
            }
        });

        buttonResume = findViewById(R.id.buttonResume);
        buttonResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.resume();
                videoView.start();
            }
        });

        videoView = findViewById(R.id.videoView);
        videoView.getHolder().setFormat(PixelFormat.TRANSPARENT);
        videoView.setZOrderOnTop(true);
        videoView.setVideoPath(getVideoPath(R.raw.big_buck_bunny));
        videoView.setOnPreparedListener(new MyOnPreparedListener());

    }


    private String getVideoPath(int resId) {
        return "android.resource://" + this.getPackageName() + "/" + resId;
    }

    private SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        // 当进度条停止修改时触发
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // 取得当前进度条的进度
            int progress = seekBar.getProgress();
            if(videoView.isPlaying()){
                // 设置当前播放的位置
                Log.d("progress", String.format("progress: %d", progress));
                Log.d("progress-cal", String.format("progress: %d", (int)(progress*(videoLength*1f)/100)));
                videoView.seekTo((int)(progress*(videoLength*1f)/100));
            }
        }
    };

    private class MyOnPreparedListener implements MediaPlayer.OnPreparedListener{
        @Override
        public void onPrepared(MediaPlayer mp){
            //TODO Auto-generated method stub

            videoLength = videoView.getDuration();
            Log.d("length", String.format("length: %d", videoLength));
            perLength = videoLength / 100;
            Log.d("perLength", String.format("perLength: %f", perLength));
        }
    }
}

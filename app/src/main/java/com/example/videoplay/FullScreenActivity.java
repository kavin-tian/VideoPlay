package com.example.videoplay;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.VideoView;

import java.util.Timer;
import java.util.TimerTask;

public class FullScreenActivity extends AppCompatActivity implements View.OnClickListener {

    private Button playPauseButton, replayButton, stopButton;
    private SeekBar mySeekBar;
    private VideoView myVideoView;
    private String path = "";
    //下面我们定义一个任务，用于处理进度条
    private Timer timer;//定时器
    private TimerTask timerTask;//任务
    private ImageButton playPause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);

        playPauseButton = (Button) this.findViewById(R.id.playPauseButton);
        replayButton = (Button) this.findViewById(R.id.replayButton);
        stopButton = (Button) this.findViewById(R.id.stopButton);
        mySeekBar = (SeekBar) this.findViewById(R.id.mySeekBar);
        myVideoView = (VideoView) this.findViewById(R.id.myVideoView);
        playPause = findViewById(R.id.play_pause_toggle);

        playPauseButton.setOnClickListener(this);
        replayButton.setOnClickListener(this);
        stopButton.setOnClickListener(this);
        playPause.setOnClickListener(this);

        //获取需要播放视频的路径
        path = MainActivity.url;
        //设置视频的路径
        myVideoView.setVideoPath(path);

        timer = new Timer();//定时器
        timerTask = new TimerTask() {//任务
            @Override
            public void run() {//每0.5秒执行一次
                //如果视频存在，且正在播放
                if (myVideoView != null && myVideoView.isPlaying()) {
                    //获取整个视频的总长度
                    int max = myVideoView.getDuration();
                    //设置为进度条的总长度
                    mySeekBar.setMax(max);
                    //获取此时视频播放的位置
                    int site = myVideoView.getCurrentPosition();
                    //设置进度条的位置
                    mySeekBar.setProgress(site);
                }
                //如果视频存在，但没在播放，进度条就不动
            }
        };
        /*
         * 参数一：需要执行的任务
         * 参数二：从定时器开始到第一次执行任务的隔离时间，单位毫秒
         * 参数三：每次执行任务的间隔时间，单位毫秒
         */
        timer.schedule(timerTask, 0, 500);//每秒钟进度条更新一次

        //为SeekBar设置事件监听
        mySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            //放掉进度条触发
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //获取当前进度条你发掉时的进度
                int site = seekBar.getProgress();
                //设置视频的位置
                myVideoView.seekTo(site);
            }
        });
    }

    //从什么位置开始播放
    private int postion = 0;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.playPauseButton:
                playPause();

                break;
            case R.id.replayButton:
                replay();

                break;
            case R.id.stopButton:
                stop();

                break;

            case R.id.play_pause_toggle:
                playPause();
                if (myVideoView.isPlaying()) {
                    playPause.setBackgroundResource(R.mipmap.btn_audio_pause_normal);
                } else {
                    playPause.setBackgroundResource(R.mipmap.btn_audio_play_normal);
                }
                break;
        }
    }

    private void stop() {
        if (myVideoView != null && myVideoView.isPlaying()) {
            postion = 0;
            myVideoView.stopPlayback();
            myVideoView = null;
            playPauseButton.setText("播放");
            mySeekBar.setProgress(0);
        }
    }

    private void replay() {
        if (myVideoView != null && myVideoView.isPlaying()) {
            postion = 0;
            myVideoView.seekTo(postion);
        }
    }

    private void playPause() {
        if (myVideoView != null && "播放".equals(playPauseButton.getText().toString().trim())) {
            //开始播放
            myVideoView.start();
            //设置到播放的位置
            myVideoView.seekTo(postion);
            //设置按钮的标题
            playPauseButton.setText("暂停");
        } else if (myVideoView != null && "暂停".equals(playPauseButton.getText().toString().trim())) {
            //记录正在播放的位置
            postion = myVideoView.getCurrentPosition();
            //暂停视频
            myVideoView.pause();
            //设置按钮的标题
            playPauseButton.setText("播放");
        } else {
            myVideoView = (VideoView) this.findViewById(R.id.myVideoView);
            myVideoView.setVideoPath(path);
            myVideoView.start();
            myVideoView.seekTo(postion);
        }
    }

}

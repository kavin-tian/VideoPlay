package com.example.videoplay;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import java.util.Timer;
import java.util.TimerTask;

public class SurfaceViewActivity extends Activity implements View.OnClickListener {
    private Button playPauseButton, replayButton, stopButton;
    private SurfaceView mySurfaceView;
    private MediaPlayer mediaPlayer;
    private String path = "";
    private SurfaceHolder surfaceHolder;
    private SeekBar mySeekBar;
    private Timer timer;
    private TimerTask timerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surface_view);

        playPauseButton = (Button) this.findViewById(R.id.playPauseButton);
        replayButton = (Button) this.findViewById(R.id.replayButton);
        stopButton = (Button) this.findViewById(R.id.stopButton);
        mySeekBar = (SeekBar) this.findViewById(R.id.seekBar);
        mySurfaceView = (SurfaceView) this.findViewById(R.id.mySurfaceView);
        surfaceHolder = mySurfaceView.getHolder();

        playPauseButton.setOnClickListener(this);
        replayButton.setOnClickListener(this);
        stopButton.setOnClickListener(this);

        path = MainActivity.url;

        updateSeekBar();
        setOnSeekBarChangeListener();
    }

    private int position = 0;

    @Override
    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.playPauseButton:
                    if (mediaPlayer == null && "播放".equals(playPauseButton.getText().toString().trim())) {
                        mediaPlayer = new MediaPlayer();
                        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        mediaPlayer.setDataSource(path);
                        //将视频内容设置到SurfaceView中显示
                        mediaPlayer.setDisplay(surfaceHolder);
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                        mediaPlayer.seekTo(position);
                        playPauseButton.setText("暂停");
                    } else if (mediaPlayer != null && "暂停".equals(playPauseButton.getText().toString().trim())) {
                        position = mediaPlayer.getCurrentPosition();
                        mediaPlayer.pause();
                        playPauseButton.setText("播放");
                    } else {
                        mediaPlayer.start();
                        mediaPlayer.seekTo(position);
                        playPauseButton.setText("暂停");
                    }
                    break;
                case R.id.replayButton:
                    break;
                case R.id.stopButton:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateSeekBar() {
        //定时器
        timer = new Timer();
        timerTask = new TimerTask() {//任务
            @Override
            public void run() {//每0.5秒执行一次
                //如果视频存在，且正在播放
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    //获取整个视频的总长度
                    int max = mediaPlayer.getDuration();
                    //设置为进度条的总长度
                    mySeekBar.setMax(max);
                    //获取此时视频播放的位置
                    int site = mediaPlayer.getCurrentPosition();
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

    }

    private void setOnSeekBarChangeListener() {
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
                mediaPlayer.seekTo(site);
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null ) {
            mediaPlayer.release();
            timerTask.cancel();
            timer.cancel();
        }
    }
}



package com.example.videoplay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

//    public static final String url = "http://192.168.1.5:8080/88.mp4";
//    public static final String url = "http://192.168.1.5:8080/880.3gp";
    public static final String url = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void click1(View view) {
        Uri uri= Uri.parse(url);
        Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri,"video/*");
        startActivity(intent);
    }

    public void click2(View view) {
        Intent intent = new Intent(this, VideoViewActivity.class);
        startActivity(intent);
    }
    public void click3(View view) {
        Intent intent = new Intent(this, SurfaceViewActivity.class);
        startActivity(intent);
    }

    public void click4(View view) {
        Intent intent = new Intent(this, FullScreenActivity.class);
        startActivity(intent);
    }
    public void click5(View view) {
        Intent intent = new Intent(this, GSYVideoPlayerActivity.class);
        startActivity(intent);
    }
}

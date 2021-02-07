package com.example.pilivideo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.dancechar.pilivideo.RVideoView;

public class MainActivity extends AppCompatActivity {
    private RVideoView rVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rVideoView = findViewById(R.id.videoView);
//        rVideoView.setRatio(720,1280);
        rVideoView.setPlayUrl("http://vin-video-upload.oss-cn-hangzhou.aliyuncs.com/dy-video-upload/f0c5be07c3ff61e30c0f37b033d07d91.mp4");
        rVideoView.setThumbs("https://vin-video-upload.oss-cn-hangzhou.aliyuncs.com/dy-video-upload/f0c5be07c3ff61e30c0f37b033d07d91.mp4?x-oss-process=video/snapshot,t_1000,f_png,w_500,m_fast");
        rVideoView.setCompleteController(new Test(this));
//        rVideoView.start();
    }
    @Override
    protected void onPause() {
        super.onPause();
        rVideoView.pause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        rVideoView.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        rVideoView.release();
    }
}
package com.example.pilivideo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dancechar.pilivideo.RVideoView;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        RecyclerView ryList = findViewById(R.id.list);
        ryList.setLayoutManager(new LinearLayoutManager(this));
        ryList.setAdapter(new Adapter(this));
    }


    private static class Adapter extends RecyclerView.Adapter<Adapter.Holder> {

        private Context context;

        public Adapter(Context context) {
            this.context = context;
        }

        @NonNull
        @Override
        public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View inflate = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
            return new Holder(inflate);
        }

        @Override
        public void onBindViewHolder(@NonNull Holder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 2;
        }

        class Holder extends RecyclerView.ViewHolder {


            public Holder(@NonNull View itemView) {
                super(itemView);
                RVideoView rVideoView = itemView.findViewById(R.id.videoView);
                rVideoView.setRatio(720,1280);
                rVideoView.setPlayUrl("http://vin-video-upload.oss-cn-hangzhou.aliyuncs.com/dy-video-upload/f0c5be07c3ff61e30c0f37b033d07d91.mp4");
                rVideoView.setThumbs("https://vin-video-upload.oss-cn-hangzhou.aliyuncs.com/dy-video-upload/f0c5be07c3ff61e30c0f37b033d07d91.mp4?x-oss-process=video/snapshot,t_1000,f_png,w_500,m_fast");
//                rVideoView.start();
            }
        }
    }
}
package com.example.spomoo;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class VideoList extends AppCompatActivity {

    private VideoView videoView;
    private String[] videoNames = {"video1", "video2"};
//Button mit durchsichtig und ausgefülltem like symbol
    /*
    private Button loffButton = (Button) findViewById(R.id.likeoffbutton);
    private Button lonButton = (Button) findViewById(R.id.likeonbutton);
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videolist);

        videoView = findViewById(R.id.videoView);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
      //  lonButton.setVisibility(View.VISIBLE); zeile crasth app iwie
        VideoAdapter adapter = new VideoAdapter(videoNames, this::playVideo);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void playVideo(String videoName) {
        int videoResId = getResources().getIdentifier(videoName, "raw", getPackageName());
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + videoResId);
        videoView.setVideoURI(videoUri);
        videoView.start();
    }
/*
    public void buttonCLicked(View v){

    }*/
}
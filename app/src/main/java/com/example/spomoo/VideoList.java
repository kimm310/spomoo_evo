package com.example.spomoo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class VideoList extends AppCompatActivity {

    private VideoView videoView;

    // an dieser stelle videos einfügen
    public String[] videoNames = new String[]{"video1", "video2"};
    //Array used to set videos as liked or not liked
    public boolean[] isFav = {false, true};

    boolean isLiked = false;
    AlertDialog.Builder favList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videolist);

        //Like button nicht geliket
        Button likeoff = (Button) findViewById(R.id.likeoffbutton);

        //LIke button geliket
        Button likeon = (Button) findViewById(R.id.likeonbutton);
        likeon.setVisibility(View.INVISIBLE);

        videoView = findViewById(R.id.videoView);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        VideoAdapter adapter = new VideoAdapter(videoNames, this::playVideo);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);

        //Builder in favList gespeichert
        favList  = new AlertDialog.Builder(VideoList.this);

        //Nachricht und Button gesetzt
        favList.setMessage("Choose your favourite Videos!");
        favList.setTitle("Choose favourites");
        favList.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast message = Toast.makeText(VideoList.this, "Saved" , Toast.LENGTH_SHORT);
                message.show();
            }
        });

        favList.setMultiChoiceItems(videoNames, isFav, new DialogInterface.OnMultiChoiceClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                favList.create();
                favList.show();
            }
        });

    }

    private void playVideo(String videoName) {
        int videoResId = getResources().getIdentifier(videoName, "raw", getPackageName());
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + videoResId);
        videoView.setVideoURI(videoUri);
        videoView.start();
    }

    public void buttonClick(View v){
        if (!isLiked) {
            findViewById(R.id.likeoffbutton).setVisibility(View.INVISIBLE);
            findViewById(R.id.likeonbutton).setVisibility(View.VISIBLE);

            favList.show();
            isLiked = true;
        }
        else if (isLiked) {
            findViewById(R.id.likeonbutton).setVisibility(View.INVISIBLE);
            findViewById(R.id.likeoffbutton).setVisibility(View.VISIBLE);

            favList.show();
            isLiked = false;
        }
    }


}
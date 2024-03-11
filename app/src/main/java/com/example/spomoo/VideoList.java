package com.example.spomoo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
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

    //Keys um boolean state der gecheckten auswahlmöglichkeiten beim favorisieren der videos zu speichern und später wieder aufzurufen
    //Mehr videos = keys etc. müssen hardcoded werden
    public static final String KEY_BOOL1 = "bool1";
    public static final String KEY_BOOL2 = "bool2";
    private VideoView videoView;

    // an dieser stelle videos einfügen
    public String[] videoNames = new String[]{"video1", "video2"};

    private SharedPreferences preferences;
    //Array used to set videos as liked or not liked
    public boolean[] isFav = {false, false};

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
        favList.setTitle("Choose favourites");

        favList.setMultiChoiceItems(videoNames, isFav, new DialogInterface.OnMultiChoiceClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean b) {

            }
        });
        favList.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast message = Toast.makeText(VideoList.this, "Saved" , Toast.LENGTH_SHORT);
                message.show();
            }
        });
    }

/*
    @Override
    protected void onStart() {
        super.onStart();
        loadState();
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveState();
    }


    // Lade den gespeicherten Zustand der Activity
    private void loadState() {
        int bo1 = preferences.getInt(KEY_BOOL1, 0);
        int bo2 = preferences.getInt(KEY_BOOL2, 0);

        dropCounter = count;
        upperText.setText(textMes);
    }

    private void saveState() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(KEY_VISIB1, blue1.getVisibility());
        editor.putInt(KEY_VISIB2, blue2.getVisibility());
        editor.putInt(KEY_VISIB3, blue3.getVisibility());
        editor.putInt(KEY_VISIB4, blue4.getVisibility());
        editor.putInt(KEY_VISIB5, blue5.getVisibility());
        editor.putInt(KEY_VISIB6, blue6.getVisibility());
        editor.putInt(KEY_VISIB7, blue7.getVisibility());
        editor.putInt(KEY_VISIB8, blue8.getVisibility());
        editor.putInt(KEY_VISIB9, blue9.getVisibility());
        editor.putInt(KEY_VISIB10, blue10.getVisibility());
        editor.putInt(KEY_COUNT, dropCounter);
        editor.putString(KEY_TEXT_MES, upperText.getText().toString());
        editor.apply();
    }


 */

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
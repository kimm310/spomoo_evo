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
    //Array used to set videos as liked or not liked
    private SharedPreferences preferences;
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
        int visib1 = preferences.getInt(KEY_VISIB1, View.INVISIBLE);
        int visib2 = preferences.getInt(KEY_VISIB2, View.INVISIBLE);
        int visib3 = preferences.getInt(KEY_VISIB3, View.INVISIBLE);
        int visib4 = preferences.getInt(KEY_VISIB4, View.INVISIBLE);
        int visib5 = preferences.getInt(KEY_VISIB5, View.INVISIBLE);
        int visib6 = preferences.getInt(KEY_VISIB6, View.INVISIBLE);
        int visib7 = preferences.getInt(KEY_VISIB7, View.INVISIBLE);
        int visib8 = preferences.getInt(KEY_VISIB8, View.INVISIBLE);
        int visib9 = preferences.getInt(KEY_VISIB9, View.INVISIBLE);
        int visib10 = preferences.getInt(KEY_VISIB10, View.INVISIBLE);
        int count = preferences.getInt(KEY_COUNT, 0);
        String textMes = preferences.getString(KEY_TEXT_MES, "");

        blue1.setVisibility(visib1);
        blue2.setVisibility(visib2);
        blue3.setVisibility(visib3);
        blue4.setVisibility(visib4);
        blue5.setVisibility(visib5);
        blue6.setVisibility(visib6);
        blue7.setVisibility(visib7);
        blue8.setVisibility(visib8);
        blue9.setVisibility(visib9);
        blue10.setVisibility(visib10);

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
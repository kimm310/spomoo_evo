package com.example.spomoo.mainscreen;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spomoo.R;

import java.util.Calendar;

public class waterTrack extends AppCompatActivity {
    ImageView grey1;
    ImageView grey2;
    ImageView grey3;
    ImageView grey4;
    ImageView grey5;
    ImageView grey6;
    ImageView grey7;
    ImageView grey8;
    ImageView grey9;
    ImageView grey10;
    ImageView blue1;
    ImageView blue2;
    ImageView blue3;
    ImageView blue4;
    ImageView blue5;
    ImageView blue6;
    ImageView blue7;
    ImageView blue8;
    ImageView blue9;
    ImageView blue10;
    TextView upperText;
    int dropCounter;
    Button drinkButton;
    Button backButton;

    private static final String PREF_NAME = "MyPrefs";
    private static final String KEY_VISIB1 = "visib1";
    private static final String KEY_VISIB2 = "visib2";
    private static final String KEY_VISIB3 = "visib3";
    private static final String KEY_VISIB4 = "visib4";
    private static final String KEY_VISIB5 = "visib5";
    private static final String KEY_VISIB6 = "visib6";
    private static final String KEY_VISIB7 = "visib7";
    private static final String KEY_VISIB8 = "visib8";
    private static final String KEY_VISIB9 = "visib9";
    private static final String KEY_VISIB10 = "visib10";
    private static final String KEY_COUNT = "count";
    private static final String KEY_TEXT_MES = "textMes";
    private SharedPreferences preferences;
    AlertDialog.Builder finishNoti;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_track);
        //Vorbereiten
        preferences = getSharedPreferences("PREF", MODE_PRIVATE);
        grey1 = findViewById(R.id.imageView1);
        grey2 = findViewById(R.id.imageView2);
        grey3 = findViewById(R.id.imageView3);
        grey4 = findViewById(R.id.imageView4);
        grey5 = findViewById(R.id.imageView5);
        grey6 = findViewById(R.id.imageView6);
        grey7 = findViewById(R.id.imageView7);
        grey8 = findViewById(R.id.imageView8);
        grey9 = findViewById(R.id.imageView9);
        grey10 = findViewById(R.id.imageView10);
        blue1 = findViewById(R.id.imageView11);
        blue2 = findViewById(R.id.imageView12);
        blue3 = findViewById(R.id.imageView13);
        blue4 = findViewById(R.id.imageView14);
        blue5 = findViewById(R.id.imageView15);
        blue6 = findViewById(R.id.imageView16);
        blue7 = findViewById(R.id.imageView17);
        blue8 = findViewById(R.id.imageView18);
        blue9 = findViewById(R.id.imageView19);
        blue10 = findViewById(R.id.imageView20);
        drinkButton = findViewById(R.id.drinkButton);
        upperText = findViewById(R.id.uppertext);
        backButton = findViewById(R.id.backButton);
        dropCounter = 0;
        upperText.setVisibility(View.VISIBLE);

        finishNoti = new AlertDialog.Builder(this);
        finishNoti.setMessage(R.string.waterEpicDesc);
        finishNoti.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(waterTrack.this, MainActivity.class));
            }
        });

        //Hole alle blauen drops
        ImageView[] blueImageViews = new ImageView[]{blue1, blue2, blue3, blue4,
                blue5, blue6, blue7, blue8, blue9, blue10};
        //Setze sie unsichtbar
        for (int i = 0; i <= 9; i++) {
            blueImageViews[i].setVisibility(View.INVISIBLE);
        }

        //Back Button

        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(waterTrack.this, MainActivity.class));
            }
        });

    }

    //Logik zum färben der Drops
    public void addDrink(View v) {
        dropCounter++;
        //Hole alle blauen drops
        ImageView[] blueImageViews = new ImageView[]{blue1, blue2, blue3, blue4,
                blue5, blue6, blue7, blue8, blue9, blue10};

        if (dropCounter == 1) {
            blueImageViews[0].setVisibility(View.VISIBLE);
        } else if (dropCounter > 0 && dropCounter <= blueImageViews.length - 1) {
            blueImageViews[dropCounter].setVisibility(View.VISIBLE);
        }
        //Reihenfolge völlig durcheinander deswegen ist blue2 warum auch immer
        //das letzte Element
        if (dropCounter == 10) {
            blue2.setVisibility(View.VISIBLE);
            finishNoti.show();
        }
    }

    public void reset(View v){
        dropCounter = 0;
        ImageView[] blueImageViews = new ImageView[]{blue1, blue2, blue3, blue4,
                blue5, blue6, blue7, blue8, blue9, blue10};
        //Setze sie unsichtbar
        for (int i = 0; i <= 9; i++) {
            blueImageViews[i].setVisibility(View.INVISIBLE);
        }
    }

    // Speichere den aktuellen Status der Activity ab
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

    public void checkNewDay(){
        Calendar savedDate = Calendar.getInstance();
        int today = savedDate.get(Calendar.DAY_OF_YEAR);

    }


}
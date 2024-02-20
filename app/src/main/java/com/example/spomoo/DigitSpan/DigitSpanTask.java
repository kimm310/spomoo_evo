package com.example.spomoo.DigitSpan;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.spomoo.R;
import com.example.spomoo.databinding.ActivityDigitSpanTaskBinding;
import com.example.spomoo.mainscreen.MainActivity;
import com.example.spomoo.utility.LocalDatabaseManager;
import com.example.spomoo.utility.SharedPrefManager;
import com.example.spomoo.utility.TimeDateFormatter;
import com.google.android.material.color.DynamicColors;

import java.util.ArrayList;
import java.util.Random;

public class DigitSpanTask extends AppCompatActivity {
    private ActivityDigitSpanTaskBinding binding;
    private SharedPrefManager sharedPrefManager;
    private LocalDatabaseManager db;
    private TextView txtDigitSequence, txtEnter;
    private Button button1, button2, button3, button4, button5, button6, button7, button8, button9, button0;
    private final ArrayList<Integer> sequence = new ArrayList<>();
    private final ArrayList<Integer> userSequence = new ArrayList<>();
    private int sequenceLength = 2;
    final private int maxSequenceLength = 12;
    private int currentDigit = 0;
    private int timesFailed = 0;
    private int timesSucceeded = 0;
    private final Handler handler = new Handler();
    private final Random random = new Random();
    private int totalTasks = 0;
    private int correctTasks = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new LocalDatabaseManager(this);
        setContentView(R.layout.activity_digit_span_task);

        //cache sharedPrefManager
        sharedPrefManager = SharedPrefManager.getInstance(getApplicationContext());

        //apply dynamic color for Android 12+
        if(sharedPrefManager.getBool(SharedPrefManager.KEY_DYNAMIC_COLOR_ENABLED))
            DynamicColors.applyIfAvailable(this);

        //setup layout
        binding = ActivityDigitSpanTaskBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //set top action bar
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //add arrow for navigating back to Main Activity

        //get colors for dynamic coloring
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(androidx.appcompat.R.attr.colorPrimary, typedValue, true);
        int colorPrimary = typedValue.data;
        getTheme().resolveAttribute(com.google.android.material.R.attr.colorOnPrimary, typedValue, true);
        int colorTextPrimary = typedValue.data;
        getTheme().resolveAttribute(com.google.android.material.R.attr.colorError, typedValue, true);
        int colorClicked = typedValue.data;
        getTheme().resolveAttribute(com.google.android.material.R.attr.colorOnError, typedValue, true);
        int colorTextClicked = typedValue.data;
        //set colors
        getWindow().setStatusBarColor(colorPrimary);
        binding.toolbar.setBackgroundColor(colorPrimary);
        binding.toolbar.setTitleTextColor(colorTextPrimary);

        txtDigitSequence = findViewById(R.id.txtDigitView);
        txtEnter = findViewById(R.id.txtEnter);
        txtEnter.setHint("wait");

        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        button6 = findViewById(R.id.button6);
        button7 = findViewById(R.id.button7);
        button8 = findViewById(R.id.button8);
        button9 = findViewById(R.id.button9);
        button0 = findViewById(R.id.button0);

        //set alert with task description for the Digit Span Task
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(DigitSpanTask.this);
        builder.setTitle(R.string.digit_span_title);
        builder.setMessage(getResources().getString(R.string.digit_span_explanation));

        builder.setPositiveButton(R.string.digit_span_got_it, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                handler.postDelayed(showNextDigit, 1500);
            }
        });

        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                handler.postDelayed(showNextDigit, 1500);
            }
        });
        builder.setCancelable(false);

        //Create and show the explanation
        androidx.appcompat.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();
        disableButtons();

        //Set up OnClickListeners for the Buttons
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtEnter.setText("1");
                userSequence.add(1);
                if (checkSequence()) {rightInput();}
                else {falseInput();}
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtEnter.setText("2");
                userSequence.add(2);
                if (checkSequence()) {rightInput();}
                else {falseInput();}
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtEnter.setText("3");
                userSequence.add(3);
                if (checkSequence()) {rightInput();}
                else {falseInput();}
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtEnter.setText("4");
                userSequence.add(4);
                if (checkSequence()) {rightInput();}
                else {falseInput();}
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtEnter.setText("5");
                userSequence.add(5);
                if (checkSequence()) {rightInput();}
                else {falseInput();}
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtEnter.setText("6");
                userSequence.add(6);
                if (checkSequence()) {rightInput();}
                else {falseInput();}
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtEnter.setText("7");
                userSequence.add(7);
                if (checkSequence()) {rightInput();}
                else {falseInput();}
            }
        });

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtEnter.setText("8");
                userSequence.add(8);
                if (checkSequence()) {rightInput();}
                else {falseInput();}
            }
        });

        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtEnter.setText("9");
                userSequence.add(9);
                if (checkSequence()) {rightInput();}
                else {falseInput();}
            }
        });

        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtEnter.setText("0");
                userSequence.add(0);
                if (checkSequence()) {rightInput();}
                else {falseInput();}
            }
        });
    }

    //Runs code for showing the next digit of the sequence
    private final Runnable showNextDigit = new Runnable() {
        @Override
        public void run() {
            txtEnter.setHint(R.string.digit_span_wait);
            txtDigitSequence.setVisibility(View.VISIBLE);
            int digit = random.nextInt(10);
            txtDigitSequence.setText(String.format(Integer.toString(digit)));
            sequence.add(digit);
            handler.postDelayed(delay, 1500);
        }
    };

    //Runs code to disable the visibility of TextView for Sequence
    private final Runnable delay = new Runnable() {
        @Override
        public void run() {
            txtDigitSequence.setVisibility(View.INVISIBLE);
            currentDigit++;
            if (currentDigit < sequenceLength) {
                handler.postDelayed(showNextDigit, 500);
            }
            else {
                enableButtons();
                currentDigit = 0;
                txtEnter.setHint(R.string.digit_span_enter_digits);
            }
        }
    };

    //Enable all Buttons
    private void enableButtons() {
        button1.setEnabled(true);
        button2.setEnabled(true);
        button3.setEnabled(true);
        button4.setEnabled(true);
        button5.setEnabled(true);
        button6.setEnabled(true);
        button7.setEnabled(true);
        button8.setEnabled(true);
        button9.setEnabled(true);
        button0.setEnabled(true);
    }

    //Disable all Buttons
    private void disableButtons() {
        button1.setEnabled(false);
        button2.setEnabled(false);
        button3.setEnabled(false);
        button4.setEnabled(false);
        button5.setEnabled(false);
        button6.setEnabled(false);
        button7.setEnabled(false);
        button8.setEnabled(false);
        button9.setEnabled(false);
        button0.setEnabled(false);
    }

    //Returns true if given sequence equal to users input, false otherwise
    private boolean checkSequence() {
        for (int i = 0; i < userSequence.size(); i++) {
            if (!(sequence.get(i).equals(userSequence.get(i)))) {
                return false;
            }
        }
        return true;
    }

    //Logic for when a Input was correct
    private void rightInput() {
        if (userSequence.size() == sequenceLength) {
            timesSucceeded++;
            correctTasks++;
            totalTasks++;
            txtDigitSequence.setVisibility(View.VISIBLE);
            txtDigitSequence.setText(R.string.digit_span_correct_input);
            txtEnter.setText("");
            disableButtons();
            userSequence.clear();
            sequence.clear();
            //increment the sequence length, if user did two of one sequence length right and resets times failed and succeeded
            if (timesSucceeded >= 2) {
                //Show a popup if max SequenceLength is reached
                if (sequenceLength >= maxSequenceLength) {
                    androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(DigitSpanTask.this);
                    builder.setTitle(R.string.digit_span_max);
                    builder.setMessage(getResources().getString(R.string.digit_span_max_text) + maxSequenceLength + " ");

                    builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //saveTest();
                            endTest();
                            onBackPressed();
                        }
                    });
                    builder.setCancelable(false);

                    androidx.appcompat.app.AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                    txtEnter.setText(R.string.digit_span_done);
                }
                else {
                    sequenceLength++;
                    timesSucceeded = 0;
                    timesFailed = 0;
                    txtEnter.setHint(R.string.digit_span_wait);
                    handler.postDelayed(showNextDigit, 1500);
                }
            }
            else {
                txtEnter.setHint(R.string.digit_span_wait);
                handler.postDelayed(showNextDigit, 1500);
            }
        }
    }

    //Logic for when a Input was false
    public void falseInput() {
        timesFailed++;
        totalTasks++;
        txtDigitSequence.setVisibility(View.VISIBLE);
        txtDigitSequence.setText(R.string.digit_span_false_input);
        txtEnter.setText("");
        disableButtons();
        userSequence.clear();
        sequence.clear();
        //End test if two sequences of the same sequence length were failed two times
        if (timesFailed >= 2) {
            showScore();
            txtEnter.setText(R.string.digit_span_done);
        }
        if (timesFailed < 2) {
            txtEnter.setHint(R.string.digit_span_wait);
            handler.postDelayed(showNextDigit, 1500);
        }
    }

    private void showScore() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(DigitSpanTask.this);
        builder.setTitle(R.string.digit_span_end);
        builder.setMessage(getResources().getString(R.string.digit_span_end_text) + sequenceLength + " ");

        builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveTest();
                endTest();
            }
        });
        builder.setCancelable(false);

        //Create and show the description
        androidx.appcompat.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    public void saveTest() {
        DigitSpanData digitSpanData = new DigitSpanData(sequenceLength, totalTasks, correctTasks, TimeDateFormatter.getDateString(), TimeDateFormatter.getTimeString(), 1, 0);
        LocalDatabaseManager db = new LocalDatabaseManager(this);
        db.addDigitSpanData(digitSpanData, 0);
        Toast.makeText(this, R.string.digit_span_saved, Toast.LENGTH_SHORT).show();
    }

    private void endTest() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}

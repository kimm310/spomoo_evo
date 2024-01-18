package com.example.spomoo.DigitSpan;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.spomoo.R;
import com.example.spomoo.mainscreen.MainActivity;
import com.example.spomoo.utility.LocalDatabaseManager;

import java.util.ArrayList;
import java.util.Random;

public class DigitSpanTask extends AppCompatActivity {
    private Boolean isBadMood= false;
    private TextView txtDigitSequence, txtEnter, test;
    private Button button1, button2, button3, button4, button5, button6, button7, button8, button9, button0, infoButton, exitButton;
    private final ArrayList<Integer> sequence = new ArrayList<>();
    private final ArrayList<Integer> userSequence = new ArrayList<>();
    private int sequenceLength = 2;
    final private int maxSequenceLength = 10;
    private int currentDigit = 0;
    private int timesFailed = 0;
    private int timesSucceeded = 0;
    private final Handler handler = new Handler();
    private final Random random = new Random();
    private int totalTasks = 0;
    private int correctTasks = 0;
    private double correctRatio = 0.0;
    String completionType;
    private String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_digit_span_task);

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

        test = findViewById(R.id.dbTest);
        LocalDatabaseManager db = new LocalDatabaseManager(this);
        ArrayList array = db.readDigitSpanData();
        for (Object i : array) {
            test.setText(i.toString());
        }

        // Set up the Info Button in the Action Bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        /*
        //set alert with task description for the Digit Span Task
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(DigitSpanTask.this);
        builder.setTitle("Digit Span Task");
        builder.setMessage(getResources().getString(R.string.digit_span_explanation));

        builder.setPositiveButton("Got it!", (dialog, which) -> dialog.dismiss());

        builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                handler.postDelayed(showNextDigit, 2000);
            }
        });

        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                handler.postDelayed(showNextDigit, 2000);
            }
        });
        builder.setCancelable(false);

        //Create and show the explanation
        androidx.appcompat.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();
        disableButtons();
         */

        handler.postDelayed(showNextDigit, 2000);


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
            txtEnter.setHint("wait");
            txtDigitSequence.setVisibility(View.VISIBLE);
            int digit = random.nextInt(10);
            txtDigitSequence.setText(String.format(Integer.toString(digit)));
            sequence.add(digit);
            handler.postDelayed(delay, 2000);
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
                txtEnter.setHint("enter digits");
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
            txtDigitSequence.setText("correct input");
            txtEnter.setText("");
            disableButtons();
            userSequence.clear();
            sequence.clear();
            //increment the sequence length, if user did two of one sequence length right and resets times failed and succeeded
            if (timesSucceeded >= 2) {
                //Show a popup if max SequenceLength is reached
                if (sequenceLength >= maxSequenceLength) {
                    androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(DigitSpanTask.this);
                    builder.setTitle("Max Sequence length");
                    builder.setMessage("Reached the max sequence length of: " + maxSequenceLength + " ");

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
                    txtEnter.setText("done");
                }
                else {
                    sequenceLength++;
                    timesSucceeded = 0;
                    timesFailed = 0;
                    txtEnter.setHint("wait");
                    handler.postDelayed(showNextDigit, 2000);
                }
            }
            else {
                txtEnter.setHint("wait");
                handler.postDelayed(showNextDigit, 2000);
            }
        }
    }

    //Logic for when a Input was false
    public void falseInput() {
        timesFailed++;
        totalTasks++;
        txtDigitSequence.setVisibility(View.VISIBLE);
        txtDigitSequence.setText("false input");
        txtEnter.setText("");
        disableButtons();
        userSequence.clear();
        sequence.clear();
        //End test if two sequences of the same sequence length were failed two times
        if (timesFailed >= 2) {
            completionType = "Too many mistakes.";
            showScore();
            txtEnter.setText("done");
        }
        if (timesFailed < 2) {
            txtEnter.setHint("wait");
            handler.postDelayed(showNextDigit, 2000);
        }
    }

    private void showScore() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(DigitSpanTask.this);
        builder.setTitle("Score");
        builder.setMessage("Reached sequence length of: " + sequenceLength + " ");

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

    private void calculateCorrectRatio() {
        if (totalTasks > 0) {
            correctRatio = ((double) correctTasks / totalTasks) * 100;
        } else {
            correctRatio = 0.0;
        }
    }

    public void saveTest() {
        LocalDatabaseManager db = new LocalDatabaseManager(this);
        db.addDigitSpanData(sequenceLength);
    }

    /*
    //Save the DigitSpanTest result
    @SuppressLint("DefaultLocale")
    private void saveTest() {
        LocalDatabaseManager databaseHelper = new LocalDatabaseManager(this);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        Context context = getApplicationContext();
        String userId = UserManager.getUserId(context);
        if(userId == null){
            userId = UserManager.generateUserID(context);
        }


        ContentValues values = new ContentValues();
        values.put("user_id", userId);
        values.put("max_sequence_length", sequenceLength);
        values.put("total_tasks", totalTasks);
        values.put("correct_tasks", correctTasks);

        calculateCorrectRatio();
        values.put("correct_ratio", String.format("%.2f%%", correctRatio));
        values.put("completion_type", completionType);
        values.put("started", "after Questionnaire");

        //add 5 Ep after completing digital span task
        LevelSystem levelSystem = LevelSystem.getInstance(getApplicationContext(), 0.35, 2);
        levelSystem.addEP(5);
        Log.d("Level System", "Ep: " + LevelSystem.getCurrentEP());


        db.insert("DigitalSpanTask_table", null, values);
        Toast.makeText(DigitSpanTask.this, "saved", Toast.LENGTH_SHORT).show();
    }
     */


    /*
    //Handling the actionbar up top
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.info_button_on_actionbar, menu);
        return true;
    }

    //If the back-arrow is pressed call the onBackPressed()-Method
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_info) {
            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(DigitSpanTask.this);
            builder.setTitle("Digit Span Task");
            builder.setMessage(getResources().getString(R.string.digit_span_explanation));

            builder.setPositiveButton("Got it!", (dialog, which) -> dialog.dismiss());

            //Create and show the explanation
            androidx.appcompat.app.AlertDialog alertDialog = builder.create();
            alertDialog.show();
            return true;
        } else if (item.getItemId() == android.R.id.home) {
            // Handle the "up" button in the Action Bar (if needed)
            completionType = "Pressed Exit button.";
            saveTest();
            endTest();
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
     */

    //End Test and save result
    private void endTest() {
        Intent earlyIntent = getIntent();

        /*
        if(earlyIntent!=null) {

            isBadMood = earlyIntent.getBooleanExtra("isBadMood", false);
        }
        if (isBadMood) {
            Random random = new SecureRandom();
            if (random.nextFloat() < 0.5) {
                Intent intent = new Intent(this, InterventionActivity.class);
                startActivity(intent);
            }
        }
         */
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}

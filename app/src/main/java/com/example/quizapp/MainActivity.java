package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import android.app.Activity;

import static java.util.Arrays.asList;

public class MainActivity extends AppCompatActivity {

    public int counter;
    Button btn;
    TextView time;
    ArrayList<String> questArrayList;
    ArrayList<String> optArrayList;
    ArrayList<String> selectedAns;
    ArrayList<String> ansArrayList;
    TextView question;
    RadioGroup radioGroup;
    RadioButton option;
    RadioButton opt1;
    RadioButton opt2;
    RadioButton opt3;
    Timer timer;
    TimerTask timerTask;
    int cursor = 0;
    int quizTime = 60;

    void setValues()
    {
        question.setText("Q No " +Integer.toString(cursor + 1)+ ": "+ questArrayList.get(cursor));
        radioGroup.clearCheck();
        ArrayList<String> tempList = new ArrayList<String>(3);
        tempList.add(optArrayList.get(cursor * 3));
        tempList.add(optArrayList.get((cursor * 3) + 1));
        tempList.add(optArrayList.get((cursor * 3) + 2));
        Collections.shuffle(tempList);
        opt1.setText(tempList.get(0));
        opt2.setText(tempList.get(1));
        opt3.setText(tempList.get(2));
    }

    void saveAnswer()
    {
        if(radioGroup.getCheckedRadioButtonId()==-1)
            selectedAns.set(cursor, "Not Selected");
        else {
            int selectedId = radioGroup.getCheckedRadioButtonId();
            option = (RadioButton) findViewById(selectedId);

            selectedAns.set(cursor, option.getText().toString());
        }
    }
    void showToast(String msg)
    {
        Toast toast = Toast.makeText(getApplicationContext(), msg,
                Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }

    public void previousCall(View view)
    {
        if(cursor == 0)
        {
            showToast("You are already on First Question:");
        }
        else {

            saveAnswer();
            cursor--;
            setValues();
        }
    }

    public void nextCall(View view)
    {
        if(cursor == 9)
        {
            showToast("This is the Last Question:");
        }
        else {
            saveAnswer();
            cursor++;
            setValues();
        }
    }

    public int result()
    {
        int count = 0;
        for (int i = 0; i < 10; i ++)
        {
            if(ansArrayList.get(i).equals(selectedAns.get(i)))
                count++;
        }

        return count;
    }

    public void submission(View view)
    {
        saveAnswer();
        int count = result();
        Intent intent = new Intent(this,MainActivity2.class);
        intent.putExtra("Count", count);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent2 = new Intent(this, MainActivity2.class);
        radioGroup = findViewById(R.id.radioGroup);
        question = findViewById(R.id.question);
        opt1 = findViewById(R.id.opt1);
        opt2 = findViewById(R.id.opt2);
        opt3 = findViewById(R.id.opt3);
        selectedAns = new ArrayList<String>(10);


        String[] questions = getResources().getStringArray(R.array.questions);
        questArrayList = new ArrayList<String>(asList(questions));
        Collections.shuffle(questArrayList);
        String[] options = getResources().getStringArray(R.array.options);
        optArrayList = new ArrayList<String>(asList(options));
        String[] answers = getResources().getStringArray(R.array.answers);
        ansArrayList = new ArrayList<String>(asList(answers));

        for (int i = 0; i < 10; i++)
        {
            selectedAns.add("");
        }
        setValues();


	//Add Time constraint
        time= (TextView) findViewById(R.id.time);
        new CountDownTimer(quizTime * 1000, 1000){
            public void onTick(long millisUntilFinished){
                time.setText(String.valueOf(quizTime - counter) + "s Left");
                counter++;
            }
            public  void onFinish(){
                saveAnswer();
                int count = result();
                intent2.putExtra("Count", count);
                startActivity(intent2);
            }
        }.start();
    }
}







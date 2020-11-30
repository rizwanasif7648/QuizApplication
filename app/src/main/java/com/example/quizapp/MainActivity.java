package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import static java.util.Arrays.asList;

public class MainActivity extends AppCompatActivity {

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
    int cursor = 0;

    void setValues()
    {
        question.setText(questArrayList.get(cursor));
        opt1.setText(optArrayList.get(cursor * 3));
        opt2.setText(optArrayList.get((cursor * 3) + 1));
        opt3.setText(optArrayList.get((cursor * 3) + 2));
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

        radioGroup = findViewById(R.id.radioGroup);
        question = findViewById(R.id.question);
        opt1 = findViewById(R.id.opt1);
        opt2 = findViewById(R.id.opt2);
        opt3 = findViewById(R.id.opt3);
        selectedAns = new ArrayList<String>(10);

        String[] questions = getResources().getStringArray(R.array.questions);
        questArrayList = new ArrayList<String>(asList(questions));
        String[] options = getResources().getStringArray(R.array.options);
        optArrayList = new ArrayList<String>(asList(options));
        String[] answers = getResources().getStringArray(R.array.answers);
        ansArrayList = new ArrayList<String>(asList(answers));

        for (int i = 0; i < 10; i++)
        {
            selectedAns.add("");
        }
        setValues();
    }
}







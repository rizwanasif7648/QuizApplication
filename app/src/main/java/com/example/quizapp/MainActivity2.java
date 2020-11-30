package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

import static java.util.Arrays.asList;

public class MainActivity2 extends AppCompatActivity {

    TextView textView;
    ArrayList<String> ansArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        textView = findViewById(R.id.textView);
        Intent intent = getIntent();
        int count = intent.getIntExtra("Count", -1);
        textView.setText("Your result is " + count +"/10");
    }
}
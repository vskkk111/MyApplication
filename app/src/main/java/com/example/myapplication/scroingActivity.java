package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class scroingActivity extends AppCompatActivity {
    TextView scoreA;
    TextView scoreB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_scroing);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        scoreA = findViewById(R.id.team_a_score);
        scoreB = findViewById(R.id.team_b_score);

    }
    public void click1(View v)
    {
        if(v.getId()==R.id.team_a_free_throw) {
            String s1 = scoreA.getText().toString();
            s1 = String.valueOf(Integer.parseInt(s1) + 1);
            scoreA.setText(s1);
        }
        if(v.getId()==R.id.team_a_2points) {
            String s1 = scoreA.getText().toString();
            s1 = String.valueOf(Integer.parseInt(s1) + 2);
            scoreA.setText(s1);
        }
        if(v.getId()==R.id.team_a_3points) {
            String s1 = scoreA.getText().toString();
            s1 = String.valueOf(Integer.parseInt(s1) + 3);
            scoreA.setText(s1);
        }

    }
    public void click2(View v)
    {
        if(v.getId()==R.id.team_b_free_throw) {
            String s1 = scoreB.getText().toString();
            s1 = String.valueOf(Integer.parseInt(s1) + 1);
            scoreB.setText(s1);
        }
        if(v.getId()==R.id.team_a_2points) {
            String s1 = scoreA.getText().toString();
            s1 = String.valueOf(Integer.parseInt(s1) + 2);
            scoreA.setText(s1);
        }
        if(v.getId()==R.id.team_b_2points) {
            String s1 = scoreB.getText().toString();
            s1 = String.valueOf(Integer.parseInt(s1) + 3);
            scoreB.setText(s1);
        }
    }
    public void click(View v)
    {
        if(v.getId()==R.id.reset_button) {
            int s=0;
            scoreB.setText(String.valueOf(s));
        }
    }
}
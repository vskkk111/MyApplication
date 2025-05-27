package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class NetActivity extends AppCompatActivity implements  Runnable{
    private  static final String TAG = "Net";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_net);

        Log.i(TAG,"onCreate: start Thread");
        Thread t = new Thread(this);
        t.start();;
    }

    @Override
    public void run() {
        Log.i(TAG,"run: 子线程run()......");

    }
}
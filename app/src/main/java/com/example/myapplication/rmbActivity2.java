package com.example.myapplication;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class rmbActivity2 extends AppCompatActivity {

    private EditText rmbToDollarEdit;
    private EditText rmbToEuroEdit;
    private EditText rmbToWonEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rmb2);

        rmbToDollarEdit = findViewById(R.id.rmbToDollar);
        rmbToEuroEdit = findViewById(R.id.rmbToEuro);
        rmbToWonEdit = findViewById(R.id.rmbToWon);
        Button saveButton = findViewById(R.id.saveButton);


        // 获取当前汇率
        double dollarRate = getIntent().getDoubleExtra("RMB_TO_DOLLAR", 0.138);
        double euroRate = getIntent().getDoubleExtra("RMB_TO_EURO", 0.128);
        double wonRate = getIntent().getDoubleExtra("RMB_TO_WON", 184.5);

        // 设置当前汇率
        rmbToDollarEdit.setText(String.valueOf(dollarRate));
        rmbToEuroEdit.setText(String.valueOf(euroRate));
        rmbToWonEdit.setText(String.valueOf(wonRate));

        // 保存按钮
        saveButton.setOnClickListener(v -> {
            try {
                double newDollarRate = Double.parseDouble(rmbToDollarEdit.getText().toString());
                double newEuroRate = Double.parseDouble(rmbToEuroEdit.getText().toString());
                double newWonRate = Double.parseDouble(rmbToWonEdit.getText().toString());

                Intent resultIntent = new Intent();
                resultIntent.putExtra("RMB_TO_DOLLAR", newDollarRate);
                resultIntent.putExtra("RMB_TO_EURO", newEuroRate);
                resultIntent.putExtra("RMB_TO_WON", newWonRate);
                setResult(RESULT_OK, resultIntent);
                finish();
            } catch (NumberFormatException e) {
                // 处理无效输入
            }
        });
    }
}
package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class rmbActivity extends AppCompatActivity {

    private View rmbInput;
    private TextView resultText;

    // 默认汇率11
    private double rmbToDollarRate = 0.138;
    private double rmbToEuroRate = 0.128;
    private double rmbToWonRate = 184.5;
    private com.example.myapplication.EditText editText;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rmb);


        rmbInput = findViewById(R.id.rmbInput);
        resultText = findViewById(R.id.resultText);

        Button convertToDollar = findViewById(R.id.convertToDollar);
        Button convertToEuro = findViewById(R.id.convertToEuro);
        Button convertToWon = findViewById(R.id.convertToWon);
        Button configButton = findViewById(R.id.configButton);

        // 美元换算
        convertToDollar.setOnClickListener(v -> {
            convertCurrency("DOLLAR", rmbToDollarRate);
        });

        // 欧元换算
        convertToEuro.setOnClickListener(v -> {
            convertCurrency("EURO", rmbToEuroRate);
        });

        // 韩元换算
        convertToWon.setOnClickListener(v -> {
            convertCurrency("WON", rmbToWonRate);
        });

        // 配置按钮
        configButton.setOnClickListener(v -> {
            Intent intent = new Intent(rmbActivity.this, rmbActivity.class);
            intent.putExtra("RMB_TO_DOLLAR", rmbToDollarRate);
            intent.putExtra("RMB_TO_EURO", rmbToEuroRate);
            intent.putExtra("RMB_TO_WON", rmbToWonRate);
            startActivityForResult(intent, 1);
        });
    }
    @SuppressLint("DefaultLocale")
    private void convertCurrency(String currency, double rate) {
        EditText rmbInput = findViewById(R.id.rmbInput);
        String input = editText.getText().toString();
        if (input.isEmpty()) {
            resultText.setText("请输入人民币金额");
            resultText.setVisibility(View.VISIBLE);
            return;
        }

        try {
            double rmb = Double.parseDouble(input);
            double result = rmb * rate;

            String formattedResult;
            if (currency.equals("WON")) {
                formattedResult = String.format("%,.0f WON", result);
            } else {
                formattedResult = String.format("%,.2f %s", result, currency);
            }

            resultText.setText(formattedResult);
            resultText.setVisibility(View.VISIBLE);
        } catch (NumberFormatException e) {
            resultText.setText("请输入有效数字");
            resultText.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            rmbToDollarRate = data.getDoubleExtra("RMB_TO_DOLLAR", 0.138);
            rmbToEuroRate = data.getDoubleExtra("RMB_TO_EURO", 0.128);
            rmbToWonRate = data.getDoubleExtra("RMB_TO_WON", 184.5);

        }
    }
}
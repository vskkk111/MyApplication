package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity5 extends AppCompatActivity {
    private TextView tvCurrencyName;
    private TextView tvExchangeRate;
    private EditText etAmount;
    private Button btnCalculate;
    private TextView tvResult;
    private double exchangeRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main5);
        tvCurrencyName = findViewById(R.id.tvCurrencyName);
        tvExchangeRate = findViewById(R.id.tvExchangeRate);
        etAmount = findViewById(R.id.etAmount);
        btnCalculate = findViewById(R.id.btnCalculate);
        tvResult = findViewById(R.id.tvResult);

        // 获取传递的数据
        Intent intent = getIntent();
        String currencyName = intent.getStringExtra("CURRENCY_NAME");
        String rateStr = intent.getStringExtra("EXCHANGE_RATE");

        // 处理汇率字符串（去除逗号）
        rateStr = rateStr.replace(",", "");
        exchangeRate = Double.parseDouble(rateStr);

        // 显示数据
        tvCurrencyName.setText(currencyName);
        tvExchangeRate.setText(String.format("%.4f", exchangeRate));

        // 设置计算按钮点击事件
        btnCalculate.setOnClickListener(v -> calculateConversion());
    }

    private void calculateConversion() {
        String amountStr = etAmount.getText().toString();
        if (amountStr.isEmpty()) {
            Toast.makeText(this, "请输入金额", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double amount = Double.parseDouble(amountStr);
            double result = amount * exchangeRate;

            tvResult.setText(String.format("兑换结果: %.2f", result));
            tvResult.setVisibility(View.VISIBLE);

        } catch (NumberFormatException e) {
            Toast.makeText(this, "请输入有效的数字", Toast.LENGTH_SHORT).show();
        }
    }
}
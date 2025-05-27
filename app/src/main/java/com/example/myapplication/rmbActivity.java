package com.example.myapplication;

import static java.lang.System.out;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.os.Handler;
import android.os.Message;


import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.appcompat.app.AppCompatActivity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class rmbActivity extends AppCompatActivity implements  Runnable {
    TextView show;
    private float dollarRate = 0.1f;
    private float euroRate = 0.05f;
    private float wonRate = 500f;
    String TAG = "Rate";
    Handler handler;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_rmb);


        show = findViewById(R.id.textView5);
        //启用线程
        Thread td = new Thread(this);
        td.start();//this.run();
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 5) {
                    String str = (String) msg.obj;
                    show.setText(str);

                }
                super.handleMessage(msg);

            }
   };

}

public void click(View btn) {
    EditText input = findViewById(R.id.editTextText2);
    String inpstr = input.getText().toString();
    try {
        float rmb = Float.parseFloat(inpstr);
        float result = 0.0f;
        if (btn.getId() == R.id.button7) {
            result = rmb * dollarRate;
        } else if (btn.getId() == R.id.button8) {
            result = rmb * euroRate;
        } else if (btn.getId() == R.id.button9) {
            result = rmb * wonRate;
        }
        show.setText(String.valueOf(result));


    } catch (NumberFormatException ex) {
        show.setText("请输入数字");
    }


}
public void clickOpen(View btn) {
    Intent config = new Intent(this, newrmb.class);
    config.putExtra("dollar_rate_key", dollarRate);
    config.putExtra("euro_rate_key", euroRate);
    config.putExtra("won_rate_key", wonRate);
    startActivityForResult(config, 3);


}

@Override
protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

    if (requestCode == 3 && resultCode == 6) {
        Bundle bdl = data.getExtras();
        dollarRate = bdl.getFloat("key_dollar2");
        euroRate = bdl.getFloat("key_euro2");
        wonRate = bdl.getFloat("key_won2");

    }
    super.onActivityResult(requestCode, resultCode, data);
}
@Override
public void run() {
    Log.i(TAG, "run:run()");
    URL url = null;
        /*try {

            url = new URL("https://chl.cn//huilv/?gonggao-2025-4-17");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            InputStream in = http.getInputStream();
            String html = inputStream25String(in);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    try{
        Thread.sleep(5000);
        Document doc = Jsoup.connect("https://www.boc.cn/sourcedb/whpj/").get();
        Elements tables = doc.getElementsByTag("table");
        Element table2= tables.get(1);
        Elements trs = table2.getElementsByTag("tr");
        trs.remove(0);
        for(Element tr:trs)
        {
            Elements tds = tr.children();
            Element td1 = tds.first();
            Element td2= tds.get(5);
            String str1 = td1.text();
            String str2 = td2.text();
            Log.i(TAG,"run:"+str1+"==>"+str2);
        }




    } catch (InterruptedException e) {
        throw new RuntimeException(e);
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
    Message msg = handler.obtainMessage(0, "swufe.edu.cn");
    handler.sendMessage(msg);

}

private String inputStream25String(InputStream inputStream) throws IOException {
    final int bufferSize = 1024;
    final char[] buffer = new char[bufferSize];
    final StringBuilder out = new StringBuilder();
    Reader in = new InputStreamReader(inputStream, "utf-8");
    while (true) {
        int rsz = in.read(buffer, 0, buffer.length);
        if (rsz < 0) {
            break;


        }
        out.append(buffer, 0, rsz);

    }


    return out.toString();
}
}
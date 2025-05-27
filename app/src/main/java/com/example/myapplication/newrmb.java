package com.example.myapplication;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;

public class newrmb extends AppCompatActivity implements  Runnable{
    private  static final String TAG = "rate";
    Handler handler;
    TextView show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_newrmb);
        show = findViewById(R.id.textView6);

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
    public void onClickbtn(View btn)
    {
        Log.i(TAG,"onCreate: start Thread");
        Thread t = new Thread(this);
        t.start();;
    }

    @Override
    public void run() {
        Log.i(TAG,"run: 子线程run()......");
        URL url = null;
        String html = "";
        try{

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
                html+=(td1.text()+"=>"+td2.text()+"\n");
            }




        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Message msg = handler.obtainMessage(5, html);
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
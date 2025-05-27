package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity3 extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private  static final String TAG = "MainActivity3";
    private ArrayList<HashMap<String,String>> listItems;
    private SimpleAdapter listItemAdapter;
    Handler handler;
    ListView mylist;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main3);

        mylist = findViewById(R.id.mylistview2);

        listItems = new ArrayList<>();
        for(int i=0;i<10;i++)
        {
            HashMap<String,String> map = new HashMap<String,String>();
            map.put("ItemTitle","Rate: "+i);
            map.put("ItemDetail","detail: "+i);
            listItems.add(map);
        }
        listItemAdapter = new SimpleAdapter(this,listItems,R.layout.list_item,new String[] {"ItemTitle","ItemDetail"},new int[]{R.id.itemTitle,R.id.itemDetail});
        mylist.setAdapter(listItemAdapter);
        handler =new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                Log.i(TAG,"handleMessage:");
                if (msg.what == 2) {
                    ArrayList<HashMap<String,String>> retlist2 = (ArrayList<HashMap<String, String>>) msg.obj;
                    MyAdapter myAdapter = new MyAdapter(MainActivity3.this,R.layout.list_item,retlist2);
                    mylist.setAdapter(myAdapter);

                }
                super.handleMessage(msg);
            }
        };
        mylist.setOnItemClickListener(this);

        mylist.setOnItemClickListener(this);

        new Thread(()->{
            ArrayList<HashMap<String,String>> retlist= new ArrayList<>();
            try {



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
                    HashMap<String,String> map = new HashMap<String,String>();
                    map.put("ItemTitle",str1);
                    map.put("ItemDetail",str2);
                    retlist.add(map);
                }










            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Message msg = handler.obtainMessage(2,retlist);
            handler.sendMessage(msg);
            Log.i(TAG,"run:handler.sendMessage(msg);");

        }).start();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Object itemAtPosition = mylist.getItemAtPosition(position);
        HashMap<String,String> map = (HashMap<String, String>) itemAtPosition;
        String titleStr = map.get("ItemTitle");
        String detailStr = map.get("ItemDetail");
        Intent intent = new Intent(this, MainActivity5.class);
        intent.putExtra("CURRENCY_NAME", titleStr);
        intent.putExtra("EXCHANGE_RATE", detailStr);

        // 启动详情页
        startActivity(intent);
        Log.i(TAG,"onItemClick: titleStr"+titleStr);
        Log.i(TAG,"onItemClick: detailStr"+detailStr);



    }
}
package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class listActivity10 extends AppCompatActivity {
    Handler handler;
    private  static final String TAG = "listActibity10";
    ProgressBar progressBar;
    private String logDate = "";
    private final String DATE_SP_KEY = "lastDataStr";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sp = getSharedPreferences("myrate", Context.MODE_PRIVATE);
        logDate = sp.getString(DATE_SP_KEY,"");
        Log.i("List","lastRateDataStr" + logDate);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list10);
        GridView mylist = findViewById(R.id.mylist2);
        progressBar=findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        List<String> list_data = new ArrayList<>(100);
        for(int i=1;i<=100;i++)
        {
            //list_data.add("Item"+i);
        }
        //ListAdapter adapter = new ArrayAdapter<String>(this,R.layout.activity_list10);
        //mylist.setAdapter(adapter);
        //mylist.setEmptyView(findViewById(R.id.nodata));
        handler =new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                Log.i(TAG,"handleMessage:");
                if (msg.what == 2) {
                    ArrayList<HashMap<String,String>> retlist2 = (ArrayList<HashMap<String, String>>) msg.obj;
                    MyAdapter myAdapter = new MyAdapter(listActivity10.this,R.layout.activity_list10,retlist2);
                    mylist.setAdapter(myAdapter);
                    mylist.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);


                }
                super.handleMessage(msg);
            }
        };




        new Thread(()->{
            ArrayList<HashMap<String,String>> retlist= new ArrayList<>();
            String curDateStr = (new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            Log.i("run","curDateStr"+curDateStr+"logDate:"+logDate);
            if(curDateStr.equals(logDate))
            {
                Log.i("run","日期相等，从数据库获取数据");
                RateManager rateManager = new RateManager(listActivity10.this);
                HashMap<String,String> map = new HashMap<String,String>();
                for(RateItem2 rateItem2 : rateManager.listAll())
                {
                    map.put("ItemTitle",rateItem2.getCurName());
                    map.put("ItemDetail",rateItem2.getCurRate());
                    retlist.add(map);

                }
            }
            else {
                try {
                    List<RateItem2> rateItem2s = new ArrayList<RateItem2>();
                    Document doc = Jsoup.connect("https://www.boc.cn/sourcedb/whpj/").get();
                    Elements tables = doc.getElementsByTag("table");
                    Element table2 = tables.get(1);
                    Elements trs = table2.getElementsByTag("tr");
                    trs.remove(0);
                    for (Element tr : trs) {
                        Elements tds = tr.children();
                        Element td1 = tds.first();
                        Element td2 = tds.get(5);
                        String str1 = td1.text();
                        String str2 = td2.text();
                        Log.i(TAG, "run:" + str1 + "==>" + str2);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("ItemTitle", str1);
                        map.put("ItemDetail", str2);
                        retlist.add(map);
                        RateItem2 rateItem2 = new RateItem2(str1,str2);
                        rateItem2s.add(rateItem2);
                    }
                    RateManager rateManager = new RateManager(listActivity10.this);
                    rateManager.deleteAll();
                    Log.i("db","删除所有记录");
                    rateManager.addAll(rateItem2s);
                    Log.i("db","添加所有记录");


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                SharedPreferences sp2 = getSharedPreferences("myrate",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp2.edit();
                editor.putString(DATE_SP_KEY,curDateStr);
                editor.commit();
                Log.i("run","更新日期结束"+curDateStr);
            }
            Message msg = handler.obtainMessage(2,retlist);
            handler.sendMessage(msg);
            Log.i(TAG,"run:handler.sendMessage(msg);");


        }).start();
    }
}

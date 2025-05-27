package com.example.myapplication;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;

public class WebItemTakt implements Runnable{
    private Handler handler;
    private String TAG="";
    String html;
    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
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
}
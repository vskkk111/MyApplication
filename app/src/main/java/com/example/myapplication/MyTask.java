package com.example.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.activity.EdgeToEdge;
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
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MyTask implements Runnable {
    private static final String TAG = "Rate";
    Handler handler;

    public MyTask(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        URL url = null;
        String html = "";
        Bundle retbundle = new Bundle();
        ArrayList<String> list = new ArrayList<String>();
        try {

            Document doc = Jsoup.connect("https://www.huilvbiao.com/").get();
            Element selectElement = doc.selectFirst("select[name=fromcode]");
            Elements optionElements = selectElement.select("option");
            Elements spanElements = doc.select("span:matches(^[0-9.]+$)");
            String exchangeRate;
            String text;
            List<String> results = combineResults(spanElements, optionElements);
            list = new ArrayList<>(results);

            StringBuilder resultStringBuilder = new StringBuilder();
            for (String result : results) {
                resultStringBuilder.append(result).append("\n");
            }
            html+= resultStringBuilder.toString();



        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i(TAG,"run:MyTask bundle="+retbundle);

        retbundle.putStringArrayList("mylist",list);
        Message msg = handler.obtainMessage(5, retbundle);
        handler.sendMessage(msg);


    }



    public  List<String> combineResults(Elements spanElements, Elements optionElements) {
        List<String> combinedResults = new ArrayList<>();
        boolean startRecord = false;
        int spanIndex = 0;

        for (Element option : optionElements) {
            String text = option.text();

            if (text.contains("美元")) {
                startRecord = true;
            }

            if (startRecord) {
                if (spanIndex < spanElements.size()) {
                    Element span = spanElements.get(spanIndex);
                    String exchangeRate = span.text();
                    String combined = text + "==>" + exchangeRate;
                    combinedResults.add(combined);
                    Log.i(TAG, combined);
                    spanIndex++;
                }

                if (text.contains("老挝基普")) {
                    startRecord = false;
                }
            }
        }
        return combinedResults;
    }


}
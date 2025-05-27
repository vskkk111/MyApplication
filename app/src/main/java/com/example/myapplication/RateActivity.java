package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class RateActivity extends ListActivity {
    Handler handler;


    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        String[] list_data = {"hello","android","studio"};
        List<String> list1 = new ArrayList<String>();
        for(int i=1 ;i<100;i++){list1.add("Item"+i);}
        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,list_data);
        setListAdapter(adapter);
        handler = new Handler(){
            public void handleMessage(Message msg) {
                if (msg.what == 5) {
                    /*List<String> getlist = (List<String>) msg.obj;
                    ListAdapter adapter2 = new ArrayAdapter<String>(RateActivity3.this, android.R.layout.simple_list_item_1,getlist);
                    setListAdapter(adapter2);*/
                    Bundle bundle = (Bundle) msg.obj;
                    ArrayList<String> relist = bundle.getStringArrayList("mylist");
                    ListAdapter adapter = new ArrayAdapter<String>(RateActivity.this, android.R.layout.simple_list_item_1,relist);
                    setListAdapter(adapter);



                }
                super.handleMessage(msg);

            }




        };
        //启动线程，获取网络数据
        Thread t= new Thread(()->{
            try{
                Thread.sleep(3000);
            }
            catch (InterruptedException e)
            {
                throw new RuntimeException(e);
            }
            //从网页获取数据项
            List<String> list = new ArrayList<>();
            list.add("美元==>123.45");
            list.add("美元2==>123.45");
            list.add("美元3==>123.45");
            list.add("美元4==>123.45");
            //发消息
            Message msg = handler.obtainMessage(5) ;
            handler.sendMessage(msg);

        });
    }
}
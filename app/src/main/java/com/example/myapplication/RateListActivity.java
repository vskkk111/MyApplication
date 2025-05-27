package com.example.myapplication;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class RateListActivity extends ListActivity {
    private static final String TAG = "Rate";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] list_data = {"hello","android","studio"};
        List<String> list1 = new ArrayList<String>();
        for(int i=10 ;i<100;i++){list1.add("Item"+i);}
        Handler handler = new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                if(msg.what==5)
                {
                    Bundle bundle = (Bundle) msg.obj;
                    ArrayList<String> relist = bundle.getStringArrayList("mylist");
                    ListAdapter adapter = new ArrayAdapter<String>(RateListActivity.this, android.R.layout.simple_list_item_1,relist);
                    setListAdapter(adapter);
                }
                super.handleMessage(msg);
            }
        };
        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,list_data);
        setListAdapter(adapter);
        Thread t = new Thread(new MyTask(handler));
        t.start();
        Log.i(TAG,"onCreate: 启动线程");


    }
}
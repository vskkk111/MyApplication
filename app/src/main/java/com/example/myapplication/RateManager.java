package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class RateManager {
    private DBHelper dbHelper;
    private String TBNAME;

    public RateManager(Context context) {
        dbHelper = new DBHelper(context);
        TBNAME=DBHelper.TB_NAME;
    }
    public void add(RateItem2 item2)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("CURNAME",item2.getCurName());
        values.put("CURRATE",item2.getCurRate());
        db.insert(TBNAME,null,values);
        db.close();

    }
    public void addAll(List<RateItem2> list)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        for(RateItem2 item: list) {
            ContentValues values = new ContentValues();
            values.put("CURNAME", item.getCurName());
            values.put("CURRATE", item.getCurRate());
            db.insert(TBNAME, null, values);

        }
        db.close();

    }
    public void delete(int id)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TBNAME,"ID=?",new String[]{String.valueOf(id)});
        db.close();

    }
    public void deleteAll()
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TBNAME,null,null);
        db.close();

    }
    @SuppressLint("Range")
    public RateItem2 findById(int id)
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME,null,"ID=?",new String[]{String.valueOf(id)},null,null,null);
        RateItem2 rateItem2 = null;
        if(cursor!=null && cursor.moveToFirst())
        {
            rateItem2 = new RateItem2();
            rateItem2.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            rateItem2.setCurName(cursor.getString(cursor.getColumnIndex("CURNAME")));
            rateItem2.setCurRate(cursor.getString(cursor.getColumnIndex("CURRATE")));
            cursor.close();
        }
        db.close();
        return rateItem2;
    }
    @SuppressLint("Range")
    public List<RateItem2> listAll()
    {
        List<RateItem2> ratelist = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME,null,null,null,null,null,null);
        if(cursor!=null)
        {
            ratelist = new ArrayList<RateItem2>();
            while(cursor.moveToNext())
            {
                RateItem2 rateItem2 = new RateItem2();
                rateItem2.setId(cursor.getInt(cursor.getColumnIndex("ID")));
                rateItem2.setCurRate(cursor.getString(cursor.getColumnIndex("CURRATE")));
                rateItem2.setCurName(cursor.getString(cursor.getColumnIndex("CURNAME")));
                ratelist.add(rateItem2);
            }
            cursor.close();

        }
        db.close();
        return ratelist;
    }

}
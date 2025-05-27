package com.example.myapplication;

public class RateItem2 {
    private String curName;
    private String curRate;
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RateItem2()
    {
        super();
        curName="";
        curRate="";
    }
    public RateItem2(String curName,String curRate)
    {
        super();
        this.curName=curName;
        this.curRate=curRate;
    }

    public String getCurName() {
        return curName;
    }

    public void setCurName(String curName) {
        this.curName = curName;
    }

    public String getCurRate() {
        return curRate;
    }

    public void setCurRate(String curRate) {
        this.curRate = curRate;
    }




}
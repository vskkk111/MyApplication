package com.example.myapplication;

public class RateItem {
    private String cname;
    private  float cval;

    public String getCname() {
        return cname;
    }

    public float getCval() {
        return cval;
    }

    public RateItem(String cname, float cval) {
        this.cname = cname;
        this.cval = cval;
    }

    public void setCval(float cval) {
        this.cval = cval;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }
}
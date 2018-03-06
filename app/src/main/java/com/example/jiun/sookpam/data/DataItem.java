package com.example.jiun.sookpam.data;

import java.util.Date;

public class DataItem {
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    private String body;

    private Date date;

    public Date getDate() {return date; }

    public void setDate(Date date) {this.date = date;}

}

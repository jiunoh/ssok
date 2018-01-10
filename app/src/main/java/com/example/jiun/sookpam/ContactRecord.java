package com.example.jiun.sookpam;

import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

@RealmClass
public class ContactRecord implements RealmModel {
    private String class1;
    private String class2;
    private String phone;

    public String getClass1() {
        return class1;
    }

    public String getClass2() {
        return class2;
    }

    public String getPhone() {
        return phone;
    }

    public void setClass1(String class1) {
        this.class1 = class1;
    }

    public void setClass2(String class2) {
        this.class2 = class2;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
}

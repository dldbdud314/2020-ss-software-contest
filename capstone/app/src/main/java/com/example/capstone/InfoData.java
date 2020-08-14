package com.example.capstone;

import java.util.ArrayList;

public class InfoData {
    private String store_name;
    private String store_category;
    private String store_menu;
    private String store_time;
    private String store_instagram;
    private ArrayList<String> hash = new ArrayList<String>();

    public String getStore_name() {
        return store_name;
    }
    public String getStore_category() {
        return store_category;
    }
    public String getStore_menu() {
        return store_menu;
    }
    public String getStore_time() {
        return store_time;
    }
    public String getStore_instagram() {
        return store_instagram;
    }
    public String gethash(int i){
        String str=hash.get(i);
        return str;
    }
    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }
    public void setStore_category(String store_category) {
        this.store_category = store_category;
    }
    public void setStore_menu(String store_menu) {
        this.store_menu = store_menu;
    }
    public void setStore_time(String store_time) {
        this.store_time = store_time;
    }
    public void setStore_instagram(String store_insta) { this.store_instagram = store_insta;}
    public void sethash(String hast){this.hash.add(hast);}

    private String userId;
    public String getUser_id() {
        return userId;
    }
    public void setUser_id(String userId) {
        this.userId = userId;
    }
}


package com.example.capstone;

import java.util.ArrayList;

public class StoreData {
    private String store_name;
    private String store_category;
    private int store_price;
    private String store_insta;
    private ArrayList<String> hash = new ArrayList<String>();

    public String getStore_name() {
        return store_name;
    }
    public String getStore_category() {
        return store_category;
    }
    public String getStore_price() {
        if(store_price==1){
            return "만원이하";
        }else if(store_price==2){
            return "만원대";
        }else if(store_price==3){
            return "이만원이상";
        }
        return "가격없음";
    }
    public String gethash(){
        int length = hash.size();
        String str= "";
        for(int i=0; i<length; i++){
            str+='#'+hash.get(i);
        }
        hash.clear();
        return str;
    }
    public String getStore_insta() {
        return store_insta;
    }
    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }
    public void setStore_category(String store_category) {
        this.store_category = store_category;
    }
    public void setStore_price(int store_price) {
        this.store_price = store_price;
    }
    public void setStore_insta(String store_insta) { this.store_insta = store_insta; }
    public void sethash(String hast){this.hash.add(hast);}

    private String userId;
    public String getUser_id() {
        return userId;
    }
    public void setUser_id(String userId) {
        this.userId = userId;
    }
}


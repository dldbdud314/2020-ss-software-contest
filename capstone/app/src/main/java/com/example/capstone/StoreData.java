package com.example.capstone;

public class StoreData {
    private String store_name;
    private String store_category;
    private int store_price;

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
    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }
    public void setStore_category(String store_category) {
        this.store_category = store_category;
    }
    public void setStore_price(int store_price) {
        this.store_price = store_price;
    }
}


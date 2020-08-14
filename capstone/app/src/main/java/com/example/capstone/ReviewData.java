package com.example.capstone;

public class ReviewData {
    private String nickname;
    private int reviewRate;
    private String reviewText;
    private String storeName;

    public ReviewData(){
    }

    public ReviewData(String nickname, int reviewRate, String reviewText){
        this.nickname = nickname;
        this.reviewRate = reviewRate;
        this.reviewText = reviewText;
    }

    public String getNickname(){
        return nickname;
    }
    public int getReviewRate(){
        return reviewRate;
    }
    public String getReviewRateString(){
        return Integer.toString(reviewRate);
    }
    public String getReviewText(){ return reviewText; }
    public String getStoreName(){ return storeName; }
    public void setNickname(String nickname) { this.nickname = nickname; }
    public void setReviewRate(int reviewRate) { this.reviewRate = reviewRate; }
    public void setReviewText(String reviewText) { this.reviewText = reviewText; }
    public void setStoreName(String storeName) { this.storeName = storeName; }
}
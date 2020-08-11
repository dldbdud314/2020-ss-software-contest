package com.example.capstone;

public class ReviewData {
    private String nickname;
    private int reviewRate;
    private String reviewText;

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

    public String getReviewText(){
        return reviewText;
    }
}

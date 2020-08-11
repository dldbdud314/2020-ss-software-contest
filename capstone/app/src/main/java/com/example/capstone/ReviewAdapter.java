package com.example.capstone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;


import java.util.ArrayList;

public class ReviewAdapter extends BaseAdapter {
    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<ReviewData> review;

    public ReviewAdapter(Context context, ArrayList<ReviewData> review){
        mContext = context;
        this.review = review;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return review.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public ReviewData getItem(int position) {
        return review.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.item_review, null);

        TextView nickname = (TextView)view.findViewById(R.id.nicknameTxt);
        RatingBar rate = (RatingBar) view.findViewById(R.id.reviewRate);
        TextView reviewText = (TextView)view.findViewById(R.id.reviewTxt);

        nickname.setText(review.get(position).getNickname());
        rate.setRating(review.get(position).getReviewRate());
        reviewText.setText(review.get(position).getReviewText());

        return view;
    }
}

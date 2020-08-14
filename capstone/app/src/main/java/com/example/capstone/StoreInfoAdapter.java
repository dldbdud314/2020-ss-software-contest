package com.example.capstone;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
public class StoreInfoAdapter extends RecyclerView.Adapter<StoreInfoAdapter.CustomViewHolder> {

    private ArrayList<InfoData> mList = null;
    private Activity context = null;
    private String sId;


    public StoreInfoAdapter(Activity context, ArrayList<InfoData> list) {
        this.context = context;
        this.mList = list;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView name;
        protected TextView category;
        protected TextView menu;
        protected TextView time;
        protected Button btn_map;
        protected TextView instaText;
        protected LinearLayout instaLayout;
        protected Button hash1,hash2,hash3,hash4,hash5;
        protected Button newReviewBtn;
        protected ListView reviewListView;
        protected ReviewAdapter reviewAdapter;

        @SuppressLint("WrongViewCast")
        public CustomViewHolder(View view) {
            super(view);
            this.name = (TextView) view.findViewById(R.id.storeName);
            this.category = (TextView) view.findViewById(R.id.storeCategory);
            this.menu = (TextView) view.findViewById(R.id.storeMenu);
            this.time = (TextView) view.findViewById(R.id.storeTime);
            this.btn_map = (Button) view.findViewById(R.id.btn_map);

            btn_map.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context.getApplicationContext(), MapsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    context.startActivity(intent);
                }
            });

            this.instaText = (TextView) view.findViewById(R.id.storeInstaText);
            this.instaLayout = (LinearLayout) view.findViewById(R.id.storeInstaLayout);
            this.hash1=(Button)view.findViewById(R.id.storeInsta1);
            this.hash2=(Button)view.findViewById(R.id.storeInsta2);
            this.hash3=(Button)view.findViewById(R.id.storeInsta3);
            this.hash4=(Button)view.findViewById(R.id.storeInsta4);
            this.hash5=(Button)view.findViewById(R.id.storeInsta5);
            newReviewBtn = (Button) view.findViewById(R.id.reviewBtn);
            reviewListView = (ListView) view.findViewById(R.id.reviewList);

            reviewListView.setAdapter(reviewAdapter);
            final Intent intent = new Intent(context.getApplicationContext(), InstaSearchActivity.class);
            intent.putExtra("userId", sId);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

            newReviewBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent itn = new Intent(context.getApplicationContext(), ReviewActivity.class);
                    itn.putExtra("store_name", name.getText().toString());
                    itn.putExtra("userId", sId);
                    itn.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    context.startActivity(itn);
                }
            });
            hash1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent.putExtra("insta", hash1.getText().toString());
                    context.startActivity(intent);
                    context.finish();
                }
            });
            hash2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent.putExtra("insta", hash2.getText().toString());
                    context.startActivity(intent);
                    context.finish();
                }
            });
            hash3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent.putExtra("insta", hash3.getText().toString());
                    context.startActivity(intent);
                    context.finish();
                }
            });
            hash4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent.putExtra("insta", hash4.getText().toString());
                    context.startActivity(intent);
                    context.finish();
                }
            });
            hash5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent.putExtra("insta", hash5.getText().toString());
                    context.startActivity(intent);
                    context.finish();
                }
            });
        }
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_store, viewGroup, false);
        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {
        viewholder.name.setText(mList.get(position).getStore_name());
        viewholder.category.setText('['+mList.get(position).getStore_category()+']');
        viewholder.menu.setText(mList.get(position).getStore_menu());
        viewholder.time.setText(mList.get(position).getStore_time());
        if(!mList.get(position).getStore_insta().equals("...")){
            viewholder.instaText.setVisibility(View.VISIBLE);
            viewholder.instaLayout.setVisibility(View.VISIBLE);
            viewholder.hash1.setText(mList.get(position).gethash(0));
            viewholder.hash2.setText(mList.get(position).gethash(1));
            viewholder.hash3.setText(mList.get(position).gethash(2));
            viewholder.hash4.setText(mList.get(position).gethash(3));
            viewholder.hash5.setText(mList.get(position).gethash(4));
        }
        sId=mList.get(position).getUser_id();
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}

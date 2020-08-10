package com.example.capstone;
import android.annotation.SuppressLint;
import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
public class StoreInfoAdapter extends RecyclerView.Adapter<StoreInfoAdapter.CustomViewHolder> {

    private ArrayList<InfoData> mList = null;
    private Activity context = null;


    public StoreInfoAdapter(Activity context, ArrayList<InfoData> list) {
        this.context = context;
        this.mList = list;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView name;
        protected TextView category;
        protected TextView menu;
        protected TextView time;


        @SuppressLint("WrongViewCast")
        public CustomViewHolder(View view) {
            super(view);
            this.name = (TextView) view.findViewById(R.id.storeName);
            this.category = (TextView) view.findViewById(R.id.storeCategory);
            this.menu = (TextView) view.findViewById(R.id.storeMenu);
            this.time = (TextView) view.findViewById(R.id.storeTime);
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
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}

package com.example.capstone;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.CustomViewHolder> {

    private ArrayList<StoreData> mList = null;
    private Activity context = null;
    private AdapterView.OnItemClickListener mListener = null;
    private StoreData item;
    private String sId;

    public ListAdapter(Activity context, ArrayList<StoreData> list) {
        this.context = context;
        this.mList = list;
    }

    public interface OnItemClickLListener{
        void onItemCLick(View v, int position);
    }

    public void setOnClickListener(AdapterView.OnItemClickListener listener){
        this.mListener = listener;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView name;
        protected TextView category;
        protected TextView price;
        protected TextView insta;
        protected Button btn_scrapsave;

        @SuppressLint("WrongViewCast")
        public CustomViewHolder(View view) {
            super(view);
            this.name = (TextView) view.findViewById(R.id.textView_list_name);
            this.category = (TextView) view.findViewById(R.id.textView_list_category);
            this.price = (TextView) view.findViewById(R.id.textView_list_price);
            this.insta = (TextView) view.findViewById(R.id.textView_list_insta);
            this.btn_scrapsave = (Button) view.findViewById(R.id.btn_scrapsave);
            view.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION)
                    {
                        item = mList.get(pos);
                        Intent intent = new Intent(context.getApplicationContext(), StoreInfoActivity.class);
                        intent.putExtra("store_name", item.getStore_name());
                        intent.putExtra("userId", sId);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        context.startActivity(intent);
                    }
                }
            });

            btn_scrapsave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("longi", "스크랩 자리입니다");
                }
            });
        }
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list, viewGroup, false);
        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {
        viewholder.name.setText(mList.get(position).getStore_name());
        viewholder.category.setText('['+mList.get(position).getStore_category()+']');
        viewholder.price.setText(mList.get(position).getStore_price());
        if(!mList.get(position).getStore_instagram().equals("...")){
            viewholder.insta.setVisibility(View.VISIBLE);
            viewholder.insta.setText(mList.get(position).gethash());
        }
        sId=mList.get(position).getUser_id();
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}

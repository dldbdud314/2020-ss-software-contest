package com.example.capstone;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
public class MyReviewAdapter extends RecyclerView.Adapter<MyReviewAdapter.CustomViewHolder> {

    private ArrayList<ReviewData> mList = null;
    private Activity context = null;
    private AdapterView.OnItemClickListener mListener = null;
    private ReviewData item;
    private String sId;

    public MyReviewAdapter(Activity context, ArrayList<ReviewData> list) {
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
        protected TextView rate;
        protected TextView text;
        @SuppressLint("WrongViewCast")
        public CustomViewHolder(View view) {
            super(view);
            this.name = (TextView) view.findViewById(R.id.textView_list_name);
            this.rate = (TextView) view.findViewById(R.id.textView_list_rate);
            this.text = (TextView) view.findViewById(R.id.textView_list_text);
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
                        intent.putExtra("store_name", item.getStoreName());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        context.startActivity(intent);
                    }
                }
            });
        }
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mypage_review_item, viewGroup, false);
        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {
        Log.e("data", "?");
        viewholder.name.setText(mList.get(position).getStoreName());
        viewholder.rate.setText(mList.get(position).getReviewRateString()+'점');
        viewholder.text.setText(mList.get(position).getReviewText());
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}

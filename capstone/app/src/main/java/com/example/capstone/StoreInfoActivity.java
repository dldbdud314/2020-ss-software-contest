package com.example.capstone;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class StoreInfoActivity extends AppCompatActivity {
    private static String IP_ADDRESS = "220.69.170.218";
    private static String TAG = "phptest";

    private TextView tvname, tvcategory, tvmenu, tvtime;
    private TextView instaText;
    private View instaLine;
    private LinearLayout instaLayout;
    private Button hash1,hash2,hash3,hash4,hash5,btn_map,newReviewBtn;
    static public ArrayList<ReviewData> reviewList;
    static public ReviewAdapter reviewAdapter;
    private ListView rListView;

    private String mJsonString;
    String sId;
    String store_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_store);

        Intent itn = getIntent();
        sId = itn.getStringExtra("userId");
        store_name = itn.getStringExtra("store_name");

        tvname=(TextView)findViewById(R.id.storeName);
        tvcategory=(TextView)findViewById(R.id.storeCategory);
        tvmenu=(TextView)findViewById(R.id.storeMenu);
        tvtime=(TextView)findViewById(R.id.storeTime);
        instaText = (TextView) findViewById(R.id.storeInstaText);
        instaLayout = (LinearLayout) findViewById(R.id.storeInstaLayout);
        instaLine = (View) findViewById(R.id.instar_line);
        hash1=(Button)findViewById(R.id.storeInsta1);
        hash2=(Button)findViewById(R.id.storeInsta2);
        hash3=(Button)findViewById(R.id.storeInsta3);
        hash4=(Button)findViewById(R.id.storeInsta4);
        hash5=(Button)findViewById(R.id.storeInsta5);
        rListView=(ListView)findViewById(R.id.reviewList);

        reviewList = new ArrayList<ReviewData>();
        reviewAdapter = new ReviewAdapter(this, reviewList);
        rListView.setAdapter(reviewAdapter);

        GetReviewData reviewTask = new GetReviewData();
        String rLink = "http://220.69.170.218/storeReview.php?name=" + store_name;
        reviewTask.execute(rLink, "");

        btn_map = (Button) findViewById(R.id.btn_map);
        btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StoreInfoActivity.this, MapsActivity.class);
                intent.putExtra("store_name", tvname.getText().toString());
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

        newReviewBtn = (Button) findViewById(R.id.reviewBtn);
        newReviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itn = new Intent(StoreInfoActivity.this, ReviewRegisterActivity.class);
                itn.putExtra("store_name", tvname.getText().toString());
                itn.putExtra("userId", sId);
                itn.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(itn);
            }
        });

        final Intent intent = new Intent(StoreInfoActivity.this, InstaSearchActivity.class);
        intent.putExtra("userId", sId);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        hash1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("insta", hash1.getText().toString());
                startActivity(intent);
            }
        });
        hash2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("insta", hash2.getText().toString());
                startActivity(intent);
            }
        });
        hash3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("insta", hash3.getText().toString());
                startActivity(intent);
            }
        });
        hash4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("insta", hash4.getText().toString());
                startActivity(intent);
            }
        });
        hash5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("insta", hash5.getText().toString());
                startActivity(intent);
            }
        });

        GetData task = new GetData();
        String link = "http://220.69.170.218/store.php?name=" + store_name;
        task.execute(link, "");
    }

    private class GetReviewData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(StoreInfoActivity.this,
                    "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();

            Log.d(TAG, "response - " + result);

            if (result == null) {
                Log.d("error", "resultNull");
            } else {
                mJsonString = result;
                showRResult();
            }
        }


        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];
            String postParameters = params[1];

            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString().trim();

            } catch (Exception e) {

                Log.d(TAG, "GetData : Error ", e);
                errorString = e.toString();

                return null;
            }

        }
    }

    private void showRResult() {

        String TAG_JSON = "webnautes";
        String TAG_NICKNAME = "nick";
        String TAG_RATE = "rate";
        String TAG_REVIEW = "reviewText";

        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);


            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);

                String nickname = item.getString(TAG_NICKNAME);
                int rate = Integer.parseInt(item.getString(TAG_RATE));
                String reviewTxt = item.getString(TAG_REVIEW);

                ReviewData reviewData = new ReviewData(nickname, rate, reviewTxt);

                reviewData.setNickname(nickname);
                reviewData.setReviewRate(rate);
                reviewData.setReviewText(reviewTxt);

                reviewList.add(reviewData);
                reviewAdapter.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            Log.d(TAG, "showResult : ", e);
        }

    }

   private class GetData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(StoreInfoActivity.this,
                    "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            Log.d(TAG, "response - " + result);

            if (result == null) {
                Log.e("error", "resultNull");
            } else {
                mJsonString = result;
                showResult();
            }
        }


        @Override
        protected String doInBackground(String... params) {
            String serverURL = params[0];
            String postParameters = params[1];


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString().trim();


            } catch (Exception e) {

                Log.d(TAG, "GetData : Error ", e);
                errorString = e.toString();

                return null;
            }

        }
    }


    private void showResult() {

        String TAG_JSON = "webnautes";
        String TAG_NAME = "name";
        String TAG_CATEGORY = "category";
        String TAG_MENU = "menu";
        String TAG_TIME = "time";
        String TAG_INSTA ="insta";


        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for (int i = 0; i < jsonArray.length(); i++) {
                Log.e("size", String.valueOf(i));
                JSONObject item = jsonArray.getJSONObject(i);

                String name = item.getString(TAG_NAME);
                String category = item.getString(TAG_CATEGORY);
                String menu = item.getString(TAG_MENU);
                String time = item.getString(TAG_TIME);
                String insta = item.getString(TAG_INSTA);

                InfoData personalData = new InfoData();

                personalData.setStore_name(name);
                personalData.setStore_category(category);
                personalData.setStore_menu(menu);
                personalData.setStore_time(time);
                personalData.setStore_instagram(insta);
                String[] array = insta.split(",");
                for(int k=0; k<array.length; k++){
                    personalData.sethash(array[k]);
                }
                personalData.setUser_id(sId);
                change(personalData);
            }
        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }
    private void change(InfoData data){
        tvname.setText(data.getStore_name());
        tvcategory.setText('['+data.getStore_category()+']');
        tvmenu.setText(data.getStore_menu());
        tvtime.setText(data.getStore_time());
        Log.e("data", data.getStore_instagram());
        if(!data.getStore_instagram().equals("...")){
            instaText.setVisibility(View.VISIBLE);
            instaLayout.setVisibility(View.VISIBLE);
            instaLine.setVisibility(View.VISIBLE);
            hash1.setText(data.gethash(0));
            hash2.setText(data.gethash(1));
            hash3.setText(data.gethash(2));
            hash4.setText(data.gethash(3));
            hash5.setText(data.gethash(4));
        }
    }
}
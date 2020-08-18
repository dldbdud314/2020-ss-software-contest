package com.example.capstone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ReviewRegisterActivity extends AppCompatActivity {
    private static String IP_ADDRESS = "220.69.170.218";
    private static String TAG = "phptest";
    TextView nicknameTxt, storenameTxt;
    RatingBar ratingbar;
    EditText reviewEdittext;
    Button registerBtn;
    String sNickname, sReviewText, sUserId, sStoreName, ssNickname;
    Integer sRate;
    private String mJsonString;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_review_page);

        Intent intent = getIntent();

        nicknameTxt = (TextView)findViewById(R.id.nickname);
        storenameTxt = (TextView)findViewById(R.id.store);
        ratingbar = (RatingBar)findViewById(R.id.ratingBar);
        reviewEdittext = (EditText)findViewById(R.id.reviewEdittext);
        registerBtn = (Button)findViewById(R.id.reviewRegisterBtn);

        sUserId = intent.getStringExtra("userId");
        sStoreName = intent.getStringExtra("store_name");

        GetData task = new GetData();
        String link = "http://220.69.170.218/getNickname.php?id=" + sUserId;
        task.execute(link, "");

        storenameTxt.setText(sStoreName);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sReviewText = reviewEdittext.getText().toString();
                sRate = (int)ratingbar.getRating();

                RegistReview rr = new RegistReview();
                rr.execute();
            }
        });
    }

    private class GetData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(ReviewRegisterActivity.this,
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
        String TAG_NAME = "nickname";

        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            JSONObject item = jsonArray.getJSONObject(0);
            sNickname = item.getString(TAG_NAME);
            nicknameTxt.setText(sNickname);

            ssNickname = sNickname;

        } catch (JSONException e) {
            Log.d(TAG, "showResult : ", e);
        }

    }

    public class RegistReview extends AsyncTask<Void, Integer, Void> {
        String data = "";

        @Override
        protected Void doInBackground(Void... voids) {
            /* 인풋 파라메터값 생성 */
            String param = "&u_reviewText=" + sReviewText + "&u_rate=" + sRate + "&u_userId=" + sUserId + "&u_storeName=" + sStoreName + "";

            try {
                /* 서버연결 */
                URL url = new URL(
                        "http://" + IP_ADDRESS + "/registerReview.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.connect();

                /* 안드로이드 -> 서버 파라메터값 전달 */
                OutputStream outs = conn.getOutputStream();
                outs.write(param.getBytes("UTF-8"));
                outs.flush();
                outs.close();

                /* 서버 -> 안드로이드 파라메터값 전달 */
                InputStream is = null;
                BufferedReader in = null;

                is = conn.getInputStream();
                in = new BufferedReader(new InputStreamReader(is), 8 * 1024);
                String line = null;
                StringBuffer buff = new StringBuffer();
                while ((line = in.readLine()) != null) {
                    buff.append(line + "\n");
                }
                data = buff.toString().trim();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            /* 서버에서 응답 */
            Log.e("RECV DATA", data);
            int len = data.length();
            String check = data.substring(len - 1, len);
            if (check.equals("s")) {
                Log.e("Success", "완료!");
                Toast successToast = Toast.makeText(getApplicationContext(),"등록 성공", Toast.LENGTH_SHORT);
                successToast.show();

                ReviewData reviewData = new ReviewData(ssNickname, sRate, sReviewText);

                reviewData.setNickname(ssNickname);
                reviewData.setReviewRate(sRate);
                reviewData.setReviewText(sReviewText);

                StoreInfoActivity.reviewList.add(reviewData);
                StoreInfoActivity.reviewAdapter.notifyDataSetChanged();
            } else {
                Log.e("Fail", "실패!");
                Toast failToast = Toast.makeText(getApplicationContext(),"등록 실패", Toast.LENGTH_SHORT);
                failToast.show();
            }
            finish();
        }
    }
}

package com.example.capstone;

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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ReviewActivity extends AppCompatActivity {
    private static String IP_ADDRESS = "220.69.170.218";
    TextView nicknameTxt, storenameTxt;
    RatingBar ratingbar;
    EditText reviewEdittext;
    Button registerBtn;
    String sReviewText, sUserId, sStoreName;
    Integer sRate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_review_page);

        Intent intent = getIntent();

        nicknameTxt = (TextView)findViewById(R.id.id);
        storenameTxt = (TextView)findViewById(R.id.store);
        ratingbar = (RatingBar)findViewById(R.id.ratingBar);
        reviewEdittext = (EditText)findViewById(R.id.reviewEdittext);
        registerBtn = (Button)findViewById(R.id.reviewRegisterBtn);


        //세션유지 구현 후 유저 정보 넘겨주는 거
        //sUserId 웅앵웅
        sUserId = intent.getStringExtra("userId");
        sStoreName = intent.getStringExtra("store_name");
        nicknameTxt.setText(sUserId);
        storenameTxt.setText(sStoreName);
        //사용자의 nickname이 나와야 함
        //nicknameTxt.setText();

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
            } else {
                Log.e("Fail", "실패!");
                Toast failToast = Toast.makeText(getApplicationContext(),"등록 실패", Toast.LENGTH_SHORT);
                failToast.show();
            }
            finish();
        }
    }
}

package com.example.capstone;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MyPageActivity extends AppCompatActivity {
    private static String IP_ADDRESS = "220.69.170.218";
    TextView tv_name, tv_nick, tv_email;
    Button logout, clear;
    String sId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage);

        tv_name = (TextView) findViewById(R.id.textView_name);
        tv_nick = (TextView) findViewById(R.id.textView_nick);
        tv_email = (TextView) findViewById(R.id.textView_email);
        Intent itn = getIntent();
        sId = itn.getStringExtra("userId");
        mypageDB fdb = new mypageDB();
        fdb.execute();

        logout = (Button)findViewById(R.id.btn_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(MyPageActivity.this)
                        .setTitle("로그아웃").setMessage("로그아웃 하시겠습니까?")
                        .setPositiveButton("로그아웃", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Intent i = new Intent(MyPageActivity.this, SignInActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(i);
                                finish();
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                            }
                        })
                        .show();

            }
        });
        clear = (Button)findViewById(R.id.btn_clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(MyPageActivity.this)
                        .setTitle("회원탈퇴").setMessage("탈퇴를 계속 진행하시겠습니까?")
                        .setPositiveButton("진행", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                clearDB fdb = new clearDB();
                                fdb.execute();
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                            }
                        })
                        .show();
            }
        });
    }
    public class mypageDB  extends AsyncTask<Void, Integer, Void> {
        String data = "";
        int count=0;
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MyPageActivity.this);
        @Override
        protected Void doInBackground(Void... unused) {

            /* 인풋 파라메터값 생성 */
            String param = "u_id=" + sId+ "";
            ///Log.e("POST",param);
            try {
                /* 서버연결 */
                URL url = new URL(
                        "http://"+IP_ADDRESS+"/mypage.php");
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
                    count++;
                }
                data = buff.toString().trim();
                //Log.e("data",data);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            /* 서버에서 응답 */
            int len=data.length();
            StringBuffer checkBf= new StringBuffer();
            int countChk=0;
            count--;
            for(int i=0; i<len; i++){
                if(data.charAt(i)=='\n'){
                    countChk++;
                }
                if(countChk==count){
                    checkBf.append(data.charAt(i));
                }
            }
            String check = checkBf.toString().trim();
            Log.e("data",check);
            if(check.equals("N"))
            {
                Log.e("Fail","아이디없음!");
            }
            else{
                String[] array = check.split(",");
                tv_name.setText(array[0]);
                tv_nick.setText("'"+array[1]+"'");
                tv_email.setText(array[2]);
            }
        }

    }
    public class clearDB  extends AsyncTask<Void, Integer, Void> {
        String data = "";
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MyPageActivity.this);
        @Override
        protected Void doInBackground(Void... unused) {

            /* 인풋 파라메터값 생성 */
            String param = "u_id=" + sId+ "";
            ///Log.e("POST",param);
            try {
                /* 서버연결 */
                URL url = new URL(
                        "http://"+IP_ADDRESS+"/userClear.php");
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
                Log.e("data",data);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            /* 서버에서 응답 */
            int len =  data.length();
            String check=data.substring(len-1, len);
            Log.e("data",check);
            if(check.equals("N"))
            {
                Log.e("Fail","실패!");
            }
            else{
                Log.e("Success","성공!");
                Intent i = new Intent(MyPageActivity.this, SignInActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(i);
                finish();
            }
        }

    }
}

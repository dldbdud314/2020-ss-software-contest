package com.example.capstone;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class SignInActivity extends AppCompatActivity {
    private static String IP_ADDRESS = "220.69.170.218";
    EditText et_id, et_pass;
    String sId, sPw;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_page);

        Intent intent = getIntent();

        et_id = (EditText) findViewById(R.id.et_id);
        et_pass = (EditText) findViewById(R.id.et_pass);

        Button btn_moveSignUp = (Button) findViewById(R.id.btn_moveSignUp);
        btn_moveSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

        Button btn_find = (Button) findViewById(R.id.btn_find);
        btn_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), IdPassSearchActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });
    }
    public void btn_signIn(View view){
        try{
            sId = et_id.getText().toString();
            sPw = et_pass.getText().toString();
        }catch (NullPointerException e)
        {
            Log.e("err",e.getMessage());
        }

        loginDB lDB = new loginDB();
        lDB.execute();
    }
    public class loginDB  extends AsyncTask<Void, Integer, Void> {
        String data = "";
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(SignInActivity.this);
        @Override
        protected Void doInBackground(Void... unused) {

            /* 인풋 파라메터값 생성 */
            String param = "u_id=" + sId + "&u_pw=" + sPw + "";
            Log.e("POST",param);
            try {
                /* 서버연결 */
                URL url = new URL(
                        "http://"+IP_ADDRESS+"/login.php");
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
                while ( ( line = in.readLine() ) != null )
                {
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
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            /* 서버에서 응답 */
            Log.e("RECV DATA",data);
            int len =  data.length();
            String check=data.substring(len-1, len);
            if(check.equals("s"))
            {
                Log.e("Success","완료!");
                Intent itn = new Intent(getApplication(), MainActivity.class);
                itn.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(itn);
            }
            else if(check.equals("e"))
            {
                Log.e("Fail","비번오류!");
                alertBuilder
                        .setTitle("로그인 오류")
                        .setMessage("비밀번호를 확인해주세요. ")
                        .setCancelable(true)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent itn = new Intent(getApplication(), SignInActivity.class);
                                itn.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(itn);
                            }
                        });
                AlertDialog dialog = alertBuilder.create();
                dialog.show();

            }
            else if(check.equals("c"))
            {
                Log.e("Fail","아이디없음!");
                alertBuilder
                        .setTitle("로그인 오류")
                        .setMessage("존재하지않는 아이디입니다.")
                        .setCancelable(true)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent itn = new Intent(getApplication(), SignInActivity.class);
                                itn.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(itn);
                            }
                        });
                AlertDialog dialog = alertBuilder.create();
                dialog.show();
            }
        }
    }
}

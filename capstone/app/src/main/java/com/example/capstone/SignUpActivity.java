package com.example.capstone;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SignUpActivity extends AppCompatActivity {
    private static String IP_ADDRESS = "220.69.170.218";
    EditText et_id, et_pass, et_passck;
    String sId, sPw, sPwck;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_page);

        et_id = (EditText) findViewById(R.id.et_id);
        et_pass = (EditText) findViewById(R.id.et_pass);
        et_passck = (EditText) findViewById(R.id.et_passck);
    }
    public void btn_signup(View view){
        sId = et_id.getText().toString();
        sPw = et_pass.getText().toString();
        sPwck = et_passck.getText().toString();
        if(sPw.equals(sPwck)){
            registDB rdb = new registDB();
            rdb.execute();
        }else{
            Toast.makeText(getApplicationContext(), "비밀번호 확인 부탁드립니다.", Toast.LENGTH_LONG).show();
        }
    }

    public class registDB extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... unused) {

            /* 인풋 파라메터값 생성 */
            String param = "u_id=" + sId + "&u_pw=" + sPw + "";
            try {
                /* 서버연결 */
                URL url = new URL(
                        "http://"+IP_ADDRESS+"/join.php");
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
                String data = "";

                is = conn.getInputStream();
                in = new BufferedReader(new InputStreamReader(is), 8 * 1024);
                String line = null;
                StringBuffer buff = new StringBuffer();
                while ( ( line = in.readLine() ) != null )
                {
                    buff.append(line + "\n");
                }
                data = buff.toString().trim();
                Log.e("RECV DATA",data);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

    }

}


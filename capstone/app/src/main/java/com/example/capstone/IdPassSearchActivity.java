package com.example.capstone;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
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

public class IdPassSearchActivity extends AppCompatActivity {
    private static String IP_ADDRESS = "220.69.170.218";
    EditText et_id, et_name;
    String sId, sName;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.id_pass_search_page);

        et_id = (EditText) findViewById(R.id.et_Email);
        et_name = (EditText) findViewById(R.id.et_name);
    }

    public void btn_findPw(View view){
        sId = et_id.getText().toString();
        sName = et_name.getText().toString();
        registDB rdb = new registDB();
        rdb.execute();
    }

    public class registDB extends AsyncTask<Void, Integer, Void> {
        String data = "";
        int count=0;
        @Override
        protected Void doInBackground(Void... unused) {

            /* 인풋 파라메터값 생성 */
            String param = "u_id=" + sId + "&u_name=" + sName+ "";
            try {
                /* 서버연결 */
                URL url = new URL(
                        "http://"+IP_ADDRESS+"/findpwMail.php");
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
                        buff.append(line+'\n');
                        count++;
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
            if (check.equals("e")) {
                Log.e("Fail", "실패!");
                AlertDialog.Builder builder = new AlertDialog.Builder(IdPassSearchActivity.this);
                builder.setTitle("비밀번호 찾기 결과");
                builder.setMessage("회원이 아닙니다.\n다시한번 확인해주세요.");
                builder.setPositiveButton("예",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(intent);
                                finish();
                            }
                        });
                builder.create().show();
            } else {
                Log.e("Success", "성공!");
                AlertDialog.Builder builder = new AlertDialog.Builder(IdPassSearchActivity.this);
                builder.setTitle("비밀번호 찾기 결과");
                builder.setMessage("학교 웹메일을 확인하세요\n");
                builder.setPositiveButton("예",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(intent);
                                finish();
                            }
                        });
                builder.create().show();
            }
        }

    }
}

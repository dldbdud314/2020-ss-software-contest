package com.example.capstone;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    private static String IP_ADDRESS = "220.69.170.218";
    EditText et_name, et_nickname, et_id, et_pass, et_passck;
    String sName, sNick, sId, sPw, sPwck;
    boolean cCheck = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_page);
        et_name = (EditText) findViewById(R.id.et_name);
        et_nickname = (EditText) findViewById(R.id.et_nickname);
        et_id = (EditText) findViewById(R.id.et_id);
        et_pass = (EditText) findViewById(R.id.et_pass);
        et_passck = (EditText) findViewById(R.id.et_passck);
    }

    public void validateButton_nick(View view){
        sNick = et_nickname.getText().toString();

        findDB fdb = new findDB();
        fdb.execute();
    }

    public void btn_signup(View view){
        sName = et_name.getText().toString();
        sNick = et_nickname.getText().toString();
        sId = et_id.getText().toString();
        sPw = et_pass.getText().toString();
        sPwck = et_passck.getText().toString();

        if(isValidId(sId)){
            if(isValidPassword(sPw)){
                if(sPw.equals(sPwck)){
                    if(cCheck==false){
                        Toast.makeText(getApplicationContext(), "닉네임이 이미 있습니다.", Toast.LENGTH_LONG).show();
                    } else {
                        registDB rdb = new registDB();
                        rdb.execute();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "비밀번호 형식이 잘못 되었습니다.", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "아이디 형식이 잘못 되었습니다.", Toast.LENGTH_LONG).show();
        }
    }
    public boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[@#$%^&+!?_+.=])(?=\\S+$).{3,15}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

    public boolean isValidId(final String id) {

        Pattern pattern;
        Matcher matcher;

        final String ID_PATTERN = "^([0-9]{8})*@sungshin.ac.kr$";

        pattern = Pattern.compile(ID_PATTERN);
        matcher = pattern.matcher(id);

        return matcher.matches();

    }

    public class registDB extends AsyncTask<Void, Integer, Void> {
        String data = "";
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(SignUpActivity.this);
        @Override
        protected Void doInBackground(Void... unused) {

            /* 인풋 파라메터값 생성 */
            String param = "u_id=" + sId + "&u_pw=" + sPw + "&u_name=" + sName + "&u_nick=" + sNick + "";
            try {
                /* 서버연결 */
                URL url = new URL(
                        "http://" + IP_ADDRESS + "/joinmail.php");
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

        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            /* 서버에서 응답 */
            Log.e("RECV DATA", data);
            int len = data.length();
            String check = data.substring(len - 1, len);
            if (check.equals("s")) {
                Log.e("Success", "완료!");
                alertBuilder
                        .setTitle("회원가입 완료")
                        .setMessage("이메일 인증을 완료하셔야 정상적으로 로그인이 됩니다.")
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
            } else {
                Log.e("Fail", "실패!");
                alertBuilder
                        .setTitle("회원가입 오류")
                        .setMessage("다시시도해주세요!")
                        .setCancelable(true)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent itn = new Intent(getApplication(), SignUpActivity.class);
                                itn.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(itn);
                                finish();
                            }
                        });
                AlertDialog dialog = alertBuilder.create();
                dialog.show();
            }
        }
    }

    public class findDB extends AsyncTask<Void, Integer, Void> {
        String data = "";
        int count=0;
        @Override
        protected Void doInBackground(Void... unused) {

            /* 인풋 파라메터값 생성 */
            String param = "u_nick=" + sNick + "";
            try {
                /* 서버연결 */
                URL url = new URL(
                        "http://" + IP_ADDRESS + "/findnick.php");
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
                Log.e("Success", "중복없음!");
                Toast.makeText(getApplicationContext(), "사용 할 수 있는 닉네임입니다.", Toast.LENGTH_LONG).show();
                cCheck = true;
            } else if (check.equals("s")) {
                Log.e("Fail", "중복있음!");
                Toast.makeText(getApplicationContext(), "이미 있는 닉네임입니다.", Toast.LENGTH_LONG).show();
            }
        }
    }
}


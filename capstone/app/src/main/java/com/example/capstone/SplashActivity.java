package com.example.capstone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash);

        Handler hd = new Handler();
        hd.postDelayed(new splashHandler(), 3000); // 3초 후에 hd handler 실행

    }

    private class splashHandler implements Runnable{
        public void run(){
            startActivity(new Intent(getApplication(), MainActivity.class));
            finish(); // 로딩페이지 Activity stack에서 제거
        }
    }

}
package com.example.capstone;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);

        //맛집추천 버튼 button1, 맛집검색 버튼 button2, 마이페이지 버튼 button3
        Button button1 = (Button)findViewById(R.id.btn1);
        Button button2 = (Button)findViewById(R.id.btn2);
        Button button3 = (Button)findViewById(R.id.btn3);

        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent itn = getIntent();
                String sId = itn.getStringExtra("userId");
                Intent intent = new Intent(getApplicationContext(), SituationActivity.class);
                intent.putExtra("userId", sId);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });
        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent itn = getIntent();
                String sId = itn.getStringExtra("userId");
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                intent.putExtra("userId", sId);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });
        button3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent itn = getIntent();
                String sId = itn.getStringExtra("userId");
                Log.e("Data",sId);
                Intent intent = new Intent(getApplicationContext(), MyPageActivity.class);
                intent.putExtra("userId", sId);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

    }
}
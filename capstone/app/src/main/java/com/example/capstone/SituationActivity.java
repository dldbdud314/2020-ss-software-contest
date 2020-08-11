package com.example.capstone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SituationActivity extends Activity {
    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.situation_page);

        btn1 = (Button)findViewById(R.id.btn1);
        btn2 = (Button)findViewById(R.id.btn2);
        btn3 = (Button)findViewById(R.id.btn3);
        btn4 = (Button)findViewById(R.id.btn4);
        btn5 = (Button)findViewById(R.id.btn5);
        btn6 = (Button)findViewById(R.id.btn6);
        btn7 = (Button)findViewById(R.id.btn7);
        btn8 = (Button)findViewById(R.id.btn8);

        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent itn = getIntent();
                String sId = itn.getStringExtra("userId");
                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                intent.putExtra("sit", 1);
                intent.putExtra("userId", sId);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent itn = getIntent();
                String sId = itn.getStringExtra("userId");
                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                intent.putExtra("sit", 2);
                intent.putExtra("userId", sId);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
            }
        });
        btn3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent itn = getIntent();
                String sId = itn.getStringExtra("userId");
                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                intent.putExtra("sit", 3);
                intent.putExtra("userId", sId);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
            }
        });
        btn4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent itn = getIntent();
                String sId = itn.getStringExtra("userId");
                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                intent.putExtra("sit", 4);
                intent.putExtra("userId", sId);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
            }
        });
        btn5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent itn = getIntent();
                String sId = itn.getStringExtra("userId");
                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                intent.putExtra("sit", 5);
                intent.putExtra("userId", sId);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
            }
        });
        btn6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent itn = getIntent();
                String sId = itn.getStringExtra("userId");
                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                intent.putExtra("sit", 6);
                intent.putExtra("userId", sId);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
            }
        });
        btn7.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent itn = getIntent();
                String sId = itn.getStringExtra("userId");
                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                intent.putExtra("sit", 7);
                intent.putExtra("userId", sId);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
            }
        });
        btn8.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent itn = getIntent();
                String sId = itn.getStringExtra("userId");
                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                intent.putExtra("sit", 8);
                intent.putExtra("userId", sId);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
            }
        });
    }
}

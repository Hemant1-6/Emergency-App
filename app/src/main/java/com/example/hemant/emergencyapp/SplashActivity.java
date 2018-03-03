package com.example.hemant.emergencyapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    FragmentManager fm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sp = getSharedPreferences("my_sp",MODE_PRIVATE);
        String s = sp.getString("k1",null);
        if(s==null) {
            setContentView(R.layout.activity_splash);
            new Thread() {
                public void run() {
                    try {
                        Thread.sleep(3000);
                        startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
        else
            startActivity(new Intent(SplashActivity.this,Emergency.class));
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.finish();
    }
}

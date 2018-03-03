package com.example.hemant.emergencyapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
  SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = getSharedPreferences("my_sp",MODE_PRIVATE);
        String x = sp.getString("k1",null);
        if(x!=null)
        {
            Intent i = new Intent(this,Emergency.class);
            startActivity(i);
        }
        else {
            setContentView(R.layout.activity_home);
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.containerhome, new DetailsFragment());
            ft.commit();
        }

    }
}

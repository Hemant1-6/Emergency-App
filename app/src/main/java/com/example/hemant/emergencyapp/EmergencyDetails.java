package com.example.hemant.emergencyapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class EmergencyDetails extends AppCompatActivity {
    TextView tv,tv1,tv2,tv3,tv4,tv5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emergency_details);
        tv = (TextView) findViewById(R.id.t0);
        tv1 = (TextView) findViewById(R.id.t2);
        tv2 = (TextView) findViewById(R.id.t3);
        tv3 = (TextView) findViewById(R.id.t4);
        tv4 = (TextView) findViewById(R.id.t5);
        tv5 = (TextView) findViewById(R.id.t6);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SharedPreferences sp = getSharedPreferences("my_sp", MODE_PRIVATE);

        final String data = sp.getString("k1", null);
        final String data1 = sp.getString("k2", null);
        final String data2 = sp.getString("k3", null);
        final String data3 = sp.getString("k4", null);
        final String data4 = sp.getString("k5", null);
        final String data5 = sp.getString("k6",null);
            tv.setText( data);
            tv1.setText(data1);
            tv2.setText( data2);
            tv3.setText(data3);
            tv4.setText( data4);
            tv5.setText( data5);

//        ImageButton ib = (ImageButton) findViewById(R.id.acc1);
//        ib.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(EmergencyDetails.this,Emergency.class);
//                startActivity(i);
//                pause();
//            }
//        });

    }
    public void pause(){
        finish();
    }
}

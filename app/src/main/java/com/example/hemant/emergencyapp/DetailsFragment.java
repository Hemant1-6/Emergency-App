package com.example.hemant.emergencyapp;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {
    View v;
    Button btn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         v=  inflater.inflate(R.layout.fragment_details, container, false);
        btn = v.findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                EditText et1 = v.findViewById(R.id.et);
                EditText et2 = v.findViewById(R.id.et2);
                EditText et3 = v.findViewById(R.id.et3);
                EditText et4 = v.findViewById(R.id.et4);
                EditText et5 = v.findViewById(R.id.et5);
                EditText et6 = v.findViewById(R.id.et6);

                String data = et1.getText().toString();
                String data1 = et5.getText().toString();
                String data2 = et6.getText().toString();
                String blood = et4.getText().toString();
                String add = et2.getText().toString();
                String con = et3.getText().toString();
                SharedPreferences sp = DetailsFragment.this.getActivity().getSharedPreferences("my_sp",MODE_PRIVATE);
                SharedPreferences.Editor e = sp.edit();
                if(et1.getText().toString().length()!=0)
                e.putString("k1",et1.getText().toString());
                e.putString("k2",et2.getText().toString());
                e.putString("k3",et3.getText().toString());
                e.putString("k4",et4.getText().toString());
                e.putString("k5",et5.getText().toString());
                e.putString("k6",et6.getText().toString());
                e.commit();

                if(data.matches("")||data1.matches("")||data2.matches("")||blood.matches("")||add.matches("")||con.matches("")) {
                    Toast.makeText(getActivity(), "Please Enter Details", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(blood.equalsIgnoreCase("A+")||blood.equalsIgnoreCase("B+")||blood.equalsIgnoreCase("AB+")||blood.equalsIgnoreCase("O+")
                            ||blood.equalsIgnoreCase("A-")||blood.equalsIgnoreCase("B-")||blood.equalsIgnoreCase("AB-")||blood.equalsIgnoreCase("O-")){
                        Intent i = new Intent(getActivity(), Emergency.class);
                        startActivity(i);
                        getActivity().finish();
                    }
                    else
                        Toast.makeText(getActivity(), "Please Enter Correct Blood Group", Toast.LENGTH_SHORT).show();

                }

            }
        });

        return v;
    }

}


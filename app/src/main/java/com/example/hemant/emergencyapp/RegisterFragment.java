package com.example.hemant.emergencyapp;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
public class RegisterFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_register, container, false);
        final EditText t1 = v.findViewById(R.id.reg1);
        final EditText t2 = v.findViewById(R.id.reg2);
        final EditText t3 = v.findViewById(R.id.reg3);
        Button bt = v.findViewById(R.id.bt);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sp = RegisterFragment.this.getActivity().getSharedPreferences("my_sp", MODE_PRIVATE);
                SharedPreferences.Editor e = sp.edit();
                e.putString("reg1", t1.getText().toString());
                e.commit();

//                SharedPreferences sp2 = RegisterFragment.this.getActivity().getSharedPreferences("my_log", MODE_PRIVATE);
//                SharedPreferences.Editor e2 = sp2.edit();
//                e2.putString("usr", t1.getText().toString());
//                e2.putString("pass", t2.getText().toString());
//                e2.commit();

                String data1 = t1.getText().toString();
                String data2 = t2.getText().toString();
                String data3 = t3.getText().toString();

                if (data1.matches("") || data2.matches("") || data3.matches("")) {
                    Toast.makeText(getActivity(), "Please enter details", Toast.LENGTH_SHORT).show();
                } else {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.containerhome, new DetailsFragment());
                    ft.commit();
                }

            }
        });
        return v;
    }

}

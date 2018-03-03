package com.example.hemant.emergencyapp;


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


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    Button btn,button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragmen
        View v =inflater.inflate(R.layout.fragment_login, container, false);
        final EditText et = v.findViewById(R.id.id);
        final EditText et1 = v.findViewById(R.id.pass);
        button = v.findViewById(R.id.button);
        btn = v.findViewById(R.id.btn1);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                sp2 = LoginFragment.this.getActivity().getSharedPreferences("my_log",MODE_PRIVATE);
//                String usr = sp2.getString("usr",null);
//                String pass = sp2.getString("pass",null);
//                   String data = et.getText().toString();
//                 String dat1 = et1.getText().toString();
                Toast.makeText(getActivity(), "Please Register", Toast.LENGTH_SHORT).show();
//                if(usr.equals(data)&&pass.equals(dat1))
//                {
//                startActivity(new Intent(getActivity(),Emergency.class));
//                    getActivity().finish();
//                }
//                else if(usr!=data&&pass==dat1){
//                    Toast.makeText(getActivity(), "Invalid Username", Toast.LENGTH_SHORT).show();
//                }
//                else if(usr==data&&pass!=dat1){
//                    Toast.makeText(getActivity(), "Invalid Password", Toast.LENGTH_SHORT).show();
//                }
//                else
//                    Toast.makeText(getActivity(), "Invalid Password or Username", Toast.LENGTH_SHORT).show();

//                else
//                {
//                    FragmentManager fm = getActivity().getSupportFragmentManager();
//                    FragmentTransaction ft = fm.beginTransaction();
//                    ft.replace(R.id.containerhome,new RegisterFragment());
//                    ft.commit();
//
//                }

            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.containerhome,new RegisterFragment());
                ft.commit();

            }
        });
        return v;
    }

}


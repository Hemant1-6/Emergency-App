package com.example.hemant.emergencyapp;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class EmegencyService extends Fragment {
    int MY_PERMISSION_REQUEST=1;
    String title[] ={"Emergency","Gas Leakage","Traffic Police","Tourist Helpline","Child helpline","Blood Requirement","Women Helpline","Train accident","Road Accident",
            "Disaster management","Kisan Call Center","Consumer Helpline"};
    String number[] = {"112","1906","103","1363","1098","104","181","1072","1073","108","1551","1800-11-4000"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vv = inflater.inflate(R.layout.emegency_service, null);
        MyAdapter myAdapter = new MyAdapter(getActivity());
        ListView list = vv.findViewById(R.id.listemrg);
        list.setAdapter(myAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView contact = view.findViewById(R.id.tvnumber);
                String cont = contact.getText().toString();
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:"+cont));
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSION_REQUEST);
                } else
                    startActivity(intent);
            }
        });
        return vv;
    }
    class MyAdapter extends ArrayAdapter{

        public MyAdapter(@NonNull Context context) {
            super(context,R.layout.service_list,R.id.tvtitle,title);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View vv = getActivity().getLayoutInflater().inflate(R.layout.service_list,parent,false);
//            LinearLayout ll = vv.findViewById(R.id.mainlay);
//            ll.setBackgroundColor(getResources().getColor(R.color.emergency));
             TextView tvtitle = vv.findViewById(R.id.tvtitle);
            TextView tvnumber = vv.findViewById(R.id.tvnumber);
            tvtitle.setText(title[position]);
            tvnumber.setText(number[position]);
            return vv;
        }
    }
}

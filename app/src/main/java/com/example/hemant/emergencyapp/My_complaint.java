package com.example.hemant.emergencyapp;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * A simple {@link Fragment} subclass.
 */
public class My_complaint extends Fragment {
    EditText fname,lname,address,subject,complaint;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vv = inflater.inflate(R.layout.my_complaint,container,false);
        fname = vv.findViewById(R.id.fname);
        lname = vv.findViewById(R.id.lname);
        address = vv.findViewById(R.id.address);
        subject = vv.findViewById(R.id.subject);
        complaint = vv.findViewById(R.id.complaint);
        Button submit = vv.findViewById(R.id.submit);
        Button cancel = vv.findViewById(R.id.cancel);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:")); // only email apps should handle this
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"hemantahuja.1016@gmail.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, subject.getText().toString());
                intent.putExtra(Intent.EXTRA_TEXT, complaint.getText().toString() + "\n\n" + fname.getText().toString() +
                        lname.getText().toString() + "\n" + address.getText().toString());
                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(intent);

                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lname.setText("");
                fname.setText("");
                address.setText("");
                complaint.setText("");
            }
        });
        return vv;
    }

}

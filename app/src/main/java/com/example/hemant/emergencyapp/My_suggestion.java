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
public class My_suggestion extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vv= inflater.inflate(R.layout.my_suggestion,container,false);
        final EditText fbname = vv.findViewById(R.id.fbname);
        final EditText fbmsg = vv.findViewById(R.id.fbmsg);
        Button btsub = vv.findViewById(R.id.fbsub);
        Button btcncl = vv.findViewById(R.id.fbcncl);
        btsub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:")); // only email apps should handle this
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"hemantahuja.1016@gmail.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "Suggestion for Emergency App");
                intent.putExtra(Intent.EXTRA_TEXT, "From\t" + fbname.getText().toString() + "," + "\n\n" + fbmsg.getText().toString());
                if( (intent.resolveActivity(getActivity().getPackageManager()) != null))
                startActivity(intent);
            }
        });
        btcncl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            fbname.setText("");
                fbmsg.setText("");
            }
        });
        return vv;
    }

}

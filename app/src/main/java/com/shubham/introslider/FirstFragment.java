package com.shubham.introslider;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class FirstFragment extends Fragment {


    int bandera=1;
    int d1=0;

    public ImageView imageView2;
    public ImageView imageView3;
    public TextView textView2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_first, container, false);


        imageView2 = root.findViewById(R.id.imageView2);
        textView2 = root.findViewById(R.id.textView2);
        imageView3 = root.findViewById(R.id.imageView3);

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLog = new Intent(root.getContext(), ShoppingActivity.class);
                startActivity(intentLog);
            }
        });

        imageView3.setOnClickListener(new View.OnClickListener() {
            String s2;
            @Override
            public void onClick(View view) {
                if (bandera == 1) {
                    String s1 = textView2.getText().toString();
                     d1 = Integer.parseInt(s1);
                    d1 = d1 + 1;


                    s2 = String.valueOf(d1);
                    textView2.setText(s2);
                    bandera = bandera + 1;
                }
                else if(bandera == 2){
                    d1 = d1-1;
                    s2 = String.valueOf(d1);
                    textView2.setText(s2);
                    bandera = 1;
                }
            }
        });


        return root;

    }

}
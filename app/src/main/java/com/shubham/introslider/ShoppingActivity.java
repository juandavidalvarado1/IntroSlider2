package com.shubham.introslider;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

public class ShoppingActivity extends AppCompatActivity {
    public Button btncomprar;
    public RadioButton rbbitcoin;
    public RadioButton rbcreditcard;
    public RadioButton rbpaypal;
    public RadioButton rbefecty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);
        btncomprar = findViewById(R.id.btncomprar);
        rbbitcoin = findViewById(R.id.rbbitcoin);
        rbcreditcard = findViewById(R.id.rbcreditcard);
        rbpaypal = findViewById(R.id.rbpaypal);
        rbefecty = findViewById(R.id.rbefecty);


        btncomprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rbbitcoin.isChecked()==true | rbcreditcard.isChecked()==true | rbpaypal
                        .isChecked()==true | rbefecty.isChecked()==true) {
                    Toast.makeText(getBaseContext(), "Compra realizada", Toast.LENGTH_SHORT).show();
                }
                // Toast.makeText(getBaseContext(), "Compra realizada", Toast.LENGTH_SHORT).show();
            }
        });

    }



}
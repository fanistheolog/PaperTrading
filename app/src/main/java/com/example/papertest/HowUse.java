package com.example.papertest;


import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;



public class HowUse extends AppCompatActivity {
    int number = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.use_how);
       Button before =(Button)findViewById(R.id.before);
       Button next =(Button)findViewById(R.id.next);
        FrameLayout frame =(FrameLayout)findViewById(R.id.frame);
        getSupportActionBar().hide();

        before.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (number == 1){
                    Intent myIntent = new Intent(HowUse.this, PageFirst.class);
                    HowUse.this.startActivity(myIntent);
                } else if (number == 2){
                    frame.setBackgroundResource(R.drawable.use);
                    number = number -1;
                } else if (number == 3){
                    frame.setBackgroundResource(R.drawable.use2);
                    number = number -1;
                } else if (number == 4){
                    frame.setBackgroundResource(R.drawable.use3);
                    number = number -1;
                } else if (number == 5){
                    frame.setBackgroundResource(R.drawable.use4);
                    number = number -1;
                } else if (number == 6){
                    frame.setBackgroundResource(R.drawable.use5);
                    number = number -1;
                } else if (number == 7){
                    frame.setBackgroundResource(R.drawable.use6);
                    number = number -1;

                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (number == 1){
                    frame.setBackgroundResource(R.drawable.use2);
                    number = number +1;

                }else if (number == 2){
                    frame.setBackgroundResource(R.drawable.use3);
                    number = number +1;
                }else if (number == 3){
                    frame.setBackgroundResource(R.drawable.use4);
                    number = number +1;
                } else if (number == 4){
                    frame.setBackgroundResource(R.drawable.use5);
                    number = number +1;
                } else if (number == 5){
                    frame.setBackgroundResource(R.drawable.use6);
                    number = number +1;
                }else if (number == 6){
                    frame.setBackgroundResource(R.drawable.use7);
                    number = number +1;
                } else if (number == 7){
                    number = number +1;
                    Intent myIntent = new Intent(HowUse.this, PageFirst.class);
                    HowUse.this.startActivity(myIntent);

                }
            }
        });






    }
}

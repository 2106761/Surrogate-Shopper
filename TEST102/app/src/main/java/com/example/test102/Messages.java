package com.example.test102;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.toolbox.Volley;

public class Messages extends AppCompatActivity {
    Button back;
    TextView message;
    String name = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        back=(Button)findViewById(R.id.button2);
        message=(TextView) findViewById(R.id.textView5);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent=getIntent();
        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        //storing the size
        int width=dm.widthPixels;
        int height=dm.heightPixels;
        //set size of the desired popup relative to % of current screen
        getWindow().setLayout((int)(width*.8),(int)(height*.6));


        name =intent.getStringExtra("name");
        //  if(name =="No messages"){
        //  message.setText("No messages yet");
        // }else {
        message.setText(name);
        // }
    }
}
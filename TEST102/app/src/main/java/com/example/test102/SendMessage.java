package com.example.test102;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class SendMessage extends AppCompatActivity {
    private Button Send;
    private TextView Text;
    private String mess;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);
        //getting current screen size
        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        //storing the size
        int width=dm.widthPixels;
        int height=dm.heightPixels;
        //set size of the desired popup relative to % of current screen
        getWindow().setLayout((int)(width*.8),(int)(height*.6));
        mess ="Message sent";
        Send = (Button) findViewById(R.id.button);
        Text = (TextView) findViewById(R.id.textView3);

        Text.setText("Send message of appreciation in advance");
        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Text.setText(mess);
            }
        });

    }

}
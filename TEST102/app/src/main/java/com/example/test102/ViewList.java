package com.example.test102;

import android.database.Cursor;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class ViewList extends AppCompatActivity {
    DatabaseHelper MyDBS;
    Button Add,Delete;
    ArrayList<User> userList;
    ListView listView;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list);
        Add = (Button)findViewById(R.id.btnAdd);
        Delete = (Button)findViewById(R.id.btnDelete);
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //getting current screen size
        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        //storing the size
        int width=dm.widthPixels;
        int height=dm.heightPixels;
        //set size of the desired popup relative to % of current screen
        getWindow().setLayout((int)(width*.8),(int)(height*.6));

        MyDBS = new DatabaseHelper(this);
        //populate an ArrayList<String> from the database and then view it
        userList = new ArrayList<>();

        Cursor data = MyDBS.getListContents();

        if(data.getCount() == 0){
            Toast.makeText(this, "There are no contents in this list!",Toast.LENGTH_LONG).show();
        }
        else {
            int i=0;
            while(data.moveToNext())
            {
                user = new User(data.getString(1), data.getString(2), data.getString(3));
                userList.add(i, user);

                //System.out.println(data.getString(1) + " " + data.getString(2) + " " + data.getString(3));
                //System.out.println(userList.get(i).getItemName());
                i++;

            }
            final ListAdapter adapter =  new ListAdapter(this,R.layout.list_adapter_view, userList);
            listView = findViewById(R.id.lvItems);
            listView.setAdapter(adapter);

        }

        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDBS.deleteAll();
                finish();
            }
        });

    }
}


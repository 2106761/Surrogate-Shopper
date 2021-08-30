package com.example.test102;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestorActivity extends AppCompatActivity {
    //  private static final String URL_Results = "https://lamp.ms.wits.ac.za/home/s2280727/notification.php";

    MainActivity a;
    private EditText ItemName,ItemSize,ItemPrice;
    private Button ViewList;
    private Button AddList;
    private Button Request;
    private Button Logout;
    private Button Notification;


    private String list="";
    DatabaseHelper MyDBS;
    private RequestQueue requestQueue;
    private static final String URL = "https://lamp.ms.wits.ac.za/home/s2280727/request.php";
    private StringRequest request;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requestor);
        //MyDBS.Del();
        Notification=(Button)findViewById(R.id.btnNot);
        ViewList = (Button) findViewById(R.id.btnViewList);
        AddList = (Button) findViewById(R.id.btnAddList);
        Request = (Button) findViewById(R.id.btnRequest);
        Logout = (Button) findViewById(R.id.btnLogout);
        ItemName = (EditText) findViewById(R.id.etItemName);
        ItemSize = (EditText) findViewById(R.id.etItemSize);
        ItemPrice = (EditText) findViewById(R.id.etPrice);
        MyDBS = new DatabaseHelper(this);
        requestQueue = Volley.newRequestQueue(this);
        Logout=(Button)findViewById(R.id.btnLogout);



        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        AddList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEntry1 = ItemName.getText().toString();
                String newEntry2 = ItemSize.getText().toString();
                String newEntry3 = ItemPrice.getText().toString();
                if (ItemName.length() != 0 && ItemSize.length() != 0 && ItemPrice.length() != 0) {
                    AddData(newEntry1, newEntry2, newEntry3);
                    ItemName.setText("");
                    ItemSize.setText("");
                    ItemPrice.setText("");
                } else {
                    Toast.makeText(RequestorActivity.this, "You must put something in the text field!", Toast.LENGTH_LONG).show();
                }
            }
        });

        Request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor data = MyDBS.getListContents();
                if(data.getCount() == 0){
                    //Toast.makeText(this, "There are no contents in this list!",Toast.LENGTH_LONG).show();
                    list="No items in the list";
                }else {
                    while(data.moveToNext())
                    {
                        // user = new User(data.getString(1), data.getString(2), data.getString(3));
                        //  userList.add(i, user);
                        //System.out.println(data.getString(1) + " " + data.getString(2) + " " + data.getString(3));
                        //System.out.println(userList.get(i).getItemName());
                        //i++;
                        list += data.getString(1)+"("+data.getString(2)+","+data.getString(3)+")"+" ";

                    }

                    request = new StringRequest(com.android.volley.Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if(jsonObject.names().get(0).equals("success")) {

                                    Toast.makeText(getApplicationContext(), "SUCCESS " + jsonObject.getString("success"), Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), RequestorActivity.class));
                                }else{
                                    Toast.makeText(getApplicationContext(), "ERROR " + jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }


                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String,String> hashMap = new HashMap<String, String>();
                            Intent intent=getIntent();
                            String hi=intent.getStringExtra("my");

                            hashMap.put("email",hi);

                            hashMap.put("list",list);

                            return hashMap;
                        }
                    };

                    requestQueue.add(request);

                    MyDBS.Del();
                }
            }
        });

        ViewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RequestorActivity.this, ViewList.class);
                startActivity(intent);
            }
        });

        Notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RequestorActivity.this, SendMessage.class);
                startActivity(intent);

            }
        });

    }


    public void AddData(String newEntry1,String newEntry2,String newEntry3) {

        boolean insertData = MyDBS.AddData(newEntry1,newEntry2,newEntry3);

        if(insertData == true){
            Toast.makeText(this, "Data Successfully Inserted!", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "Something went wrong :(.", Toast.LENGTH_LONG).show();
        }


        //Move to view activity
        ViewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ViewList.class));
            }
        });
        Notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RequestorActivity.this, SendMessage.class);
                startActivity(intent);

            }
        });

    }

    @Override
    protected void onStop() {
        MyDBS.Del();
        super.onStop();
    }

}


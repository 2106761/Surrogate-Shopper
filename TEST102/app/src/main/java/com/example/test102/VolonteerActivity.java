package com.example.test102;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VolonteerActivity extends AppCompatActivity {

    //this is the JSON Data URL
    private static final String URL_Results = "https://lamp.ms.wits.ac.za/home/s2280727/info.php";
    private Button Logout,Message;
    //a list to store all the requests
    List<RequestClass> requestList;

    //the recyclerview object
    RecyclerView recyclerView;


    //Declare the RecycleView ClickListener
    private RequestAdapter.RecycleViewClickListener listener;
    private String email,userName="" ;
    // private String message="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volonteer);
        Logout = (Button)findViewById(R.id.btnLogOut);
        Message = (Button)findViewById(R.id.btnMessage);

        //Onclick
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });


        //getting the recyclerview from xml
        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initializing the requestlist
        requestList = new ArrayList<>();

        //this method will fetch and parse json
        //to display it in recyclerview
        loadRequests();

        Message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveTo = new Intent(getApplicationContext(),Messages.class);
                //Getting name and passing it to View Activity
                if(userName==""){
                    moveTo.putExtra("name","No messages");
                    startActivity(moveTo);
                }else{
                    moveTo.putExtra("name",userName+" thanks you");
                    startActivity(moveTo);
                }

            }
        });

    }

    private void loadRequests() {

        /*
         * Creating a String Request
         * The request type is GET defined by first parameter
         * The URL is defined in the second parameter
         * Then we have a Response Listener and a Error Listener
         * In response listener we will get the JSON response as a String
         * */
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_Results,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting order object from json array
                                JSONObject order = array.getJSONObject(i);

                                //adding the order to request list
                                requestList.add(new RequestClass(
                                        order.getInt("id"),
                                        order.getString("name"),
                                        order.getString("location"),
                                        order.getString("list")
                                ));
                            }

                            //creating adapter object and setting it to recyclerview,pass the listener for our adapter
                            setOnClickListener();
                            RequestAdapter adapter = new RequestAdapter(VolonteerActivity.this, requestList,listener);
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }

    private void setOnClickListener() {
        listener = new RequestAdapter.RecycleViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent extras = getIntent();
                email = extras.getStringExtra("email");
                userName = requestList.get(position).getName();
                //Move to View list
                Intent moveTo = new Intent(getApplicationContext(),ViewActivity.class);
                //Getting name and passing it to View Activity
                moveTo.putExtra("name",userName);
                moveTo.putExtra("email",email);
                startActivity(moveTo);
            }
        };
    }
}
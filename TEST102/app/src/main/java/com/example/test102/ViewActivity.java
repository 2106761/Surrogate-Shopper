package com.example.test102;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ViewActivity extends AppCompatActivity {
    private Button Accept, Decline;
    private TextView ViewRequest;
    private RequestQueue requestQueue;
    private static final String URL = "https://lamp.ms.wits.ac.za/home/s2280727/accept.php";
    private StringRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        Accept = findViewById(R.id.btnAccept);
        Decline = findViewById(R.id.btnDecline);
        ViewRequest = findViewById(R.id.tvRequest);
        requestQueue = Volley.newRequestQueue(this);


        //New string store the name from intent
        String name = "";
        Intent intent=getIntent();
        name =intent.getStringExtra("name");
        ViewRequest.setText(name );

        Accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                request = new StringRequest(com.android.volley.Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if (jsonObject.names().get(0).equals("success")) {

                                Toast.makeText(getApplicationContext(), "SUCCESS " + jsonObject.getString("success"), Toast.LENGTH_SHORT).show();
                                //startActivity(new Intent(getApplicationContext(),VolonteerActivity.class));
                                finish();
                            }
                            else{
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

                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> hashMap = new HashMap<String, String>();
                        Intent intent=getIntent();
                        String email =intent.getStringExtra("email");
                        String name =intent.getStringExtra("name");
                            hashMap.put("name", name);

                            hashMap.put("email", email);

                        return hashMap;
                    }
                };

                requestQueue.add(request);

            }

        });

        Decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
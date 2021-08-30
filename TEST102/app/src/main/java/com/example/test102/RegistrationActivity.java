package com.example.test102;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {
    TextView mLogin;
    private EditText email,password,name,location;
    private Button mREGISTER;
    private RequestQueue requestQueue;
    private static final String URL = "https://lamp.ms.wits.ac.za/home/s2280727/registration.php";
    private static final String URL2 = "https://lamp.ms.wits.ac.za/home/s2280727/volunteer_register.php";
    private StringRequest request;
    private RadioButton radVolunteer,radRequester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration2);
        email = (EditText) findViewById(R.id.edtEmail);
        password = (EditText) findViewById(R.id.edtPassword);
        mREGISTER = (Button) findViewById(R.id.btnRegister);
        name = (EditText) findViewById(R.id.edtUsername);
        location = (EditText) findViewById(R.id.etLocation);
        requestQueue = Volley.newRequestQueue(this);
        mREGISTER=(Button)findViewById(R.id.btnRegister);
        radVolunteer = (RadioButton) findViewById(R.id.radVolunteer);
        radRequester = (RadioButton)findViewById(R.id.radRequester);

        mREGISTER.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (radRequester .isChecked()) {
                    request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.names().get(0).equals("success")) {
                                    Toast.makeText(getApplicationContext(), "" + jsonObject.getString("success"), Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));


                                } else if (jsonObject.names().get(0).equals("incomplete")) {
                                    Toast.makeText(getApplicationContext(), "Error: " + jsonObject.getString("incomplete"), Toast.LENGTH_SHORT).show();
                                } else {

                                    Toast.makeText(getApplicationContext(), "Error " + jsonObject.getString("exist"), Toast.LENGTH_SHORT).show();

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
                            hashMap.put("email", email.getText().toString());
                            hashMap.put("password", password.getText().toString());
                            hashMap.put("location", location.getText().toString());
                            hashMap.put("name", name.getText().toString());


                            return hashMap;
                        }
                    };

                    requestQueue.add(request);
                }
                else if(radVolunteer.isChecked())
                {
                    request = new StringRequest(Request.Method.POST, URL2, new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.names().get(0).equals("success")) {
                                    Toast.makeText(getApplicationContext(), "" + jsonObject.getString("success"), Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));


                                } else if (jsonObject.names().get(0).equals("incomplete")) {
                                    Toast.makeText(getApplicationContext(), "Error: " + jsonObject.getString("incomplete"), Toast.LENGTH_SHORT).show();
                                } else {

                                    Toast.makeText(getApplicationContext(), "Error " + jsonObject.getString("exist"), Toast.LENGTH_SHORT).show();

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
                            hashMap.put("email", email.getText().toString());
                            hashMap.put("password", password.getText().toString());
                            hashMap.put("location", location.getText().toString());
                            hashMap.put("name", name.getText().toString());


                            return hashMap;
                        }
                    };

                    requestQueue.add(request);
                }

            }
        });



        mLogin=(TextView)findViewById(R.id.txvLogin);
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent regint= new Intent(RegistrationActivity.this,MainActivity.class);
                startActivity(regint);
            }
        });

    }
}

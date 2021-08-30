package com.example.test102;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private TextInputLayout email,password;
    private Button mLogin;
    private RequestQueue requestQueue;
    private static final String URL = "https://lamp.ms.wits.ac.za/home/s2280727/user_control.php";
    private static final String URL2 = "https://lamp.ms.wits.ac.za/home/s2280727/volunteer_control.php";
    private StringRequest request;
    TextView mRegister;
    RadioButton mRequester;
    RadioButton mVolonteer;
    private String mEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRequester=(RadioButton)findViewById(R.id.radRequester);
        mVolonteer=(RadioButton)findViewById(R.id.radVolonteer);
        email = (TextInputLayout) findViewById(R.id.text_input_email);
        password = (TextInputLayout) findViewById(R.id.text_input_password);
        mLogin = (Button) findViewById(R.id.btnLogin);
        mRegister=(TextView)findViewById(R.id.txvRegister);
        requestQueue = Volley.newRequestQueue(this);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mRequester.isChecked()) {
                    request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.names().get(0).equals("success")) {

                                    Toast.makeText(getApplicationContext(), "SUCCESS " + jsonObject.getString("success"), Toast.LENGTH_SHORT).show();
                                    String Email = email.getEditText().getText().toString().trim();
                                    Intent moveTo = new Intent(getApplicationContext(), RequestorActivity.class);
                                    mEmail = email.getEditText().getText().toString().trim();
                                    moveTo.putExtra("my", Email);

                                    startActivity(moveTo);
                                }
                                else if (jsonObject.names().get(0).equals("incomplete")) {
                                    Toast.makeText(getApplicationContext(), "Error: " + jsonObject.getString("incomplete"), Toast.LENGTH_SHORT).show();
                                }
                                else {

                                    Toast.makeText(getApplicationContext(), "Error: " + jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
                                }

                            }
                            catch (JSONException e) {
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
                            hashMap.put("email", email.getEditText().getText().toString().trim());

                            hashMap.put("password", password.getEditText().getText().toString().trim());

                            return hashMap;
                        }
                    };

                    requestQueue.add(request);
                }

                else if (mVolonteer.isChecked()) {
                    request = new StringRequest(Request.Method.POST, URL2, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.names().get(0).equals("success")) {
                                    Toast.makeText(getApplicationContext(), "SUCCESS " + jsonObject.getString("success"), Toast.LENGTH_SHORT).show();
                                    String Email = email.getEditText().getText().toString().trim();
                                    Intent moveTo = new Intent(getApplicationContext(),VolonteerActivity.class);
                                    mEmail = email.getEditText().getText().toString().trim();
                                    moveTo.putExtra("email", Email);
                                    startActivity(moveTo);
                                }
                                else if (jsonObject.names().get(0).equals("incomplete"))
                                {
                                    Toast.makeText(getApplicationContext(), "Error: " + jsonObject.getString("incomplete"), Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(), "Error: " + jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
                                }

                            }
                            catch (JSONException e)
                            {
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
                            hashMap.put("email", email.getEditText().getText().toString().trim());

                            hashMap.put("password", password.getEditText().getText().toString().trim());

                            return hashMap;
                        }
                    };

                    requestQueue.add(request);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Select role ", Toast.LENGTH_SHORT).show();
                }
            }

        });



        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent regint = new Intent(MainActivity.this, RegistrationActivity.class);
                    startActivity(regint);

            }
        });
    }/*
    String getEmail(){
        return mEmail;

    }*/

}

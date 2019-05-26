package com.example.rifat.hellodoctor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class signupP extends AppCompatActivity {
    EditText usrname,usrid,loca,phn,gen,pass,con_pass;
    Button snup;
    String[] usrnams;
    Spinner spnn;
    String[] gender;
    String usernm="",userId="",locatn="",phne="",gendr="",passwrd="",confrm_pass="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_p);

        usrname=(EditText) findViewById(R.id.usernameSP);
        usrid=(EditText) findViewById(R.id.useridSP);
        loca=(EditText) findViewById(R.id.locationSP);
        phn=(EditText) findViewById(R.id.phnSP);
        gen=(EditText) findViewById(R.id.genderSP);
        pass=(EditText) findViewById(R.id.passSP);
        con_pass=(EditText) findViewById(R.id.conpassSP);
        spnn=(Spinner) findViewById(R.id.patient_gender_spinner);

        gender=getResources().getStringArray(R.array.gender_names);

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_sample_view_time,R.id.spinner_times,gender);
        spnn.setAdapter(adapter);

        snup=(Button) findViewById(R.id.sign_up_Sp);

        spnn.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    gen.setText(spnn.getSelectedItem().toString());
                }
                return false;
            }
        });



        fetchingData();

        snup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "http://192.168.43.171:1234/sinP1.php";



                StringRequest sq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
                    protected Map<String, String > getParams(){
                        Map<String, String> parr = new HashMap<String, String>();

                        parr.put("username", usrname.getText().toString());
                        parr.put("userid", usrid.getText().toString());
                        parr.put("location", loca.getText().toString());
                        parr.put("phone", phn.getText().toString());
                        parr.put("gender", gen.getText().toString());
                        parr.put("password", pass.getText().toString());
                        parr.put("conpassword", con_pass.getText().toString());


                        return parr;


                    }

                };
                usernm=usrname.getText().toString();
                userId=usrid.getText().toString();
                locatn=loca.getText().toString();
                phne=phn.getText().toString();
                gendr=gen.getText().toString();
                passwrd=pass.getText().toString();
                confrm_pass=con_pass.getText().toString();

                Boolean flag1=false,flag2=false;



                if(usernm.equals("")||userId.equals("")||locatn.equals("")||phne.equals("")||gendr.equals("")||passwrd.equals("")||confrm_pass.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Fill All The Information",Toast.LENGTH_SHORT).show();
                }
                else if(passwrd.equals(confrm_pass)==false)
                {
                    Toast.makeText(getApplicationContext(),"Re-enter correct password",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    flag1=true;
                    for(int kk=0;kk<usrnams.length;kk++)
                    {
                        if(usrnams[kk].equals(userId))
                        {
                            flag2=true;
                            Toast.makeText(getApplicationContext(),"UserId Already exist",Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                }

                if(flag1==true && flag2==false) {


                    com.example.rifat.hellodoctor.AppController.getInstance().addToRequestQueue(sq);
                    Toast.makeText(getApplicationContext(), "Sign Up successfully, login please", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(),loginP.class));
                }
                else
                {

                }

            }

        });
    }

    void fetchingData(){


        String myURL = "http://192.168.43.171:1234/logP1.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(myURL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                final String[] Pat_id = new String[response.length()];
                final String[] Pat_password = new String[response.length()];
                usrnams=new String[response.length()];
                //final String[] news_time = new String[response.length()];

                for (int i =0; i < response.length(); i++){

                    try {

                        JSONObject jsonObject = (JSONObject) response.get(i);
                        Pat_id[i] = jsonObject.getString("Pat_id");
                        usrnams[i]=Pat_id[i];
                        Pat_password[i] = jsonObject.getString("Pat_password");
                        //news_time[i] = jsonObject.getString("time");


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Volley Log", error);
            }
        });


        com.example.rifat.hellodoctor.AppController.getInstance().addToRequestQueue(jsonArrayRequest);
        //Toast.makeText(getApplicationContext(), "Enter Valid name and Password", Toast.LENGTH_SHORT).show();

    }
}

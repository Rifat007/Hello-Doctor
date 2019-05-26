package com.example.rifat.hellodoctor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class signupD extends AppCompatActivity {
    EditText usrname,usrid,cata,phn,pass,con_pass;
    Button snup;
    String[] usrnams;
    String usernm="",userId="",catagry="",phne="",passwrd="",confrm_pass="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_d);

        usrname=(EditText) findViewById(R.id.usernameSD);
        usrid=(EditText) findViewById(R.id.useridSD);
        cata=(EditText) findViewById(R.id.cataSD);
        phn=(EditText) findViewById(R.id.phnSD);
        pass=(EditText) findViewById(R.id.passSD);
        con_pass=(EditText) findViewById(R.id.conpassSD);

        snup=(Button) findViewById(R.id.sign_up_SD);

        fetchingData();

        snup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "http://192.168.43.171:1234/sinD1.php";


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
                        parr.put("catagory", cata.getText().toString());
                        parr.put("phone", phn.getText().toString());
                        parr.put("password", pass.getText().toString());
                        parr.put("conpassword", con_pass.getText().toString());


                        return parr;


                    }

                };
                usernm=usrname.getText().toString();
                userId=usrid.getText().toString();
                catagry=cata.getText().toString();
                phne=phn.getText().toString();
                passwrd=pass.getText().toString();
                confrm_pass=con_pass.getText().toString();

                Boolean flag1=false,flag2=false;



                if(usernm.equals("")||userId.equals("")||catagry.equals("")||phne.equals("")||passwrd.equals("")||confrm_pass.equals(""))
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
                    startActivity(new Intent(getApplicationContext(),loginD.class));
                }
                else
                {

                }

            }

        });


    }

    void fetchingData(){


        String myURL = "http://192.168.43.171:1234/logD1.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(myURL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                final String[] Doc_id = new String[response.length()];
                final String[] Doc_password = new String[response.length()];
                usrnams=new String[response.length()];
                //final String[] news_time = new String[response.length()];

                for (int i =0; i < response.length(); i++){

                    try {

                        JSONObject jsonObject = (JSONObject) response.get(i);
                        Doc_id[i] = jsonObject.getString("Doc_id");
                        usrnams[i]=Doc_id[i];
                        Doc_password[i] = jsonObject.getString("Doc_Password");
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
        //Toast.makeText(getApplicationContext(), "Enter Valid Name and password", Toast.LENGTH_SHORT).show();

    }


}

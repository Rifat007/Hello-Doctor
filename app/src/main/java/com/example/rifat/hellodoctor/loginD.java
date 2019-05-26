package com.example.rifat.hellodoctor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class loginD extends AppCompatActivity {
    EditText usrname,passw;
    Button lgin,new_acc;
    String usrnm="",passwrd="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_d);

        usrname=(EditText) findViewById(R.id.usernameD);
        passw=(EditText) findViewById(R.id.passD);

        lgin=(Button) findViewById(R.id.login_buttonD);
        new_acc=(Button) findViewById(R.id.new_idD);

        fetchingData();

        new_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),signupD.class));
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
                final String[] Doc_name=new String[response.length()];
                final String[] Doc_typ=new String[response.length()];
                //final String[] news_time = new String[response.length()];

                for (int i =0; i < response.length(); i++){

                    try {

                        JSONObject jsonObject = (JSONObject) response.get(i);
                        Doc_id[i] = jsonObject.getString("Doc_id");
                        Doc_password[i] = jsonObject.getString("Doc_Password");
                        Doc_name[i]=jsonObject.getString("Doc_Name");
                        Doc_typ[i]=jsonObject.getString("Doc_type");

                        //news_time[i] = jsonObject.getString("time");


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                //lv.setAdapter(new ArrayAdapter(getApplicationContext(), R.layout.mylistview, R.id.textviewforlist, news_title));

                //lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                //  @Override
                //           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //      Intent intent = new Intent(MainActivity.this, Details.class);
                //    intent.putExtra("MyTITLE", news_title[position]);
                //  intent.putExtra("MyNEWS", news_detail[position]);
                //intent.putExtra("MyTime", news_time[position]);
                //startActivity(intent);

                //    }
                // });

                lgin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        usrnm=usrname.getText().toString();
                        passwrd=passw.getText().toString();
                        Boolean flag=false;
                        int point=-1;

                        if(usrnm.equals("")==true)
                        {
                            Toast.makeText(getApplicationContext(),"Enter Username",Toast.LENGTH_SHORT).show();
                        }
                        else if(passwrd.equals("")==true)
                        {
                            Toast.makeText(getApplicationContext(),"Enter Password",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            for(int k=0;k<Doc_id.length;k++)
                            {
                                if(usrnm.equals(Doc_id[k])==true)
                                {
                                    if(passwrd.equals(Doc_password[k])==true)
                                    {
                                        //startActivity(new Intent(getApplicationContext(),Selection.class));
                                        point=k;
                                        flag=true;
                                        break;
                                    }
                                    else
                                    {
                                        Toast.makeText(getApplicationContext(),"Password Not matched",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                            if(flag==true)
                            {
                                Intent intnt=new Intent(getApplicationContext(),Doctor_Home.class);
                                intnt.putExtra("DOC_ID",Doc_id[point]);
                                intnt.putExtra("DOC_NAME",Doc_name[point]);
                                intnt.putExtra("DOC_CATAGORY",Doc_typ[point]);
                                startActivity(intnt);
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Username not found",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Volley Log", error);
            }
        });


        com.example.rifat.hellodoctor.AppController.getInstance().addToRequestQueue(jsonArrayRequest);
        Toast.makeText(getApplicationContext(), "Enter Valid Name and password", Toast.LENGTH_SHORT).show();

    }
}

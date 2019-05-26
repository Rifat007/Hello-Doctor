package com.example.rifat.hellodoctor;

import android.app.Notification;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
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

public class loginP extends AppCompatActivity {
    EditText usrname,passw;
    Button lgin,new_acc;
    String usrnm="",passwrd="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_p);

        usrname=(EditText) findViewById(R.id.username);
        passw=(EditText) findViewById(R.id.pass);

        lgin=(Button) findViewById(R.id.login_button);
        new_acc=(Button) findViewById(R.id.new_id);
        fetchingData();

        new_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),signupP.class));
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
                final String[] Pat_name=new String[response.length()];
                //final String[] news_time = new String[response.length()];

                for (int i =0; i < response.length(); i++){

                    try {

                        JSONObject jsonObject = (JSONObject) response.get(i);
                        Pat_id[i] = jsonObject.getString("Pat_id");
                        Pat_password[i] = jsonObject.getString("Pat_password");
                        Pat_name[i]=jsonObject.getString("Pat_Name");
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
                        int flag1=-1;

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
                            for(int k=0;k<Pat_id.length;k++)
                            {
                                if(usrnm.equals(Pat_id[k])==true)
                                {
                                    if(passwrd.equals(Pat_password[k])==true)
                                    {
                                        //startActivity(new Intent(getApplicationContext(),Selection.class));
                                        flag=true;
                                        flag1=k;
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
                                Intent inti=new Intent(getApplicationContext(),Navi.class);
                                inti.putExtra("PATIENT_NAME",Pat_name[flag1]);
                                inti.putExtra("PATIENT_ID",Pat_id[flag1]);
                                if(Pat_id[flag1].equals("ns001"))
                                {
                                    NotificationCompat.Builder np=new NotificationCompat.Builder(loginP.this)
                                            .setSmallIcon(R.drawable.notiii)
                                            .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.notiii))
                                            .setContentTitle("Schedule Cancellation")
                                            .setContentText("Next Schedule 27 Jan,2018. Thanks");

                                    np.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE);
                                    NotificationManagerCompat nmc=NotificationManagerCompat.from(loginP.this);
                                    nmc.notify(1,np.build());
                                }
                                startActivity(inti);
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
        Toast.makeText(getApplicationContext(), "Enter Valid name and Password", Toast.LENGTH_SHORT).show();

    }
}

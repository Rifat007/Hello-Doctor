package com.example.rifat.hellodoctor;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Doc_Manage_Schedule extends AppCompatActivity {
    EditText hosSele,dateSele,timeSele,room;
    Button datebutton,timebutton,addbutton;
    //DatePickerDialog dp;
    //TimePickerDialog tpd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc__manage__schedule);



        hosSele=(EditText) findViewById(R.id.hosSelect321);
        dateSele=(EditText) findViewById(R.id.DateDoc321);
        timeSele=(EditText) findViewById(R.id.hourSelectDoc321);
        room=(EditText) findViewById(R.id.room321);


        datebutton=(Button) findViewById(R.id.selectDateDoc321);
        timebutton=(Button) findViewById(R.id.selectHourDoc321);
        addbutton=(Button) findViewById(R.id.addScheduleDoc321);

        final String nm=getIntent().getStringExtra("DOC_NAME");
        final String idd=getIntent().getStringExtra("DOC_ID");
        final String cata=getIntent().getStringExtra("DOC_CATAGORY");

        datebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePicker datePicker=new DatePicker(Doc_Manage_Schedule.this);
                int currentDay=datePicker.getDayOfMonth();
                int currentMonth=(datePicker.getMonth())+1;
                int currentYear=datePicker.getYear();


                DatePickerDialog dp=new DatePickerDialog(Doc_Manage_Schedule.this,

                        new DatePickerDialog.OnDateSetListener(){


                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                                int d=i1+1;
                                dateSele.setText(i2+"/"+d+"/"+i);

                            }
                        },currentYear,currentMonth,currentDay);

                dp.show();
            }
        });

        timebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePicker  tp=new TimePicker(Doc_Manage_Schedule.this);
                int currHour=tp.getCurrentHour();
                int currMin=tp.getCurrentMinute();


                TimePickerDialog tpd=new TimePickerDialog(Doc_Manage_Schedule.this,

                        new TimePickerDialog.OnTimeSetListener()
                        {
                            @Override
                            public void onTimeSet(TimePicker view,int hour,int min)
                            {
                                timeSele.setText(hour+" : "+min);
                            }
                        },currHour,currMin,false);
                tpd.show();
            }
        });

        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://192.168.43.171:1234/doc_scheduling.php";



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

                        parr.put("myname", getIntent().getStringExtra("DOC_NAME"));
                        parr.put("myhosname", hosSele.getText().toString());
                        parr.put("mydate", dateSele.getText().toString());
                        parr.put("mytime",timeSele.getText().toString());
                        parr.put("mycata",getIntent().getStringExtra("DOC_CATAGORY"));
                        parr.put("myroom",room.getText().toString());

                        return parr;

                    }

                };



                com.example.rifat.hellodoctor.AppController.getInstance().addToRequestQueue(sq);
                Toast.makeText(getApplicationContext(), "Schedule Added....", Toast.LENGTH_LONG).show();

            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater mf=getMenuInflater();
        mf.inflate(R.menu.menu_layout,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.lgoutId)
        {
            startActivity(new Intent(getApplicationContext(),DocPatSelection.class));
        }
        else if(item.getItemId()==R.id.homeeId)
        {
            startActivity(new Intent(getApplicationContext(),Doctor_Home.class));
        }
        return super.onOptionsItemSelected(item);
    }
}

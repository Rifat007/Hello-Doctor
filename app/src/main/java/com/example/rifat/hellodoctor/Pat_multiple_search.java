package com.example.rifat.hellodoctor;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

public class Pat_multiple_search extends AppCompatActivity {
    EditText selPalce,selDate,selCata;
    Button dt,submit;
    Spinner spn;
    DatePickerDialog dpd;

    String[] catas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pat_multiple_search);

        selPalce=(EditText) findViewById(R.id.place_select_123);
        selDate=(EditText) findViewById(R.id.select_date_123);

        selCata=(EditText) findViewById(R.id.select_catagory_123);


        dt=(Button) findViewById(R.id.select_datebutton_123);
        submit=(Button) findViewById(R.id.select_submit_123);

        spn=(Spinner) findViewById(R.id.select_spinner_123);

        catas=getResources().getStringArray(R.array.Catagories);

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_sample_view_time,R.id.spinner_times,catas);
        spn.setAdapter(adapter);

        spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selCata.setText(spn.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        dt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePicker dpik=new DatePicker(Pat_multiple_search.this);
                int currentDay=dpik.getDayOfMonth();
                int currentMonth=(dpik.getMonth())+1;
                int currentYear=dpik.getYear();


                dpd=new DatePickerDialog(Pat_multiple_search.this,

                        new DatePickerDialog.OnDateSetListener(){


                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                                int d=i1+1;
                                selDate.setText(i2+"/"+d+"/"+i);

                            }
                        },currentYear,currentMonth,currentDay);

                dpd.show();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String plc=selPalce.getText().toString();
                String cat=selCata.getText().toString();
                String dat=selDate.getText().toString();

                Intent inti=new Intent(getApplicationContext(),MapsActivity.class);
                inti.putExtra("PATIENT_NAME",getIntent().getStringExtra("PATIENT_NAME"));
                inti.putExtra("PATIENT_ID",getIntent().getStringExtra("PATIENT_ID"));
                inti.putExtra("PLACE",plc);
                inti.putExtra("CATAGORY",cat);
                inti.putExtra("DATE",dat);

                startActivity(inti);
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
        if(item.getItemId()==R.id.homeeId)
        {
            startActivity(new Intent(getApplicationContext(),Navi.class));
        }
        return super.onOptionsItemSelected(item);
    }
}

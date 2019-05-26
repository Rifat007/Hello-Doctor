package com.example.rifat.hellodoctor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class Doctor_Home extends AppCompatActivity {
    Button view_pat,manage_sche,blg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor__home);

        view_pat=(Button) findViewById(R.id.view_pat222);

        manage_sche=(Button) findViewById(R.id.namage_schedule) ;
        blg=(Button) findViewById(R.id.blog);

        final String nm=getIntent().getStringExtra("DOC_NAME");
        final String idd=getIntent().getStringExtra("DOC_ID");
        final String cata=getIntent().getStringExtra("DOC_CATAGORY");



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        manage_sche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inn=new Intent(getApplicationContext(),Doc_Manage_Schedule.class);
                inn.putExtra("DOC_NAME",nm);
                inn.putExtra("DOC_ID",idd);
                inn.putExtra("DOC_CATAGORY",cata);
                startActivity(inn);
            }
        });

        blg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(getApplicationContext(),Edit_Blog.class);
                in.putExtra("DOC_NAME",nm);
                startActivity(in);
            }
        });
        view_pat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inti=new Intent(getApplicationContext(),Doc_View_Pat.class);
                inti.putExtra("DOC_NAME",nm);
                inti.putExtra("DOC_ID",idd);
                startActivity(inti);
            }
        });


    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),Doctor_Home.class));
        super.onBackPressed();
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
        return super.onOptionsItemSelected(item);
    }
}

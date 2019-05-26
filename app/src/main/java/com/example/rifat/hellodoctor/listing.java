package com.example.rifat.hellodoctor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

public class listing extends AppCompatActivity {
    ListView lv;
    ArrayAdapter<String> adapter;
    String catagory_name,datee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing);

        catagory_name=getIntent().getStringExtra("CATAGORY");
        datee=getIntent().getStringExtra("DATE");

        lv=(ListView) findViewById(R.id.mylist);

        adapter= new ArrayAdapter<String>(getApplicationContext(), R.layout.lviwe, R.id.textviewforlist, MapsActivity.ara);

        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intn=new Intent(getApplicationContext(),Doctors_Show_List.class);
                intn.putExtra("PATIENT_NAME",getIntent().getStringExtra("PATIENT_NAME"));
                intn.putExtra("PATIENT_ID",getIntent().getStringExtra("PATIENT_ID"));
                intn.putExtra("HospitalAddress",adapter.getItem(position));
                intn.putExtra("CATAGORY",catagory_name);
                intn.putExtra("DATE",datee);
                startActivity(intn);
            }
        });
        //Toast.makeText(getApplicationContext(), catagory_name+datee, Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.search_layout,menu);


        MenuItem menuItem=menu.findItem(R.id.searchViewId);
        SearchView searchView=(SearchView) menuItem.getActionView();


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                adapter.getFilter().filter(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}


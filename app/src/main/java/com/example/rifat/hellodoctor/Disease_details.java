package com.example.rifat.hellodoctor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.rifat.hellodoctor.R;


public class Disease_details extends AppCompatActivity {
    TextView disesase,symtoms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease_details);

        disesase=(TextView) findViewById(R.id.mydisease);
        symtoms=(TextView) findViewById(R.id.mysymtoms);

        String _dis=getIntent().getStringExtra("MyDISEASE");
        String _sym=getIntent().getStringExtra("MySYMTOMS");

        disesase.setText(_dis+"\n\n");
        symtoms.setText(_sym);
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

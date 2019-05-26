package com.example.rifat.hellodoctor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.rifat.hellodoctor.R;

public class Tips_Details extends AppCompatActivity {
    TextView Heading,author,details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips__details);

        Heading=(TextView) findViewById(R.id.bloggtitle);
        author=(TextView) findViewById(R.id.bloggname);
        details=(TextView) findViewById(R.id.bloggdetails);

        String _headd=getIntent().getStringExtra("MyTITLE");
        String _namee=getIntent().getStringExtra("MyNAME");
        String _details=getIntent().getStringExtra("MyBLOG");

        Heading.setText(_headd);
        author.setText(_namee+"\n\n");
        details.setText(_details);
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

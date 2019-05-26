package com.example.rifat.hellodoctor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.rifat.hellodoctor.AppController;
import com.example.rifat.hellodoctor.R;

import java.util.HashMap;
import java.util.Map;

public class Edit_Blog extends AppCompatActivity {
    EditText blg_title,blg_details;
    Button submt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit__blog);

        blg_title=(EditText) findViewById(R.id.blog_title);
        blg_details=(EditText) findViewById(R.id.blog_details);
        submt=(Button) findViewById(R.id.submit_blog);

        submt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "http://192.168.43.171:1234/docblog.php";




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

                        parr.put("mytitle", blg_title.getText().toString());
                        parr.put("myname", getIntent().getStringExtra("DOC_NAME"));
                        parr.put("mynews", blg_details.getText().toString());

                        return parr;

                    }

                };



                com.example.rifat.hellodoctor.AppController.getInstance().addToRequestQueue(sq);
                Toast.makeText(getApplicationContext(), "Blog published Successfully!", Toast.LENGTH_LONG).show();

            }

        });
    }
}

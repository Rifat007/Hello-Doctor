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
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Show_tips_title extends AppCompatActivity {
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_tips_title);

        lv=(ListView) findViewById(R.id.tips_list);
        fetchingData();

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

    void fetchingData(){


        String myURL = "http://192.168.43.171:1234/hTips.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(myURL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                final String[] news_title = new String[response.length()];
                final String[] news_detail = new String[response.length()];
                final String[] news_time = new String[response.length()];
                final String[] title_detail=new String[response.length()];

                for (int i =0; i < response.length(); i++){

                    try {

                        JSONObject jsonObject = (JSONObject) response.get(i);
                        news_title[i] = jsonObject.getString("Heading");
                        news_detail[i] = jsonObject.getString("author_name");
                        title_detail[i]=news_title[i]+"\n"+""+news_detail[i];
                        news_time[i] = jsonObject.getString("Tips");


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                lv.setAdapter(new ArrayAdapter(getApplicationContext(), R.layout.lview_tips, R.id.tipss, title_detail));

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(Show_tips_title.this, Tips_Details.class);
                        intent.putExtra("MyTITLE", news_title[position]);
                        intent.putExtra("MyNAME", news_detail[position]);
                        intent.putExtra("MyBLOG", news_time[position]);
                        startActivity(intent);

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
        Toast.makeText(getApplicationContext(), "Data Loaded Successfully!", Toast.LENGTH_SHORT).show();

    }


}

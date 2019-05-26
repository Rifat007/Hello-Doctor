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

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Catagory_List_Show extends AppCompatActivity {
    ListView lv;
    String name;
    String Hos_name;
    ArrayAdapter<String>adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catagory__list__show);

        lv=(ListView) findViewById(R.id.myCatagories);
        name=getIntent().getStringExtra("HospitalAddress");
        Hos_name=name.substring(0,name.indexOf('\n'));
        //Hos_name="BIRDEM General Hospital";
        fetchingData();
    }

    void fetchingData(){


        String myURL = "http://192.168.43.171:1234/DocCatagory.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(myURL  , new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                final String[] news_title = new String[response.length()];
                final String[] news_detail = new String[response.length()];

                //final String[] news_time = new String[response.length()];

                for (int i =0; i < response.length(); i++){

                    try {

                        JSONObject jsonObject = (JSONObject) response.get(i);
                        news_title[i] = jsonObject.getString("hos_name");
                        news_detail[i] = jsonObject.getString("catagory_name");
                        //news_time[i] = jsonObject.getString("time");


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
                int point=0;
                for(int dd=0;dd<news_title.length;dd++)
                {
                    if(news_title[dd].equals(Hos_name))
                    {
                        point++;
                    }
                }

                int mark=0;
                final String[] catagories=new String[point];
                for(int dd=0;dd<news_title.length;dd++)
                {
                    if(news_title[dd].equals(Hos_name))
                    {
                        catagories[mark]=news_detail[dd];
                        mark++;
                    }
                }

               // adapter=new ArrayAdapter(getApplicationContext(), R.layout.lview_pat_appoint, R.id.catagry, catagories);
                lv.setAdapter(adapter);

                /*lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(MainActivity.this, Details.class);
                        intent.putExtra("MyTITLE", news_title[position]);
                        intent.putExtra("MyNEWS", news_detail[position]);
                        intent.putExtra("MyTime", news_time[position]);
                        startActivity(intent);

                    }
                });*/

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        String value=adapter.getItem(position);
                        Intent intent = new Intent(getApplicationContext(), Doctors_Show_List.class);
                        intent.putExtra("MyCATAGORY", news_title[position]);

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

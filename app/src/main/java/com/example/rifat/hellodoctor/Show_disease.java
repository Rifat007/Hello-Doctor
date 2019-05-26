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

public class Show_disease extends AppCompatActivity {
    ListView lv;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_disease);

        lv=(ListView) findViewById(R.id.diseases_list);

        fetchingData();
    }


    void fetchingData(){


        String myURL = "http://192.168.43.171:1234/disSym.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(myURL  , new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                final String[] news_title = new String[response.length()];
                final String[] news_detail = new String[response.length()];
                //final String[] news_time = new String[response.length()];

                for (int i =0; i < response.length(); i++){

                    try {

                        JSONObject jsonObject = (JSONObject) response.get(i);
                        news_title[i] = jsonObject.getString("disease");
                        news_detail[i] = jsonObject.getString("symtoms");
                        //news_time[i] = jsonObject.getString("time");


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
                adapter=new ArrayAdapter<String>(getApplicationContext(), R.layout.lview_disesases, R.id.diseasess, news_title);

                lv.setAdapter(adapter);

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //Intent intent = new Intent(getApplicationContext(), Disease_details.class);
                        //intent.putExtra("MyDISEASE", news_title[position]);
                        //intent.putExtra("MySYMTOMS", news_detail[position]);
                        //intent.putExtra("MyTime", news_time[position]);
                      //  startActivity(intent);
                        String value=adapter.getItem(position);
                        //Toast.makeText(getApplicationContext(),value,Toast.LENGTH_SHORT).show();
                        int k=-1;
                        for(int kkr=0;kkr<news_title.length;kkr++)
                        {
                            if(news_title[kkr].equals(value))
                            {
                                k=kkr;
                                break;
                            }
                        }
                        //String detail=news_detail[k].toString();

                        Intent intent = new Intent(getApplicationContext(), Disease_details.class);
                        intent.putExtra("MyDISEASE", news_title[k]);
                        intent.putExtra("MySYMTOMS", news_detail[k]);
                        //intent.putExtra("MyTime", news_time[position]);
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

package com.example.rifat.hellodoctor;

import android.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Doc_View_Pat extends AppCompatActivity {
    ListView lv;
    ArrayAdapter<String> adapter;
    String docnamee,docidd;
    View vie;
    AlertDialog.Builder progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc__view__pat);

        docnamee=getIntent().getStringExtra("DOC_NAME");
        docidd=getIntent().getStringExtra("DOC_ID");

        lv=(ListView) findViewById(R.id.docViewingg);
        vie=getLayoutInflater().inflate(R.layout.cancellation_layout,null);
        fetchingData();
    }

    void fetchingData(){


        String myURL = "http://192.168.43.171:1234/docpatappshow.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(myURL  , new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                final String[] docnm = new String[response.length()];
                final String[] docid = new String[response.length()];
                final String[] patnm=new String[response.length()];
                final String[] patid=new String[response.length()];
                final String[] hosnm=new String[response.length()];
                final String[] dt=new String[response.length()];
                final String[] hr=new String[response.length()];

                final String[] listy;

                //final String[] news_time = new String[response.length()];

                for (int i =0; i < response.length(); i++){

                    try {

                        JSONObject jsonObject = (JSONObject) response.get(i);
                        docnm[i] = jsonObject.getString("Doc_Name");
                        docid[i] = jsonObject.getString("Doc_id");
                        patnm[i]=jsonObject.getString("Pat_Name");
                        patid[i]=jsonObject.getString("Pat_id");
                        hosnm[i]=jsonObject.getString("hos_name");
                        dt[i]=jsonObject.getString("Date");
                        hr[i]=jsonObject.getString("Hour");

                        //listy[i]=patnm[i]+"\n"+dt[i]+"    "+hr[i]+"    "+"cancel";
                        //news_time[i] = jsonObject.getString("time");


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
                int point=0;
                for(int dd=0;dd<docnm.length;dd++)
                {
                    if(docidd.equals(docid[dd]))
                    {
                        point++;
                    }
                }

                listy=new String[point];
                int point1=0;
                for(int dd=0;dd<docnm.length;dd++)
                {
                    if(docidd.equals(docid[dd]))
                    {
                        listy[point1]=patnm[dd]+"\n"+dt[dd]+"    "+hr[dd]+"    "+"cancel";
                        point1++;
                    }
                }

                adapter=new ArrayAdapter(getApplicationContext(), R.layout.lview_pat_appoint, R.id.Appointedtext, listy);
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
                        progress=new AlertDialog.Builder(Doc_View_Pat.this);
                        //final View vie=inflater.inflate(R.layout.progress_custom,null);
                        final EditText et=(EditText)vie.findViewById(R.id.doccanceledit);
                        final Button bt1=(Button) vie.findViewById(R.id.cancelcancelButton);
                        final Button bt2=(Button) vie.findViewById(R.id.sendsendButton);

                        bt1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(getApplicationContext(),"Ok",Toast.LENGTH_SHORT).show();
                            }
                        });
                        bt2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String kk=et.getText().toString();
                                Toast.makeText(getApplicationContext(),"Cancellation completed..",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),Doctor_Home.class));
                            }
                        });
                        progress.setView(vie);
                        AlertDialog ad=progress.create();
                        ad.show();
                        //Intent intent = new Intent(getApplicationContext(), Doctors_Show_List.class);
                        //intent.putExtra("MyCATAGORY", news_title[position]);

                        //startActivity(intent);

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
        //Toast.makeText(getApplicationContext(), "Data Loaded Successfully!", Toast.LENGTH_SHORT).show();

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

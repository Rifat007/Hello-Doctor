package com.example.rifat.hellodoctor;

import android.app.AlertDialog;
import android.app.Notification;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.zip.Inflater;

public class Pat_Show_Bookings extends AppCompatActivity {
    ListView lv;
    ArrayAdapter<String> adapter;
    String patnamee,patidd;
    AlertDialog.Builder alDiabld;
     View vie;
    String Dnm,Did,Pnm,Pid,Hos,Dt,Hr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pat__show__bookings);
        lv=(ListView) findViewById(R.id.mybookingss);


        patnamee=getIntent().getStringExtra("PATIENT_NAME");
        patidd=getIntent().getStringExtra("PATIENT_ID");
        ///Toast.makeText(getApplicationContext(),patidd,Toast.LENGTH_SHORT).show();
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
                //=new String[response.length()];

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

                        //listy[i]=docnm[i]+"\n"+dt[i]+"    "+hr[i]+"   "+"cancel";
                        //news_time[i] = jsonObject.getString("time");


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                int point=0;
                for(int dd=0;dd<patnm.length;dd++)
                {
                    if(patidd.equals(patid[dd]))
                    {
                        point++;
                    }
                }

                listy=new String[point];
                int point1=0;
                for(int dd=0;dd<patnm.length;dd++)
                {
                    if(patidd.equals(patid[dd]))
                    {
                        listy[point1]=docnm[dd]+"\n"+dt[dd]+"    "+hr[dd]+"    "+"cancel";
                        point1++;
                    }
                }


                adapter=new ArrayAdapter(getApplicationContext(), R.layout.lview_pat_appoint, R.id.Appointedtext, listy);
                lv.setAdapter(adapter);



                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        final String value=adapter.getItem(position);
                        String gett=value.substring(0,value.indexOf('\n'));

                        for(int ddr=0;ddr<docnm.length;ddr++)
                        {
                            if(gett.equals(docnm[ddr]))
                            {
                                Dnm=docnm[ddr];
                                Did=docid[ddr];
                                Pnm=patnm[ddr];
                                Pid=patid[ddr];
                                Hos=hosnm[ddr];
                                Dt=dt[ddr];
                                Hr=hr[ddr];
                            }
                        }

                        alDiabld=new AlertDialog.Builder(Pat_Show_Bookings.this);

                        alDiabld.setTitle("Cancellation");

                        alDiabld.setMessage("Are you sure to cancel appointment?");

                        alDiabld.setIcon(R.drawable.confirmation);

                        alDiabld.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();


                                String url = "http://192.168.43.171:1234/docpatappDel.php";


                                StringRequest sq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        //Toast.makeText(getActivity(),error,Toast.LENGTH_SHORT).show();

                                    }
                                }) {
                                    protected Map<String, String > getParams(){
                                        Map<String, String> parr = new HashMap<String, String>();

                                        parr.put("mydocname", Dnm);
                                        parr.put("mydocid", Did);
                                        parr.put("mypatname", Pnm);
                                        parr.put("mypatid", Pid);
                                        parr.put("myhosname", Hos);
                                        parr.put("mydate", Dt);
                                        parr.put("myhour", Hr);


                                        return parr;


                                    }

                                };
                                com.example.rifat.hellodoctor.AppController.getInstance().addToRequestQueue(sq);

                                Toast.makeText(getApplicationContext(),"Cancellation Done...",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),Navi.class));




                            }
                        });

                        alDiabld.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });

                        AlertDialog alertDialog=alDiabld.create();
                        alertDialog.show();

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

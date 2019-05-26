package com.example.rifat.hellodoctor;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
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

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


public class Fragment_Appointment extends Fragment  {

    View v,vie;
    EditText date;
    EditText hour;
    DatePickerDialog dp;
    Button submit;
    Button dateSel;
    Spinner spinn;
    String[] times;
    AlertDialog.Builder alDiabld;
    Doctor_Profile_Appointment dpa;
    String docnm,hosnameee,catag,dte,rm,patnm,patid;
    String docid;
    ArrayAdapter<String> adapter;
    AlertDialog.Builder progress;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_fragment__appointment, container, false);
         vie=inflater.inflate(R.layout.progress_custom,null);

        dpa=(Doctor_Profile_Appointment)getActivity();

        docnm=dpa.getIntent().getStringExtra("DOCTOR_NAME");
        hosnameee=dpa.getIntent().getStringExtra("HOSPITAL_NAME");
        catag=dpa.getIntent().getStringExtra("CATAGORY");
        dte=dpa.getIntent().getStringExtra("DATE");
        rm=dpa.getIntent().getStringExtra("ROOM_NO");
        patnm=dpa.getIntent().getStringExtra("PATIENT_NAME");
        patid=dpa.getIntent().getStringExtra("PATIENT_ID");

       // times=getResources().getStringArray(R.array.Times);



        hour=(EditText) v.findViewById(R.id.hourIdd);


        submit=(Button) v.findViewById(R.id.submissionAppointment);
        spinn=(Spinner) v.findViewById(R.id.spinnerIDD);

        fetchingData();
        fetchingData1();




       if(spinn.getSelectedItem()!=null)
       {
           hour.setText(spinn.getSelectedItem().toString());
       }


        spinn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                hour.setText(spinn.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alDiabld=new AlertDialog.Builder(getActivity());

                alDiabld.setTitle("Confirmation");

                alDiabld.setMessage("Confirm sure your appointment....");

                alDiabld.setIcon(R.drawable.confirmation);

                alDiabld.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        String url = "http://192.168.43.171:1234/docpatapp.php";


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

                                parr.put("mydocname", docnm);
                                parr.put("mydocid", docid);
                                parr.put("mypatname", patnm);
                                parr.put("mypatid", patid);
                                parr.put("myhosname", hosnameee);
                                parr.put("mydate", dte);
                                parr.put("myhour", hour.getText().toString());


                                return parr;


                            }

                        };
                        com.example.rifat.hellodoctor.AppController.getInstance().addToRequestQueue(sq);
                        //Toast.makeText(getActivity(),docnm+docid+patid+patnm+hosnameee+dte+hour.getText().toString(),Toast.LENGTH_SHORT).show();

                        progress=new AlertDialog.Builder(getActivity());
                        //final View vie=inflater.inflate(R.layout.progress_custom,null);
                        final TextView tv=(TextView)vie.findViewById(R.id.progressNum);
                        final ProgressBar progressBar=(ProgressBar)vie.findViewById(R.id.progIddd);

                        for(int pro=10;pro<=100;pro=pro+10)
                        {
                            progressBar.setProgress(pro);
                            tv.setText(pro+"%");
                            progress.setView(vie);

                        }
                        AlertDialog ad=progress.create();
                        ad.show();
                        //Toast.makeText(getActivity(),hour.getText().toString(),Toast.LENGTH_SHORT).show();


                        //Toast.makeText(getActivity(), "Appoint Added....", Toast.LENGTH_LONG).show();

                        String ddrl1 = "http://192.168.43.171:1234/doc_schedulingdel.php";


                        StringRequest sq1 = new StringRequest(Request.Method.POST, ddrl1, new Response.Listener<String>() {
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

                                parr.put("myname", docnm);
                                parr.put("myhosname", hosnameee);
                                parr.put("mydate", dte);
                                parr.put("mytime", hour.getText().toString());
                                parr.put("mycata",catag);
                                parr.put("myroom",rm);



                                return parr;


                            }

                        };
                        com.example.rifat.hellodoctor.AppController.getInstance().addToRequestQueue(sq1);

                        ad.cancel();


                        NotificationCompat.Builder np=new NotificationCompat.Builder(getActivity())
                                .setSmallIcon(R.drawable.notiii)
                                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.notiii))
                                .setContentTitle("Appointment Done!!!")
                                .setContentText("You have Appointed . Appoint date :"+dte+" Time :"+hour.getText().toString());

                        np.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE);
                        NotificationManagerCompat nmc=NotificationManagerCompat.from(getActivity());
                        nmc.notify(1,np.build());



                    }
                });

                alDiabld.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                AlertDialog alertDialog=alDiabld.create();
                alertDialog.show();


            }
        });


        return v;
    }

    void fetchingData(){


        String myURL = "http://192.168.43.171:1234/logD1.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(myURL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                final String[] Doc_id = new String[response.length()];
                final String[] Doc_name=new String[response.length()];

                for (int i =0; i < response.length(); i++){

                    try {

                        JSONObject jsonObject = (JSONObject) response.get(i);
                        Doc_id[i] = jsonObject.getString("Doc_id");
                        Doc_name[i]=jsonObject.getString("Doc_Name");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                for(int kkr=0;kkr<Doc_id.length;kkr++)
                {
                    if(docnm.equals(Doc_name[kkr]))
                    {
                        docid=Doc_id[kkr];
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Volley Log", error);
            }
        });


        com.example.rifat.hellodoctor.AppController.getInstance().addToRequestQueue(jsonArrayRequest);
        Toast.makeText(getActivity(), "Enter Valid Name and password", Toast.LENGTH_SHORT).show();

    }

    void fetchingData1(){


        String myURL = "http://192.168.43.171:1234/hos_cata_doc1.php";
        String str1,str2;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(myURL  , new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                final String[] docname = new String[response.length()];
                final String[] hosname = new String[response.length()];
                final String[] cata = new String[response.length()];
                final String[] date=new String[response.length()];
                final String[] hour=new String[response.length()];
                final String[] roomno=new String[response.length()];
                //hosName,catagory_name,datee

                for (int i =0; i < response.length(); i++){

                    try {

                        JSONObject jsonObject = (JSONObject) response.get(i);
                        docname[i] = jsonObject.getString("Doc_Name");
                        hosname[i]=jsonObject.getString("hos_name");
                        cata[i]=jsonObject.getString("catagory_name");
                        date[i]=jsonObject.getString("Date");
                        hour[i]=jsonObject.getString("Hour");
                        roomno[i]=jsonObject.getString("Room_no");

                        //news_detail[i] = jsonObject.getString("news");
                        //news_time[i] = jsonObject.getString("time");


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                int point=0;
                for(int ddr=0;ddr<docname.length;ddr++)
                {
                    if(docnm.equals(docname[ddr]) && hosnameee.equals(hosname[ddr]) && catag.equals(cata[ddr]) && dte.equals(date[ddr]))
                    {
                        point++;
                    }
                }

                times=new String[point];
                int point1=0;
                for(int ddr=0;ddr<docname.length;ddr++)
                {
                    if(docnm.equals(docname[ddr]) && hosnameee.equals(hosname[ddr]) && catag.equals(cata[ddr]) && dte.equals(date[ddr]))
                    {
                        times[point1]=hour[ddr];
                        point1++;
                    }
                }

                adapter=new ArrayAdapter<String>(getActivity(),R.layout.spinner_sample_view_time,R.id.spinner_times,times);
                spinn.setAdapter(adapter);




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Volley Log", error);
            }
        });


        com.example.rifat.hellodoctor.AppController.getInstance().addToRequestQueue(jsonArrayRequest);
        Toast.makeText(getActivity(), "Data Loaded Successfully!", Toast.LENGTH_SHORT).show();

    }






}

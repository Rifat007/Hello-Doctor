package com.example.rifat.hellodoctor;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.Arrays;
import java.util.HashSet;


public class FragmentOne extends Fragment {

    ListView lv;
    View v;
    String[] AsholDoctor,Asholroom;
    Doctors_Show_List dsl;


    String hsnm,ctagry,dte;
    public void  FragmentOne(String hosname,String catagory,String date)
    {
        hsnm=hosname;
        ctagry=catagory;
        dte=date;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_fragment_one, container, false);
        dsl=(Doctors_Show_List)getActivity();

        lv=(ListView) v.findViewById(R.id.view_doc_list);
        fetchingData();

        return v;
    }

    void fetchingData(){


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
                final String tmp=Doctors_Show_List.hosName1.substring(0,Doctors_Show_List.hosName1.indexOf('\n'));


                int point=0;
                for(int kk=0;kk<docname.length;kk++)
                {
                    if(hosname[kk].equals(tmp) && cata[kk].equals(Doctors_Show_List.catagory_name1) && date[kk].equals(Doctors_Show_List.datee1))
                    {
                        point++;
                    }

                }

                AsholDoctor=new String[point];
                Asholroom=new String[point];
                int point1=0;
                for(int kk=0;kk<docname.length;kk++)
                {
                    if(hosname[kk].equals(tmp) && cata[kk].equals(Doctors_Show_List.catagory_name1) && date[kk].equals(Doctors_Show_List.datee1))
                    {
                        AsholDoctor[point1]=docname[kk];
                        Asholroom[point1]=roomno[kk];
                        point1++;
                    }
                }

                AsholDoctor= new HashSet<String>(Arrays.asList(AsholDoctor)).toArray(new String[0]);
                Asholroom=new HashSet<String> (Arrays.asList(Asholroom)).toArray(new String[0]);



                lv.setAdapter(new ArrayAdapter(getActivity(), R.layout.frag_lview_showdoc, R.id.fragone_view_doc, AsholDoctor));

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent inti = new Intent(getActivity(), Doctor_Profile_Appointment.class);
                        inti.putExtra("DOCTOR_NAME", AsholDoctor[position]);
                        inti.putExtra("HOSPITAL_NAME",tmp);
                        inti.putExtra("CATAGORY",Doctors_Show_List.catagory_name1);
                        inti.putExtra("DATE",Doctors_Show_List.datee1);
                        inti.putExtra("ROOM_NO",Asholroom[position]);
                        inti.putExtra("PATIENT_NAME",dsl.getIntent().getStringExtra("PATIENT_NAME"));
                        inti.putExtra("PATIENT_ID",dsl.getIntent().getStringExtra("PATIENT_ID"));
                        //intent.putExtra("MyNEWS", news_detail[position]);
                        //intent.putExtra("MyTime", news_time[position]);
                        startActivity(inti);

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
        Toast.makeText(getActivity(), "Data Loaded Successfully!"+dsl.getIntent().getStringExtra("PATIENT_ID"), Toast.LENGTH_SHORT).show();

    }

}

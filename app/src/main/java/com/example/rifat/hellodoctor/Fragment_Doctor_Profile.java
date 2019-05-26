package com.example.rifat.hellodoctor;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import static com.example.rifat.hellodoctor.R.id.custom;


public class Fragment_Doctor_Profile extends Fragment {

    View v,vie;
    /* inti.putExtra("DOCTOR_NAME", AsholDoctor[position]);
                        inti.putExtra("HOSPITAL_NAME",tmp);
                        inti.putExtra("CATAGORY",Doctors_Show_List.catagory_name1);
                        inti.putExtra("DATE",Doctors_Show_List.datee1);
                        inti.putExtra("ROOM_NO",Asholroom[position]);
                        inti.putExtra("PATIENT_NAME",dsl.getIntent().getStringExtra("PATIENT_NAME"));
                        inti.putExtra("PATIENT_ID",dsl.getIntent().getStringArrayExtra("PATIENT_ID"));*/

    TextView docname,doctype,docrating,room,phn,fare;
    Button giveRaing;
    RatingBar rtbar;
    Doctor_Profile_Appointment dpa;
    String docnm,hosname,cata,dte,rm,patnm,patid;
    AlertDialog.Builder abc;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_fragment__doctor__profile, container, false);
        vie=inflater.inflate(R.layout.rating_dialog,null);
        dpa=(Doctor_Profile_Appointment)getActivity();

        docnm=dpa.getIntent().getStringExtra("DOCTOR_NAME");
        hosname=dpa.getIntent().getStringExtra("HOSPITAL_NAME");
        cata=dpa.getIntent().getStringExtra("CATAGORY");
        dte=dpa.getIntent().getStringExtra("DATE");
        rm=dpa.getIntent().getStringExtra("ROOM_NO");
        patnm=dpa.getIntent().getStringExtra("PATIENT_NAME");
        patid=dpa.getIntent().getStringExtra("PATIENT_ID");


        docname=(TextView) v.findViewById(R.id.Docproname);
        docname.setText(docnm);

        doctype=(TextView) v.findViewById(R.id.docspecialist);
        doctype.setText("Catagory : "+cata);

        docrating=(TextView) v.findViewById(R.id.Docratingid);
        room=(TextView) v.findViewById(R.id.doctorroomno);
        room.setText("Room No. : "+rm);

        phn=(TextView)v.findViewById(R.id.doctorphnNo);
        fare=(TextView) v.findViewById(R.id.doctorfare);

        rtbar=(RatingBar) v.findViewById(R.id.docratingpane);


        giveRaing=(Button) v.findViewById(R.id.docgiveRating);
        fetchingData();
        giveRaing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    abc=new AlertDialog.Builder(getActivity());

                final RatingBar rtbr=(RatingBar) vie.findViewById(R.id.ratdialogid);

                final TextView txt=(TextView) vie.findViewById(R.id.ratdialogtext);


                rtbr.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                        txt.setText("Rating : "+ v);
                    }
                });

                abc.setView(vie);
                 AlertDialog a=abc.create();
                a.show();
            }
        });

        return v;
    }
    void fetchingData(){


        String myURL = "http://192.168.43.171:1234/logD1.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(myURL  , new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                final String[] Doc_id = new String[response.length()];
                final String[] Doc_password = new String[response.length()];
                final String[] Doc_name=new String[response.length()];
                final String[] Doc_typ=new String[response.length()];
                final String[] Doc_fare=new String[response.length()];
                final String[] Doc_rating=new String[response.length()];
                final String[] Doc_phn=new String[response.length()];
                //final String[] news_time = new String[response.length()];

                for (int i =0; i < response.length(); i++){

                    try {

                        JSONObject jsonObject = (JSONObject) response.get(i);
                        Doc_id[i] = jsonObject.getString("Doc_id");
                        Doc_password[i] = jsonObject.getString("Doc_Password");
                        Doc_name[i]=jsonObject.getString("Doc_Name");
                        Doc_typ[i]=jsonObject.getString("Doc_type");
                        Doc_fare[i]=jsonObject.getString("Salary");
                        Doc_rating[i]=jsonObject.getString("Rating");
                        Doc_phn[i]=jsonObject.getString("Doc_Phn_no");





                        //news_time[i] = jsonObject.getString("time");


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                for(int kks=0;kks<Doc_name.length;kks++)
                {
                    if(docnm.equals(Doc_name[kks]))
                    {
                        phn.setText("Contact No. : "+Doc_phn[kks]);
                        docrating.setText("rating "+Doc_rating[kks]);
                        fare.setText("Fare : "+Doc_fare[kks]);
                        rtbar.setProgress(3);


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
        //Toast.makeText(getActivity(), "Enter Valid Name and password", Toast.LENGTH_SHORT).show();

    }

}

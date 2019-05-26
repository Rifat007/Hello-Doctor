package com.example.rifat.hellodoctor;

/**
 * Created by Rifat on 11/22/2017.
 */

import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.lang.Object;

/**
 * Created by ASUS on 12-October-2017.
 */


public class GetNearbyPlacesData extends AsyncTask<Object, String ,String>{
    String googlePlacesData;
    GoogleMap mMap;
    String url;

    String[] databaseHospital;
    String[] jayga;
    String[] plc,vic;
    Double[] lon,lati;


    @Override
    protected String doInBackground(Object... objects) {
        mMap=(GoogleMap)objects[0];
        url=(String)objects[1];
        DownloadUrl downloadUrl=new DownloadUrl();
        try {
            googlePlacesData=downloadUrl.readUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return googlePlacesData;
    }

    @Override
    protected void onPostExecute(String s) {
        List<HashMap<String,String>>nearbyPlaceList=null;
        DataParser dataParser =new DataParser();
        nearbyPlaceList=dataParser.parse(s);
        showNearbyPlaces(nearbyPlaceList);
    }
    private void showNearbyPlaces(List<HashMap<String,String>> nearbyPlaceList)
    {
        plc=new String[nearbyPlaceList.size()];
        vic=new String[nearbyPlaceList.size()];
        lon=new Double[nearbyPlaceList.size()];
        lati=new Double[nearbyPlaceList.size()];


        int cnt=0;
        for(int i=0;i<nearbyPlaceList.size();i++)
        {
            //MarkerOptions markerOptions =new MarkerOptions();
            HashMap<String,String>googlePlace=nearbyPlaceList.get(i);
            String placeName=googlePlace.get("place_name");
            String vicinity=googlePlace.get("vicinity");
            double lat=Double.parseDouble(googlePlace.get("lat"));
            double lng=Double.parseDouble(googlePlace.get("lng"));

            plc[i]=placeName;
            vic[i]=vicinity;
            lati[i]=lat;
            lon[i]=lng;




        }
        fetchingData();


    }

    void fetchingData(){


        String myURL = "http://192.168.43.171:1234/hos_cata_doc1.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(myURL  , new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                final String[] docname = new String[response.length()];
                final String[] cataname = new String[response.length()];
                final String[] hosname = new String[response.length()];
                final String[] date=new String[response.length()];

                for (int i =0; i < response.length(); i++){

                    try {

                        JSONObject jsonObject = (JSONObject) response.get(i);
                        docname[i] = jsonObject.getString("Doc_Name");
                        cataname[i] = jsonObject.getString("catagory_name");
                        hosname[i] = jsonObject.getString("hos_name");
                        date[i]=jsonObject.getString("Date");


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
                int cnt=0;
                for(int i=0;i<plc.length;i++)
                {
                    for(int j=0;j<hosname.length;j++)
                    {
                        if(plc[i].equals(hosname[j]) && cataname[j].equals(MapsActivity.catagory_name) && date[j].equals(MapsActivity.datee))
                        {
                            MarkerOptions markerOptions =new MarkerOptions();
                            LatLng latlng = new LatLng(lati[i], lon[i]);
                            markerOptions.position(latlng);
                            markerOptions.title(plc[i] + " : " + vic[i]);
                            //MapsActivity.ara[i] = "" + placeName + "\n";
                            cnt++;

                            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                            mMap.addMarker(markerOptions);
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));
                            mMap.animateCamera(CameraUpdateFactory.zoomTo(10));

                        }
                    }
                }

                MapsActivity.ara=new String[cnt];
                int cnt1=0;
                for(int i=0;i<plc.length;i++)
                {
                    for(int j=0;j<hosname.length;j++)
                    {
                        if(plc[i].equals(hosname[j]) && cataname[j].equals(MapsActivity.catagory_name) && date[j].equals(MapsActivity.datee))
                        {
                            MapsActivity.ara[cnt1] = "" + plc[i] +"\n"+vic[i]+"\n";
                            cnt1++;


                        }
                    }
                }

                MapsActivity.ara= new HashSet<String>(Arrays.asList(MapsActivity.ara)).toArray(new String[0]);







            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Volley Log", error);
            }
        });


        com.example.rifat.hellodoctor.AppController.getInstance().addToRequestQueue(jsonArrayRequest);
        //Toast.makeText(getActivity(), "Data Loaded Successfully!", Toast.LENGTH_SHORT).show();

    }



}

package com.example.trapconnectwyres;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;

import com.example.trapconnectwyres.Download_data.download_complete;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements download_complete {

    private Toolbar toolbar;


    public ListView listview;

    public static String[] liste = new String[800];
    public ListAdapter adapter;

    public static int ligne;

    ArrayList<Cages> cage = new ArrayList<Cages>();

    public long previous_date;
    public long actual_date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        listview = (ListView) findViewById(R.id.listView1);
        adapter = new ListAdapter(this);


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //do something when user CLik on listView
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("Details", listview.getItemAtPosition(position).toString());
                ligne = position;
                startActivity(intent);
            }
        });
        listview.setAdapter(adapter);

        Download_data download_data = new Download_data((download_complete) this);
        download_data.download_data_from_link("https://api.wyres.io/geolocmgr/rest/v1/geoloc/entities/UniversiteGrenobleAlpes/tags?user=marmotteViewer&pass=uga2019&limit=100");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /*
     * Listen for option item selections so that we receive a notification
     * when the user requests a refresh by selecting the refresh action bar item.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            // Check if user triggered a refresh:
            case R.id.action_favorite:

                Log.i("LOG_TAG", "Refresh menu item selected");
                cage = new ArrayList<Cages>();
                // Start the refresh background task.
                // This method calls setRefreshing(false) when it's finished.
                Download_data download_data = new Download_data((download_complete) this);
                download_data.download_data_from_link("https://api.wyres.io/geolocmgr/rest/v1/geoloc/entities/UniversiteGrenobleAlpes/tags?user=marmotteViewer&pass=uga2019&limit=100");
                return true;
        }

        // User didn't trigger a refresh, let the superclass handle this action
        return super.onOptionsItemSelected(item);
    }

    public void addInList(String obj) {
        //Bring interrest information in the URL request
        try {
            JSONObject jsonResponse1 = new JSONObject(obj);
            JSONObject jsonResponse2 = new JSONObject(jsonResponse1.getString("data"));
            JSONArray data_array = new JSONArray(jsonResponse2.getString("objList"));


            for (int i = 0; i < data_array.length(); i++) {
                Cages item = new Cages();
                JSONObject object = data_array.getJSONObject(i);

                item.setHashedId(object.getString("hashedId"));
                liste[i*8] = object.getString("hashedId");
                item.setRssi(object.getString("rssi"));
                liste[i*8+2] = object.getString("rssi");


                JSONObject meta = new JSONObject(object.getString("meta"));

                if (meta.toString().length() != 2) {
                    switch (meta.getString("etat")) {
                        case "Ouvert":
                            item.setState("Open");
                            liste[i*8+1] = "Open";
                            break;
                        case "Fermé":
                            item.setState("Close");
                            liste[i*8+1] = "Close";
                            break;
                        case "Self-test":
                            item.setState("Self-test");
                            liste[i*8+1] = "Self-test";
                            break;
                        default:
                            item.setState("Device not connected");
                            liste[i*8+1] = "Device not connected";
                            break;
                    }
                    item.setBattery_level(meta.getString("battery_level"));
                    liste[i*8+3] = meta.getString("battery_level");
                    item.setLastRx(meta.getString("lastRx"));
                    liste[i*8+4] = meta.getString("lastRx");
                    item.setLast_door_change(meta.getString("lastDoorChange"));
                    liste[i*8+5] = meta.getString("lastDoorChange");


                    //Calcul of device timeout
                    previous_date = parseISODate(meta.getString("lastRx")).getTime();
                    actual_date =new Date().getTime()-(2*60*60*1000);

                    if ((actual_date-previous_date) > (30*60*1000))
                    {
                        item.setState("No Communication");
                        liste[i*8+1] = "No Communication";
                    }


                } else {
                    item.setState("Device not connected");
                    liste[i*8+1] = "Device not connected";
                    item.setBattery_level("Device not connected");
                    liste[i*8+3] = "Device not connected";
                    item.setLastRx("Device not connected");
                    liste[i*8+4] = "Device not connected";
                    item.setLast_door_change("Device not connected");
                    liste[i*8+5] = "Device not connected";
                }

                cage.add(item);

                runOnUiThread(new Runnable() {
                 public void run() {
                    adapter.notifyDataSetChanged();
                }
                 });
            }

            } catch(JSONException e){
                e.printStackTrace();
            }

        }

        public Date parseISODate(String s)
        {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.FRANCE);
                return sdf.parse(s);
            } catch (Exception e) {
                return new Date(0);
            }
        }
    }






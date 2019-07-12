package com.example.trapconnectwyres;

import android.annotation.SuppressLint;
import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;


public class SecondActivity extends Activity  {


    private Toolbar toolbar1;


    public TextView id;
    public TextView state;
    public TextView rssi;
    public TextView battery_level;
    public TextView lastRx;
    public TextView last_door_change;
    public TextView latitude;
    public TextView longitude;


    public Button set_cordinates;
    public Button mapView;
    public LocationManager locationManager;
    public LocationListener listener;

    private double battery;
    private int rssi0;

    public String lat_liste;
    public String lon_liste;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        toolbar1 = findViewById(R.id.toolbar2);

        set_cordinates = findViewById(R.id.set_cordinates);
        mapView = findViewById(R.id.maps);

        id = findViewById(R.id.hashedId);
        state = findViewById(R.id.state);
        rssi = findViewById(R.id.rssi);
        battery_level = findViewById(R.id.battery_level);
        lastRx = findViewById(R.id.lastRx);
        last_door_change = findViewById(R.id.last_door_change);
        latitude = findViewById(R.id.latitude);
        longitude = findViewById(R.id.longitude);

        for (int i=0; i<7; i++)
        {
            id.setText(String.valueOf(MainActivity.liste[MainActivity.ligne *8]));
            state.setText(MainActivity.liste[MainActivity.ligne *8+1]);
            rssi.setText(MainActivity.liste[MainActivity.ligne *8+2]);
            battery_level.setText(MainActivity.liste[MainActivity.ligne *8+3]);
            lastRx.setText(MainActivity.liste[MainActivity.ligne *8+4]);
            last_door_change.setText(MainActivity.liste[MainActivity.ligne *8+5]);
            latitude.setText(MainActivity.liste[MainActivity.ligne *8+6]);
            longitude.setText(MainActivity.liste[MainActivity.ligne *8+7]);

            switch (MainActivity.liste[MainActivity.ligne *8+1]) {
                case "Open":
                    state.setTextColor(Color.parseColor("#8BC34A")); //couleur verte
                    break;
                case "Close":
                    state.setTextColor(Color.parseColor("#E00707")); //couleur rouge
                    break;
                case "Self-test":
                    state.setTextColor(Color.parseColor("#FFDD00")); //couleur jaune
                    break;
                case "Device not connected":
                    state.setTextColor(Color.parseColor("#2196F3")); //couleur bleue
                    break;
                case "No Communication":
                    state.setTextColor(Color.parseColor("#E00707")); //couleur roouge
                    break;
                default:
                    state.setTextColor(Color.parseColor("#000000")); //couleur noire
                    break;

            }



            if (MainActivity.liste[MainActivity.ligne *8+3] != "Device not connected") {
                battery = Double.parseDouble(MainActivity.liste[MainActivity.ligne *8+3]);
                if (battery <= 1.8) {
                    battery_level.setTextColor(Color.parseColor("#E00707")); //couleur rouge

                } else {
                    battery_level.setTextColor(Color.parseColor("#8BC34A")); //couleur verte
                }

            } else {
                battery_level.setTextColor(Color.parseColor("#2196F3")); //couleur bleue
            }



            rssi0 = Integer.parseInt(MainActivity.liste[MainActivity.ligne *8+2]);
            if (rssi0 > -100) {
                rssi.setTextColor(Color.parseColor("#8BC34A")); //couleur verte

            } else if (-100 >= rssi0 & rssi0 >= -120) {
                rssi.setTextColor(Color.parseColor("#FFDD00")); //couleur jaune
            } else if (rssi0 <= -120) {
                rssi.setTextColor(Color.parseColor("#E00707")); //couleur rouge
            }

        }

        mapView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something when user CLik on listView
                mapView();
            }
        });

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                lat_liste = String.valueOf(location.getLatitude());
                MainActivity.liste[MainActivity.ligne *8+6] = lat_liste;
                latitude.setText(lat_liste);
                lon_liste = String.valueOf(location.getLongitude());
                MainActivity.liste[MainActivity.ligne *8+7] = lon_liste;
                longitude.setText(lon_liste);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };



        set_cordinates.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View v) {

                locationManager.requestLocationUpdates(
                        "gps",
                        6000000,
                        1000,
                        listener);
            }
        });
    }

    public void mapView (){
        Intent intent = new Intent(SecondActivity.this, MapActivity.class);
        startActivity(intent);
    }

}

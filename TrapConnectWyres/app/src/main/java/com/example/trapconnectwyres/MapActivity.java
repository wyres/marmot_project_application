package com.example.trapconnectwyres;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.util.Log;


public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private double lat_map;
    private double lon_map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_maps_trap);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Log.i("lat", ""+MainActivity.liste[MainActivity.ligne * 8 + 6]);
        Log.i("lon",""+MainActivity.liste[MainActivity.ligne * 8 + 7]);


        if (MainActivity.liste[MainActivity.ligne * 8 + 6]==null & MainActivity.liste[MainActivity.ligne * 8 + 7]==null)
        {
            lat_map = 0.0;
            lon_map = 0.0;
        }
        else {
            lat_map = Double.parseDouble(MainActivity.liste[MainActivity.ligne *8+6]);
            lon_map = Double.parseDouble(MainActivity.liste[MainActivity.ligne *8+7]);
        }
        LatLng cage = new LatLng(lat_map, lon_map);
        LatLng home = new LatLng(45.035826, 6.401010);

        mMap.addMarker(new MarkerOptions()
                .position(home)
                .title("Marker of home"));
        mMap.addMarker(new MarkerOptions()
                .position(cage)
                .title("Marker of cage n°" + MainActivity.liste[MainActivity.ligne *8]));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cage, 18F));
    }
}

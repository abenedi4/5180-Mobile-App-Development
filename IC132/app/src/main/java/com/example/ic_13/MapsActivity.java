package com.example.ic_13;

import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/*
In Class Assignment 13
IC-13
Nicholas Osaka and Alexandria Benedict (Group 22)
 */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    List<Location> points;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        String json = getJsonFromAssets(this, "trip-1.json");
        Log.d("demo", "JSON: " + json);

        Locations locations = getLocationsfromJson(json);
        points = locations.getPoints();


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        PolylineOptions polylineOptions = new PolylineOptions();
        LatLngBounds.Builder latlngBuilder = new LatLngBounds.Builder();
        for(Location loc : points) {
            LatLng latlngTemp = new LatLng(loc.getLatitude(),loc.getLongitude());
            polylineOptions.add(latlngTemp);
            latlngBuilder.include(latlngTemp);
        }
        //add markers
        LatLng start = new LatLng(points.get(0).getLatitude(),points.get(0).getLongitude());
        LatLng end = new LatLng(points.get(points.size()-1).getLatitude(),points.get(points.size()-1).getLongitude());
        googleMap.addMarker(new MarkerOptions().position(start)
                .title("Start Location"));
        googleMap.addMarker(new MarkerOptions().position(end)
                .title("End Location"));

        Polyline polyline = mMap.addPolyline(polylineOptions);



        final LatLngBounds latLngBounds = latlngBuilder.build();

        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 50));
                mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 50));
            }
        });
    }



    String getJsonFromAssets(Context context, String fileName) {
        String jsonString;
        try {
            InputStream is = context.getAssets().open(fileName);

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            jsonString = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return jsonString;
    }


    Locations getLocationsfromJson(String json){
        Gson gson = new Gson();
        Locations locations = gson.fromJson(json, Locations.class);
        Log.d("demo", "GSON return: " + locations.toString());
        return locations;
    }
}

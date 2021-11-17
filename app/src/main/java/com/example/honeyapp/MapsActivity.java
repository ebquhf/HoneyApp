package com.example.honeyapp;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    public static final String EXTRA_MESSAGE = "com.example.honeyapp.TEST";
    public static final String CHECKED_MESSAGE = "hasHoney";
    public static final String DESC_MESSAGE = "desc";

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
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

        Map<LatLng, String> positions = new HashMap<>();

        mMap = googleMap;
        LatLng odense = new LatLng(55.42206633810837, 10.447166699880208);

        Stand.getStands().forEach((a)->{
            mMap.addMarker(new MarkerOptions().position(a.location).title(a.name));
            positions.put(a.location,a.name);
        });
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(odense,15));

        //mMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(odense));
        mMap.setOnMarkerClickListener(marker -> {
            // Send marker coordinates to server, receive json for name, bio and pic
            Stand actual = Stand.getStandByName(positions.get(marker.getPosition()));

            String name = actual.name;
            Intent intent = new Intent(this, BeekeeperActivity.class);
            intent.putExtra(EXTRA_MESSAGE, name);
            intent.putExtra(CHECKED_MESSAGE, actual.hasHoney);
            intent.putExtra(DESC_MESSAGE,actual.description);

            startActivity(intent);
            return true;
        });




    }




}
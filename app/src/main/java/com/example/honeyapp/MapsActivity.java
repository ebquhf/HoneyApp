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

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(55.42206633810837, 10.447166699880208);

        LatLng stand1 = new LatLng(55.422460196625394, 10.449149695897693);
        positions.put(stand1, "Søren K. Rasmussen");
        mMap.addMarker(new MarkerOptions().position(stand1).title("Beekeeper 1"));
        LatLng stand2 = new LatLng(55.42008547764949, 10.446928826842152);
        positions.put(stand2, "Peter Ingemann");
        mMap.addMarker(new MarkerOptions().position(stand2).title("Beekeeper 2"));
        LatLng stand3 = new LatLng(55.42235059735412, 10.444096414133636);
        positions.put(stand3, "Morten Messerfedt");
        mMap.addMarker(new MarkerOptions().position(stand3).title("Beekeeper 3"));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.setOnMarkerClickListener(marker -> {
            // Send marker coordinates to server, receive json for name, bio and pic
            String name = positions.get(marker.getPosition());
            Intent intent = new Intent(this, BeekeeperActivity.class);
            intent.putExtra(EXTRA_MESSAGE, name);
            startActivity(intent);
            /*setContentView(R.layout.activity_beekeeper);
            TextView name_view = (TextView) findViewById(R.id.beekeeperName);
            name_view.setText(name);*/
            return true;
        });




    }




}
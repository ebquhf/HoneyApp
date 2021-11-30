package com.example.honeyapp;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    public static final String NAME = "name";
    public static final String CHECKED_MESSAGE = "hasHoney";
    public static final String DESC_MESSAGE = "desc";
    public static final String LINK = "url";
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private boolean isFirstRun;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;
    private Location mLastLocation;
    //private Marker mCurrLocationMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        this.isFirstRun = true;
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
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

        Stand.getStands().forEach((a) -> {
            mMap.addMarker(new MarkerOptions().position(a.location).title(a.name));
            positions.put(a.location, a.name);
        });

        Zoom zoom = Zoom.getInstance(getZoomLevel(mMap));
        Log.d("onMapReady", "Initial zoom level: " + zoom.zoomLevel);

        mMap.setOnCameraIdleListener(() -> {
            float zoomLevel = mMap.getCameraPosition().zoom;
            LatLng new_pos = mMap.getCameraPosition().target;
            if (zoomLevel != zoom.zoomLevel){
                Log.d("onCameraChange", "Before value is " + zoom.zoomLevel);
                zoom.zoomLevel = zoomLevel;  // here you get zoom level
                Log.d("onCameraChange", "New value is " + zoom.zoomLevel);
            }

            if (new_pos != zoom.position){
                zoom.position = new_pos;  // here you get zoom level
            }
        });

/*        mMap.setOnCameraChangeListener(position -> {
            if (position.zoom != zoom.zoomLevel){
                Log.d("onCameraChange", String.valueOf(position.zoom));
                zoom.zoomLevel = position.zoom;  // here you get zoom level
                Log.d("onCameraChange", "zoom level is " + position.zoom);
            }
        });*/

        //mMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(odense));
        mMap.setOnMarkerClickListener(marker -> {
            // Send marker coordinates to server, receive json for name, bio and pic
            Stand actual = Stand.getStandByName(positions.get(marker.getPosition()));

            String name = actual.name;
            Intent intent = new Intent(this, BeekeeperActivity.class);
            intent.putExtra(NAME, name);
            intent.putExtra(CHECKED_MESSAGE, actual.hasHoney);
            intent.putExtra(DESC_MESSAGE, actual.description);
            intent.putExtra(DESC_MESSAGE,actual.description);
            intent.putExtra(LINK, actual.url);

            startActivity(intent);
            return true;
        });


        AddPhoneLocation(googleMap);

    }

    private void AddPhoneLocation(GoogleMap googleMap) {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1200); // two minute interval
        mLocationRequest.setFastestInterval(1200);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        LocationCallback mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                List<Location> locationList = locationResult.getLocations();
                if (locationList.size() > 0) {
                    //The last location in the list is the newest
                    Location location = locationList.get(locationList.size() - 1);
                    Log.i("MapsActivity", "Location: " + location.getLatitude() + " " + location.getLongitude());
                    mLastLocation = location;

                    //Place current location marker
                    if (isFirstRun){
                        isFirstRun = false;
                        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(latLng);
                        markerOptions.title("Current Position");
                        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
                      //  mCurrLocationMarker = googleMap.addMarker(markerOptions);
                        Zoom zoom = Zoom.getInstance(getZoomLevel(mMap));
                        //move map camera
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(zoom.position,zoom.zoomLevel));
                 }
                }
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                googleMap.setMyLocationEnabled(true);
            } else {
                //Request Location Permission
                checkLocationPermission();
            }
        }else {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
            googleMap.setMyLocationEnabled(true);
        }

    }

    private float getZoomLevel(GoogleMap googleMap) {
        List<LatLng> locations = new ArrayList<>();
        float maxZoomLevel = googleMap.getMaxZoomLevel();
        float maxDist = -1;
        float distance = 0;
        Stand.getStands().forEach(s->locations.add(s.location));
        for (int i = 0; i < locations.size()-1; i++) {
            Location temp1 = new Location(LocationManager.GPS_PROVIDER);
            Location temp2 = new Location(LocationManager.GPS_PROVIDER);
            temp1.setLatitude(locations.get(i).latitude);
            temp1.setLongitude(locations.get(i).longitude);
            temp2.setLatitude(locations.get(i+1).latitude);
            temp2.setLongitude(locations.get(i+1).longitude);
             distance = temp1.distanceTo(temp2);
           if(distance>maxDist){
               maxDist = distance;
           }
        }

        double  res = Math.log(1080 / (distance * 1920));
        float asd = (float) Math.floor( maxZoomLevel+res);
        return asd;
    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MapsActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION );
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION );
            }
        }
    }
}
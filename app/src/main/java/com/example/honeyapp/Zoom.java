package com.example.honeyapp;

import android.app.Application;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

public class Zoom extends Application {
    public float zoomLevel;
    public LatLng position = new LatLng(55.42206633810837, 10.447166699880208);

    // Getter/setter

    private static Zoom instance;

    protected Zoom(float zoomLevel){
        this.zoomLevel = zoomLevel;
    }

    public static synchronized Zoom getInstance(float zoomLevel) {
        if(null == instance){
            Log.d("Zoom", "NEW INSTANCE CREATED");
            instance = new Zoom(zoomLevel);
        }
        return instance;
    }
}
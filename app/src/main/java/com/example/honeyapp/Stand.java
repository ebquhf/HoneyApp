package com.example.honeyapp;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class Stand {
    public String name;
    public LatLng location;
    public boolean hasHoney;
    private static List<Stand> testList;
    public Stand(String name,double latitude,double longitude,boolean honey){
        this.name=name;
        this.location = new LatLng(latitude,longitude);
        this.hasHoney = honey;
    }
     public static List<Stand> getStands(){
         if (testList !=null && testList.size() > 0){
             return testList;
         }
         else{
             testList  = new ArrayList<Stand>();
             testList.add(new Stand("first",55.399083881325396, 10.379019283535353,true));
             testList.add(new Stand("second",55.39470926481129, 10.368440650865,true));
             testList.add(new Stand("third",55.397975041049406, 10.381272339175085,true));
             testList.add(new Stand("fourth",55.39385621911716, 10.376616024186307,true));
             testList.add(new Stand("fifth",55.39479456836836, 10.371702217124419,true));
             return testList;
         }
     }
}

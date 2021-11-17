package com.example.honeyapp;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class Stand {
    public String name;
    public LatLng location;
    public boolean hasHoney;
    public String description;
    private static List<Stand> testList;
    public Stand(String name,double latitude,double longitude,boolean honey,String description){
        this.name=name;
        this.location = new LatLng(latitude,longitude);
        this.hasHoney = honey;
        this.description = description;
    }
     public static List<Stand> getStands(){
         if (testList !=null && testList.size() > 0){
             return testList;
         }
         else{
             testList  = new ArrayList<Stand>();
             testList.add(new Stand("Søren K. Rasmussen",55.422460196625394, 10.449149695897693,true,"Best organic honey in Odense!"));
             testList.add(new Stand("Peter Ingemann",55.42008547764949, 10.446928826842152,false,"Sweet as honey, Honey"));
             testList.add(new Stand("Morten Messerfedt",55.42235059735412, 10.444096414133636,true,"Just buy it ma'am it's honey!"));
             return testList;
         }
     }
     public static Stand getStandByLocation(LatLng location){
         if (testList !=null && testList.size() > 0){
             return testList.stream().filter((s)->s.location==location).findFirst().orElse(null);
         }
         else{
            return null;
         }
     }

    public static Stand getStandByName(String s) {
        if (testList !=null && testList.size() > 0){
            return testList.stream().filter((f)->f.name==s).findFirst().orElse(null);
        }
        else{
            return null;
        }
    }
}

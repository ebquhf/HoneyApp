package com.example.honeyapp;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class Stand {
    public String name;
    public LatLng location;
    public boolean hasHoney;
    public String description;
    public String url;
    private static List<Stand> testList;
    public Stand(String name,double latitude,double longitude,boolean honey,String description, String url){
        this.name=name;
        this.location = new LatLng(latitude,longitude);
        this.hasHoney = honey;
        this.description = description;
        this.url = url;
    }
     public static List<Stand> getStands(){
         if (testList !=null && testList.size() > 0){
             return testList;
         }
         else{
             testList  = new ArrayList<Stand>();
             testList.add(
                     new Stand("Søren K. Rasmussen",55.422460196625394, 10.449149695897693,true,"Best organic honey in Odense!", "https://st2.depositphotos.com/1006318/5909/v/950/depositphotos_59094701-stock-illustration-businessman-profile-icon.jpg"));
             testList.add(new Stand("Cecilie Knudsen",55.42008547764949, 10.446928826842152,false,"Sweet as honey, Honey", "https://st2.depositphotos.com/1006318/5909/v/950/depositphotos_59094623-stock-illustration-female-avatar-woman.jpg"));
             testList.add(new Stand("Petra Sofia",55.4860260598918, 9.445560617672358,true,"Just buy it ma'am it's honey!", "https://st2.depositphotos.com/1006318/5909/v/950/depositphotos_59094961-stock-illustration-businesswoman-profile-icon.jpg"));
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
            return testList.stream().filter((f)-> f.name.equals(s)).findFirst().orElse(null);
        }
        else{
            return null;
        }
    }
}

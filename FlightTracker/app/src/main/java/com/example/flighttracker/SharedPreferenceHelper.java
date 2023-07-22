package com.example.flighttracker;

import android.content.ContentProvider;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceHelper {

    private  static SharedPreferences sharedPreferences;
    private  static SharedPreferences.Editor editor;

    public static void initialize(Context context,String preferenceName)
    {
        if(sharedPreferences==null)
        {
           sharedPreferences = context.getSharedPreferences(preferenceName,Context.MODE_PRIVATE);
           editor = sharedPreferences.edit();
        }
    }

    public static void setStringValue(String key,String value)
    {
        editor.putString(key,value).commit();
    }

    public static  String getStringValue(String key)
    {
       return sharedPreferences.getString(key,null);
    }

}

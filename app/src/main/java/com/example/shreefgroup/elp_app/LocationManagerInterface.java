package com.example.shreefgroup.elp_app;

import android.location.Location;

/**
 * Created by ashfaq on 11/18/2016.
 */
public interface LocationManagerInterface {
    String TAG = LocationManagerInterface.class.getSimpleName();

    void locationFetched(Location mLocation, Location oldLocation, String time, String locationProvider);

}
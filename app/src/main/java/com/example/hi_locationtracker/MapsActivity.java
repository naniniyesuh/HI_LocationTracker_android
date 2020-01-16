package com.example.hi_locationtracker;

import android.Manifest;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.net.URL;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {


    public String photoUri;
    private GoogleMap mMap;
    private double userLat, userLong;
    private Location lastKnownLocation;
    private LatLng userLocation;
    TextView enlemBoylam;
    int minDistance = 5;
    String fullname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        System.out.println("_________________________________________MAP 0_______________________________________________");

        System.out.println("_________________________________________MAP 03_______________________________________________");
        minDistance = 5;
        System.out.println("_________________________________________MAP 04_______________________________________________");

        System.out.println("_________________________________________MAP 1_______________________________________________");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        System.out.println("_________________________________________MAP 2_______________________________________________");
        //onMapReady(this.mMap);
        System.out.println("_________________________________________MAP 3_______________________________________________");
        mapFragment.getMapAsync(this);

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        String locationProvider = LocationManager.GPS_PROVIDER;
        System.out.println("_________________________________________MAP 4_______________________________________________");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        System.out.println("_________________________________________MAP 5_______________________________________________");
        lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
        userLat = lastKnownLocation.getLatitude();
        userLong = lastKnownLocation.getLongitude();
        System.out.println("_________________________________________MAP 6_______________________________________________");



        System.out.println("_________________________________________MAP 7_______________________________________________");
        final Button button = (Button) findViewById(R.id.profileButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile_butonu(v);
            }
        });

        final Button settingsbutton = (Button) findViewById(R.id.settingButton);
        settingsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, minDistance, this);
        System.out.println("_________________________________________MAP 8_______________________________________________");

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
        mMap = googleMap;
        URL url;

        System.out.println("_________________________________________MAP 6 ______________________________________________");
        userLocation = new LatLng (userLat, userLong);
        photoUri = getIntent().getStringExtra("PHOTOURI"); //bunu yukarıdaki finalda yapmalı

        System.out.println("_________________________________________"+photoUri+" ______________________________________________");
        try {
            url = new URL(photoUri);


            System.out.println("_________________________________________"+url+" ______________________________________________");
            //Bitmap osman = getBitmapFromURL(); //image ı alamadım ____________________________________________________________

            mMap.addMarker(new MarkerOptions().position(userLocation).title("My Location"));
            System.out.println("_________________________________________ "+userLocation+" ______________________________________________");
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation,17.0f));

        } catch(IOException e) {
            System.out.println(e);
        }

        enlemBoylam = findViewById(R.id.enlemBoylam);
        enlemBoylam.setText(userLocation.latitude + " - " + userLocation.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));

    }
/*
    @SuppressLint("StaticFieldLeak")
    public Bitmap getBitmapFromURL() {

        final Bitmap[] myBitmap = new Bitmap[1];
        try {
            //Bitmap image = BitmapFactory.decodeStream(imageUrl.openConnection().getInputStream());
            final AsyncTask asyncTask = new AsyncTask() {

                @Override
                protected Object doInBackground(Object[] objects) {
                    URL url = imageUrl;
                    HttpURLConnection connection = null;
                    try {
                        connection = (HttpURLConnection) url.openConnection();

                        connection.setDoInput(true);
                        connection.connect();
                        InputStream input = connection.getInputStream();
                        myBitmap[0] = BitmapFactory.decodeStream(input);
                        return myBitmap[0];
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                    return myBitmap[0];
                }
            };
            asyncTask.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return myBitmap[0];
    }
*/
    @Override
    public void onLocationChanged(Location location) {
        mMap.clear();
        System.out.println("_________________________________________ onLocationChanged ______________________________________________");
        LatLng currentLoc = new LatLng(location.getLatitude(), location.getLongitude());
        System.out.println("_________________________________________"+ currentLoc+" ______________________________________________");
        mMap.addMarker(new MarkerOptions().position(currentLoc).title("My Location"));
        System.out.println("_________________________________________ marker ______________________________________________");
        enlemBoylam.setText(location.getLatitude() + " - " + location.getLongitude());
        System.out.println("_________________________________________ settext ______________________________________________");
        //this.showWarningMessage().notify();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private NotificationCompat.Builder showWarningMessage() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setContentTitle("GERİ DÖN")
                .setContentText("geridön")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        Notification osman = new Notification();

        return builder;
    }

    public void profile_butonu(View view) {

        fullname = getIntent().getStringExtra("FULLNAME");
        Intent intent = new Intent(MapsActivity.this, ProfileActivity.class);
        intent.putExtra("FULLNAME", fullname);
    }
}

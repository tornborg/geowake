package com.example.geowake;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.HapticFeedbackConstants;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import android.widget.FrameLayout;

import android.widget.SeekBar;
import android.widget.Toast;


import com.example.geowake.ThirdFragment.OnFragmentInteractionListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapsActivity extends FragmentActivity implements
        OnMapReadyCallback,
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        SecondFragment.OnFragmentInteractionListener,
        StartFragment.OnFragmentInteractionListener,
        OnFragmentInteractionListener {

    private GoogleMap mMap;
    static public final int REQUEST_LOCATION = 1;
    private Button setAlarm;
    private Location myLocation = new Location("");
    private float distanceInMeters = 10000;
    public Circle mCircle;
    private Marker mMarker;
    private Button setFavorite;
    private AutoCompleteTextView textInput;
    private FrameLayout fragmentContainer;
    private StartFragment startFragment;
    private boolean alarmIsActive = false;
    private int currentFragment = 0;
    private FragmentManager fragmentManager;
    private  FragmentTransaction transaction;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        fragmentContainer = (FrameLayout) findViewById(R.id.startFragment);
        textInput = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView2);
        setFavorite = (Button) findViewById(R.id.button2);

        openStartFragment();
    }

    public void openStartFragment() {
        startFragment = StartFragment.newInstance();
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.slide, R.anim.slideback);
        transaction.addToBackStack(null);
        transaction.add(R.id.main, startFragment, "START_FRAGMENT").commit();
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

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Check permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION);
        } else {
            mMap.setMyLocationEnabled(true); // <-- Start Beemray here
        }


        LatLng lund = new LatLng(55.70584, 13.19321);
        LatLng onon = new LatLng(55.714845, 13.213390);

        //inits
        mMap.setMinZoomPreference(13.0f);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(lund));
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        mMap.setOnMyLocationChangeListener(myLocationChangeListener);

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                if (currentFragment == 0) {
                    startFragment.openSecondFragment();

                    // First check if myMarker is null
                    if (mMarker == null && mCircle == null) {

                        // Marker was not set yet. Add marker:
                        mMarker = mMap.addMarker(new MarkerOptions()
                                .position(latLng)
                                .title("Destination")
                                .snippet("I'm going here"));

                        //Circle
                        mCircle = mMap.addCircle(new CircleOptions().center(latLng).radius(300).strokeColor(Color.RED).fillColor(0x22FF0000).strokeWidth(5));

                        //progress.setVisibility(View.VISIBLE);
                    } else {

                        // Marker already exists, just update it's position
                        mMarker.setPosition(latLng);
                        mCircle.setCenter(latLng);


                    }
                }
            }
        });


    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();

    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "Your Position", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;

    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions,
                                           int[] grantResults) {
        if (requestCode == REQUEST_LOCATION) {
            if (grantResults.length == 1
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mMap.setMyLocationEnabled(true); // <-- Start Beemray here
            } else {
                // Permission was denied or request was cancelled
            }
        }
    }

    public void openSetAlarm() {
        setAlarm.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        Intent intent = new Intent(this, SetAlarmActivity.class);
        startActivity(intent);

    }

    public void openFavorites() {
        setFavorite.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        Intent intent = new Intent(this, FavoritesActivity.class);
        startActivity(intent);
    }

    public void openAlarmScreen() {
        Intent intent = new Intent(this, AlarmScreen.class);
        startActivity(intent);
    }

    public void setDestination() {
        if (startFragment.textInput.getText().toString().equals("Lund LTH")) {
            LatLng lth = new LatLng(55.712028, 13.208590);
            CameraUpdate current = CameraUpdateFactory.newLatLngZoom(lth, 15);

            mMap.animateCamera(current);
            if (mMarker == null && mCircle == null) {

                // Marker was not set yet. Add marker:
                mMarker = mMap.addMarker(new MarkerOptions()
                        .position(lth)
                        .title("Destination")
                        .snippet("I'm going here"));

                //Circle
                mCircle = mMap.addCircle(new CircleOptions().center(lth).radius(300).strokeColor(Color.RED).fillColor(0x22FF0000).strokeWidth(5));


            } else {

                // Marker already exists, just update it's position
                mMarker.setPosition(lth);
                mCircle.setCenter(lth);


            }
        }
    }


    private GoogleMap.OnMyLocationChangeListener myLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
        @Override
        public void onMyLocationChange(Location location) {
            myLocation = location;
            Location circle = new Location("");


            if (mCircle != null && alarmIsActive == true) {
                circle.setLatitude(mCircle.getCenter().latitude);
                circle.setLongitude(mCircle.getCenter().longitude);
                distanceInMeters = circle.distanceTo(myLocation);
                if (distanceInMeters < mCircle.getRadius()) {
                    //Trigger Alarm
                    openAlarmScreen();
                }
            }

        }
    };

    @Override
    public void onFragmentInteraction() {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void changeAlarmStatus() {
        alarmIsActive = !alarmIsActive;
    }

    public void reset() {

        if (mCircle != null && mCircle != null) {
            mCircle.remove();
            mCircle = null;

            mMarker.remove();
            mMarker = null;
        }
    }

    public void setFragment(int current) {
        currentFragment = current;
    }

    public void onBackPressed() {

        if (currentFragment == 0) {
        }
        if (currentFragment == 1) {
            super.onBackPressed();
            startFragment.textInput.clearComposingText();

            transaction.setCustomAnimations(R.anim.slide, R.anim.slideback);

        }

        if (currentFragment == 2) {
        }

    }
}



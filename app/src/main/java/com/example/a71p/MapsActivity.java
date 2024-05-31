package com.example.a71p;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.a71p.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private LatLng locationLatLng;
    private String locationName;
    LatLng locationlatlng;
    MyDatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        myDB = new MyDatabaseHelper(this);

//        Intent intent = getIntent();
//        if (intent != null) {
//            locationName = intent.getStringExtra("locationName");
//            locationLatLng = intent.getParcelableExtra("locationLatLng");
//            setTitle(locationName);
//
//
//        }

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
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        Cursor cursor = myDB.getAllItems();
        if (cursor != null && cursor.moveToFirst()){
            do{

                String name = cursor.getString(cursor.getColumnIndexOrThrow(MyDatabaseHelper.COLUMN_NAME));
                double lat = cursor.getDouble(cursor.getColumnIndexOrThrow(MyDatabaseHelper.COLUMN_LATITUDE));
                double lng = cursor.getDouble(cursor.getColumnIndexOrThrow(MyDatabaseHelper.COLUMN_LONGITUDE));
                Log.e("LATLNG", "LAT: "+ lat + " LNG: " + lng);

                locationlatlng = new LatLng(lat,lng);
                Log.e("LcationLATLNG", locationlatlng.toString());

                mMap.addMarker(new MarkerOptions().position(locationlatlng).title(name));

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationlatlng, 10));
            }while (cursor.moveToNext());
            cursor.close();
        }



//        if (locationlatlng != null){
//            mMap.addMarker(new MarkerOptions().position(locationlatlng).title(MyDatabaseHelper.COLUMN_NAME));
//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationlatlng, 10));
//        }

    }
}
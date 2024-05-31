package com.example.a71p;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.List;

public class CreateActivity extends AppCompatActivity {
    EditText Name, Phone, Desc, Date, Location;
    RadioGroup posttype;
    Button Save;
    MyDatabaseHelper myDB;

    LatLng selectedLocation;

//    private final ActivityResultLauncher<Intent> startAutocomplete = registerForActivityResult(
//            new ActivityResultContracts.StartActivityForResult(),
//            result -> {
//                if (result.getResultCode()== Activity.RESULT_OK) {
//                    Intent intnet = result.getData();
//                    if (intnet != null) {
//                        Place place = Autocomplete.getPlaceFromIntent(intnet);
//                        //Log.i(...)
//                    }
//                } else if (result.getResultCode() == Activity.RESULT_CANCELED){
//                    //Log.i(TAG, "User canceled autocomplete");
//                }
//            }
//    );

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);


        Places.initialize(getApplicationContext(), "AIzaSyASUMb_UECTd7uDuIB-W_5NOcGLD42VNXY");

        PlacesClient placesClient = Places.createClient(this);

        Toolbar tool = findViewById(R.id.toolbar);
        setSupportActionBar(tool);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Name = findViewById(R.id.editTextText);
        Phone = findViewById(R.id.editTextText2);
        Desc = findViewById(R.id.editTextText3);
        Date = findViewById(R.id.editTextText4);
        Location = findViewById(R.id.editTextText5);
        posttype = findViewById(R.id.posttype);
        Save = findViewById(R.id.button3);

        myDB = new MyDatabaseHelper(this);


        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
            getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);
        autocompleteFragment.setPlaceFields(fields);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onError(@NonNull Status status) {
                Toast.makeText(CreateActivity.this, "Error: " +status.getStatusMessage(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPlaceSelected(@NonNull Place place) {
                Location.setText(place.getName());

                String locationName = place.getName();
                LatLng locationLatLng = place.getLatLng();
                selectedLocation = place.getLatLng();

                if (locationLatLng != null) {
                    Log.e("LatLng", "Lat: " + locationLatLng.latitude + "lng: " + locationLatLng.longitude);
                    Intent intent = new Intent(CreateActivity.this, MapsActivity.class);
                    intent.putExtra("locationName", locationName);
                    intent.putExtra("locationLatLng", locationLatLng);
////                    startActivity(intent);
                    Log.e("LatLng", locationLatLng.toString());
//                    selectedLocation.latitude = locationLatLng.latitude;

                } else {
                    Log.e("LATLNG", "Location is null");
                }
            }
        });

//        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME);
//
//        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields).build(this);
//        startAutocomplete.launch(intent);



        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int SelectedId = posttype.getCheckedRadioButtonId();
                RadioButton selectedbutton = findViewById(SelectedId);
                String postYpe = selectedbutton.getText().toString();

                if (selectedLocation != null)
                {
                    boolean isInserted = myDB.insertItem(postYpe, Name.getText().toString(), Phone.getText().toString(), Desc.getText().toString(), Date.getText().toString(), Location.getText().toString(), selectedLocation.latitude, selectedLocation.longitude);

                    if (isInserted){
                        Toast.makeText(CreateActivity.this, "Data Created", Toast.LENGTH_SHORT).show();
                    } else{
                        Toast.makeText(CreateActivity.this, "Data unable to be Created", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CreateActivity.this, "Error: Selected Location is null", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

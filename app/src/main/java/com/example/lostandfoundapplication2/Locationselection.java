package com.example.lostandfoundapplication2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.PackageManagerCompat;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class Locationselection extends AppCompatActivity {

    EditText locationedit;
    Button CurrentLocationButton, back_button;
    TextView textview1, textview2, textview6;
    FusedLocationProviderClient fusedLocationProviderClient;


    public static String API_KEY = "AIzaSyDTkCMouwK4GuTUcAsZBglpj5tg99Jo1qw";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locationselection);



        textview1 = findViewById(R.id.textView3);
        textview2 = findViewById(R.id.textView4);
        textview6 = findViewById(R.id.textView6);
        locationedit = findViewById(R.id.LocationBox);
        back_button = findViewById(R.id.back_button);
        CurrentLocationButton = findViewById(R.id.currentButton);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


        //INITIALIZE places
        Places.initialize(getApplicationContext(), API_KEY);

        locationedit.setFocusable(false);
        locationedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //initialize place field list
                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS
                        , Place.Field.LAT_LNG, Place.Field.NAME);

                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY
                        , fieldList).build(Locationselection.this);

                //start Activity
                startActivityForResult(intent, 100);
            }
        });



        CurrentLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ActivityCompat.checkSelfPermission(Locationselection.this,
                        Manifest.permission.ACCESS_FINE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {

                    getLocation();
                } else {
                    ActivityCompat.requestPermissions(Locationselection.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
            }
        });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Double Longitude = Double.valueOf(textview2.getText().toString());
                Double Latitude = Double.valueOf(textview1.getText().toString());
                String Location_name = textview6.getText().toString();

                Intent data = new Intent();
                data.putExtra("latitude",Latitude );
                data.putExtra("longitude", Longitude);
                data.putExtra("location_name", Location_name);
                setResult(RESULT_OK, data);
                finish();

            }
        });


        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    // this event will enable the back
    // function to the button on press
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }







    private void getLocation() {

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
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null)
                    try {
                        Geocoder geocoder = new Geocoder(Locationselection.this,
                                Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(
                                location.getLatitude(), location.getLongitude(), 1
                        );
                        //set latitude on textview
                        textview1.setText(
                                 String.valueOf(addresses.get(0).getLatitude()));
                        textview2.setText(String.valueOf(
                                        +addresses.get(0).getLongitude()));
                        textview6.setText("My Location");
                    } catch (IOException e) {
                        e.printStackTrace();

                    }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode ==RESULT_OK)
        {
            Place place = Autocomplete.getPlaceFromIntent(data);
            locationedit.setText(place.getAddress());
            textview1.setText(String.valueOf(place.getLatLng().latitude));
            textview2.setText(String.valueOf(place.getLatLng().longitude));
            textview6.setText(place.getName());
        }else if(resultCode == AutocompleteActivity.RESULT_ERROR)
        {
            Status status = Autocomplete.getStatusFromIntent(data);
            Toast.makeText(getApplicationContext(), ((Status) status).getStatusMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }
}
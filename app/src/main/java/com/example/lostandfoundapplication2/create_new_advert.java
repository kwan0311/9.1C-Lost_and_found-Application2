package com.example.lostandfoundapplication2;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class create_new_advert extends AppCompatActivity {
    RadioGroup post_type;
    RadioButton radioButton1;
    Button saveButton;
    EditText name_, phone_, description_,  location_latitude, location_longitude, date_;
    String Name,  Description,  Date, Location_name;
    Double Location_latitude, Location_longitude;
    Integer Phone;

    ActivityResultLauncher<Intent> activityResultLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult activityResult) {
                            int result = activityResult.getResultCode();
                            Intent data = activityResult.getData();

                            if (result == RESULT_OK) {
                                Double Longitude = data.getDoubleExtra("longitude",0);
                                Double Latitude = data.getDoubleExtra("latitude",0);
                                Location_name = data.getStringExtra("location_name");
                                location_latitude.setText(""+Latitude);
                                location_longitude.setText(""+Longitude);
                                Toast.makeText(create_new_advert.this, "Saved successfully", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(create_new_advert.this, "Operation canceled", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
            );



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_advert);
        post_type = findViewById(R.id.Post_type);
        saveButton = findViewById(R.id.saveButton);
        name_ = findViewById(R.id.enter_name);
        phone_ = findViewById(R.id.enter_phone);
        description_ = findViewById(R.id.enter_description);
        location_latitude = findViewById(R.id.enter_Location_latitude);
        location_longitude = findViewById(R.id.enter_Location_longtitude);
        date_ = findViewById(R.id.enter_date);



        location_latitude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(create_new_advert.this, Locationselection.class);
                //startActivityForResult(intent, 0);
                activityResultLauncher.launch(intent);

            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Goodtype;
                int radioId = post_type.getCheckedRadioButtonId();
                radioButton1 = findViewById(radioId);
                Goodtype = radioButton1.getText().toString().trim();

                DataBaseForItems db = new DataBaseForItems(create_new_advert.this);

                Name = name_.getText().toString().trim();
                Phone = Integer.valueOf(phone_.getText().toString().trim());
                Description = description_.getText().toString().trim();
                Location_latitude = Double.valueOf(location_latitude.getText().toString().trim());
                Location_longitude = Double.valueOf(location_longitude.getText().toString().trim());
                Date = date_.getText().toString().trim();

                db.add_Data1(
                        Name,
                        Goodtype,
                        Phone,
                        Description,
                        Date,
                        Location_latitude,
                        Location_longitude,
                        Location_name

                );


            }
        });


    }

    public void checkButton(View v){
        int radioId = post_type.getCheckedRadioButtonId();
        radioButton1 = findViewById(radioId);
        Toast.makeText(create_new_advert.this, "Selected radio Button: " + radioButton1.getText(), Toast.LENGTH_SHORT).show();
    }


    protected void onActivity(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }
}
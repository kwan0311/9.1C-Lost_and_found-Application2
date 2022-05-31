package com.example.lostandfoundapplication2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DeleteActivity extends AppCompatActivity {

    TextView posttype1, name1, phone1, description1, date1,location1;
    Button Delete_;
    String posttype2, name2, phone2, description2, date2,location2, Good_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        posttype1 = findViewById(R.id.post_type_final);
        name1 = findViewById(R.id.name_final);
        phone1 = findViewById(R.id.phone_final);
        description1 = findViewById(R.id.Description_final);
        date1 =findViewById(R.id.date_final);
        location1 = findViewById(R.id.Location_final);
        Delete_ = findViewById(R.id.Delete_Button);
        getIntentData();

        Delete_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });
    }

    void getIntentData(){
        if(getIntent().hasExtra("name")&&getIntent().hasExtra("post_type")&&
                getIntent().hasExtra("phone") && getIntent().hasExtra("date")&&
                getIntent().hasExtra("description")&&getIntent().hasExtra("location")
                && getIntent().hasExtra("good_id")){

            Good_id= getIntent().getStringExtra("good_id");
            name2= getIntent().getStringExtra("name");
            posttype2 = getIntent().getStringExtra("post_type");
            phone2 = getIntent().getStringExtra("phone");
            description2 = getIntent().getStringExtra("description");
            date2 = getIntent().getStringExtra("date");
            location2 = getIntent().getStringExtra("location");

            posttype1.setText(posttype2);
            name1.setText(name2);
            phone1.setText(phone2);
            description1.setText(description2);
            date1.setText(date2);
            location1.setText(location2);
        }
        else
        {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete this message?");
        builder.setMessage("Are you sure you want to delete ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DataBaseForItems db = new DataBaseForItems(DeleteActivity.this);

                db.deleteOneRow(Good_id);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.create().show();

    }
}
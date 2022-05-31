package com.example.lostandfoundapplication2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class display_page extends AppCompatActivity {

    RecyclerView recyclerView;

    DataBaseForItems db;
    ArrayList<String> good_id,user_name, post_type, phone_no_, description, date_, Location_;
    Recycler_Adapter myadapter;
    Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_page);
        recyclerView = findViewById(R.id.Item_list);

        db = new DataBaseForItems(display_page.this);
        good_id = new ArrayList<>();
        post_type = new ArrayList<>();
        description = new ArrayList<>();
        user_name =  new ArrayList<>();
        phone_no_ = new ArrayList<>();
        date_= new ArrayList<>();
        Location_= new ArrayList<>();



        storeDataInArrays();

        myadapter = new Recycler_Adapter(display_page.this, good_id, user_name,post_type, phone_no_,description,date_,Location_);
        recyclerView.setAdapter(myadapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(display_page.this));

    }


        void storeDataInArrays() {
            Cursor cursor = db.readAllData();
            if (cursor.getCount() == 0) {
                Toast.makeText(display_page.this, "No data", Toast.LENGTH_SHORT).show();
            } else {
                while (cursor.moveToNext()) {
                    good_id.add(cursor.getString((0)));
                    user_name.add(cursor.getString((1)));
                    post_type.add(cursor.getString((2)));
                    phone_no_.add(cursor.getString((3)));
                    description.add(cursor.getString((4)));
                    date_.add(cursor.getString((5)));
                    Location_.add(cursor.getString((6)));

                }
            }
        }
}
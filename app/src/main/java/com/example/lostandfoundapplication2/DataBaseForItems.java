package com.example.lostandfoundapplication2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DataBaseForItems extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "DataBase_for_items";
    private static final int DATABASE_VISION = 1;

    private static  final String TABLE_NAME = "Lost_and_Found_table";
    private static  final String COLUMN_ID = "_id";
    private static final String COL1_NAME = "Fullname";
    private static final String COL2_POST_TYPE = "Type";
    private static final String COL3_PHONE = "Phone";
    private static final String COL4_DESCRIPTION = "Description";
    private static final String COL5_DATE = "Date";
    private static final String COL6_LOCATION_LATITUDE = "Location_latitude";
    private static final String COL7_LOCATION_LONGITUDE = "Location_longitude";
    private static final String COL8_LOCATION_NAME = "Location_Name";







    public DataBaseForItems(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VISION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL1_NAME + " TEXT, " +
                COL2_POST_TYPE + " TEXT, " +
                COL3_PHONE + " INTEGER, " +
                COL4_DESCRIPTION + " TEXT, " +
                COL5_DATE + " TEXT, " +
                COL6_LOCATION_LATITUDE + " DOUBLE, " +
                COL8_LOCATION_NAME + " TEXT, " +
                COL7_LOCATION_LONGITUDE + " DOUBLE);";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }


    void add_Data1(String Name, String Post_type, int Phone, String Description, String Date, double Latitude, double Longitude, String Location_Name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL1_NAME, Name);
        contentValues.put(COL2_POST_TYPE, Post_type);
        contentValues.put(COL3_PHONE, Phone);
        contentValues.put(COL4_DESCRIPTION, Description);
        contentValues.put(COL5_DATE, Date);
        contentValues.put(COL6_LOCATION_LATITUDE, Latitude);
        contentValues.put(COL7_LOCATION_LONGITUDE, Longitude);
        contentValues.put(COL8_LOCATION_NAME, Location_Name);


        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1){
            Toast.makeText(context,"SORRY", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context,"OK!!!!!!!!!", Toast.LENGTH_SHORT).show();
        }

    }


    Cursor readAllData(){
        String query = "SELECT * FROM "+ TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null)
        {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void deleteOneRow(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});

        if (result == -1) {
            Toast.makeText(context, "Delete Failed", Toast.LENGTH_SHORT).show();
        } else
        {
            Toast.makeText(context,"Successfully Deleted", Toast.LENGTH_SHORT).show();
        }
    }

}


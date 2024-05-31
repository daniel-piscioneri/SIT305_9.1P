package com.example.a71p;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "lostfound1.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "items";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_POSTTYPE = "post_type";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_DESC = "desc";
    public static final String COLUMN_LOCATION = "location";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_LATITUDE = "latitude"; // New column for latitude
    public static final String COLUMN_LONGITUDE = "longitude";
    public MyDatabaseHelper( Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public boolean insertItem(String postYpe, String string, String string1,
                              String string2, String string3, String string4, double latitude, double longitude) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_POSTTYPE, postYpe);
        contentValues.put(COLUMN_NAME, string);
        contentValues.put(COLUMN_PHONE, string1);
        contentValues.put(COLUMN_DESC,string2);
        contentValues.put(COLUMN_DATE, string3);
        contentValues.put(COLUMN_LOCATION,string4);
        contentValues.put(COLUMN_LATITUDE, latitude); // Insert latitude
        contentValues.put(COLUMN_LONGITUDE, longitude);
        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_POSTTYPE + " TEXT, " + COLUMN_NAME + " TEXT, " + COLUMN_PHONE + " TEXT, " + COLUMN_DESC + " TEXT, " +
                COLUMN_LOCATION + " TEXT, " + COLUMN_DATE + " TEXT, " + COLUMN_LATITUDE + " REAL, " + COLUMN_LONGITUDE + " REAL)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public Cursor getAllItems(){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    public Integer deleteItem(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, COLUMN_ID +" = ?", new String[]{id});
    }
}

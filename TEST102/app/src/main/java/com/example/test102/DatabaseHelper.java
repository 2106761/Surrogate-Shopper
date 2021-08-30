package com.example.test102;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;


public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "d2106761";

    public static final String TABLE_NAME = "ITEMS";
    public static final String COL1 = "ID";
    public static final String COL2 = "ITEMNAME";
    public static final String COL3 = "ITEMSIZE";
    public static final String COL4 = "ITEMPRICE";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, ITEMNAME TEXT NOT NULL, ITEMSIZE TEXT NOT NULL, ITEMPRICE TEXT NOT NULL, UNIQUE(ITEMNAME))";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public void Del() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    boolean AddData(String item1, String item2, String item3) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, item1);
        contentValues.put(COL3, item2);
        contentValues.put(COL4, item3);

        long result = db.insert(TABLE_NAME, null, contentValues);

        //if date as inserted incorrectly it will return -1
        if(result == -1) {
            return false;
        } else {
            return true;
        }
    }
    public Cursor getListContents(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return data;
    }
    public int deleteRow(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, COL1 + "=?", new String[]{String.valueOf(id)}) ;
    }

    public void deleteAll()
    {
        Cursor data = getListContents();
        long rowId = data.getColumnIndexOrThrow(COL1);
        if (data.moveToFirst()){
            do{
                deleteRow((int) data.getLong((int)rowId));
            }while (data.moveToNext());
        }
        data.close();
    }

}



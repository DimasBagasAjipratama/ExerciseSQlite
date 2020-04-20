package com.example.exercisesqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String MHS_TABLE_NAME = "mahasiswa";
    public static final String MHS_COLUMN_NAMA = "nama";
    public static final String MHS_COLUMN_NO_TELEPON = "no_telepon";
    public static final String MHS_COLUMN_EMAIL = "email";
    public static final String MHS_COLUMN_ALAMAT = "alamat";
    private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table mahasiswa " +
                        "(id integer primary key, nama text,no_telepon text,email text,alamat text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS mahasiswa");
        onCreate(db);
    }

    public boolean insertContact (String nama, String no_telepon, String email, String alamat) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nama", nama);
        contentValues.put("no_telepon", no_telepon);
        contentValues.put("email", email);
        contentValues.put("alamat", alamat);

        db.insert("mahasiswa", null, contentValues);
        return true;
    }

    public Cursor getData(int nama) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from mahasiswa where nama="+nama+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, MHS_TABLE_NAME);
        return numRows;
    }

    public ArrayList<String> getAllCotacts() {
        ArrayList<String> array_list = new ArrayList<String>();
        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from mahasiswa", null );
        res.moveToFirst();
        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(MHS_COLUMN_NAMA)));
            res.moveToNext();
        }
        return array_list;
    }
}


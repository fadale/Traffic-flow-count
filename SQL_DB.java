package com.example.alawael.trafficflowcount;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQL_DB extends SQLiteOpenHelper {

    private static final String DBtraffic="traffic.db";
    public SQL_DB(Context context) {
        super(context,DBtraffic,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table Sheets (ID_SHEET INTEGER PRIMARY KEY AUTOINCREMENT,SHEET_NO INTEGER, SHEET_DAY TEXT,SHEET_DATE TEXT,TIME_FROM TEXT ,TIME_TO TEXT," +
                "WEATHER TEXT ,STREET_NAME TEXT,POSITION_A TEXT,POSITION_B TEXT)");


        db.execSQL("CREATE TABLE CATAGORYCAR(ID_CTG INTEGER PRIMARY KEY AUTOINCREMENT,NAME_CTG TEXT,COUNT_CTG TEXT,SHEET_NO INTEGER)");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS SHEETS");
        db.execSQL("DROP TABLE IF EXISTS CATAGORYCAR");
        onCreate(db);
    }

    public boolean insert_sheet(String sheetno,String day,String date,String timefrom,String timeto,String weather,String street,String posiona,String positionb ){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues convs=new ContentValues();

        convs.put("SHEET_NO",sheetno);
        convs.put("SHEET_DAY",day);
        convs.put("SHEET_DATE",date);
        convs.put("TIME_FROM",timefrom);
        convs.put("TIME_TO",timeto);
        convs.put("WEATHER",weather);
        convs.put("STREET_NAME",street);
        convs.put("POSITION_A",posiona);
        convs.put("POSITION_B",positionb);
        long result = sqLiteDatabase.insert("Sheets",null,convs);
        if(result ==-1)
            return false;
            else
        return true;
    }


    public boolean inset_count_car(String catagory_car,String count,int sheetnum){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues convs=new ContentValues();
        convs.put("NAME_CTG",catagory_car);
        convs.put("COUNT_CTG",count);
        convs.put("SHEET_NO",sheetnum);
        long result = sqLiteDatabase.insert("CATAGORYCAR",null,convs);
        if(result ==-1)
            return false;
        else
            return true;
    }


    public Cursor select_cars(int sheetno){
       SQLiteDatabase db=this.getReadableDatabase();
       Cursor cursor=db.rawQuery("select * from CATAGORYCAR where SHEET_NO ="+sheetno,null);
        return cursor;
    }
    public Cursor select_sheet(int sheet){
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from Sheets where SHEET_NO ="+sheet,null);
        return cursor;
    }

    public Cursor shecksheetno (int sheet){
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor=db.rawQuery("select SHEET_NO from Sheets where SHEET_NO ="+sheet,null);
        return cursor;
    }


    public boolean update_timeto(String timeto, int sheet){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("TIME_TO",timeto);

        int i = db.update("sheets",cv,"SHEET_NO ="+sheet,null);
        return i>0;
    }
}

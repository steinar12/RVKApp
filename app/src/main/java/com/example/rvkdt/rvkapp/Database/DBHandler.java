package com.example.rvkdt.rvkapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "database.db";

    public static final String TABLE_LIKED_BARS = "liked_bars";
    public static final String LIKED_BARS_COLUMN_ID = "id";

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        System.out.println("  ---  HALLO ---  ");

        String query = "CREATE TABLE " + TABLE_LIKED_BARS + "(" +
                LIKED_BARS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" +
                ");";
        System.out.println(query);
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LIKED_BARS);
        onCreate(db);
    }

    public void addLikedBarId (int id) {
        ContentValues values = new ContentValues();
        values.put(LIKED_BARS_COLUMN_ID, id);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_LIKED_BARS, null, values);
        db.close();

    }
    public void removeBarId (int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_LIKED_BARS, LIKED_BARS_COLUMN_ID + "=" + id, null);
        db.close();
    }
    public int[] getLikedBarIds (){
        SQLiteDatabase db = getWritableDatabase();

        Cursor resultSet = db.rawQuery("SELECT * FROM " + TABLE_LIKED_BARS + ";",null);


        Log.d("hello",resultSet.toString());
        int resultCount = resultSet.getCount();

        if(resultCount == 0) return null;



        resultSet.moveToFirst();

        int[] returnIds = new int[resultCount];
        returnIds[0] = resultSet.getInt(0);
        for (int i = 1; i < resultCount; i++){
            resultSet.moveToNext();
            returnIds[i] = resultSet.getInt(0);
        }
        db.close();
        return returnIds;

    }
}

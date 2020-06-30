package com.example.sensorbasebiometricauthentification;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class databaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "info.db";
    public static final String TABLE_NAME = "registeruser";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "username";
    public static final String COL_3 = "password";
    public static int G_Id = 0;
    //------------------------------------------------------------
    public static final String TABLE_NAME2 = "sensor_data";
    private static final String ID = "id";
    private static final String acc_x = "X";
    private static final String acc_y = "Y";
    private static final String acc_z = "Z";
    private static final String id_sensor = "id_user_sensor";
    public static String K;


    public databaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE registeruser (ID INTEGER PRIMARY  KEY AUTOINCREMENT, username TEXT, password TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE  sensor_data  ( id_sensor  INTEGER PRIMARY  KEY AUTOINCREMENT,X float, Y float, Z float, id_user_sensor int,FOREIGN KEY(id_user_sensor) REFERENCES registeruser(ID)  )");
      //  sqLiteDatabase.execSQL("CREATE TABLE info_data (id_info INTEGER , X_info float , Y_info float , Z_info float ,  FOREIGN KEY(id_info) REFERENCES registeruser(ID),FOREIGN KEY(X_info ) REFERENCES sensor_data (X),FOREIGN KEY(Y_info) REFERENCES sensor_data (Y),FOREIGN KEY(Z_info) REFERENCES sensor_data (Z))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME2);
        onCreate(sqLiteDatabase);
    }

    public long addUser(String user, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", user);
        contentValues.put("password", password);

        long res = db.insert(TABLE_NAME, null, contentValues);
        //   db.close();

        return res;
    }


    public boolean checkUser(String username, String password) {
        String[] columns = {COL_1};
        SQLiteDatabase db = getReadableDatabase();
        String selection = COL_2 + "=?" + " and " + COL_3 + "=?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        if (cursor.moveToFirst()) {
            int _id = cursor.getInt(cursor.getColumnIndex(COL_1));
            G_Id=_id;
        }
      //  int numRows = (int) DatabaseUtils.longForQuery(db, "SELECT  ID  FROM "+TABLE_NAME, null);
      //  G_Id=numRows;
        //cursor.close();
        db.close();

        if (count > 0) {
            return true;

        } else
            return false;
    }

    //----------------------------------------------------------------------------------------------
    public boolean addvalue(String X, String Y, String Z, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(acc_x, X);
        contentValues.put(acc_y, Y);
        contentValues.put(acc_z, Z);
      contentValues.put(id_sensor,id);

        long res = db.insert(TABLE_NAME2, null, contentValues);
        if (res == -1)
            return false;
        else
            return true;


    }


    public ArrayList getAllrecord1() {
        ArrayList arrayList = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from " + TABLE_NAME2;
        Cursor res = db.rawQuery(query, null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            float d1 = res.getFloat(1);
            float d2 = res.getFloat(2);
            float d3 = res.getFloat(3);
            int d4 = res.getInt(4);
        arrayList.add(d1 + "    " + d2 + "     " + d3 + "      " +d4);
            res.moveToNext();
            Log.d("sql error", "show Succes");
        }

        return arrayList;

    }



}



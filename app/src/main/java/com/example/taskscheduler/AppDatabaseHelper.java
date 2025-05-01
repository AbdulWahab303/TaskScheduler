package com.example.taskscheduler;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AppDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="taskSchedulerDb";
    public static final int DATABASE_VERSION=1;

    Context context;




    public AppDatabaseHelper(Context c){
        super(c,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query="CREATE TABLE Tasks (_id INTEGER PRIMARY KEY AUTOINCREMENT, _title TEXT ,_description TEXT, _datetime TEXT,_status INTEGER)";
        db.execSQL(query);

        String query2="CREATE TABLE Notifications (_id INTEGER PRIMARY KEY AUTOINCREMENT, _message TEXT, _datetime TEXT)";
        db.execSQL(query2);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS Tasks");
        db.execSQL("DROP TABLE IF EXISTS Notifications");
        onCreate(db);
    }
}

package com.example.taskscheduler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class TasksDao {

    private SQLiteDatabase db;
    public static final String TABLE_NAME="Tasks";

    final String COLUMN_ID="_id";
    final String COLUMN_TITLE="_title";
    final String COLUMN_DESCRIPTION="_description";
    final String COLUMN_DATETIME="_datetime";
    final String COLUMN_STATUS="_status";


    private TaskModel lastInsertedData;

    public TasksDao(Context c){
        AppDatabaseHelper helper=new AppDatabaseHelper(c);
        db=helper.getWritableDatabase();
    }

    public long insert(String title,String description,String datetime,int status){
        Log.d("CHECKING",title);
        Log.d("CHECKING",description);
        Log.d("CHECKING",datetime);
        ContentValues cv=new ContentValues();
        cv.put("_title",title);
        cv.put("_description",description);
        cv.put("_datetime",datetime);
        cv.put("_status",status);
        return db.insert(TABLE_NAME,null,cv);
    }

    public ArrayList<TaskModel> getAllTasks(){
        ArrayList<TaskModel>tasks=new ArrayList<>();

        Cursor cursor=db.query(TABLE_NAME,null,null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                int id=cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                String title=cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE));
                String description=cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION));
                String datetime=cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATETIME));
                int status=cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STATUS));

                TaskModel task=new TaskModel(id,title,description,datetime,status);
                tasks.add(task);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return tasks;
    }

    public int deleteTask(int id){
        return db.delete(TABLE_NAME,COLUMN_ID+"=?",new String[]{String.valueOf(id)});
    }



}

package br.edu.ifsuldeminas.mch.todolist.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DAO {
    private DBHandler dbHandler = null;

    DAO(Context context){
        if (dbHandler == null)
            dbHandler = new DBHandler(context);
    }

    SQLiteDatabase openToWrite(){
        return dbHandler.getWritableDatabase();

    }

    SQLiteDatabase openToRead(){
        return dbHandler.getReadableDatabase();

    }
}
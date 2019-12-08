package com.example.iloveallah.moviesapp1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

/**
 * Created by I Love Allah on 11/16/2016.
 */
public class Database extends SQLiteOpenHelper {
    static final String Database_name="moviesApp";
    static  final  String TABLE_NAME="Movies";
    static  final  String title="title";
    static  final  String image="image";
    static  final  String rate="rate";
    static  final  String date="date";
    static  final  String overview="overview";
    static  final  String movie_id="movie_id";
    static  final String ID="id";
    static final int version=1;
    public Database(Context context) {
        super(context, Database_name, null, version);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE="CREATE TABLE " +  TABLE_NAME  + " ("  +
                ID  + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                title+ " TEXT UNIQUE NOT NULL, " +
               image + " TEXT UNIQUE NOT NULL, " +
                rate + " TEXT  NOT NULL, " +
                date + " TEXT  NOT NULL, " +
                overview + " TEXT  NOT NULL, " +
                movie_id + " TEXT UNIQUE NOT NULL " +
                ");";
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP"+TABLE_NAME+" ;");
        onCreate(sqLiteDatabase);
    }
    public static void deleteDatabase(Context context) {
        context.deleteDatabase(Database_name);
    }
}

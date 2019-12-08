package com.example.iloveallah.moviesapp1;

import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by I Love Allah on 03/20/2017.
 */

public class ContentProvider extends android.content.ContentProvider {
    private  UriMatcher MATCHER=buildMatcher() ;
    static final int movie = 1;
    private Database db=new Database(getContext());
   public static UriMatcher buildMatcher()
    {
        UriMatcher uriMatcher= new UriMatcher(UriMatcher.NO_MATCH);
uriMatcher.addURI(Contract.CONTENT_AUTHORITY, Database.TABLE_NAME,movie);
        return uriMatcher;
    }
    @Override
    public boolean onCreate() {
        db=new Database(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        Cursor cursor;
        switch (MATCHER.match(uri)) {
            case movie: {
                cursor = db.getReadableDatabase().query(
                        Database.TABLE_NAME, strings, s, strings1,
                        null, null, s1);
                break;
            }
            default:
                throw new UnsupportedOperationException("error");
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        SQLiteDatabase sql_db = db.getWritableDatabase();
        Uri newUri;
        switch (MATCHER.match(uri)) {
            case movie: {
                long id = sql_db.insert(Database.TABLE_NAME, null, contentValues);
                if (id > 0) {
                    newUri = Contract.MovieEntry.MovieUri(id);
                }
                else {
                    throw new android.database.SQLException("insert Failed " );
                }
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri");
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return newUri;

    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
       /* final SQLiteDatabase s_db = db.getWritableDatabase();
        int deletedMovie;
        if (null == s) s = "1";
        switch (MATCHER.match(uri)) {
            case movie:
                deletedMovie = s_db.delete(
                        Database.TABLE_NAME, s, strings);
                break;
            default:
                throw new UnsupportedOperationException("error");
        }
            getContext().getContentResolver().notifyChange(uri, null);

        return deletedMovie;*/
/*

        if (uri.equals(Contract.BASE_CONTENT_URI)) {
            deleteDatabase();
            notifyChange(uri);
            return 1;
        }
        final SQLiteDatabase s_db = db.getWritableDatabase();
        int deletedMovie;
        switch (MATCHER.match(uri)) {
            case movie:
                deletedMovie = s_db.delete(
                        Database.TABLE_NAME, s, strings);
                break;
            default:
                throw new UnsupportedOperationException("error");
        }
        getContext().getContentResolver().notifyChange(uri, null);

        return deletedMovie;
        */
        int DeletedMovie;
        int match = MATCHER.match(uri);
        SQLiteDatabase s_db = db.getWritableDatabase();
        if (s == null) {
            s = "1";
        }
        switch (match) {
            case movie:
                DeletedMovie = s_db.delete(Database.TABLE_NAME, s, strings);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (DeletedMovie != 0) {
            if (getContext() != null && getContext().getContentResolver() != null) {
                getContext().getContentResolver().notifyChange(uri, null);
            }
        }
        s_db.close();
        return DeletedMovie;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    private void deleteDatabase() {
        db.close();
        Context context = getContext();
        Database.deleteDatabase(context);
        db = new Database(getContext());
    }
    private void notifyChange(Uri uri) {
        getContext().getContentResolver().notifyChange(uri, null);
    }
}

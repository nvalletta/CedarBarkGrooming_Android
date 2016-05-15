package com.cedarbarkgrooming.data.reminders;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.HashMap;

/**
 * Created by Nora on 5/15/2016.
 */
public class ReminderContentProvider extends ContentProvider {

    public static final String REMINDERS_TABLE_NAME = "reminders";
    public static final String TITLE = "title";
    public static final String DATE = "date";

    static final String PROVIDER_NAME = "com.cedarbarkgrooming.provider.CedarBarkGrooming";
    static final String URL = "content://" + PROVIDER_NAME + "/reminders";
    public static final Uri CONTENT_URI = Uri.parse(URL);

    static final String _ID = "_id";

    static final int REMINDERS = 1;
    static final int REMINDERS_ID = 2;

    static final int DATABASE_VERSION = 1;
    static final String DATABASE_NAME = "CedarBarkGrooming";
    static final String CREATE_DB_TABLE =
            " CREATE TABLE " + REMINDERS_TABLE_NAME +
                    " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    " title TEXT NOT NULL, " +
                    " date TEXT NOT NULL);";

    private static final UriMatcher sUriMatcher;
    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(PROVIDER_NAME, "reminders", REMINDERS);
        sUriMatcher.addURI(PROVIDER_NAME, "reminders/#", REMINDERS_ID);
    }

    private SQLiteDatabase mSqLiteDatabase;
    private static HashMap<String, String> REMINDERS_PROJECTION_MAP;

    @Override
    public boolean onCreate() {
        Context context = getContext();
        DatabaseHelper dbHelper = new DatabaseHelper(context);

        mSqLiteDatabase = dbHelper.getWritableDatabase();
        return mSqLiteDatabase != null;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = buildQuery(uri);

        if (sortOrder == null || sortOrder.equals("")){
            sortOrder = DATE;
        }
        Cursor cursor = queryBuilder.query(mSqLiteDatabase,	projection,	selection, selectionArgs, null, null, sortOrder);
        cursor = sendNotificationForUri(cursor, uri);
        if (null == cursor) {
            throw new SQLException("Failed to query. " + uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch(sUriMatcher.match(uri)) {
            case REMINDERS:
                return "vnd.android.cursor.dir/vnd.cedarbarkgrooming.reminders";
            case REMINDERS_ID:
                return "vnd.android.cursor.item/vnd.cedarbarkgrooming.reminders";
        }
        throw new IllegalArgumentException("Unsupported URI. " + uri);
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        long rowId = mSqLiteDatabase.insert(REMINDERS_TABLE_NAME, "", values);
        if (rowId > 0) {
            Uri contentUri = ContentUris.withAppendedId(CONTENT_URI, rowId);
            notifyContentChange(contentUri);
            return contentUri;
        }
        throw new SQLException("Failed to insert data. " + uri);
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        int count;

        switch(sUriMatcher.match(uri)) {
            case REMINDERS:
                count = mSqLiteDatabase.delete(REMINDERS_TABLE_NAME, selection, selectionArgs);
                break;
            case REMINDERS_ID:
                String id = uri.getPathSegments().get(1);
                String selectString = _ID +  " = " + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : "");
                count = mSqLiteDatabase.delete(REMINDERS_TABLE_NAME, selectString, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        notifyContentChange(uri);
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int count;
        switch(sUriMatcher.match(uri)) {
            case REMINDERS:
                count = mSqLiteDatabase.update(REMINDERS_TABLE_NAME, values, selection, selectionArgs);
                break;
            case REMINDERS_ID:
                String selectString = uri.getPathSegments().get(1) + (!TextUtils.isEmpty(selection) ? " AND (" +selection + ')' : "");
                count = mSqLiteDatabase.update(REMINDERS_TABLE_NAME, values, selectString, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        notifyContentChange(uri);
        return count;
    }

    private SQLiteQueryBuilder buildQuery(Uri uri) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(REMINDERS_TABLE_NAME);
        switch (sUriMatcher.match(uri)) {
            case REMINDERS:
                queryBuilder.setProjectionMap(REMINDERS_PROJECTION_MAP);
                break;
            case REMINDERS_ID:
                queryBuilder.appendWhere( _ID + "=" + uri.getPathSegments().get(1));
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        return queryBuilder;
    }

    private void notifyContentChange(Uri uri) {
        Context context = getContext();
        if (null != context) {
            ContentResolver contentResolver = context.getContentResolver();
            if (null != contentResolver) {
                contentResolver.notifyChange(uri, null);
            }
        }
    }

    private Cursor sendNotificationForUri(Cursor cursor, Uri uri) {
        Context context = getContext();
        if (null != context) {
            ContentResolver contentResolver = context.getContentResolver();
            if (null != contentResolver) {
                cursor.setNotificationUri(contentResolver, uri);
                return cursor;
            }
        }
        return null;
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_DB_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + REMINDERS_TABLE_NAME);
            onCreate(db);
        }
    }

}

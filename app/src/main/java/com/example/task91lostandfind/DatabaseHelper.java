package com.example.task91lostandfind;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper databaseHelper;
    private static final int DATABASE_VERSION = 1;
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_TYPE = "type";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_LOCATION = "location";

    private static final String DATABASE_NAME = "LostFound";
    private static final String DATABASE_TABLE_NAME = "infodb";

    private Context context;


    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (databaseHelper == null) {
            databaseHelper = new DatabaseHelper(context);
        }
        return databaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create tables SQL execution
        String CREATE_INFO_TABLE = "CREATE TABLE " + DATABASE_TABLE_NAME + "(" + COLUMN_NAME + " " + "TEXT, " + COLUMN_PHONE + " TEXT, " + COLUMN_TYPE + " TEXT, " + COLUMN_DESCRIPTION + " TEXT, " + COLUMN_DATE + " TEXT, " + COLUMN_LOCATION + " TEXT " + ")";


        Log.d("Table create SQL: ", "" + CREATE_INFO_TABLE);

        db.execSQL(CREATE_INFO_TABLE);

        Log.d("DB created!", "");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);

        // Create tables again
        onCreate(db);
    }

    public long insertItem(Item item) {

        long id = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, item.getTitle());
        contentValues.put(COLUMN_TYPE, item.getId());
        contentValues.put(COLUMN_PHONE, item.getContact());
        contentValues.put(COLUMN_DESCRIPTION, item.getDescription());
        contentValues.put(COLUMN_LOCATION, item.getLocation());
        contentValues.put(COLUMN_DATE, item.getDate());

        try {
            id = sqLiteDatabase.insertOrThrow(DATABASE_TABLE_NAME, null, contentValues);
        } catch (SQLiteException e) {
            Log.d("Exception: ", "" + e.getMessage());
            Toast.makeText(context, "Operation failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return id;
    }

    public List<Item> getAllItems() {

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        try {

            cursor = sqLiteDatabase.query(DATABASE_TABLE_NAME, null, null, null, null, null, null, null);

            if (cursor != null)
                if (cursor.moveToFirst()) {
                    List<Item> itemList = new ArrayList<>();
                    do {
                        String id = cursor.getString(cursor.getColumnIndex(COLUMN_TYPE));
                        String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                        String location = cursor.getString(cursor.getColumnIndex(COLUMN_LOCATION));
                        String phone = cursor.getString(cursor.getColumnIndex(COLUMN_PHONE));
                        String description = cursor.getString(cursor.getColumnIndex(COLUMN_LOCATION));
                        String date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE));


                        Item item = new Item();
                        item.setContact(phone);
                        item.setDate(date);
                        item.setDescription(description);
                        item.setLocation(location);
                        item.setTitle(name);
                        item.setId(id);

                        itemList.add(item);
                    } while (cursor.moveToNext());

                    return itemList;
                }
        } catch (Exception e) {
            Log.d("Exception: ", "" + e.getMessage());
            Toast.makeText(context, "Operation failed", Toast.LENGTH_SHORT).show();
        } finally {
            if (cursor != null)
                cursor.close();
            sqLiteDatabase.close();
        }
        return Collections.emptyList();
    }

    public long removeRecord(Item itm) {
        long id = 0;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();
        try {
            id = sqLiteDatabase.delete(DATABASE_TABLE_NAME, COLUMN_PHONE + "=?",
                    new String[]{itm.getContact()});
        } catch (SQLiteException e) {
            Log.d("Exception: ", "" + e.getMessage());
            Toast.makeText(context, "Operation failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }
        return id;
    }
}
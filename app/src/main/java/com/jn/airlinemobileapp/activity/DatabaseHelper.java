package com.jn.airlinemobileapp.activity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    static String dbName = "AirlineDB";
    static int dbVersion = 1;
    String userTable = "CREATE TABLE IF NOT EXISTS user (id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "address TEXT DEFAULT NULL, " +
            "email TEXT DEFAULT NULL, " +
            "first_name TEXT DEFAULT NULL, " +
            "last_name TEXT DEFAULT NULL, " +
            "nationality TEXT DEFAULT NULL, " +
            "passport_number TEXT DEFAULT NULL, " +
            "password TEXT DEFAULT NULL, " +
            "phone_number TEXT DEFAULT NULL, " +
            "avatar_url TEXT DEFAULT NULL, " +
            "avatar BLOB, " +
            "role TEXT," +
            "avatar_key TEXT DEFAULT NULL)";


    public DatabaseHelper(@Nullable Context context) {
        super(context, dbName, null, dbVersion);
        getWritableDatabase().execSQL(userTable);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL(userTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void createUser(ContentValues values) {
        getWritableDatabase().insert("user", null, values);
    }

    public void updateUser(ContentValues values) {

    }

    public void deleteUser(String id) {
        getWritableDatabase().delete("user", "id=?", new String[]{id});
    }

    @SuppressLint("Range")
    public User getUserByEmail(String email) throws IllegalAccessException, InstantiationException {
        User user = new User();
        String query = "Select * from user";
        try {

            Cursor cursor = getReadableDatabase().rawQuery(query, null);

            if (cursor.moveToFirst()) {
                user = new User();
                user.setId(cursor.getInt(cursor.getColumnIndex("id")));
                user.setPassword(cursor.getString(cursor.getColumnIndex("password")));
            }
        } catch (Exception e) {
            Toast.makeText(LoginActivity.class.newInstance(), "Error", Toast.LENGTH_SHORT).show();
        }

        return user;
    }

    @SuppressLint("Range")
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String getQueryString = "SELECT * FROM user";

        try {
            @SuppressLint("Recycle") Cursor cursor = getReadableDatabase().rawQuery(getQueryString, null);
            while (cursor.moveToNext()) {
                User user = new User();
                user.setId(cursor.getInt(cursor.getColumnIndex("id")));
                user.setFirstName(cursor.getString(cursor.getColumnIndex("first_name")));
                user.setLastName(cursor.getString(cursor.getColumnIndex("last_name")));
                user.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                user.setPassword(cursor.getString(cursor.getColumnIndex("password")));
                user.setPhoneNumber(cursor.getString(cursor.getColumnIndex("phone_number")));
                user.setRole(cursor.getString(cursor.getColumnIndex("role")));
                user.setAvatar(cursor.getBlob(cursor.getColumnIndex("avatar")));
                userList.add(user);

            }


        } catch (Exception e) {
            Log.d("error", e.getMessage());
        }
        return userList;
        
    }
}

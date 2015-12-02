package com.example.phonedemo;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.UserDictionary;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        getContentResolver().query()
        Uri uri = ContentUris.withAppendedId(UserDictionary.CONTENT_URI, 4);

        String[] mProjection = {UserDictionary.Words._ID, UserDictionary.Words.WORD, UserDictionary.Words.LOCALE};
        String mSelectionClause = "";
        String[] mSelectionArgs = {""};

        Cursor cursor = getContentResolver().query(uri, mProjection, null, null, null);
        while (cursor.moveToNext()) {
        }
    }
}

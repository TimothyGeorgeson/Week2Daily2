package com.example.user.week2daily2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class PhotoDatabase extends SQLiteOpenHelper {

    public PhotoDatabase(@Nullable Context context)
    {
        super(context, PhotoContract.NAME, null, PhotoContract.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(PhotoContract.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //drop table and upgrade to new version of database schema
        //migration scripts for saving database
    }

    public long savePhoto(String desc, String photo) {

        //get instance of the database
        SQLiteDatabase database = getWritableDatabase();

        //create content values to save the data as a row
        ContentValues contentValues = new ContentValues();
        //contentValues.put(PhotoContract.FeedEntry.COL_ID, ID);
        contentValues.put(PhotoContract.FeedEntry.COL_DESC, desc);
        contentValues.put(PhotoContract.FeedEntry.COL_PHOTO, photo);

        long rowId = database.insert(PhotoContract.FeedEntry.TABLE_NAME, null, contentValues);

        return rowId;
    }

    public Cursor getPhoto(long rowId)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT " + PhotoContract.FeedEntry.COL_PHOTO + " FROM " +
                PhotoContract.FeedEntry.TABLE_NAME + " WHERE " + PhotoContract.FeedEntry.COL_ID + "=" + rowId,null);
    }


    public boolean deletePhoto(long rowId)
    {
        //get instance of the database
        SQLiteDatabase database = getWritableDatabase();
        return database.delete(PhotoContract.FeedEntry.TABLE_NAME, PhotoContract.FeedEntry.COL_ID + "=" + rowId, null) > 0;
    }

//    public List<Person> getPeople() {
//        SQLiteDatabase database = getWritableDatabase();
//
//        List<Person> personList = new ArrayList<>();
//
//        Cursor cursor = database.rawQuery(PersonContract.GET_ALL, null);
//
//        if (cursor.moveToFirst()) {
//            do {
//                Person person = new Person(cursor.getString(cursor.getColumnIndex(PersonContract.FeedEntry.COL_NAME)),
//                        cursor.getString(cursor.getColumnIndex(PersonContract.FeedEntry.COL_AGE)),
//                        cursor.getString(cursor.getColumnIndex(PersonContract.FeedEntry.COL_GENDER)));
//
//                personList.add(person);
//            } while (cursor.moveToNext());
//        }
//
//        return personList;
//    }
}


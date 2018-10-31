package com.example.user.week2daily2;

import android.provider.BaseColumns;

public class PhotoContract {
    public static final String NAME = "Photo.db";
    public static final int VERSION = 1;

    //Creates table with primary key
    public static final String CREATE_TABLE = "CREATE TABLE " +
            FeedEntry.TABLE_NAME + "(" +
            FeedEntry.COL_ID + " INTEGER PRIMARY KEY, " +
            FeedEntry.COL_DESC + " Text," +
            FeedEntry.COL_PHOTO + " Text)";


    public static final String GET_ALL = "SELECT * FROM " + FeedEntry.TABLE_NAME;

    public static class FeedEntry implements BaseColumns {

        public static final String TABLE_NAME = "photos";
        public static final String COL_ID = "id";
        public static final String COL_DESC = "desc";
        public static final String COL_PHOTO = "photo";
    }
}

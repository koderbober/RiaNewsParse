package com.ghostofchaos.rianewsparse;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ghost on 16.11.2015.
 */
public class DBOpenHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "news";
    public static final String TITLE = "title";
    public static final String ANNOUNCE = "announce";
    public static final String DATA = "data";
    public static final String DESCRIPTION = "description";
    public static final String ID_THEME = "id_theme";
    final String CREATE_DB = "CREATE TABLE " + TABLE_NAME + " ("
            + TITLE + " TEXT NOT NULL, "
            + ANNOUNCE + " TEXT NOT NULL, "
            + DATA + " TEXT NOT NULL, "
            + DESCRIPTION + " TEXT NOT NULL, "
            + ID_THEME + " TEXT NOT NULL" + ");";
    public DBOpenHelper(Context context) {
        super(context, "NewsDB", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF IT EXISTS news");
        db.execSQL(CREATE_DB);
    }
}

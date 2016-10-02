package com.bk.girltrollsv.databasehelper;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bk.girltrollsv.BaseApplication;

/**
 * Created by Dell on 16-Sep-16.
 */
public class DatabaseOpenHelper extends SQLiteOpenHelper {


    private static DatabaseOpenHelper dbHelper = new DatabaseOpenHelper();
    private static SQLiteDatabase dbReader = dbHelper.getReadableDatabase();
    private static SQLiteDatabase dbWriter = dbHelper.getWritableDatabase();


    private DatabaseOpenHelper() {
        super(BaseApplication.getContext(), SQLStatement.DATABASE_NAME, null, SQLStatement.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // do create all table in database
        db.execSQL(SQLStatement.MemberTable.CREATE_TABLE);
        db.execSQL(SQLStatement.FeedTable.CREATE_TABLE);
        db.execSQL(SQLStatement.ImageInfoTable.CREATE_TABLE);
        db.execSQL(SQLStatement.VideoTable.CREATE_TABLE);
        db.execSQL(SQLStatement.FeedCommentTable.CREATE_TABLE);
        db.execSQL(SQLStatement.MyActivityInfoTable.CREATE_TABLE);

        db.execSQL(SQLStatement.TRIGGER_DELETE_IMAGE);
        db.execSQL(SQLStatement.TRIGGER_DELETE_VIDEO);
        db.execSQL(SQLStatement.TRIGGER_DELETE_FEED_COMMENT);
        db.execSQL(SQLStatement.TRIGGER_DELETE_MY_ACTIVITY_INFO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // do delete all table and then recreate them
        db.execSQL(SQLStatement.MyActivityInfoTable.DELETE_TABLE);
        db.execSQL(SQLStatement.FeedCommentTable.DELETE_TABLE);
        db.execSQL(SQLStatement.VideoTable.DELETE_TABLE);
        db.execSQL(SQLStatement.ImageInfoTable.DELETE_TABLE);
        db.execSQL(SQLStatement.FeedCommentTable.DELETE_TABLE);
        db.execSQL(SQLStatement.MemberTable.DELETE_TABLE);
        onCreate(db);
    }

    public static SQLiteDatabase getDbWriter() {
        return dbWriter;
    }

    public static SQLiteDatabase getDbReader() {
        return dbReader;
    }

}

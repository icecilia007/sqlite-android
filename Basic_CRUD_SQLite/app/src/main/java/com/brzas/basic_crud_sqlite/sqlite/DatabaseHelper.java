package com.brzas.basic_crud_sqlite.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.brzas.basic_crud_sqlite.helper.SQLiteAttributes;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static String TAG = "DatabateHelper";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = SQLiteAttributes.DATABASE_NAME;
    private static DatabaseHelper databaseHelper;


    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (databaseHelper == null) {
            databaseHelper = new DatabaseHelper(context);
        }
        return databaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Create SQL tables
        String CREATE_EXAM_TABLE = "CREATE TABLE " + SQLiteAttributes.TABLE_EXAM + "("
                + SQLiteAttributes.COLUMN_EXAM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + SQLiteAttributes.COLUMN_EXAM_TITLE + " TEXT NOT NULL, "
                + SQLiteAttributes.COLUMN_EXAM_THEME + " TEXT NOT NULL, "
                + SQLiteAttributes.COLUMN_EXAM_TEACHER + " TEXT NOT NULL, "
                + SQLiteAttributes.COLUMN_EXAM_TIMEDURATION + " INTEGER, "
                + SQLiteAttributes.COLUMN_EXAM_EXAMDATE + " TEXT NOT NULL"
                + ")";
        Log.i(TAG, "Table " + CREATE_EXAM_TABLE);
        sqLiteDatabase.execSQL(CREATE_EXAM_TABLE);
        Log.i(TAG, "Data base created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SQLiteAttributes.TABLE_EXAM);
        //recreate table
        onCreate(sqLiteDatabase);
    }
}

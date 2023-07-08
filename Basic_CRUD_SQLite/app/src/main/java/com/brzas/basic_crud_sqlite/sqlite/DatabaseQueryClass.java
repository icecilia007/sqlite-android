package com.brzas.basic_crud_sqlite.sqlite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.widget.Toast;

import com.brzas.basic_crud_sqlite.helper.SQLiteAttributes;
import com.brzas.basic_crud_sqlite.models.Exam;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DatabaseQueryClass {
    private static String TAG = "DatabaseQueryClass";
    private Context context;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public DatabaseQueryClass(Context context) {
        this.context = context;
    }

    public long insertExam(Exam exam) {
        long id = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLiteAttributes.COLUMN_EXAM_TITLE, exam.getTitle());
        contentValues.put(SQLiteAttributes.COLUMN_EXAM_THEME, exam.getTheme());
        contentValues.put(SQLiteAttributes.COLUMN_EXAM_TEACHER, exam.getTeacher());
        contentValues.put(SQLiteAttributes.COLUMN_EXAM_TIMEDURATION, exam.getTimeDuration());
        String examDate = sdf.format(exam.getExamDate());
        contentValues.put(SQLiteAttributes.COLUMN_EXAM_EXAMDATE, examDate);

        try {
            id = sqLiteDatabase.insertOrThrow(SQLiteAttributes.TABLE_EXAM, null, contentValues);
        } catch (SQLiteException e) {
            Log.d(TAG, "Exception: " + e.getMessage());
            e.printStackTrace();
            Toast.makeText(context, "Operation failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return id;
    }

    @SuppressLint("Range")
    public List<Exam> getAllExam() {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        try {

            cursor = sqLiteDatabase.query(SQLiteAttributes.TABLE_EXAM, null, null, null, null, null, null, null);

            if (cursor != null)
                if (cursor.moveToFirst()) {
                    List<Exam> examList = new ArrayList<>();
                    do {
                        long id = cursor.getLong(cursor.getColumnIndex(SQLiteAttributes.COLUMN_EXAM_ID));
                        String title = cursor.getString(cursor.getColumnIndex(SQLiteAttributes.COLUMN_EXAM_TITLE));
                        String theme = cursor.getString(cursor.getColumnIndex(SQLiteAttributes.COLUMN_EXAM_THEME));
                        String teacher = cursor.getString(cursor.getColumnIndex(SQLiteAttributes.COLUMN_EXAM_TEACHER));
                        long timeDuration = cursor.getLong(cursor.getColumnIndex(SQLiteAttributes.COLUMN_EXAM_TIMEDURATION));
                        String examDateMillis = cursor.getString(cursor.getColumnIndex(SQLiteAttributes.COLUMN_EXAM_EXAMDATE));
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

                        examList.add(new Exam(id, title, theme, teacher, timeDuration, formatter.parse(examDateMillis)));
                    } while (cursor.moveToNext());

                    return examList;
                }

        } catch (Exception e) {
            Log.d(TAG, "Exception: " + e.getMessage());
        } finally {
            if (cursor != null)
                cursor.close();
            sqLiteDatabase.close();
        }

        return Collections.emptyList();
    }

    @SuppressLint("Range")
    public Exam getExamById(long id) {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        Exam exam = null;

        try {

            cursor = sqLiteDatabase.query(SQLiteAttributes.TABLE_EXAM, null, SQLiteAttributes.COLUMN_EXAM_ID + " =? ",
                    new String[]{String.valueOf(id)}, null, null, null);

            if (cursor.moveToFirst()) {
                long _id = cursor.getLong(cursor.getColumnIndex(SQLiteAttributes.COLUMN_EXAM_ID));
                String title = cursor.getString(cursor.getColumnIndex(SQLiteAttributes.COLUMN_EXAM_TITLE));
                String theme = cursor.getString(cursor.getColumnIndex(SQLiteAttributes.COLUMN_EXAM_THEME));
                String teacher = cursor.getString(cursor.getColumnIndex(SQLiteAttributes.COLUMN_EXAM_TEACHER));
                long timeDuration = cursor.getLong(cursor.getColumnIndex(SQLiteAttributes.COLUMN_EXAM_TIMEDURATION));
                long examDateMillis = cursor.getLong(cursor.getColumnIndex(SQLiteAttributes.COLUMN_EXAM_EXAMDATE));
                Date examDate = new Date(examDateMillis);

                exam = new Exam(_id, title, theme, teacher, timeDuration, examDate);
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception: " + e.getMessage());
            Toast.makeText(context, "Operation failed", Toast.LENGTH_SHORT).show();
        } finally {
            if (cursor != null)
                cursor.close();
            sqLiteDatabase.close();
        }
        return exam;
    }

    public long deleteExamById(long id) {
        long deletedRowCount = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        try {
            deletedRowCount = sqLiteDatabase.delete(SQLiteAttributes.TABLE_EXAM, SQLiteAttributes.COLUMN_EXAM_ID + " =? ",
                    new String[]{String.valueOf(id)});
        } catch (SQLiteException e) {
            Log.d(TAG, "Exception: " + e.getMessage());
        } finally {
            sqLiteDatabase.close();
        }

        return deletedRowCount;
    }

    public boolean deleteAllExams() {
        boolean deleteStatus = false;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        try {

            sqLiteDatabase.delete(SQLiteAttributes.TABLE_EXAM, null, null);

            long count = DatabaseUtils.queryNumEntries(sqLiteDatabase, SQLiteAttributes.TABLE_EXAM);
            if (count == 0)
                deleteStatus = true;

        } catch (SQLiteException e) {
            Log.d(TAG, "Exception: " + e.getMessage());
        } finally {
            sqLiteDatabase.close();
        }

        return deleteStatus;
    }

    public long updateExamInfo(Exam exam) {
        long rowCount = 0;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLiteAttributes.COLUMN_EXAM_TITLE, exam.getTitle());
        contentValues.put(SQLiteAttributes.COLUMN_EXAM_THEME, exam.getTheme());
        contentValues.put(SQLiteAttributes.COLUMN_EXAM_TEACHER, exam.getTeacher());
        contentValues.put(SQLiteAttributes.COLUMN_EXAM_TIMEDURATION, exam.getTimeDuration());
        String examDate = sdf.format(exam.getExamDate());
        contentValues.put(SQLiteAttributes.COLUMN_EXAM_EXAMDATE, examDate);

        try {
            rowCount = sqLiteDatabase.update(SQLiteAttributes.TABLE_EXAM, contentValues,
                    SQLiteAttributes.COLUMN_EXAM_ID + " = ? ",
                    new String[]{String.valueOf(exam.getId())});
        } catch (SQLiteException e) {
            Log.d(TAG, "Exception: " + e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return rowCount;
    }
}

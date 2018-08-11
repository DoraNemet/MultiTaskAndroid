package com.example.dorafundak.multitask;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.dorafundak.multitask.Classes.Note;

import java.util.ArrayList;

class NoteDBHelper extends SQLiteOpenHelper {

    private static NoteDBHelper mNoteDBHelper = null;

    private NoteDBHelper(Context context) {
        super(context.getApplicationContext(), Schema.DATABASE_NAME, null, Schema.SCHEMA_VERSION);
    }

    public static synchronized NoteDBHelper getInstance(Context context) {
        if (mNoteDBHelper == null) {
            mNoteDBHelper = new NoteDBHelper(context);
        }
        return mNoteDBHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        this.onCreate(db);
    }

    //SQL statements
    static final String CREATE_TABLE = "CREATE TABLE " + Schema.TABLE_NAME + " (" + Schema.NOTE + " TEXT);";
    static final String DROP_TABLE = "DROP TABLE IF EXISTS " + Schema.TABLE_NAME;
    static final String SELECT_ALL = "SELECT " + Schema.NOTE + " FROM " + Schema.TABLE_NAME;

    public void insertNote(Note note) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Schema.NOTE, note.getNote());
        SQLiteDatabase writeableDatabase = this.getWritableDatabase();
        writeableDatabase.insert(Schema.TABLE_NAME, Schema.NOTE, contentValues);
        writeableDatabase.close();
    }

    public ArrayList<Note> getAllNotes() {
        SQLiteDatabase writeableDatabase = this.getWritableDatabase();
        Cursor notesCursor = writeableDatabase.rawQuery(SELECT_ALL, null);
        ArrayList<Note> notes = new ArrayList<>();
        if (notesCursor.moveToFirst()) {
            do {
                String note = notesCursor.getString(0);
                notes.add(new Note(note));
            } while (notesCursor.moveToNext());
        }
        notesCursor.close();
        writeableDatabase.close();
        return notes;
    }

    public static class Schema {
        private static final int SCHEMA_VERSION = 1;
        private static final String DATABASE_NAME = "notes.db";
        static final String TABLE_NAME = "all_notes";
        static final String NOTE = "note";
    }
}

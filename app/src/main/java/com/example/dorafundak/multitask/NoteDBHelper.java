package com.example.dorafundak.multitask;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.dorafundak.multitask.Classes.Note;

import java.util.ArrayList;

class NoteDBHelper extends SQLiteOpenHelper {

    private static final int SCHEMA_VERSION = 1;
    private static final String DATABASE_NAME = "notes.db";
    static final String TABLE_NAME = "all_notes";
    static final String NOTE = "note";
    
    private static NoteDBHelper mNoteDBHelper = null;

    private NoteDBHelper(Context context) {
        super(context.getApplicationContext(), DATABASE_NAME, null, SCHEMA_VERSION);
    }

    public static synchronized NoteDBHelper getInstance(Context context) {
        if (mNoteDBHelper == null) {
            mNoteDBHelper = new NoteDBHelper(context);
        }
        return mNoteDBHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + NOTE + " TEXT);";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(DROP_TABLE);
        this.onCreate(db);
    }
    
    public ArrayList<Note> selectAllNotes() {
        String SELECT_ALL = "SELECT " + NOTE + " FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor notesCursor = db.rawQuery(SELECT_ALL, null);
        ArrayList<Note> notes = new ArrayList<>();
        if (notesCursor.moveToFirst()) {
            do {
                String note = notesCursor.getString(0);
                notes.add(new Note(note));
            } while (notesCursor.moveToNext());
        }
        notesCursor.close();
        db.close();
        return notes;
    }

    public void removeNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = NOTE + "='" + note.getNote()+"'";
        db.delete(TABLE_NAME, where, null);
        db.close();
    }

    public void insertNote(Note note) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NOTE, note.getNote());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME, NOTE, contentValues);
        db.close();
    }
}

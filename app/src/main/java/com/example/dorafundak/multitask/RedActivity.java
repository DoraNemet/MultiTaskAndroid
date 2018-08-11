package com.example.dorafundak.multitask;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dorafundak.multitask.Classes.Note;

import java.util.ArrayList;

public class RedActivity extends AppCompatActivity {

    Button calculatorButton;
    Button fuelButton;
    ArrayList<Note> notesList = new ArrayList<>();
    TextView addNote;
    Dialog dialog;

    RecyclerView notesRecyclerView;
    NotesAdapter mNotesAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.ItemDecoration mItemDecoration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red);

        //navigation
        calculatorButton = findViewById(R.id.calculatorButton);
        calculatorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RedActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        fuelButton = findViewById(R.id.fuelButton);
        fuelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RedActivity.this, BlueActivity.class);
                startActivity(intent);
            }
        });

        //setup RecyclerView
         notesRecyclerView = this.findViewById(R.id.listView);
        ArrayList<Note> notes = loadNotes();
        mNotesAdapter = new NotesAdapter(notes);
        notesRecyclerView.setAdapter(this.mNotesAdapter);
        notesRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        addNoteSetup();
    }

    private void addNoteSetup() {
        addNote = findViewById(R.id.addNote);
        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(RedActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.custom_popup);
                Button cancelButton = dialog.findViewById(R.id.cancelButton);
                Button saveButton = dialog.findViewById(R.id.saveButton);
                final EditText noteInput = dialog.findViewById(R.id.noteInput);
                noteInput.requestFocus();
                dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(noteInput.getWindowToken(), 0);
                        dialog.dismiss();
                    }
                });

                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(noteInput.getText().toString() != null) {
                            Note note = new Note(noteInput.getText().toString());
                            NoteDBHelper.getInstance(getApplicationContext()).insertNote(note);
                            NotesAdapter adapter = (NotesAdapter) notesRecyclerView.getAdapter();
                            adapter.insert(note);
                        }
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
    }

    private ArrayList<Note> loadNotes() {
        return NoteDBHelper.getInstance(this).getAllNotes();
    }
}

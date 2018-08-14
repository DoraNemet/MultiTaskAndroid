package com.example.dorafundak.multitask;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
    TextView addNote;
    Dialog dialog;

    RecyclerView notesRecyclerView;
    NotesAdapter mNotesAdapter;

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
        final ArrayList<Note> notes = loadNotes();
        mNotesAdapter = new NotesAdapter(notes);
        notesRecyclerView.setAdapter(this.mNotesAdapter);
        notesRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        notesRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(RedActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                final int mpos = position;
                AlertDialog alertDialog = new AlertDialog.Builder(RedActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure you want to delete " + notes.get(position).getNote() + "?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Note note = new Note(notes.get(position).getNote());
                                NoteDBHelper.getInstance(RedActivity.this).removeNote(note);
                                mNotesAdapter = (NotesAdapter) notesRecyclerView.getAdapter();
                                mNotesAdapter.remove(position);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        })
                        .show();
            }
        }));

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
                //show keyboard
                noteInput.requestFocus();
                dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //remove keyboard
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(noteInput.getWindowToken(), 0);
                        dialog.dismiss();
                    }
                });

                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (noteInput.getText().toString() != null) {
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
        return NoteDBHelper.getInstance(this).selectAllNotes();
    }


}

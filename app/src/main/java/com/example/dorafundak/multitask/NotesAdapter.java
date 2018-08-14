package com.example.dorafundak.multitask;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dorafundak.multitask.Classes.Note;

import java.util.ArrayList;

class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    private ArrayList<Note> mNotes;

    public NotesAdapter(ArrayList<Note> notes) {
        mNotes = notes;
    }

    @Override
    public NotesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View noteView = inflater.inflate(R.layout.item_note, parent, false);
        ViewHolder noteViewHolder = new ViewHolder(noteView);
        return noteViewHolder;
    }

    @Override
    public void onBindViewHolder(NotesAdapter.ViewHolder holder, int position) {
        Note note = this.mNotes.get(position);
        holder.noteText.setText(note.getNote());
    }

    @Override
    public int getItemCount() {
        return this.mNotes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView noteText;

        public ViewHolder(View itemView) {
            super(itemView);
            noteText = itemView.findViewById(R.id.noteText);
        }
    }

    public void insert(Note note) {
        mNotes.add(note);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        mNotes.remove(position);
        notifyDataSetChanged();
    }
}

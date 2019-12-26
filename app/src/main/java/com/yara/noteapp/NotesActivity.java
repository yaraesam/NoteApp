package com.yara.noteapp;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.yara.noteapp.adapter.NoteAdapter;
import com.yara.noteapp.model.Note;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NotesActivity extends AppCompatActivity {

    final String Uid = SplashActivity.getUid();
    DatabaseReference noteReference;
    String notebookId;

    RecyclerView notesList_rv;
    NoteAdapter noteAdapter;
    List<Note> noteList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        notebookId = getIntent().getStringExtra("notebook_id");
        noteReference = FirebaseDatabase.getInstance().getReference().child("Note");
        initViews();
        initData();
    }

    private void initViews() {
        notesList_rv = findViewById(R.id.notesList_rv);
        notesList_rv.setLayoutManager(new LinearLayoutManager(this));
        noteAdapter = new NoteAdapter(this, noteList);
        notesList_rv.setAdapter(noteAdapter);

        ImageView img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(view -> onBackPressed());

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> showAddDialog());
    }

    private void initData() {
        Query query;
        if (notebookId == null)
            query = noteReference.orderByChild("uid").equalTo(Uid);
        else
            query = noteReference.orderByChild("notebookId").equalTo(notebookId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                noteList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Note note = snapshot.getValue(Note.class);
                    noteList.add(note);

                }
                noteAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
       /* noteReference
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        noteList.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                            Note note = snapshot.getValue(Note.class);
                            noteList.add(note);

                        }
                        notebookAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

*/
    }

    private void showAddDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_add_note);
        TextView save = dialog.findViewById(R.id.save);
        final EditText title = dialog.findViewById(R.id.title);
        final EditText description = dialog.findViewById(R.id.description);
        save.setOnClickListener(v -> {
            dialog.dismiss();
            String key = noteReference.push().getKey();
            Note note = new Note(key, Uid, notebookId, title.getText().toString()
                    , description.getText().toString(), new Date().getTime());
            noteReference.child(key).setValue(note.toMap());
            noteList.add(note);
            noteAdapter.notifyDataSetChanged();
        });
        dialog.show();
    }
}

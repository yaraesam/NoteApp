package com.yara.noteapp;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.yara.noteapp.adapter.NoteAdapter;
import com.yara.noteapp.adapter.NotebookAdapter;
import com.yara.noteapp.adapter.SelectColorAdapter;
import com.yara.noteapp.model.Note;
import com.yara.noteapp.model.Notebook;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    final String Uid = SplashActivity.getUid();

    DatabaseReference notebookReference;
    NotebookAdapter notebookAdapter;
    List<Notebook> notebookList = new ArrayList<>();

    DatabaseReference noteReference;
    NoteAdapter noteAdapter;
    List<Note> noteList = new ArrayList<>();

    TextView show_all_notebooks;
    TextView show_all_notes;
    View add_notebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notebookReference = FirebaseDatabase.getInstance().getReference().child("Notebook");
        noteReference = FirebaseDatabase.getInstance().getReference().child("Note");

        initViews();
        initData();
    }

    private void initViews() {
        show_all_notebooks = findViewById(R.id.show_all_notebooks);
        show_all_notes = findViewById(R.id.show_all_notes);
        add_notebook = findViewById(R.id.add_notebook);

        show_all_notebooks.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, NotebooksActivity.class);
            startActivity(intent);
        });

        show_all_notes.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, NotesActivity.class);
            startActivity(intent);
        });


        add_notebook.setOnClickListener(v -> {
            showAddDialog();
            /*FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(MainActivity.this, IntroActivity.class);
            startActivity(intent);*/
        });


        RecyclerView notebooksList_rv = findViewById(R.id.notebooksList_rv);
        notebooksList_rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        notebookAdapter = new NotebookAdapter(this, notebookList);
        notebooksList_rv.setAdapter(notebookAdapter);

        RecyclerView notesList_rv = findViewById(R.id.notesList_rv);
        notesList_rv.setLayoutManager(new LinearLayoutManager(this));
        noteAdapter = new NoteAdapter(this, noteList);
        notesList_rv.setAdapter(noteAdapter);

    }

    private void initData() {
        Query queryNotebook = notebookReference.orderByChild("uid").equalTo(Uid);
        queryNotebook.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                notebookList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Notebook note = snapshot.getValue(Notebook.class);
                    Log.d("TAG", "onDataChange:Notebook " + snapshot.getValue());
                    notebookList.add(note);
                }
                notebookAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Query queryNote = noteReference.orderByChild("uid").equalTo(Uid);
        queryNote.addListenerForSingleValueEvent(new ValueEventListener() {
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
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void showAddDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_add_notebook);
        final String[] imagePosition = new String[1];
        imagePosition[0] = "1";
        final ImageView image = dialog.findViewById(R.id.image);
        final SelectColorAdapter.OnColorSelectedListener listener = (imageRes, position) -> {
            image.setImageResource(imageRes);
            imagePosition[0] = String.valueOf(position + 1);
            Log.d("TAG", "onItemClick:position " + position);
        };
        image.setOnClickListener(v -> showSelectColorDialog(listener));
        final EditText title = dialog.findViewById(R.id.title);
        TextView save = dialog.findViewById(R.id.save);
        save.setOnClickListener(v -> {
            dialog.dismiss();
            String key = notebookReference.push().getKey();
            Notebook notebook = new Notebook(key, Uid, title.getText().toString(), imagePosition[0]);
            notebookReference.child(key).setValue(notebook.toMap());
            notebookList.add(notebook);
            notebookAdapter.notifyDataSetChanged();
        });
        dialog.show();
    }

    private void showSelectColorDialog(final SelectColorAdapter.OnColorSelectedListener listener) {
        final Dialog dialog = new Dialog(this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_select_color);
        RecyclerView colorsListView = dialog.findViewById(R.id.colors);
        colorsListView.setLayoutManager(new GridLayoutManager(this, 3));
        SelectColorAdapter colorAdapter = new SelectColorAdapter(this, (imageRes, position) -> {
            listener.onColorSelected(imageRes, position);
            dialog.dismiss();
        });
        colorsListView.setAdapter(colorAdapter);
        dialog.show();
    }
}

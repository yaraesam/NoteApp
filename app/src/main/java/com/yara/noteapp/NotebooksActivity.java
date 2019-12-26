package com.yara.noteapp;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.yara.noteapp.adapter.NotebookAdapter;
import com.yara.noteapp.adapter.SelectColorAdapter;
import com.yara.noteapp.model.Notebook;

import java.util.ArrayList;
import java.util.List;

public class NotebooksActivity extends AppCompatActivity {

    final String Uid = SplashActivity.getUid();
    DatabaseReference notebookReference;

    RecyclerView notebooksList_rv;
    NotebookAdapter notebookAdapter;
    List<Notebook> notebookList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notebooks);

        notebookReference = FirebaseDatabase.getInstance().getReference().child("Notebook");
//        noteReference.removeValue();
        initViews();
        initData();
    }

    private void initViews() {
        notebooksList_rv = findViewById(R.id.notebooksList_rv);
        notebooksList_rv.setLayoutManager(new GridLayoutManager(this, 3));
        notebookAdapter = new NotebookAdapter(this, notebookList);
        notebooksList_rv.setAdapter(notebookAdapter);

        ImageView img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(view -> onBackPressed());

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> showAddDialog());
    }

    private void initData() {
        Query query = notebookReference.orderByChild("uid").equalTo(Uid);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
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
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        /*noteReference.addValueEventListener(new ValueEventListener() {
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
        });*/
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

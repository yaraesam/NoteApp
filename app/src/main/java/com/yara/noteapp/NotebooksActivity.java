package com.yara.noteapp;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.yara.noteapp.adapter.SelectColorAdapter;

public class NotebooksActivity extends AppCompatActivity {

    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notebooks);
        initViews();

    }

    private void initViews() {
        /*LinearLayout item_notebook = findViewById(R.id.item_notebook);
        item_notebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotebooksActivity.this, NotesActivity.class);
                startActivity(intent);
            }
        });*/

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddDialog();
            }
        });
    }


    private void showAddDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_add_notebook);
        final ImageView image = dialog.findViewById(R.id.image);
        final SelectColorAdapter.OnColorSelectedListener listener = new SelectColorAdapter.OnColorSelectedListener() {
            @Override
            public void onColorSelected(int imageRes, int position) {
                image.setImageResource(imageRes);
                Log.d("TAG", "onItemClick:position " + position);
            }
        };
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectColorDialog(listener);
            }
        });
        TextView save = dialog.findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
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
        SelectColorAdapter colorAdapter = new SelectColorAdapter(this, new SelectColorAdapter.OnColorSelectedListener() {
            @Override
            public void onColorSelected(int imageRes, int position) {
                listener.onColorSelected(imageRes, position);
                dialog.dismiss();
            }
        });
        colorsListView.setAdapter(colorAdapter);
        dialog.show();
    }
}

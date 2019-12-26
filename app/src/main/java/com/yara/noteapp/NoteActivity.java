package com.yara.noteapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class NoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);


        ImageView img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(view -> onBackPressed());

        TextView note_name = findViewById(R.id.note_name);
        TextView note_desc = findViewById(R.id.note_desc);
        TextView note_date = findViewById(R.id.note_date);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String desc = intent.getStringExtra("desc");
        long createdAt = intent.getLongExtra("createdAt", 0);

        note_name.setText(title);
        note_date.setText(createdAt + "");
        note_desc.setText(desc);


    }
}

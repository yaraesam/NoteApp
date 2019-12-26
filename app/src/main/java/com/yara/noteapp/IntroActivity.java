package com.yara.noteapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.yara.noteapp.util.PreferenceManager;

public class IntroActivity extends AppCompatActivity {

    ImageView img_intro;
    ImageView img_intro_bg;
    TextView tv_intro_title;
    TextView tv_intro_content;
    ImageView intro_oval_1;
    ImageView intro_oval_2;
    TextView skip;
    TextView next;
    View lay_intro_login;
    View lay_intro_next;
    TextView sign_in;
    TextView sign_up;
    int selectedIntro;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        initViews();
        setSelectedIntro(0);
    }

    private void initViews() {
        img_intro = findViewById(R.id.img_intro);
        img_intro_bg = findViewById(R.id.img_intro_bg);
        tv_intro_title = findViewById(R.id.tv_intro_title);
        tv_intro_content = findViewById(R.id.tv_intro_content);
        intro_oval_1 = findViewById(R.id.intro_oval_1);
        intro_oval_2 = findViewById(R.id.intro_oval_2);
        skip = findViewById(R.id.skip);
        next = findViewById(R.id.next);
        lay_intro_login = findViewById(R.id.lay_intro_login);
        lay_intro_next = findViewById(R.id.lay_intro_next);
        sign_in = findViewById(R.id.sign_in);
        sign_up = findViewById(R.id.sign_up);
        skip.setOnClickListener(v -> {
            PreferenceManager.setIntroShow(IntroActivity.this);
            Intent intent = new Intent(IntroActivity.this, SignInActivity.class);
            startActivity(intent);
            finish();
        });
        next.setOnClickListener(v -> {
            if (selectedIntro == 0)
                setSelectedIntro(1);
            else if (selectedIntro == 1)
                setSelectedIntro(2);
        });
        sign_in.setOnClickListener(v -> {
            PreferenceManager.setIntroShow(IntroActivity.this);
            Intent intent = new Intent(IntroActivity.this, SignInActivity.class);
            startActivity(intent);
            finish();
        });
        sign_up.setOnClickListener(v -> {
            PreferenceManager.setIntroShow(IntroActivity.this);
            Intent intent = new Intent(IntroActivity.this, SignUpActivity.class);
            startActivity(intent);
            finish();
        });
    }

    public void setSelectedIntro(int selectedIntro) {
        this.selectedIntro = selectedIntro;
        setSelectedIntro();
    }

    public void setSelectedIntro() {
        if (selectedIntro == 0) {
            img_intro.setImageResource(R.drawable.img_intro_1);
            img_intro_bg.setImageResource(R.drawable.img_intro_1_bg);
            tv_intro_title.setText("Notebooks");
            tv_intro_content.setText("Notebooks are the best place \nto manage your Notes");
            intro_oval_1.setImageResource(R.drawable.intro_oval_1);
            intro_oval_2.setImageResource(R.drawable.intro_oval_2);
        } else if (selectedIntro == 1) {
            img_intro.setImageResource(R.drawable.img_intro_2);
            img_intro_bg.setImageResource(R.drawable.img_intro_2_bg);
            tv_intro_title.setText("Add Notes to Notebook");
            tv_intro_content.setText("Simply create your note and\nadd it to your favorite\nnotebook");
            intro_oval_1.setImageResource(R.drawable.intro_oval_2);
            intro_oval_2.setImageResource(R.drawable.intro_oval_1);
        } else {
            img_intro.setImageResource(R.drawable.img_intro_3);
            img_intro_bg.setImageResource(R.drawable.img_intro_2_bg);
            tv_intro_title.setText("Notebooks");
            tv_intro_content.setText("Notebooks are the best place \nto manage your Notes");
            lay_intro_login.setVisibility(View.VISIBLE);
            lay_intro_next.setVisibility(View.GONE);
        }
    }
}

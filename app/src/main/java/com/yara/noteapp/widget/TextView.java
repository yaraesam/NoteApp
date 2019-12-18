package com.yara.noteapp.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.yara.noteapp.R;
import com.yara.noteapp.util.FontsManager;

public class TextView extends AppCompatTextView {

    private Typeface typeface = Typeface.DEFAULT;

    public TextView(Context context) {
        this(context, null);
    }

    public TextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
    }

    public TextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TextView);
            String fontName = ta.getString(R.styleable.TextView_font_name);
            String fontStyle = ta.getString(R.styleable.TextView_font_style);
            typeface = FontsManager.getTypeFace(context, fontName, fontStyle);
            setTypeface(typeface);
            ta.recycle();
        }
    }

    @Override
    public Typeface getTypeface() {
        return typeface == null ? super.getTypeface() : typeface;
    }


}

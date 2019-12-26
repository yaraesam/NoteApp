package com.yara.noteapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yara.noteapp.R;

import java.util.ArrayList;
import java.util.List;

public class SelectColorAdapter extends RecyclerView.Adapter<SelectColorAdapter.ViewHolder> {

    private final OnColorSelectedListener listener;
    private final List<Integer> stringList;

    public SelectColorAdapter(Context context, OnColorSelectedListener listener) {
        stringList = new ArrayList<>();
        this.listener = listener;
        for (int i = 1; i <= 29; i++) {
            stringList.add(context.getResources().getIdentifier(
                    "book_" + i,
                    "drawable",
                    context.getPackageName()
            ));


        }
    }

    public Integer getItem(int position) {
        return stringList == null ? null : stringList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return stringList == null ? 0 : stringList.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_select_color, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.imageView.setImageResource(getItem(i));
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onColorSelected(getItem(i), i);
            }
        });
    }

    public interface OnColorSelectedListener {
        void onColorSelected(int imageRes, int position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.image);
        }
    }
}

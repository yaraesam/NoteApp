package com.yara.noteapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yara.noteapp.NotesActivity;
import com.yara.noteapp.R;
import com.yara.noteapp.model.Notebook;

import java.util.List;

public class NotebookAdapter extends RecyclerView.Adapter<NotebookAdapter.NotebookVh> {

    Context context;
    List<Notebook> notes;

    public NotebookAdapter(Context context, List<Notebook> notes) {
        this.context = context;
        this.notes = notes;
    }

    @NonNull
    @Override
    public NotebookVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_notebook, parent, false);
        return new NotebookVh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotebookVh holder, int position) {
        holder.setData(notes.get(position));
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    class NotebookVh extends RecyclerView.ViewHolder {
        TextView notebook_name;
        ImageView note_image;

        public NotebookVh(@NonNull View itemView) {
            super(itemView);
            notebook_name = itemView.findViewById(R.id.notebook_name);
            note_image = itemView.findViewById(R.id.image);
        }

        public void setData(Notebook note) {
            notebook_name.setText(note.getTitle());
            note_image.setImageResource(context.getResources().getIdentifier(
                    "book_" + note.getImage(),
                    "drawable",
                    context.getPackageName()
            ));
            String id = note.getId();
            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, NotesActivity.class);
                intent.putExtra("notebook_id", id);
                context.startActivity(intent);
            });
        }
    }
}

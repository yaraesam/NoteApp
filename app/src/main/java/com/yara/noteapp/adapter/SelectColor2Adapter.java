package com.yara.noteapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.yara.noteapp.R;

import java.util.ArrayList;
import java.util.List;

public class SelectColor2Adapter extends BaseAdapter {

    private final List<Integer> stringList;
    private LayoutInflater inflater;

    public SelectColor2Adapter(Context context) {
        inflater = LayoutInflater.from(context);
        stringList = new ArrayList<>();
        for (int i = 1; i <= 29; i++) {
            stringList.add(context.getResources().getIdentifier(
                    "book_" + i,
                    "drawable",
                    context.getPackageName()
            ));


        }
    }


    @Override
    public int getCount() {
        return stringList == null ? 0 : stringList.size();
    }

    @Override
    public Integer getItem(int position) {
        return stringList == null ? null : stringList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_select_color, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.image.setImageResource(getItem(position));
        return convertView;
    }

    private static class ViewHolder {
        ImageView image;

        public ViewHolder(View view) {
            image = view.findViewById(R.id.image);
        }
    }
}

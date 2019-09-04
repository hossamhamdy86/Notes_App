package com.example.notes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class NoteAdapter extends ArrayAdapter<note> {

    public NoteAdapter(Context context, ArrayList<note> noteArrayList) {
        super(context, R.layout.note_layout, noteArrayList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view1 = inflater.inflate(R.layout.note_layout,null,false);
        note note = getItem(position);
        TextView title_text_view = view1.findViewById(R.id.title_text_view);
        String title_text = note.title_text.toString();
        title_text_view.setText(title_text);
        TextView data_text_view = view1.findViewById(R.id.data_text_view);
        String data = note.timestamp.toString();
        data_text_view.setText(data);
        return view1;

    }
}

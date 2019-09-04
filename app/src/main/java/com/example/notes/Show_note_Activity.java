package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Show_note_Activity extends AppCompatActivity {

    TextView title_Text_view , note_Text_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_note_);
        title_Text_view = findViewById(R.id.Show_title);
        note_Text_view = findViewById(R.id.show_note);
        Intent intent = getIntent();
        String title_show = intent.getStringExtra("title_key");
        String note_show = intent.getStringExtra("note_key");
        title_Text_view.setText(title_show);
        note_Text_view.setText(note_show);
    }
}

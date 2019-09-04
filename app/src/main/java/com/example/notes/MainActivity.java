package com.example.notes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    DatabaseReference databaseReference = null;
    FloatingActionButton add ;
    public String title_text;
    public String note_text;
    ArrayList<note> notelist = null;
    ListView note_list_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add = findViewById(R.id.add);
        note_list_view = findViewById(R.id.notes_listview);
        notelist = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Notes");
        add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                View view1 = getLayoutInflater().inflate(R.layout.add_note, null);
                final EditText title_edit_text = (EditText) view1.findViewById(R.id.title_edit_Text);
                final EditText note_edit_text = (EditText) view1.findViewById(R.id.note_edit_Text);
                alertDialog.setView(view1);
                final AlertDialog d = alertDialog.create();
                d.show();
                view1.findViewById(R.id.add_button).setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        title_text = title_edit_text.getText().toString();
                        note_text = note_edit_text.getText().toString();
                            if (title_text.isEmpty() || note_text.isEmpty()){
                                Toast.makeText(MainActivity.this, "please enter your title and note to save it", Toast.LENGTH_SHORT).show();
                            }else {
                                String id = databaseReference.push().getKey();
                                note note = new note(id, note_text, title_text, getcurrentdate());
                                databaseReference.child(id).setValue(note);
                                d.dismiss();
                            }
                    }
                });
            }
        });
        note_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                note mynote = (note) note_list_view.getItemAtPosition(i);
                Intent intent = new Intent(MainActivity.this,Show_note_Activity.class);
                intent.putExtra("title_key",mynote.title_text);
                intent.putExtra("note_key",mynote.note_text);
                startActivity(intent);
            }
        });
        note_list_view.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                View view2 = getLayoutInflater().inflate(R.layout.delete_update,null);
                final AlertDialog dialog = alertDialog.create();
                dialog.setView(view2);
                dialog.show();
                final note updata_note = (note) note_list_view.getItemAtPosition(i);
                final EditText title_delete_update = view2.findViewById(R.id.title_delete_update);
                final EditText note_delete_update = view2.findViewById(R.id.note_delete_update);
                title_delete_update.setText(updata_note.title_text);
                note_delete_update.setText(updata_note.note_text);
                Button updata_button = view2.findViewById(R.id.updata_button);
                Button delete_button = view2.findViewById(R.id.delete_button);
                updata_button.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        note note_afterupdata = new note(updata_note.id
                                        ,note_delete_update.getText().toString()
                                        ,title_delete_update.getText().toString()
                                        ,getcurrentdate());

                        databaseReference.child(updata_note.id).setValue(note_afterupdata);
                        dialog.dismiss();
                    }
                });
                delete_button.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        databaseReference.child(updata_note.id).removeValue();
                        dialog.dismiss();
                    }
                });

                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                notelist.clear();
                for (DataSnapshot childsnapshot : dataSnapshot.getChildren()){
                    note getnote = childsnapshot.getValue(note.class);
                    notelist.add(0,getnote);
                }
                NoteAdapter noteAdapter = new NoteAdapter(getApplicationContext(),notelist);
                note_list_view.setAdapter(noteAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public String getcurrentdate(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = simpleDateFormat.format(calendar.getTime());
        return date;
    }
}




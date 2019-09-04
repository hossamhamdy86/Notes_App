package com.example.notes;

import java.io.Serializable;
import java.util.Map;

public class note {

    String id , note_text , title_text ;
    String timestamp;

    public note(){
    }

    public note(String id, String note_text, String title_text, String timestamp) {
        this.id = id;
        this.note_text = note_text;
        this.title_text = title_text;
        this.timestamp = timestamp;
    }

}

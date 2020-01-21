package com.example.testfragment.model;

import java.io.Serializable;
import java.util.Objects;

public class Note implements Serializable {
    private int id;
    private int noteValue;
    private Client rClient;

    public Note() {
    }

    public Note(int id, int noteValue) {
        this.id = id;
        this.noteValue = noteValue;
    }

    public int getNoteValue() {
        return noteValue;
    }

    public void setNoteValue(int noteValue) {
        this.noteValue = noteValue;
    }

    public Client getrClient() {
        return rClient;
    }

    public void setrClient(Client rClient) {
        this.rClient = rClient;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void addClient(Client client){
            setrClient(client);
    }
    public void remove(Client client){
        setrClient(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return id == note.id;
    }


}

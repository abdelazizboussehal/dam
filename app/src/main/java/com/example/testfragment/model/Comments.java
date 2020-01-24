package com.example.testfragment.model;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.Date;

public class Comments implements Serializable {
private int id;
private String content;
private Date creationDate ;
private int isEnabled;
    public Comments() {
    }

    public Comments(String content, Date creationDate, int isEnabled) {
        this.id = id;
        this.content = content;
        this.creationDate = creationDate;
        this.isEnabled = isEnabled;
    }

    public int getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(int isEnabled) {
        this.isEnabled = isEnabled;
    }

    private Client rClient;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }


    public Client getrClient() {
        return rClient;
    }

    public void setrClient(Client rClient) {
        this.rClient = rClient;
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
        Comments comments = (Comments) o;
        return id == comments.id;
    }
    public String tojson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

}

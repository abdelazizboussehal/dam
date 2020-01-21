package com.example.testfragment.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Comment implements Serializable {
private int id;
private String content;
private Date creationDate ;
private int isEnabled;
    public Comment() {
    }

    public Comment(String content, Date creationDate, int isEnabled) {
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
        Comment comment = (Comment) o;
        return id == comment.id;
    }


}

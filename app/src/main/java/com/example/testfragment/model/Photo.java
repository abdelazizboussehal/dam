package com.example.testfragment.model;

import java.util.Date;
import java.util.Objects;

public class Photo {
    private int id;
    private String path;
    private Date createDate;
    public Photo(int id, String path, Date createDate) {
        this.id = id;
        this.path = path;
        this.createDate = createDate;
    }

    public Photo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Photo photo = (Photo) o;
        return id == photo.id;
    }

}

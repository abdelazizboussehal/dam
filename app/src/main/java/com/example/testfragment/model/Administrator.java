package com.example.testfragment.model;

import java.io.Serializable;
import java.util.Date;

public class Administrator extends User implements Serializable {
    private String Email;

    public Administrator() {
        super();
    }

    public Administrator(int id, String lastname, String fisrtName, String userName, String password, Date birthDate, String email) {
        super(id, lastname, fisrtName, userName, password, birthDate);
        Email = email;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}

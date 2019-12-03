package com.example.testfragment;

import androidx.annotation.NonNull;

public class Challenge {
   private int idImage,id,nombreParticipent;
    private  String date ;
    private double distance;
    private String adresse;

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Challenge(int idImage, int id, int nombreParticipent, String date, double distance, String adresse) {
        this.idImage = idImage;
        this.id = id;
        this.nombreParticipent = nombreParticipent;
        this.date = date;
        this.distance = distance;
        this.adresse = adresse;
    }

    public Challenge(int idImage, int id, int nombreParticipent, String date, double distance) {
        this.idImage = idImage;
        this.id = id;
        this.nombreParticipent = nombreParticipent;
        this.date = date;
        this.distance = distance;

    }

    public int getIdImage() {
        return idImage;
    }

    public void setIdImage(int idImage) {
        this.idImage = idImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNombreParticipent() {
        return nombreParticipent;
    }

    public void setNombreParticipent(int nombreParticipent) {
        this.nombreParticipent = nombreParticipent;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @NonNull
    @Override
    public String toString() {
        return adresse+"   Date : "+date+"\n id :"+id+"   nombre des participent : "+nombreParticipent+"   distance "+distance;
    }
}

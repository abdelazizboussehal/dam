package com.example.testfragment.model;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

public class Challengeee implements Serializable {
   private int idImage,id,nombreParticipent;
    private  String date ;
    private double distance;
    private String adresse;
    private double lat ,lon;


    public Challengeee(int id, double lat, double lon) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Challengeee(int idImage, int id, int nombreParticipent, String date, double distance, String adresse) {
        this.idImage = idImage;
        this.id = id;
        this.nombreParticipent = nombreParticipent;
        this.date = date;
        this.distance = distance;
        this.adresse = adresse;
    }

    public Challengeee(int idImage, int id, int nombreParticipent, String date, double distance) {
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
    public static Challengeee getChallengeFromJson(String jsonText){
        Gson gson = new Gson();
        Challengeee challenge = gson.fromJson(jsonText, Challengeee.class);
        return challenge;
    }
    public static List<Challengeee> getChallengesFromJson(String jsonText){
        Gson gson = new Gson();
        Type type = new TypeToken<List<Challengeee>>(){}.getType();
        List<Challengeee> challenges = gson.fromJson(jsonText, type);
        return challenges;
    }
}

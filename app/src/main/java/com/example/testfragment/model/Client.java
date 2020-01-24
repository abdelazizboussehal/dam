package com.example.testfragment.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Client extends User implements Serializable {
    private String phoneNumber,androidVersion;
    private Set<Challenge> rChallenge;

    public Client() {
    }
    public Client(int id) {
        setId(id);
    }

    public Client(int id, String lastname, String fisrtName, String userName, String password, Date birthDate, String phoneNumber, String androidVersion) {
        super(id, lastname, fisrtName, userName, password, birthDate);
        this.phoneNumber = phoneNumber;
        this.androidVersion = androidVersion;
        this.rChallenge =new HashSet<>();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAndroidVersion() {
        return androidVersion;
    }

    public void setAndroidVersion(String androidVersion) {
        this.androidVersion = androidVersion;
    }
    public void addChallenge(Challenge b){
        if(!getrChallenge().contains(b)) {
            if (b.getrClient() != null) {
            b.removeClient();
            b.setrClient(this);
            getrChallenge().add(b);
            }
        }
    }

    public void removeChallenge(Challenge challenge){
        if(getrChallenge().contains(challenge)){
            challenge.setrClient(null);
            getrChallenge().remove(challenge);
        }
    }
    public Set<Challenge> getrChallenge() {
        return rChallenge;
    }

    public void setrChallenge(Set<Challenge> rChallenge) {
        this.rChallenge = rChallenge;
    }
    public static Client getClientFromJson(String jsonText){
        Gson gson = new Gson();
        Client client = gson.fromJson(jsonText, Client.class);
        return client;
    }
    public static List<Client> getClientsFromJson(String jsonText){
        Gson gson = new Gson();
        Type type = new TypeToken<List<Client>>(){}.getType();
        List<Client> clients  = gson.fromJson(jsonText, type);
        return clients;
    }
    public static Client getChallengeFromJson(String jsonText){
        Gson gson = new Gson();
        Client client = gson.fromJson(jsonText, Client.class);
        return client;
    }
    public static List<Client> getChallengesFromJson(String jsonText){
        Gson gson = new Gson();
        Type type = new TypeToken<List<Client>>(){}.getType();
        List<Client> clients = gson.fromJson(jsonText, type);
        return clients;
    }
    public String tojson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}

package com.example.testfragment.model;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Challenge {
    public Challenge(int id) {
        this.id = id;
    }

    private Client rClient;
    private Set<Client> rrClient=new HashSet<>();
    private int id,state;
    private Date createdDate,startingDate,endingDate;
    private Set<Photo> rPhoto=new HashSet<>();
    private Address address;
    private Set<Note> rNote=new HashSet<>();
    private Set<Comment> rComment=new HashSet<>();

    public Set<Client> getRrClient() {
        return rrClient;
    }

    public Challenge(int state, Date createdDate, Date startingDate, Date endingDate, Address address) {

        this.state = state;
        this.createdDate = createdDate;
        this.startingDate = startingDate;
        this.endingDate = endingDate;
        this.address = address;

    }

    public void setRrClient(Set<Client> rrClient) {
        this.rrClient = rrClient;
    }


    public Challenge() {
    }

    public int getParticipantCount(){
        return 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(Date startingDate) {
        this.startingDate = startingDate;
    }

    public Date getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(Date endingDate) {
        this.endingDate = endingDate;
    }

    public void addClient(Client c){
        if(!c.getrChallenge().contains(this)){

            if(getrClient()!=null){
                setrClient(c);
                c.addChallenge(this);
            }

        }

    }

    public void removeClient(){
        if(getrClient()!=null)
        {
            setrClient(null);
        }
    }
    public Client getrClient() {
        return rClient;
    }

    public void setrClient(Client rClient) {
        this.rClient = rClient;
    }

    public void addPhoto(Photo photo){
        if(!getrPhoto().contains(photo))
        {
            getrPhoto().add(photo);
        }
    }
    public void removePhoto(Photo photo)
    {
        if(getrPhoto().contains(photo))
        {
            getrPhoto().remove(photo);
        }
    }

    public Set<Photo> getrPhoto() {
        return rPhoto;
    }

    public void setrPhoto(Set<Photo> rPhoto) {
        this.rPhoto = rPhoto;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void addAddress(Address address){
        setAddress(address);
    }
    public void removeAddress(Address address){
        setAddress(null);
    }

    public Set<Note> getRnote() {
        return rNote;
    }

    public void setRnote(Set<Note> rnote) {
        this.rNote = rnote;
    }

    public Set<Comment> getrComment() {
        return rComment;
    }

    public void setrComment(Set<Comment> rComment) {
        this.rComment = rComment;
    }

    public void addNote(Note note){
        if(!getRnote().contains(note)){
            getRnote().add(note);
        }
    }

    public void removeNote(Note note){
        if(getRnote().contains(note)){
            getRnote().remove(note);
        }
    }

    public void addComment(Comment comment){
        if(!getrComment().contains(comment))
        {
            getrComment().add(comment);
        }
    }

    public void removeComment(Comment comment){
        if(getrComment().contains(comment)){
            getrComment().remove(comment);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Challenge challenge = (Challenge) o;
        return id == challenge.id;
    }



    public Set<Note> getrNote() {
        return rNote;
    }

    public void setrNote(Set<Note> rNote) {
        this.rNote = rNote;
    }

    @NonNull
    @Override
    public String toString() {
        return "aziz";
    }
    public static Challenge getChallengeFromJson(String jsonText){
        Gson gson = new Gson();
        Challenge challenge = gson.fromJson(jsonText, Challenge.class);
        return challenge;
    }
    public static List<Challenge> getChallengesFromJson(String jsonText){
        Gson gson = new Gson();
        Type type = new TypeToken<List<Challenge>>(){}.getType();
        List<Challenge> challenges = gson.fromJson(jsonText, type);
        return challenges;
    }
}

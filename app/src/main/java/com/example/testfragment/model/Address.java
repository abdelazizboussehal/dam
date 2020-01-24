package com.example.testfragment.model;

import java.io.Serializable;
import java.util.Objects;

public class Address implements Serializable
{
    private int id;
    private double longitude, latitude;
    private String street ,city,zipCode,country;

    public Address() {
    }

    public Address(int id, double longitude, double latitude, String street, String city, String zipCode, String country) {
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
        this.country = country;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return id == address.id;
    }

    @Override
    public String toString() {
        return

                "\n la rue ='" + street + '\'' +
                "\n ville='" + city + '\'' +
                "\n code postal='" + zipCode + '\'' +
                "\n paye='" + country + '\''
                ;
    }
}

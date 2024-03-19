package br.com.cdb.java.grupo4.eightbank.model.user.client;

import br.com.cdb.java.grupo4.eightbank.enuns.State;

public class Address {
    private String streetName;
    private long number;
    private String district;
    private String city;
    private State state;  //ENUM
    private long zipCode;

    public Address(String streetName, long number, String district, String city, State state, long zipCode) {
        this.streetName = streetName;
        this.number = number;
        this.district = district;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public long getZipCode() {
        return zipCode;
    }

    public void setZipCode(long zipCode) {
        this.zipCode = zipCode;
    }
}

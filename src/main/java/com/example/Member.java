package com.example;

import javax.persistence.*;

/**
 * Created by bearden-tellez on 10/7/16.
 */


@Entity
@Table(name = "members")

public class Member {
    @Id
    @GeneratedValue
    int id;

    @Column(nullable = false)
    String firstName;

    @Column(nullable = false)
    String lastName;

    @Column(nullable = false, unique = true)
    String email;

    @Column (nullable = false)
    String password;

    @Column (nullable = false)
    String streetAddress;

    @Column
    String photo_URL;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

//    public Member (String email, String firstName, String lastName, String password, String streetAddress){
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.email = email;
//        this.password = password;
//        this.streetAddress = streetAddress;
//
//    }

    public Member(){

    }

    public Member(String firstName, String lastName, String email, String password, String streetAddress, String photo_URL) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.streetAddress = streetAddress;
        this.photo_URL = photo_URL;
    }

    public String getPhoto_URL() {
        return photo_URL;
    }

    public void setPhoto_URL(String photo_URL) {
        this.photo_URL = photo_URL;
    }
}

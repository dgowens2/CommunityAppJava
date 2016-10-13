package com.example;

import javax.persistence.*;

/**
 * Created by bearden-tellez on 10/7/16.
 */


@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue
    int id;

    @Column(nullable = false, unique = true)
    String name;

    @Column(nullable = false)
    String date;

    @Column(nullable = false)
    String location;

    @Column (nullable = false)
    String information;

    @OneToOne
    Member organizer;

    public Event() {
    }

    public Event(String name, String date, String location, String information, Member organizer) {
        this.name = name;
        this.date = date; //user will enter date~time
        this.location = location;
        this.information = information;
       this.organizer = organizer;
    }

    public Event(String name, String date, String location, String information) {
        this.name = name;
        this.date = date; //user will enter date~time
        this.location = location;
        this.information = information;
//        this.organizer = organizer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public Member getOrganizer() {
        return organizer;
    }

    public void setOrganizer(Member organizer) {
        this.organizer = organizer;
    }
}

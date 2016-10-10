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

    @ManyToOne
    Member member;

    @Column(nullable = false)
    String eventName;

    @Column(nullable = false)
    String location;

    @Column(nullable = false)
    String information;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
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

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public void Event(String eventName, String location, String information, Member member){
     this.eventName = eventName;
     this.location = location;
     this.information = information;
     this.member = member;
    }

    public void Event(){

    }
}

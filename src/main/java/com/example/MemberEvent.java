package com.example;

import javax.persistence.*;


@Entity
@Table(name = "member_events")
public class MemberEvent {
    @Id
    @GeneratedValue
    int id;

    @ManyToOne
    Member member;

    @ManyToOne
    Event event;

    public MemberEvent() {
    }

    public MemberEvent(Member member, Event event) {
        this.member = member;
        this.event = event;
    }

    //Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}

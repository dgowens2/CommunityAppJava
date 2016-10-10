package com.example;

import javax.persistence.*;

/**
 * Created by bearden-tellez on 10/7/16.
 */


@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue
    int id;

    @Column(nullable = false)
    String date;

    @Column(nullable = false)
    String title;

    @Column(nullable = false)
    String body;

    @ManyToOne
    Member member;

    public Post() {
    }

    public Post(int id, String date, String title, String body, Member member) {
        this.id = id;
        this.date = date;
        this.title = title;
        this.body = body;
        this.member = member;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}

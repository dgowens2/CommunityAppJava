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
    Member author;

    @OneToOne
    Organization organization;

    public Post() {
    }

//    public Member getAuthor() {
//        return author;
//    }
//
//    public void setAuthor(Member author) {
//        this.author = author;
//    }

    public Post(String date, String title, String body, Member author, Organization organization) {
        this.date = date;
        this.title = title;
        this.body = body;
        this.author = author;
        this.organization = organization;
    }

    public Post(String date, String title, String body) {
        this.date = date;
        this.title = title;
        this.body = body;
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
        return author;
    }

    public void setMember(Member author) {
        this.author = author;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
}

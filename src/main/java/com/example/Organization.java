package com.example;

import javax.persistence.*;

/**
 * Created by bearden-tellez on 10/7/16.
 */


@Entity
@Table(name = "organizations")
public class Organization {
    @Id
    @GeneratedValue
    int id;

    @Column(nullable = false, unique = true)
    String name;

    public Organization() {
    }

    public Organization(String name) {
//        this.id = id;
        this.name = name;
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
}

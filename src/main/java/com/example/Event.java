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

    @Column(nullable = false)
    String eventName;

    @Column(nullable = false)
    String location;

    @Column(nullable = false)
    String information;



}

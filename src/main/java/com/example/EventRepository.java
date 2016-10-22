package com.example;

import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;


public interface EventRepository extends CrudRepository<Event, Integer> {
    Event findByName(String name);
    Event findById(Integer id);
    ArrayList<Event> findByOrganizer(Member organizer);

    ArrayList<Event> findByOrganizerOrderByDateAsc(Member organizer);

    ArrayList<Event> findByOrganization(Organization organization);
    ArrayList<Event> findByOrganizationOrderByDateAsc(Organization organization);
}

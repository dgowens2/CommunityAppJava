package com.example;

import org.springframework.data.repository.CrudRepository;


public interface EventRepository extends CrudRepository<Event, Integer> {

    Event findByName(String name);

}

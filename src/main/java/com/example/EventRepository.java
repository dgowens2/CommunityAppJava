package com.example;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by jessicatracy on 9/29/16.
 */
public interface EventRepository extends CrudRepository<Event, Integer> {

    Event findByName(String name);

}

package com.example;

import org.springframework.data.repository.CrudRepository;


public interface MemberEventRepository extends CrudRepository<MemberEvent, Integer> {
    MemberEvent findByEventId(int eventId);
}

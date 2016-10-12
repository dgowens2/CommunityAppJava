package com.example;

import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;


public interface MemberEventRepository extends CrudRepository<MemberEvent, Integer> {
    MemberEvent findByEventId(int eventId);
    ArrayList<MemberEvent> findMembersByEvent(Event event);

    ArrayList<MemberEvent>findEventsByMember(Member member);
}

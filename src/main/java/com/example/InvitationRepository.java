package com.example;

import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

/**
 * Created by bearden-tellez on 10/7/16.
 */
public interface InvitationRepository extends CrudRepository<Invitation, Integer> {
    ArrayList<Invitation> findByInvitedEmail(String invitedEmail);
}
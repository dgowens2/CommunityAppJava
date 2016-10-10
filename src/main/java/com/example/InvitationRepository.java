package com.example;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by bearden-tellez on 10/7/16.
 */
public interface InvitationRepository extends CrudRepository<Invitation, Integer> {
    Invitation findByInvitedEmail(String invitedEmail);
}
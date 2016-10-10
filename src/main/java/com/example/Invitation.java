package com.example;

import javax.persistence.*;

/**
 * Created by bearden-tellez on 10/7/16.
 */


@Entity
@Table(name = "invitations")
public class Invitation {
    @Id
    @GeneratedValue
    int id;

    @ManyToOne
    Member invitingMember;

    @Column(nullable = false, unique = true)
    String invitedEmail;

    @ManyToOne
    Organization organization;

    public Invitation() {
    }

    public Invitation(int id, Member invitingMember, String invitedEmail, Organization organization) {
        this.id = id;
        this.invitingMember = invitingMember;
        this.invitedEmail = invitedEmail;
        this.organization = organization;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Member getInvitingMember() {
        return invitingMember;
    }

    public void setInvitingMember(Member invitingMember) {
        this.invitingMember = invitingMember;
    }

    public String getInvitedEmail() {
        return invitedEmail;
    }

    public void setInvitedEmail(String invitedEmail) {
        this.invitedEmail = invitedEmail;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

}

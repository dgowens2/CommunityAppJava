package com.example;

import javax.persistence.*;


@Entity
@Table(name = "organization_members")
public class OrganizationMember {
    @Id
    @GeneratedValue
    int id;

    @ManyToOne
    Organization organization;

    @ManyToOne
    Member member;

    public OrganizationMember() {
    }

    public OrganizationMember(int id, Organization organization, Member member) {
        this.id = id;
        this.organization = organization;
        this.member = member;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}

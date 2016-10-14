package com.example;

import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;


public interface OrganizationMemberRepository extends CrudRepository<OrganizationMember, Integer> {
    ArrayList<OrganizationMember> findByMemberId(int memberId);

    ArrayList<OrganizationMember> findByMemberEmail(String email);

    ArrayList<OrganizationMember> findByOrganizationId(int organizationId);

    ArrayList<OrganizationMember> findByOrganizationName(String name);

    ArrayList<OrganizationMember> findMembersByOrganization(Organization organization);
}

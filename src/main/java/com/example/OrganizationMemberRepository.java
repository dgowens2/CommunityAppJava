package com.example;

import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;


public interface OrganizationMemberRepository extends CrudRepository<OrganizationMember, Integer> {
    ArrayList<Organization> findByMember(int memberId);

    ArrayList<Member> findByOrganization (int organizationId);
}

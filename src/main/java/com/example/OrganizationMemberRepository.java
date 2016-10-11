package com.example;

import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;


public interface OrganizationMemberRepository extends CrudRepository<OrganizationMember, Integer> {
    ArrayList<OrganizationMember> findByMemberId(int memberId);

    ArrayList<OrganizationMember> findByOrganization (int organizationId);
}

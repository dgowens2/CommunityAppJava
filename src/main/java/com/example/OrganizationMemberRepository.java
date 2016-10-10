package com.example;

import org.springframework.data.repository.CrudRepository;


public interface OrganizationMemberRepository extends CrudRepository<OrganizationMember, Integer> {
    Organization findByMember(int memberId);

    Member findByOrganization (int organizationId);
}

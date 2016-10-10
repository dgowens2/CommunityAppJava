package com.example;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by jessicatracy on 9/29/16.
 */
public interface OrganizationMemberRepository extends CrudRepository<OrganizationMember, Integer> {
    Organization findByMember(int memberId);

    Member findByOrganization (int organizationId);
}

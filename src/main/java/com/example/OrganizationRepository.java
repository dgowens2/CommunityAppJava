package com.example;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by jessicatracy on 9/29/16.
 */
public interface OrganizationRepository extends CrudRepository<Organization, Integer> {

    Organization findByName(String name);

}
